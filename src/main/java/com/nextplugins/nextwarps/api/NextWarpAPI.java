package com.nextplugins.nextwarps.api;

import com.nextplugins.nextwarps.NextWarps;
import com.nextplugins.nextwarps.model.Warp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NextWarpAPI {

    private static final NextWarps plugin = NextWarps.getInstance();

    @Getter private static final NextWarpAPI instance = new NextWarpAPI();

    /**
     * Search all warps to look for one with the entered name.
     *
     * @param warp warp name
     * @return {@link Optional} with the warp
     */
    public Optional<Warp> findWarpByName(String warp) {
        Warp target = null;

        for (Warp pluginWarp : allWarps()) {
            if (pluginWarp.getName().equalsIgnoreCase(warp)) target = pluginWarp;
        }

        return Optional.ofNullable(target);
    }

    /**
     * Search all warps to look for one with the entered custom filter.
     *
     * @param filter custom filter
     * @return {@link Optional} with the warp
     */
    public Optional<Warp> findWarpByFilter(Predicate<Warp> filter) {
        return allWarps().stream()
                .filter(filter)
                .findFirst();
    }

    /**
     * Retrieve all warps with the custom permission.
     *
     * @param permission custom permission to search
     * @return {@link Set} with warps
     */
    public Set<Warp> findWarpsByPermission(String permission) {
        return allWarps().stream()
                .filter(warp -> warp.getPermission().equalsIgnoreCase(permission))
                .collect(Collectors.toSet());
    }

    /**
     * Search all warps to look for every with the entered custom filter.
     *
     * @param filter custom filter to search
     * @return {@link Set} with warps
     */
    public Set<Warp> findWarpsByFilter(Predicate<Warp> filter) {
        return allWarps().stream()
                .filter(filter)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieve all warps loaded so far.
     *
     * @return {@link Set} with warps
     */
    public Set<Warp> allWarps() {
        return plugin.getWarpAdapter().getWarps();
    }

}
