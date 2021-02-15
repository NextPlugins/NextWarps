package com.nextplugins.nextwarps.adapter;

import com.nextplugins.nextwarps.model.WarpAliases;
import lombok.Data;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public final class WarpAliasesAdapter {

    private final Configuration config;

    public Set<WarpAliases> getAliases() {
        if (!config.getBoolean("custom-commands.enabled")) return new HashSet<>();

        final Set<WarpAliases> set = new HashSet<>();
        final ConfigurationSection customSection = config.getConfigurationSection("custom-commands.commands");

        for (String key : customSection.getKeys(false)) {
            final String command = customSection.getString(key + ".key");
            final List<String> commands = customSection.getStringList(key + ".execute");

            set.add(new WarpAliases(command, commands));
        }

        return set;
    }
}
