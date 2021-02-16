package com.nextplugins.nextwarps.commands;

import com.github.eikefab.libs.minecraft.InventoryBuilder;
import com.github.eikefab.libs.minecraft.inventory.CustomInventory;
import com.nextplugins.nextwarps.NextWarps;
import com.nextplugins.nextwarps.api.NextWarpAPI;
import com.nextplugins.nextwarps.configuration.GeneralValue;
import com.nextplugins.nextwarps.configuration.MessageValue;
import com.nextplugins.nextwarps.api.warp.Warp;
import com.nextplugins.nextwarps.api.warp.WarpItem;
import com.nextplugins.nextwarps.utils.Locations;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Optional;

public final class WarpCommand {

    private static final NextWarps plugin = NextWarps.getInstance();
    private static final NextWarpAPI api = NextWarpAPI.getInstance();
    private static final FileConfiguration file = plugin.getWarpConfiguration();
    private static final CustomInventory inventory;

    static {
        inventory = plugin.getInventoryRegistry().create(
                InventoryBuilder.newBuilder()
                        .title(GeneralValue.get(GeneralValue::menuName))
                        .size(GeneralValue.get(GeneralValue::menuSize))
        );

        for (WarpItem item : plugin.getWarpItemAdapter().getWarpItems()) {
            inventory.set(item.getSlot(), item.getIcon()).thenClick((clickEvent) -> {
                final Player clicker = (Player) clickEvent.getWhoClicked();

                clicker.teleport(item.getWarp().getLocation());

                if (clicker.hasPermission(item.getWarp().getPermission())) {
                    clicker.sendMessage(MessageValue.get(MessageValue::warpTeleport)
                            .replace("%warp%", item.getWarp().getName()));
                } else {
                    clicker.sendMessage(MessageValue.get(MessageValue::warpNoPermission));
                }
            });
        }
    }

    @Command(
            name = "warp",
            aliases = "warps",
            permission = "nextwarps.use",
            target = CommandTarget.PLAYER
    )
    public void warp(Context<Player> context, @me.saiintbrisson.minecraft.command.annotation.Optional String targetWarp) {
        final Player player = context.getSender();

        if (targetWarp == null) player.openInventory(inventory.getBukkitInventory());
        else {
            Optional<Warp> warpFound = api.findWarpByName(targetWarp);

            if (warpFound.isPresent()) {
                final Warp warp = warpFound.get();

                if (player.hasPermission(warp.getPermission())) {
                    player.teleport(warpFound.get().getLocation());
                    player.sendMessage(MessageValue.get(MessageValue::warpTeleport).replace("%warp%", targetWarp));
                } else {
                    player.openInventory(inventory.getBukkitInventory());
                }
            } else {
                player.openInventory(inventory.getBukkitInventory());
            }
        }
    }

    @Command(
            name = "setwarp",
            aliases = "setarwarp",
            permission = "nextwarps.admin",
            target = CommandTarget.PLAYER,
            usage = "setwarp <warp> [permissão]"
    )
    public void setWarp(Context<Player> context, String warp, @me.saiintbrisson.minecraft.command.annotation.Optional(def = "nextwarps.use") String permission) {
        final Player player = context.getSender();

        final Optional<Warp> warpFound = api.findWarpByName(warp);

        if (warpFound.isPresent()) {
            player.sendMessage(MessageValue.get(MessageValue::warpAlreadyExists));

            return;
        }

        api.allWarps().add(new Warp(warp, permission, player.getLocation()));

        final String path = "warps." + warp;

        file.set(path + ".name", warp);
        file.set(path + ".permission", permission);
        file.set(path + ".location", Locations.serialize(player.getLocation(), true));

        try {
            file.save(new File(plugin.getDataFolder(), "warps.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.sendMessage(MessageValue.get(MessageValue::warpSet).replace("%warp%", warp));
    }

    @Command(
            name = "delwarp",
            aliases = {"deletewarp", "deletarwarp"},
            permission = "nextwarps.admin",
            target = CommandTarget.PLAYER,
            usage = "delwarp <warp>"
    )
    public void delWarp(Context<Player> context, String warp) {
        final Player player = context.getSender();

        final Optional<Warp> warpByName = api.findWarpByName(warp);

        if (warpByName.isPresent()) {
            final Warp warpFound = warpByName.get();
            api.allWarps().remove(warpFound);

            file.set("warps." + warpFound.getName(), null);

            try {
                file.save(new File(plugin.getDataFolder(), "warps.yml"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.sendMessage(MessageValue.get(MessageValue::warpDel).replace("%warp%", warp));
        } else {
            player.sendMessage(MessageValue.get(MessageValue::warpNotFound));
        }
    }

}
