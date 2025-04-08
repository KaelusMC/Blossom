package com.xyrisdev.blossom.command.subcommand;

import com.xyrisdev.blossom.command.argument.RegionArgumentType;
import com.xyrisdev.blossom.menu.RegionManageMenu;
import com.xyrisdev.blossom.region.model.Region;
import com.xyrisdev.blossom.util.command.RegisterableSubCommand;
import com.xyrisdev.library.command.Commands;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class ManageSubCommand implements RegisterableSubCommand {

	@Override
	public @NotNull Consumer<Commands.Builder> build(@NotNull JavaPlugin plugin) {
		return builder -> builder
				.argument(new RegionArgumentType().region("region"))
				.executes(context -> {
					if (!context.instanceOfPlayer()) {
						context.sender().sendRichMessage("<red>Players are only allowed to use this command!");
						return;
					}

					final Player player = context.senderAsPlayer();
					final Region region = context.resolve("region");

					if (region == null) {
						player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Region does not exist. Please provide a valid region.</gray>");
						return;
					}

					RegionManageMenu.region(region).open(player);
				});
	}
}
