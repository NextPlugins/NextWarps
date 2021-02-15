package com.nextplugins.nextwarps.config.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import com.nextplugins.nextwarps.config.GeneralValue;
import com.nextplugins.nextwarps.config.MessageValue;
import lombok.Data;
import org.bukkit.plugin.Plugin;

@Data(staticConstructor = "of")
public final class ConfigurationRegistry {

    private final Plugin plugin;

    public void inject() {
        BukkitConfigurationInjector injector = new BukkitConfigurationInjector(plugin);

        injector.saveDefaultConfiguration(plugin, "messages.yml", "warps.yml");
        injector.injectConfiguration(GeneralValue.instance(), MessageValue.instance());
    }

}
