package com.nextplugins.nextwarps.model;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Data
public class WarpItem {

    private final ItemStack icon;
    private final int slot;
    private final Warp warp;

    public void handleClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        event.getWhoClicked().teleport(warp.getLocation());

    }
}
