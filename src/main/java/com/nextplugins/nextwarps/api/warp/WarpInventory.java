package com.nextplugins.nextwarps.api.warp;

import com.github.eikefab.libs.minecraft.InventoryBuilder;
import com.github.eikefab.libs.minecraft.InventoryRegistry;
import com.github.eikefab.libs.minecraft.inventory.CustomInventory;
import com.nextplugins.nextwarps.NextWarps;
import com.nextplugins.nextwarps.api.warp.adapter.WarpItemAdapter;
import com.nextplugins.nextwarps.configuration.GeneralValue;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

public final class WarpInventory {

    private static final NextWarps PLUGIN = NextWarps.getInstance();
    private static final WarpItemAdapter ITEM_ADAPTER = PLUGIN.getWarpItemAdapter();
    private static final InventoryRegistry INVENTORY_REGISTRY = PLUGIN.getInventoryRegistry();

    private static final String MENU_NAME = GeneralValue.get(GeneralValue::menuName);
    private static final int MENU_SIZE = GeneralValue.get(GeneralValue::menuSize);

    private static final CustomInventory INVENTORY;

    static {
        INVENTORY = INVENTORY_REGISTRY.create(InventoryBuilder.newBuilder().title(MENU_NAME).size(MENU_SIZE));

        for (WarpItem item : ITEM_ADAPTER.getWarpItems()) {
            INVENTORY.set(item.getSlot() - 1, item.getIcon()).thenClick(item::handleClick);
        }
    }

    public static Inventory asBukkit() {
        return INVENTORY.getBukkitInventory();
    }

}
