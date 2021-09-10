package com.nextplugins.warps.api.warp;

import com.nextplugins.warps.NextWarps;
import com.nextplugins.warps.api.NextWarpAPI;
import com.nextplugins.warps.utils.Locations;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Optional;

@Getter
public final class WarpFile {

    private final NextWarps plugin;
    private final NextWarpAPI warpApi;
    private final FileConfiguration warpFile;

    public WarpFile(NextWarps plugin) {
        this.plugin = plugin;
        this.warpApi = NextWarpAPI.getInstance();
        this.warpFile = getPlugin().getWarpConfiguration();
    }

    public void save() {
        try {
            warpFile.save(new File(plugin.getDataFolder(), "warps.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String key, Object value) {
        warpFile.set(key, value);

        save();
    }

    public void create(Warp warp) {
        final String path = "warps." + warp.getName() + ".";

        set(path + "name", warp.getName());
        set(path + "permission", warp.getPermission());
        set(path + "location", Locations.serialize(warp.getLocation(), true));

        warpApi.allWarps().add(warp);
    }

    public void delete(String warpName) {
        set("warps." + warpName, null);

        Optional<Warp> warp = warpApi.findWarpByName(warpName);

        warp.ifPresent(warpApi.allWarps()::remove);
    }

}
