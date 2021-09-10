package com.nextplugins.warps.api.warp.adapter;

import com.nextplugins.warps.NextWarps;
import com.nextplugins.warps.api.NextWarpAPI;
import com.nextplugins.warps.api.warp.Warp;
import com.nextplugins.warps.api.warp.WarpItem;
import com.nextplugins.warps.utils.ItemBuilder;
import com.nextplugins.warps.utils.MaterialParser;
import lombok.Data;
import lombok.val;
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
            if (iconStack == null) {
                NextWarps.getInstance().getLogger().warning("Não foi possível criar o item " + key + " no menu (Item inválido)");
                continue;
            }

            final int slot = iconSection.getInt("slot");

            set.add(new WarpItem(iconStack, slot, warp.get()));
        }

        return set;
    }

    public ItemStack getItemStack(ConfigurationSection section) {

        val icon = section.getString("icon", "");
        val data = section.getInt("data", -1);
        val head = section.getString("head", "");

        ItemBuilder itemBuilder;
        if (icon.equals("") || data == -1) {
            if (head.equals("")) {
                return null;
            }

            itemBuilder = new ItemBuilder(head);
        } else {
            itemBuilder = new ItemBuilder(MaterialParser.from(icon, data));
        }

        return itemBuilder.name(section.getString("name"))
                .setLore(section.getStringList("lore").toArray(new String[] {}))
                .wrap();
    }

}
