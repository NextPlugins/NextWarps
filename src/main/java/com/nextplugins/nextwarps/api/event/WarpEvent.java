package com.nextplugins.nextwarps.api.event;

import com.nextplugins.nextwarps.model.Warp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public abstract class WarpEvent extends Event {

    @Getter private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Warp warp;

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

}
