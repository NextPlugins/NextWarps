package com.nextplugins.nextwarps;

import com.github.eikefab.libs.minecraft.InventoryRegistry;
import com.nextplugins.nextwarps.adapter.WarpAdapter;
import com.nextplugins.nextwarps.adapter.WarpAliasesAdapter;
import com.nextplugins.nextwarps.adapter.WarpItemAdapter;
import com.nextplugins.nextwarps.config.registry.ConfigurationRegistry;
import com.nextplugins.nextwarps.model.WarpAliases;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.command.CommandInfo;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Set;

@Getter
public final class NextWarps extends JavaPlugin {


    private final FileConfiguration messageConfiguration;
    private final FileConfiguration warpConfiguration;

    private final WarpAdapter warpAdapter;
    private final WarpItemAdapter warpItemAdapter;
    private final WarpAliasesAdapter warpAliasesAdapter;

    public NextWarps() {
        saveDefaultConfig();
        saveResource("messages.yml", false);
        saveResource("warps.yml", false);

        messageConfiguration = getFile("messages.yml");
        warpConfiguration = getFile("warps.yml");

        warpAdapter = new WarpAdapter(warpConfiguration);
        warpItemAdapter = new WarpItemAdapter(getConfig(), warpAdapter);
        warpAliasesAdapter = new WarpAliasesAdapter(warpConfiguration);
    }

    private InventoryRegistry inventoryRegistry;
    private BukkitFrame bukkitFrame;

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            inventoryRegistry = InventoryRegistry.of(this);
            bukkitFrame = new BukkitFrame(this);

            ConfigurationRegistry.of(this).inject();

            registerAliases();
        });
    }

    public static NextWarps getInstance() {
        return getPlugin(NextWarps.class);
    }

    private FileConfiguration getFile(String path) {
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), path));
    }

    private void registerAliases() {
        Set<WarpAliases> warpAliases = getWarpAliasesAdapter().getAliases();

        for (WarpAliases aliases : warpAliases) {
            bukkitFrame.registerCommand(
                    CommandInfo.builder()
                            .name(aliases.getCommand())
                            .target(CommandTarget.PLAYER)
                            .build(),
                    (context) -> {
                        final Player player = (Player) context.getSender();

                        aliases.getExecuteCommands().forEach(player::performCommand);

                        return false;
                    });
        }

    }

}
