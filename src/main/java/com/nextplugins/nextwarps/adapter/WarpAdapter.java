package com.nextplugins.nextwarps.adapter;

import com.nextplugins.nextwarps.model.Warp;
import com.nextplugins.nextwarps.utils.Locations;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;

@Data
public final class WarpAdapter {

    private final Configuration config;

    public Set<Warp> getWarps() {
        final Set<Warp> set = new HashSet<>();
        final ConfigurationSection section = config.getConfigurationSection("warps");

        for (String key : section.getKeys(false)) {
            String name = section.getString(key + ".name");
            String permission = section.getString(key + ".permission");

            Location location = Locations.from(section.getString(key + ".location"));

            set.add(new Warp(name, permission, location));
        }

        return set;
    }

}
