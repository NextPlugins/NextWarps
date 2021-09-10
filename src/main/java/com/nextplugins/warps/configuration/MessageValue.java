package com.nextplugins.warps.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigSection("messages")
@ConfigFile("messages.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageValue implements ConfigurationInjectable {

    @Getter private static final MessageValue instance = new MessageValue();

    @ConfigField("errors.no-permission-warp") private String warpNoPermission;
    @ConfigField("errors.no-permission-adminwarp") private String adminWarpNoPermission;
    @ConfigField("errors.warp-not-found") private String warpNotFound;
    @ConfigField("errors.warp-list") private String warpList;
    @ConfigField("errors.warp-already-exists") private String warpAlreadyExists;

    @ConfigField("success.warp-set") private String warpSet;
    @ConfigField("success.warp-edit") private String warpEdit;
    @ConfigField("success.warp-del") private String warpDel;
    @ConfigField("success.warp-teleport") private String warpTeleport;

    @ConfigField("titles.teleporting") private String teleporting;
    @ConfigField("titles.teleported") private String teleported;

    public static <T> T get(Function<MessageValue, T> function) {
       return function.apply(instance);
    }

}
