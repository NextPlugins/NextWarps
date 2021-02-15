package com.nextplugins.nextwarps.commands;

import com.nextplugins.nextwarps.api.NextWarpAPI;
import com.nextplugins.nextwarps.config.MessageValue;
import com.nextplugins.nextwarps.menu.WarpMenu;
import com.nextplugins.nextwarps.model.Warp;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

import java.util.Optional;

public final class WarpCommands {

    @Command(
            name = "warp",
            aliases = "warps",
            permission = "nextwarps.use",
            target = CommandTarget.PLAYER
    )
    public void warp(Context<Player> context) {
        final Player player = context.getSender();

        player.openInventory(WarpMenu.getWarpInventory().getBukkitInventory());
    }

    @Command(
            name = "setwarp",
            aliases = "setarwarp",
            permission = "nextwarps.admin",
            target = CommandTarget.PLAYER,
            usage = "§cUso correto: /setwarp [warp]"
    )
    public void setWarp(Context<Player> context, String warp) {
        final Player player = context.getSender();

        final Optional<Warp> warpFound = NextWarpAPI.getInstance().findWarpByName(warp);

        if (warpFound.isPresent()) {
            player.sendMessage(MessageValue.get(MessageValue::warpAlreadyExists));
            return;
        }

        NextWarpAPI.getInstance().allWarps().add(new Warp(warp, "", player.getLocation()));
    }

    @Command(
            name = "delwarp",
            aliases = {"deletewarp", "deletarwarp"},
            permission = "nextwarps.admin",
            target = CommandTarget.PLAYER,
            usage = "§cUso correto: /setwarp [warp]"
    )
    public void delWarp(Context<Player> context, String warp) {
        final Player player = context.getSender();

        final Optional<Warp> warpByName = NextWarpAPI.getInstance().findWarpByName(warp);

        if (warpByName.isPresent()) {
            final Warp warpFound = warpByName.get();
            NextWarpAPI.getInstance().allWarps().remove(warpFound);
        } else {
            MessageValue.get(MessageValue::warpNotFound);
        }
    }

}
