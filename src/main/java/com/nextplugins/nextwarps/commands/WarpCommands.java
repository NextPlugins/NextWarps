package com.nextplugins.nextwarps.commands;

import com.nextplugins.nextwarps.menu.WarpMenu;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

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


    }

}
