package com.nextplugins.nextwarps.utils;

import org.bukkit.Material;

public final class MaterialParser {

    public static Material from(String text) {
        Material material = Material.getMaterial(text);

        if (material == null) material = Material.getMaterial("LEGACY_" + text);

        return material;
    }

}
