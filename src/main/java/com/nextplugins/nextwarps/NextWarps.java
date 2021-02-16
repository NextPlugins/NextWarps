package com.nextplugins.nextwarps;

import com.github.eikefab.libs.minecraft.InventoryRegistry;
import com.nextplugins.nextwarps.api.warp.WarpFile;
import com.nextplugins.nextwarps.api.warp.adapter.WarpAdapter;
import com.nextplugins.nextwarps.api.warp.adapter.WarpItemAdapter;
import com.nextplugins.nextwarps.commands.WarpCommand;
import com.nextplugins.nextwarps.configuration.GeneralValue;
import com.nextplugins.nextwarps.configuration.registry.ConfigurationRegistry;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
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

    private FileConfiguration messageConfiguration;
    private FileConfiguration warpConfiguration;
    private WarpFile warpFile;
    private InventoryRegistry inventoryRegistry;
    private BukkitFrame bukkitFrame;
    private WarpAdapter warpAdapter;
    private WarpItemAdapter warpItemAdapter;

    public static NextWarps getInstance() {
        return getPlugin(NextWarps.class);
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();

        messageConfiguration = getFile("messages.yml");
        warpConfiguration = getFile("warps.yml");
        warpFile = new WarpFile(this);

        warpAdapter = new WarpAdapter(warpConfiguration);
        warpItemAdapter = new WarpItemAdapter(getConfig(), warpAdapter);
    }

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            inventoryRegistry = InventoryRegistry.of(this);

            ConfigurationRegistry.of(this).inject();

            bukkitFrame = new BukkitFrame(this);
            bukkitFrame.registerCommands(new WarpCommand(warpFile));

            configureBStats();
        });
    }

    private FileConfiguration getFile(String path) {
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), path));
    }

    private void configureBStats() {
        if (!GeneralValue.get(GeneralValue::metrics)) return;

        new Metrics(this, PLUGIN_ID);

        this.getLogger().info("Enabled bStats successfully, statistics enabled");
    }


}
