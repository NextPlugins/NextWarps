package com.nextplugins.warps.api.warp;

import com.nextplugins.warps.utils.CaseInsensitiveMap;
import lombok.Getter;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class WarpCache {

    @Getter private final CaseInsensitiveMap<Warp> warps = CaseInsensitiveMap.newMap();

}
