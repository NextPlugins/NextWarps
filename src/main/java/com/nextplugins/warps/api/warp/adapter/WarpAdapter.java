package com.nextplugins.warps.api.warp.adapter;

import com.nextplugins.warps.NextWarps;
import com.nextplugins.warps.api.warp.Warp;
import com.nextplugins.warps.utils.CaseInsensitiveMap;
import com.nextplugins.warps.utils.Locations;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

@Data(staticConstructor = "of")
public final class WarpAdapter {

    private final Configuration config;

    public void loadWarps() {
        if (!config.contains("warps")) return;

        final ConfigurationSection section = config.getConfigurationSection("warps");
        CaseInsensitiveMap<Warp> warps = NextWarps.getInstance().getWarpCache().getWarps();

        for (String key : section.getKeys(false)) {
            String name = section.getString(key + ".name");
            String permission = section.getString(key + ".permission");

            Location location = Locations.from(section.getString(key + ".location"));

            warps.put(name, new Warp(name, permission, location));
        }
    }

}
