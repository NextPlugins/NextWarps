package com.nextplugins.nextwarps;

import com.github.eikefab.libs.minecraft.InventoryRegistry;
import com.nextplugins.nextwarps.adapter.WarpAdapter;
import com.nextplugins.nextwarps.adapter.WarpItemAdapter;
import com.nextplugins.nextwarps.commands.WarpCommands;
import com.nextplugins.nextwarps.config.registry.ConfigurationRegistry;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class NextWarps extends JavaPlugin {

    private FileConfiguration messageConfiguration;
    private FileConfiguration warpConfiguration;
    
    private InventoryRegistry inventoryRegistry;
    private BukkitFrame bukkitFrame;

    private WarpAdapter warpAdapter;
    private WarpItemAdapter warpItemAdapter;

    private void init() {
        saveDefaultConfig();

        messageConfiguration = getFile("messages.yml");
        warpConfiguration = getFile("warps.yml");

        warpAdapter = new WarpAdapter(warpConfiguration);
        warpItemAdapter = new WarpItemAdapter(getConfig(), warpAdapter);
    }

    @Override
    public void onEnable() {
        init();

        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            inventoryRegistry = InventoryRegistry.of(this);
            bukkitFrame = new BukkitFrame(this);

            ConfigurationRegistry.of(this).inject();

            bukkitFrame.registerCommands(new WarpCommands());
        });
    }

    public static NextWarps getInstance() {
        return getPlugin(NextWarps.class);
    }

    private FileConfiguration getFile(String path) {
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), path));
    }


}
