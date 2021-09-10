package com.nextplugins.warps.utils;

import com.nextplugins.warps.NextWarps;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public final class MaterialParser {

    public static ItemStack from(String materialName, int damage) {

        if (materialName == null || materialName.equalsIgnoreCase("")) return null;

        try {
            val material = Material.valueOf("LEGACY_" + materialName);
            return new ItemStack(Bukkit.getUnsafe().fromLegacy(new MaterialData(material, (byte) damage)));
        } catch (Exception error) {
            try {
                return new ItemStack(Material.getMaterial(materialName), 1, (short) damage);
            } catch (Exception exception) {
                NextWarps.getInstance().getLogger().warning("Material " + materialName + " is invalid!");
                return null;
            }
        }

    }

}
