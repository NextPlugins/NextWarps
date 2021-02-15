package com.nextplugins.nextwarps.api;

import com.nextplugins.nextwarps.NextWarps;
import com.nextplugins.nextwarps.model.Warp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NextWarpAPI {

    private static final NextWarps plugin = NextWarps.getInstance();

    @Getter private static final NextWarpAPI instance = new NextWarpAPI();

    public Optional<Warp> findWarpByName(String warp) {
        Set<Warp> warps = plugin.getWarpAdapter().getWarps();

        Warp target = null;

        for (Warp pluginWarp : warps) {
            if (pluginWarp.getName().equalsIgnoreCase(warp)) target = pluginWarp;
        }

        return Optional.ofNullable(target);
    }

}
