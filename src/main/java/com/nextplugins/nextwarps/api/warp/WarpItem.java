package com.nextplugins.nextwarps.api.warp;

import com.nextplugins.nextwarps.configuration.MessageValue;
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

        if (player.hasPermission(warp.getPermission())) {
            player.teleport(warp.getLocation());

            player.sendMessage(MessageValue.get(MessageValue::warpTeleport).replace("%warp%", warp.getName()));
        } else {
            player.sendMessage(MessageValue.get(MessageValue::warpNoPermission));
        }
    }
}
