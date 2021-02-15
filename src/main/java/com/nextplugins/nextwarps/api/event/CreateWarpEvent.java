package com.nextplugins.nextwarps.api.event;

import com.nextplugins.nextwarps.model.Warp;
import org.bukkit.entity.Player;

public class CreateWarpEvent extends WarpEvent {

    public CreateWarpEvent(Player player, Warp warp) {
        super(player, warp);
    }

}
