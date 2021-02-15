package com.nextplugins.nextwarps.model;

import lombok.Data;

import java.util.List;

@Data
public class WarpAliases {

    private final String command;
    private final List<String> executeCommands;

}
