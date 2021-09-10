package com.nextplugins.warps.utils;

import java.util.HashMap;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class CaseInsensitiveMap<V> extends HashMap<String, V> {

    public static <V> CaseInsensitiveMap<V> newMap() {
        return new CaseInsensitiveMap<>();
    }

    @Override
    public V get(Object key) {
        String keyFounded = this.keySet()
                .stream()
                .filter(keyMap -> keyMap.equalsIgnoreCase((String) key))
                .findAny()
                .orElse(null);

        return keyFounded == null ? null : super.get(keyFounded);
    }

}
