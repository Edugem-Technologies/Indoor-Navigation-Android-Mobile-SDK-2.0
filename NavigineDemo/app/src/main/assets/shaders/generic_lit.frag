#version 300 es
precision mediump float;

in vec3 v_Position;
in vec3 v_Normal;

out vec4 outColor;

uniform vec3 u_LightDirection;  // In view space
uniform vec3 u_LightColor;

uniform vec3 u_Ambient;
uniform vec3 u_Diffuse;
uniform vec3 u_Specular;
uniform float u_Shininess;

void main() {
    vec3 normal = normalize(v_Normal);
    vec3 lightDir = normalize(-u_LightDirection);
    vec3 viewDir = normalize(-v_Position);
    vec3 reflectDir = reflect(-lightDir, normal);

    float diff = max(dot(normal, lightDir), 0.0);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), u_Shininess);

    vec3 ambient = u_Ambient;
    vec3 diffuse = u_Diffuse * diff;
    vec3 specular = u_Specular * spec;

    vec3 result = (ambient + diffuse + specular) * u_LightColor;
    outColor = vec4(result, 1.0);
}
