package com.nextplugins.nextwarps.commands;

import com.nextplugins.nextwarps.api.NextWarpAPI;
import com.nextplugins.nextwarps.api.warp.WarpFile;
import com.nextplugins.nextwarps.api.warp.WarpInventory;
import com.nextplugins.nextwarps.configuration.MessageValue;
import com.nextplugins.nextwarps.api.warp.Warp;
import lombok.Getter;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Getter
public final class WarpCommand {

    private final WarpFile warpFile;
    private final NextWarpAPI api;

    public WarpCommand(WarpFile file) {
        this.warpFile = file;
        this.api = NextWarpAPI.getInstance();
    }

    @Command(
            name = "warp",
            aliases = "warps",
            permission = "nextwarps.use",
            target = CommandTarget.PLAYER
    )
    public void warp(Context<Player> context, @Optional String targetWarp) {
        final Player player = context.getSender();
        final Inventory inventory = WarpInventory.asBukkit();

        if (targetWarp == null) player.openInventory(inventory);
        else {
            java.util.Optional<Warp> warpSearcher = api.findWarpByName(targetWarp);

            if (warpSearcher.isPresent()) {
                final Warp warp = warpSearcher.get();

                if (player.hasPermission(warp.getPermission())) {
                    player.teleport(warp.getLocation());

                    player.sendMessage(MessageValue.get(MessageValue::warpTeleport).replace("%warp%", targetWarp));
                }

                return;
            }

            player.sendMessage(MessageValue.get(MessageValue::warpNotFound));
        }
    }

    @Command(
            name = "setwarp",
            aliases = "setarwarp",
            permission = "nextwarps.admin",
            target = CommandTarget.PLAYER,
            usage = "setwarp <warp> [permissão]"
    )
    public void setWarp(Context<Player> context, String warp, @Optional(def = "nextwarps.use") String permission) {
        final Player player = context.getSender();

        final java.util.Optional<Warp> warpFound = api.findWarpByName(warp);

        if (warpFound.isPresent()) {
            player.sendMessage(MessageValue.get(MessageValue::warpAlreadyExists));

            return;
        }

        warpFile.create(new Warp(warp, permission, player.getLocation()));

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

        final java.util.Optional<Warp> warpByName = api.findWarpByName(warp);

        if (warpByName.isPresent()) {
            warpFile.delete(warp);

            player.sendMessage(MessageValue.get(MessageValue::warpDel).replace("%warp%", warp));
        } else {
            player.sendMessage(MessageValue.get(MessageValue::warpNotFound));
        }
    }

}
