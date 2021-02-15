package com.nextplugins.nextwarps.menu;

import com.github.eikefab.libs.minecraft.InventoryBuilder;
import com.github.eikefab.libs.minecraft.inventory.CustomInventory;
import com.nextplugins.nextwarps.NextWarps;
import com.nextplugins.nextwarps.config.GeneralValue;
import com.nextplugins.nextwarps.config.MessageValue;
import com.nextplugins.nextwarps.model.WarpItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WarpMenu {

    private static final NextWarps plugin = NextWarps.getInstance();

    @Getter private static final CustomInventory warpInventory = plugin.getInventoryRegistry().create(
            InventoryBuilder.newBuilder()
                            .title(GeneralValue.get(GeneralValue::menuName))
                            .size(GeneralValue.get(GeneralValue::menuSize)));

    static {
       for (WarpItem item : plugin.getWarpItemAdapter().getWarpItems()) {
           warpInventory.set(item.getSlot(), item.getIcon()).thenClick((clickEvent) -> {
               final Player player = (Player) clickEvent.getWhoClicked();

               player.teleport(item.getWarp().getLocation());
               player.sendMessage(MessageValue.get(MessageValue::warpTeleport));
           });
       }
    }

}
