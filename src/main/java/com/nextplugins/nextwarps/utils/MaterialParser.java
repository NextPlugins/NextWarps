package com.nextplugins.nextwarps.utils;

import org.bukkit.Material;

public final class MaterialParser {

    public static Material from(String text) {
        if (isInteger(text)) {
            return Material.getMaterial(Integer.parseInt(text));
        } else {
            return Material.getMaterial(text);
        }
    }

    private static boolean isInteger(Object obj) {
        try {
            Integer.parseInt(obj.toString());

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
