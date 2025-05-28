package com.navigine.navigine.demo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.navigine.navigine.demo.ui.custom.MTLMaterial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MTLUtil {

    public static MTLMaterial parse(AssetManager assetManager, String path) {
        MTLMaterial mat = new MTLMaterial();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(assetManager.open(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Ka ")) mat.ambient = parseVec3(line);
                else if (line.startsWith("Kd ")) mat.diffuse = parseVec3(line);
                else if (line.startsWith("Ks ")) mat.specular = parseVec3(line);
                else if (line.startsWith("Ns "))
                    mat.shininess = Float.parseFloat(line.split(" ")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mat;
    }

    private static float[] parseVec3(String line) {
        String[] parts = line.trim().split("\\s+");
        return new float[]{
                Float.parseFloat(parts[1]),
                Float.parseFloat(parts[2]),
                Float.parseFloat(parts[3])
        };
    }

}
