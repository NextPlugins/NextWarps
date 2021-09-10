package com.nextplugins.warps;

import com.github.eikefab.libs.minecraft.InventoryRegistry;
import com.nextplugins.warps.api.warp.WarpCache;
import com.nextplugins.warps.api.warp.WarpFile;
import com.nextplugins.warps.api.warp.adapter.WarpAdapter;
import com.nextplugins.warps.api.warp.adapter.WarpItemAdapter;
import com.nextplugins.warps.commands.WarpCommand;
import com.nextplugins.warps.configuration.registry.ConfigurationRegistry;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class NextWarps extends JavaPlugin {

    /**
     * Metrics plugin id (used for statistics)
     */
    private static final int PLUGIN_ID = 10360;


    private WarpCache warpCache;
    private WarpAdapter warpAdapter;
    private WarpItemAdapter warpItemAdapter;

    private InventoryRegistry inventoryRegistry;

    private WarpFile warpFile;
    private FileConfiguration warpConfiguration;

    public static NextWarps getInstance() {
        return getPlugin(NextWarps.class);
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();

        warpConfiguration = getFile("warps.yml");
        warpFile = new WarpFile(this);


        warpCache = new WarpCache();
        WarpAdapter.of(warpConfiguration).loadWarps();
        warpItemAdapter = new WarpItemAdapter(getConfig(), warpAdapter);

        getLogger().info("Plugin ligado com sucesso");
    }

    @Override
    public void onEnable() {
        inventoryRegistry = InventoryRegistry.of(this);

        ConfigurationRegistry.of(this).inject();

        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(new WarpCommand(warpFile));

        new Metrics(this, PLUGIN_ID);
    }

    private FileConfiguration getFile(String path) {
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), path));
    }

}
