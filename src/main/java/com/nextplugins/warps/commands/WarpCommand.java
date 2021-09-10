package com.nextplugins.warps.commands;

import com.nextplugins.warps.NextWarps;
import com.nextplugins.warps.api.NextWarpAPI;
import com.nextplugins.warps.api.warp.WarpFile;
import com.nextplugins.warps.api.warp.WarpInventory;
import com.nextplugins.warps.configuration.MessageValue;
import com.nextplugins.warps.api.warp.Warp;
import com.nextplugins.warps.utils.CaseInsensitiveMap;
import lombok.Getter;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Set;

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

                player.sendMessage(MessageValue.get(MessageValue::warpNoPermission));

                return;
            }

            player.sendMessage(MessageValue.get(MessageValue::warpNotFound));

            StringBuilder stringBuilder = new StringBuilder();

            int i = 1;
            Set<Warp> warpsByAttachment = NextWarpAPI.getInstance().findWarpsByAttachment(player);
            for (Warp warp : warpsByAttachment) {
                if (i == 1) stringBuilder.append(", ");
                stringBuilder.append(warp.getName());
                ++i;
            }

            player.sendMessage(MessageValue.get(MessageValue::warpList).replace("$warps", stringBuilder));
        }
    }

    @Command(
            name = "setwarp",
            aliases = "setarwarp",
            permission = "nextwarps.admin",
            target = CommandTarget.PLAYER,
            usage = "setwarp <warp> [permissão]"
    )
    public void setWarp(Context<Player> context, String warpName, @Optional(def = "nextwarps.use") String permission) {
        final Player player = context.getSender();

        final java.util.Optional<Warp> warpFound = api.findWarpByName(warpName);

        if (warpFound.isPresent()) {
            player.sendMessage(MessageValue.get(MessageValue::warpAlreadyExists));
            return;
        }

        Warp warp = new Warp(warpName, permission, player.getLocation());
        warpFile.create(warp);

        CaseInsensitiveMap<Warp> warps = NextWarps.getInstance().getWarpCache().getWarps();
        warps.put(warpName, warp);

        player.sendMessage(MessageValue.get(MessageValue::warpSet).replace("%warp%", warpName));
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

            CaseInsensitiveMap<Warp> warps = NextWarps.getInstance().getWarpCache().getWarps();
            warps.remove(warp);

            player.sendMessage(MessageValue.get(MessageValue::warpDel).replace("%warp%", warp));
        } else {
            player.sendMessage(MessageValue.get(MessageValue::warpNotFound));
        }
    }

}
