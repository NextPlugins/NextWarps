package com.nextplugins.nextwarps.api.event;

import com.nextplugins.nextwarps.model.Warp;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class DeleteWarpEvent extends WarpEvent implements Cancellable {

    private boolean cancelled;

    public DeleteWarpEvent(Player player, Warp warp) {
        super(player, warp);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
