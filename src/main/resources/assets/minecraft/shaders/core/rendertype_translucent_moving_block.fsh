#version 150

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor;
    fragColor = color * ColorModulator;
	
	// Emissive
	if (color.a > 0.996 && color.a < 0.999) { //254
		fragColor = texture(Sampler0, texCoord0) * ColorModulator;
	}
}
