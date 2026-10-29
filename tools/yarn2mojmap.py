"""
Yarn -> Mojang (official) source remapper for Sculk Depths.

Why: porting Fabric (Yarn) sources to NeoForge (official mappings) means renaming
thousands of Minecraft symbols. Doing that by hand is error-prone and slow, so this
script builds the yarn->official tables from three public mapping artifacts and
rewrites the sources automatically.

Pipeline
  1. intermediary (obf -> intermediary): class + member names
  2. yarn            (intermediary -> named)
  3. Mojang client.txt (obf -> official)

Joining is done on (class, member name, kind, descriptor) with descriptors
normalised to the intermediary namespace, so overloads and synthetic members
(lambdas) never collide. Only unambiguous names are rewritten; anything
ambiguous is reported for manual follow-up.

The rewrite is deliberately conservative:
  * identifiers inside strings / chars / comments are never touched
  * package and import statements are never token-rewritten (only the import
    itself is remapped to the official FQN)
  * one pass, longest-match-first, so renames cannot cascade

Usage:  python tools/yarn2mojmap.py [--dry-run] [--report]
"""

from __future__ import annotations

import argparse
import collections
import json
import os
import re
import sys
import urllib.request
import zipfile

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CACHE = os.path.join(ROOT, "tools", "mapping-cache")

MC_VERSION = "1.21.1"
YARN_BUILD = "1.21.1+build.3"

MOJANG_CLIENT_TXT = (
    "https://piston-data.mojang.com/v1/objects/"
    "2244b6f072256667bcd9a73df124d6c58de77992/client.txt"
)
YARN_JAR = f"https://maven.fabricmc.net/net/fabricmc/yarn/{YARN_BUILD}/yarn-{YARN_BUILD}-v2.jar"
INTERMEDIARY_JAR = (
    f"https://maven.fabricmc.net/net/fabricmc/intermediary/{MC_VERSION}/"
    f"intermediary-{MC_VERSION}-v2.jar"
)

KEYWORDS = {
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
    "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
    "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
    "interface", "long", "native", "new", "package", "private", "protected", "public",
    "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
    "throw", "throws", "transient", "try", "void", "volatile", "while", "true", "false",
    "null", "record", "sealed", "permits", "var", "yield",
}

IDENT = re.compile(r"^[A-Za-z_][A-Za-z0-9_]*$")
DESC_CLASS = re.compile(r"L([\w/$]+);")


def download(url: str, name: str) -> str:
    os.makedirs(CACHE, exist_ok=True)
    path = os.path.join(CACHE, name)
    if os.path.exists(path) and os.path.getsize(path) > 0:
        return path
    print(f"  downloading {name} ...", flush=True)
    urllib.request.urlretrieve(url, path)
    return path


def read_jar_entry(jar: str, entry: str) -> str:
    with zipfile.ZipFile(jar) as zf:
        return zf.read(entry).decode("utf-8")


def translate_desc(desc: str, class_map: dict[str, str]) -> str:
    return DESC_CLASS.sub(
        lambda m: "L" + class_map.get(m.group(1), m.group(1)) + ";", desc
    )


# ------------------------------------------------------------------ tiny v2 parsing
def parse_tiny(text: str):
    """class_map first->second; member_map (class, name, kind, desc)->second name."""
    class_map: dict[str, str] = {}
    member_map: dict[tuple, str] = {}
    current = None
    for line in text.splitlines():
        if not line or line.startswith("tiny"):
            continue
        parts = line.split("\t")
        if parts[0] == "c":
            current = parts[1]
            class_map[parts[1]] = parts[2]
        elif parts[0] == "" and len(parts) >= 5 and parts[1] in ("m", "f"):
            member_map[(current, parts[3], parts[1], parts[2])] = parts[4]
    return class_map, member_map


# --------------------------------------------------------------- Mojang client.txt
CLS_RE = re.compile(r"^(\S+) -> (\S+):$")
# member lines: "    12:13:void process(com.foo.Bar,float) -> a" but abstract/interface
# members have no line-number prefix: "    com.foo.Bar thing() -> b"
MEM_RE = re.compile(
    r"^\s+(?:\d+:\d+:)?(?P<ret>\S+)\s+(?P<name>[\w$<>]+)"
    r"\((?P<args>[^)]*)\)\s*->\s*(?P<obf>[\w$]+)\s*$"
)
# field lines have no line-number prefix: "    org.slf4j.Logger LOGGER -> b"
FLD_RE = re.compile(r"^\s+(?:\d+:\d+:)?.*?\s([\w$]+)\s*->\s*([\w$]+)\s*$")

PRIMITIVES = {
    "void": "V", "boolean": "Z", "byte": "B", "char": "C", "short": "S",
    "int": "I", "long": "J", "float": "F", "double": "D",
}


def strip_generics(type_name: str) -> str:
    out, depth = [], 0
    for ch in type_name:
        if ch == "<":
            depth += 1
        elif ch == ">":
            depth = max(0, depth - 1)
        elif depth == 0:
            out.append(ch)
    return "".join(out)


def java_type_to_desc(type_name: str, official_to_obf: dict[str, str]) -> str:
    t = strip_generics(type_name.strip())
    dims = 0
    while t.endswith("[]"):
        dims += 1
        t = t[:-2].strip()
    if t.endswith("..."):
        dims += 1
        t = t[:-3].strip()
    if t in PRIMITIVES:
        return "[" * dims + PRIMITIVES[t]
    obf = official_to_obf.get(t, t).replace(".", "/")
    return "[" * dims + f"L{obf};"


def parse_mojang(text: str):
    """Returns (class_map obf->official, member_map (obfClass, obfName, kind, obfDesc)).

    Descriptors are reconstructed from the human-readable signature on the left of
    the arrow, in the *obf* namespace (matching the intermediary file), which makes
    the join key unique per overload.
    """
    class_map: dict[str, str] = {}
    for line in text.splitlines():
        m = CLS_RE.match(line)
        if m:
            class_map[m.group(2)] = m.group(1)
    official_to_obf = {v: k for k, v in class_map.items()}

    members: dict[tuple, str] = {}
    current = None
    for line in text.splitlines():
        m = CLS_RE.match(line)
        if m:
            current = m.group(2)
            continue
        if current is None:
            continue
        m = MEM_RE.match(line)
        if m:
            desc = "(" + "".join(
                java_type_to_desc(a, official_to_obf)
                for a in m.group("args").split(",")
                if a.strip()
            ) + ")" + java_type_to_desc(m.group("ret"), official_to_obf)
            members[(current, m.group("obf"), "m", desc)] = m.group("name")
            continue
        if "(" in line:
            continue
        m = FLD_RE.match(line)
        if m:
            members[(current, m.group(2), "f", None)] = m.group(1)
    return class_map, members


# ------------------------------------------------------------------- build tables
def build() -> dict:
    print("Resolving mappings ...")
    moj_path = download(MOJANG_CLIENT_TXT, "mojang-client.txt")
    yarn_path = download(YARN_JAR, "yarn-v2.jar")
    interm_path = download(INTERMEDIARY_JAR, "intermediary-v2.jar")

    with open(moj_path, "r", encoding="utf-8") as fh:
        moj_class, moj_member = parse_mojang(fh.read())
    yarn_class, yarn_member = parse_tiny(
        read_jar_entry(yarn_path, "mappings/mappings.tiny")
    )
    interm_class, interm_member = parse_tiny(
        read_jar_entry(interm_path, "mappings/mappings.tiny")
    )
    print(f"  mojang classes={len(moj_class)} members={len(moj_member)}")
    print(f"  yarn classes={len(yarn_class)} members={len(yarn_member)}")
    print(f"  intermediary classes={len(interm_class)} members={len(interm_member)}")

    # class map: yarn(named) -> official (dotted, matching source imports)
    named_to_official_class: dict[str, str] = {}
    for obf, inter in interm_class.items():
        named = yarn_class.get(inter)
        official = moj_class.get(obf)
        if named and official:
            named_to_official_class[named.replace("/", ".")] = official.replace("/", ".")

    method_votes = collections.defaultdict(collections.Counter)
    field_votes = collections.defaultdict(collections.Counter)
    for _cls, members in group_by_class(interm_member).items():
        for (obf_class, obf_name, kind, obf_desc), inter_name in members.items():
            inter_class = interm_class.get(obf_class, "")
            # descriptor arrives in the obf namespace -> translate to intermediary
            inter_desc = translate_desc(obf_desc, interm_class)
            named_name = yarn_member.get((inter_class, inter_name, kind, inter_desc))
            if not named_name:
                continue
            moj_key = (obf_class, obf_name, kind, obf_desc if kind == "m" else None)
            official_name = moj_member.get(moj_key)
            if not official_name or named_name == official_name:
                continue
            if "$" in official_name or not IDENT.match(official_name):
                continue  # synthetic (lambda$...) / invalid identifier
            target = method_votes if kind == "m" else field_votes
            target[named_name][official_name] += 1

    # Only rewrite names that map to exactly one official name. Ambiguous yarn names
    # (e.g. getWorld -> level()/getLevel()/getServer()) are left alone: a wrong guess
    # would still compile in some cases, which is far worse than a compile error.
    method_map = {k: c.most_common(1)[0][0] for k, c in method_votes.items() if len(c) == 1}
    field_map = {k: c.most_common(1)[0][0] for k, c in field_votes.items() if len(c) == 1}
    ambiguous = {
        "methods": sorted(k for k, c in method_votes.items() if len(c) > 1),
        "fields": sorted(k for k, c in field_votes.items() if len(c) > 1),
    }
    print(
        f"  => class renames={len(named_to_official_class)} "
        f"method renames={len(method_map)} field renames={len(field_map)} "
        f"(ambiguous: {len(ambiguous['methods'])} methods, "
        f"{len(ambiguous['fields'])} fields)"
    )
    return {
        "classes": named_to_official_class,
        "methods": method_map,
        "fields": field_map,
        "ambiguous": ambiguous,
    }


def group_by_class(member_map: dict) -> dict[str, dict]:
    out: dict[str, dict] = collections.defaultdict(dict)
    for (cls, name, kind, desc), value in member_map.items():
        out[cls][(cls, name, kind, desc)] = value
    return out


# --------------------------------------------------------------- java text scanner
TOKEN_RE = re.compile(r"[A-Za-z_$][A-Za-z0-9_$]*")


def code_spans(text: str):
    """(start, end) spans of actual Java code (not literals/comments)."""
    i, n = 0, len(text)
    start = 0
    while i < n:
        ch = text[i]
        if ch == "/" and i + 1 < n and text[i + 1] == "/":
            yield (start, i)
            j = text.find("\n", i)
            i = n if j == -1 else j
            start = i
        elif ch == "/" and i + 1 < n and text[i + 1] == "*":
            yield (start, i)
            j = text.find("*/", i + 2)
            i = n if j == -1 else j + 2
            start = i
        elif ch == '"' and text.startswith('"""', i):
            yield (start, i)
            j = text.find('"""', i + 3)
            i = n if j == -1 else j + 3
            start = i
        elif ch in "\"'":
            yield (start, i)
            quote = ch
            i += 1
            while i < n:
                if text[i] == "\\":
                    i += 2
                    continue
                if text[i] == quote or text[i] == "\n":
                    i += 1
                    break
                i += 1
            start = i
        else:
            i += 1
    yield (start, n)


IMPORT_RE = re.compile(
    r"^[ \t]*import[ \t]+(static[ \t]+)?([\w.]+?)(\.\*)?[ \t]*;[ \t]*$", re.MULTILINE
)
PACKAGE_RE = re.compile(r"^[ \t]*package[ \t]+[\w.]+[ \t]*;", re.MULTILINE)


def remap_file(path: str, tables: dict) -> tuple[str, list[str]]:
    with open(path, "r", encoding="utf-8") as fh:
        text = fh.read()
    original = text
    notes: list[str] = []
    classes = tables["classes"]

    # ---- 1. imports -----------------------------------------------------------
    imported: dict[str, str] = {}  # yarn simple -> official simple
    for m in list(IMPORT_RE.finditer(text)):
        whole, static, fqn, star = m.group(0), m.group(1), m.group(2), m.group(3)
        if star:
            if fqn.startswith("net.minecraft"):
                notes.append(f"wildcard import needs manual mapping: {fqn}.*")
            continue
        official = classes.get(fqn)
        if official is None:
            continue
        new_fqn = official.replace("/", ".")
        if static:
            text = text.replace(whole, f"import {static}{new_fqn};", 1)
            continue
        text = text.replace(whole, f"import {new_fqn};", 1)
        imported[fqn.rpartition(".")[2]] = new_fqn.rpartition(".")[2]

    # ---- 2. rewrite rules -----------------------------------------------------
    rules: dict[str, str] = {}

    for yarn_simple, off_simple in imported.items():
        for fqn, off_fqn in classes.items():
            if "$" in fqn and fqn.split("$")[0].rpartition(".")[2] == yarn_simple:
                yarn_inner = fqn.split("$", 1)[1].replace("$", ".")
                off_inner = off_fqn.split("$", 1)[1].replace("$", ".")
                rules[f"{yarn_simple}.{yarn_inner}"] = f"{off_simple}.{off_inner}"
        if yarn_simple != off_simple:
            rules[yarn_simple] = off_simple

    for name, replacement in tables["methods"].items():
        if name not in KEYWORDS and name not in rules:
            rules[name] = replacement
    for name, replacement in tables["fields"].items():
        if name not in KEYWORDS and name not in rules:
            rules[name] = replacement

    # ---- 3. protected regions (package + imports) -----------------------------
    protected: list[tuple[int, int]] = [
        (m.start(), m.end()) for m in PACKAGE_RE.finditer(text)
    ] + [(m.start(), m.end()) for m in IMPORT_RE.finditer(text)]

    def apply_on_code(source: str, fn) -> str:
        out, last = [], 0
        for span_start, span_end in code_spans(source):
            out.append(source[last:span_start])
            keep = source[span_start:span_end]
            # split around protected regions
            pieces, cursor = [], span_start
            for p_start, p_end in protected:
                if p_end <= span_start or p_start >= span_end:
                    continue
                if p_start > cursor:
                    pieces.append((source[cursor:p_start], True))
                pieces.append((source[max(p_start, cursor):min(p_end, span_end)], False))
                cursor = min(p_end, span_end)
            if cursor < span_end:
                pieces.append((source[cursor:span_end], True))
            if not pieces:
                pieces = [(keep, True)]
            for piece, rewrite in pieces:
                out.append(fn(piece) if rewrite else piece)
            last = span_end
        out.append(source[last:])
        return "".join(out)

    composite = {k: v for k, v in rules.items() if "." in k}
    simple = {k: v for k, v in rules.items() if "." not in k}

    if composite:
        ordered_c = sorted(composite.items(), key=lambda kv: -len(kv[0]))

        def do_composite(chunk: str) -> str:
            for old, new in ordered_c:
                chunk = re.sub(
                    r"\b" + re.escape(old).replace(r"\.", r"\s*\.\s*") + r"\b", new, chunk
                )
            return chunk

        text = apply_on_code(text, do_composite)

    if simple:
        def do_simple(chunk: str) -> str:
            res, i = [], 0
            while i < len(chunk):
                m = TOKEN_RE.match(chunk, i)
                if not m:
                    res.append(chunk[i])
                    i += 1
                    continue
                res.append(simple.get(m.group(0), m.group(0)))
                i = m.end()
            return "".join(res)

        text = apply_on_code(text, do_simple)

    # ---- 4. ResourceLocation construction helper ------------------------------
    text = re.sub(
        r"\bnew\s+ResourceLocation\s*\(\s*([^,()]+?)\s*,\s*([^,()]+?)\s*\)",
        r"ResourceLocation.fromNamespaceAndPath(\1, \2)",
        text,
    )
    text = re.sub(
        r"\bnew\s+ResourceLocation\s*\(\s*([^(),]+?)\s*\)",
        r"ResourceLocation.parse(\1)",
        text,
    )
    text = re.sub(
        r"\bResourceLocation\.of\s*\(", "ResourceLocation.fromNamespaceAndPath(", text
    )

    if text != original:
        with open(path, "w", encoding="utf-8", newline="") as fh:
            fh.write(text)
    return text, notes


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--dry-run", action="store_true")
    ap.add_argument("--report", action="store_true")
    args = ap.parse_args()

    tables = build()
    with open(os.path.join(CACHE, "tables.json"), "w") as fh:
        json.dump(tables, fh, indent=1)

    if args.dry_run:
        print("dry run: not touching sources")
        if args.report:
            print("ambiguous methods:", ", ".join(tables["ambiguous"]["methods"][:80]))
            print("ambiguous fields:", ", ".join(tables["ambiguous"]["fields"][:80]))
        return 0

    changed = 0
    all_notes: list[str] = []
    for base, _dirs, files in os.walk(os.path.join(ROOT, "src")):
        for name in files:
            if not name.endswith(".java"):
                continue
            path = os.path.join(base, name)
            _, notes = remap_file(path, tables)
            changed += 1
            all_notes.extend(f"{os.path.relpath(path, ROOT)}: {n}" for n in notes)

    print(f"remapped {changed} java files")
    for note in all_notes:
        print("  " + note)
    return 0


if __name__ == "__main__":
    sys.exit(main())
