package com.nextplugins.nextwarps.api.warp;

import lombok.Data;
import org.bukkit.Location;

@Data
public class Warp {

    private final String name;
    private final String permission;
    private final Location location;

}
