package com.navigine.navigine.demo.common.samplerender.arcore;

import android.opengl.Matrix;

import com.navigine.navigine.demo.common.samplerender.Framebuffer;
import com.navigine.navigine.demo.common.samplerender.Mesh;
import com.navigine.navigine.demo.common.samplerender.SampleRender;
import com.navigine.navigine.demo.common.samplerender.Shader;
import com.navigine.navigine.demo.ui.custom.MTLMaterial;
import com.navigine.navigine.demo.utils.MTLUtil;

import java.io.IOException;

public class ArrowRenderer {
    private Mesh arrowModel;
    private Shader shader;

    private float rotationAngle = 0.0f; // Initial rotation angle


    public ArrowRenderer(SampleRender render) {
        try {
            arrowModel = Mesh.createFromAsset(render, "models/arrow_model.obj");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            shader = Shader.createFromAssets(
                    render,
                    "shaders/generic_lit.vert",
                    "shaders/generic_lit.frag",
                    null
            );

            MTLMaterial material = MTLUtil.parse(
                    render.getAssets(),
                    "models/arrow_materials.mtl"
            );

            shader.setVec3("u_Ambient", material.ambient);
            shader.setVec3("u_Diffuse", material.diffuse);
            shader.setVec3("u_Specular", material.specular);
            shader.setFloat("u_Shininess", material.shininess);

            // Static white light
            shader.setVec3("u_LightColor", new float[]{1f, 1f, 1f});
            // Light coming from camera direction
            shader.setVec3("u_LightDirection", new float[]{1f, 1f, -3f});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(SampleRender render, float[] projMatrix, Framebuffer framebuffer) {
        float[] modelMatrix = new float[16];
        float[] mvMatrix = new float[16];
        float[] mvpMatrix = new float[16];
        float[] identityViewMatrix = new float[16];
        float[] scaleMatrix = new float[16];
        float[] rotationMatrix = new float[16];

        Matrix.setIdentityM(identityViewMatrix, 0);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(scaleMatrix, 0);
        Matrix.setIdentityM(rotationMatrix, 0);

        // Place it 1 meter in front of camera (screen center)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.0f);

        // Apply scaling
        Matrix.scaleM(scaleMatrix, 0, 0.3f, 0.3f, 0.3f);
        Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, scaleMatrix, 0);

        // Optional rotation (e.g., spin around Y axis)
        rotationAngle += 1.0f; // degrees per frame
        Matrix.rotateM(rotationMatrix, 0, rotationAngle, 0, 1, 0);
        Matrix.rotateM(rotationMatrix, 0, -135, 1, 0, 0);
        Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, rotationMatrix, 0);

        // Use identity as view matrix (camera-relative drawing)
        Matrix.multiplyMM(mvMatrix, 0, identityViewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projMatrix, 0, mvMatrix, 0);

        shader.setMat4("u_ModelViewProjection", mvpMatrix);
        shader.setMat4("u_ModelView", mvMatrix);

        render.draw(arrowModel, shader, framebuffer);
    }
}
