#version 300 es
precision mediump float;

in vec3 v_Position;
in vec3 v_Normal;

out vec4 outColor;

uniform vec3 u_Ambient;
uniform vec3 u_Diffuse;

void main() {
    vec3 normal = normalize(v_Normal);
    // Light direction from far top right
    vec3 lightDir = normalize(vec3(100.0, 100.0, 100.0));
    
    // Calculate diffuse lighting with linear falloff
    float diff = max(dot(normal, lightDir), 0.0);
    // Apply a more linear lighting curve
    diff = pow(diff, 0.5) * 0.5; // Using power function for more linear falloff
    
    // Combine lighting components
    vec3 ambient = u_Ambient;
    vec3 diffuse = u_Diffuse * diff;
    
    // Final color with balanced lighting
    vec3 result = ambient + diffuse;
    outColor = vec4(result, 1.0);
}
