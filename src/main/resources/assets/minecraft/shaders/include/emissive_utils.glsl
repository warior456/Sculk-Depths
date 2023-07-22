#version 150

// Checking for the exact alpha value breaks things, so I use this function to cut down on space while also making it work better.
bool roughly_equal(float num1, float num2, float threshold) {
    return abs(num1 - num2) <= threshold;
}

float hash(float n) { return fract(sin(n) * 1e4); }
float hash(vec3 p) { return fract(1e4 * sin(17.0 * p.x + p.y * 0.1 + p.z) * (0.1 + abs(sin(p.y * 13.0 + p.x+ p.z)))); }

float noise(vec3 x) {
	vec3 i = floor(x);
	vec3 f = fract(x);

	
	
	float a = hash(i);
	float b = hash(i + vec3(2.0, 0.0, 0.0));
	float c = hash(i + vec3(0.0, 2.0, 2.0));
	float d = hash(i + vec3(2.0, 2.0, 2.0));
	vec3 u = f * f * (3.0 - 2.0 * f);
	return (mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y * u.z);
	
	
	
}


float color_modulation_noise(vec3 pos_pixel, float vertexDistance){
    if(vertexDistance < 28){
    float noise_color = noise(pos_pixel);
    float anti_parkingson = noise_color;
    float mix_noise = anti_parkingson * (2.3);
    return mix_noise;
    }else{
    float noise_color = noise(pos_pixel);
    float anti_parkingson = noise_color;
    float mix_noise = anti_parkingson * 1.3;

    return mix_noise;   
    }
    
}


bool check_alpha(float textureAlpha, float targetAlpha) {
	
	float targetLess = targetAlpha - 0.01;
	float targetMore = targetAlpha + 0.01;
	return (textureAlpha > targetLess && textureAlpha < targetMore);
	
}


// For cases in which you want something to have a lower light level, but still be bright when in light.

vec4 apply_partial_emissivity(vec4 inputColor, vec4 originalLightColor, vec3 minimumLightColor) {
	
	vec4 newLightColor = originalLightColor;
	newLightColor.r = max(originalLightColor.r, minimumLightColor.r);
	newLightColor.g = max(originalLightColor.g, minimumLightColor.g);
	newLightColor.b = max(originalLightColor.b, minimumLightColor.b);
	return inputColor * newLightColor;
	
}


// The meat and bones of the pack, does all the work for making things emissive.

vec4 make_emissive(vec4 inputColor, vec4 lightColor, vec4 maxLightColor, float vertexDistance, float inputAlpha, vec3 pos, float dim) {
	
	if (vertexDistance > 800) return inputColor; // Vertex Distance > 800 generally means an object is in the UI, which we don't want to affect.
	
	if (check_alpha(inputAlpha, 110.0)) return inputColor*1.3; // Checks for alpha 252 and just returns the input color if it is. Used in the example pack for redstone ore and the zombie's eyes.
	else if (check_alpha(inputAlpha, 250.0)) return inputColor; // You can copy & this line and change the function to add a new emissive type. Used in the example pack for lime concrete. 
	else if (check_alpha(inputAlpha, 120.0)) return inputColor*1.37; // You can copy & this line and change the function to add a new emissive type. Used in the example pack for lime concrete. 
	else if (check_alpha(inputAlpha, 106.0)) return inputColor * color_modulation_noise(pos, vertexDistance); // You can copy & this line and change the function to add a new emissive type. Used in the example pack for lime concrete. 

	
	else return inputColor * lightColor; // If none of the pixels are supposed to be emissive, then it adds the light.
	
}


// Gets the dimension that an object is in, -1 for The Nether, 0 for The Overworld, 1 for The End.

float get_dimension(vec4 minLightColor) {
	
	if (minLightColor.r == minLightColor.g && minLightColor.g == minLightColor.b) return 0.0; // Shadows are grayscale in The Overworld
	else if (minLightColor.r > minLightColor.g) return -1.0; // Shadows are more red in The Nether
	else return 1.0; // Shadows are slightly green in The End
	
}


// Gets the face lighting of a block. Credits to Venaxsys for the original function.

vec4 get_face_lighting(vec3 normal, float dimension) { 
	
	vec4 faceLighting = vec4(1.0, 1.0, 1.0, 1.0);
	vec3 absNormal = abs(normal);
	float top = 229.0 / 255.0;
	float bottom = 127.0 / 255.0;
	float east = 153.0 / 255.0;
	float north = 204.0 / 255.0;
	
	// Top (only required in the Nether)
	if (normal.y > normal.z && normal.y > normal.x && check_alpha(dimension, -1.0)) faceLighting = vec4(top, top, top, 1.0); // It's not really checking the alpha but I'm too stubborn to change the function name
	
	// Bottom
	if (normal.y < normal.z && normal.y < normal.x && !check_alpha(dimension, -1.0)) faceLighting = vec4(bottom, bottom, bottom, 1.0);
	else if (normal.y < normal.z && normal.y < normal.x && check_alpha(dimension, -1.0)) faceLighting = vec4(top, top, top, 1.0);

	// East-West
	if (absNormal.x > absNormal.z && absNormal.x > absNormal.y) faceLighting = vec4(east, east, east, 1.0);

	// North-South
	if (absNormal.z > absNormal.x && absNormal.z > absNormal.y) faceLighting = vec4(north, north, north, 1.0);

	return faceLighting;
}


// Checks the alpha and removes face lighting if required.

vec4 face_lighting_check(vec3 normal, float inputAlpha, float dimension) {

	if (check_alpha(inputAlpha, 250.0)) return get_face_lighting(normal, dimension); // Checks for alpha 250, and runs it through the remove_face_lighting() function if it is. Used in the example pack for lime concrete.
	else return vec4(1.0, 1.0, 1.0, 1.0); // If the block doesn't need to have its face lighting removed, returns 1.0 so nothing gets divided.
	
}


// Makes sure transparent things don't become solid and vice versa.

float remap_alpha(float inputAlpha) {
	
	if (check_alpha(inputAlpha, 252.0)) return 255.0; // Checks for alpha 252 and converts all pixels of that to alpha 255. Used in the example pack for redstone ore and the zombie's eyes.
	else if (check_alpha(inputAlpha, 251.0)) return 190.0; // You can copy & paste this line and change the values to make any transparent block work with this pack. Used in the example pack for ice.
	else if (check_alpha(inputAlpha, 250.0)) return 255.0; // Used in the example pack for lime concrete.
	
	else return inputAlpha; // If a pixel doesn't need to have its alpha changed then it simply does not change.
	
}

//alpha thing

vec2 alpha_hub(float alpha, float dimension){
	if(alpha == 132 || alpha == 133 || alpha == 134){
		return vec2(dimension , alpha - 132); // return 0, 2 in overworld, 1,1 in nether, -1,0 in end
	}

}

