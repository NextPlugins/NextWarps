package com.nextplugins.warps.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;

public final class Locations {

    public static String serialize(Location location) {
        return serialize(location, false);
    }

    public static String serialize(Location location, boolean includeYawPitch) {
        String data = location.getWorld().getName();

        data += "/" + location.getX();
        data += "/" + location.getY();
        data += "/" + location.getZ();

        if (includeYawPitch) {
            data += "/" + location.getYaw();
            data += "/" + location.getPitch();
        }

        return data;
    }

    public static Location from(String text) {
        String[] data = text.split("/");

        World world = Bukkit.getWorld(data[0]);

        double x = parseDouble(data[1]);
        double y = parseDouble(data[2]);
        double z = parseDouble(data[3]);

        if (data.length > 4){
            return new Location(world, x, y, z, parseFloat(data[4]), parseFloat(data[5]));
        } else {
            return new Location(world, x, y, z);
        }
    }

}
