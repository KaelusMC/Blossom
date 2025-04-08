package com.xyrisdev.blossom.util.command;

import com.xyrisdev.library.command.Commands;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface RegisterableSubCommand {
	@NotNull
	Consumer<Commands.Builder> build(@NotNull JavaPlugin plugin);
}
