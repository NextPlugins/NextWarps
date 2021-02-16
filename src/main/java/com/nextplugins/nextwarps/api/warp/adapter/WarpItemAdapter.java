package com.nextplugins.nextwarps.api.warp.adapter;

import com.github.eikefab.libs.minecraft.item.ItemCreator;
import com.nextplugins.nextwarps.api.NextWarpAPI;
import com.nextplugins.nextwarps.api.warp.Warp;
import com.nextplugins.nextwarps.api.warp.WarpItem;
import com.nextplugins.nextwarps.utils.MaterialParser;
import lombok.Data;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
public final class WarpItemAdapter {

    private static final NextWarpAPI api = NextWarpAPI.getInstance();

    private final Configuration config;
    private final WarpAdapter adapter;

    public Set<WarpItem> getWarpItems() {
        final Set<WarpItem> set = new HashSet<>();
        final ConfigurationSection itemsSection = config.getConfigurationSection("menu.items");

        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection iconSection = itemsSection.getConfigurationSection(key);

            String targetWarp = iconSection.getString("warp");
            Optional<Warp> warp = api.findWarpByName(targetWarp);

            if (!warp.isPresent()) continue;

            final ItemStack iconStack = getItemStack(iconSection);
            final int slot = iconSection.getInt("slot");

            set.add(new WarpItem(iconStack, slot, warp.get()));
        }

        return set;
    }

    public ItemStack getItemStack(ConfigurationSection section) {
        return ItemCreator.newItem(MaterialParser.from(section.getString("icon")))
                .display(section.getString("name"))
                .lore(section.getStringList("lore").toArray(new String[] {}))
                .getItemStack();
    }

}
