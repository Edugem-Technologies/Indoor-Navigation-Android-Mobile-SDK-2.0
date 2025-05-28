#version 300 es
layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec3 a_Normal;

uniform mat4 u_ModelViewProjection;
uniform mat4 u_ModelView;

out vec3 v_Position;
out vec3 v_Normal;

void main() {
    v_Position = vec3(u_ModelView * vec4(a_Position, 1.0));
    v_Normal = normalize(mat3(u_ModelView) * a_Normal);
    gl_Position = u_ModelViewProjection * vec4(a_Position, 1.0);
}
