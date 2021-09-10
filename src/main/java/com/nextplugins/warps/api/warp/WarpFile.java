package com.nextplugins.warps.api.warp;

import com.nextplugins.warps.NextWarps;
import com.nextplugins.warps.api.NextWarpAPI;
import com.nextplugins.warps.utils.CaseInsensitiveMap;
import com.nextplugins.warps.utils.Locations;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@Getter
public final class WarpFile {

    private final NextWarps plugin;
    private final NextWarpAPI warpApi;
    private final FileConfiguration file;

    public WarpFile(NextWarps plugin) {
        this.plugin = plugin;
        this.warpApi = NextWarpAPI.getInstance();
        this.file = getPlugin().getWarpConfiguration();
    }

    public void save() {
        try {
            file.save(new File(plugin.getDataFolder(), "warps.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String key, Object value) {
        file.set(key, value);
    }

    public void create(Warp warp) {
        final String path = "warps." + warp.getName() + ".";

        set(path + "name", warp.getName());
        set(path + "permission", warp.getPermission());
        set(path + "location", Locations.serialize(warp.getLocation(), true));

        save();

        CaseInsensitiveMap<Warp> warps = NextWarps.getInstance().getWarpCache().getWarps();
        warps.put(warp.getName(), warp);
    }

    public void delete(String warpName) {
        set("warps." + warpName, null);

        CaseInsensitiveMap<Warp> warps = NextWarps.getInstance().getWarpCache().getWarps();
        warps.remove(warpName);
    }

}
