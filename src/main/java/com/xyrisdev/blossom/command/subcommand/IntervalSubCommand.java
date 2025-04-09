package com.xyrisdev.blossom.command.subcommand;

import com.xyrisdev.blossom.command.argument.RegionArgumentType;
import com.xyrisdev.blossom.region.RegionManager;
import com.xyrisdev.blossom.region.model.Region;
import com.xyrisdev.blossom.util.command.RegisterableSubCommand;
import com.xyrisdev.library.command.Commands;
import com.xyrisdev.library.command.arguments.Arguments;
import com.xyrisdev.library.command.model.CommandSuggestions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class IntervalSubCommand implements RegisterableSubCommand {

	@Override
	public @NotNull Consumer<Commands.Builder> build(@NotNull JavaPlugin plugin) {
		return builder -> builder
				.argument(new RegionArgumentType().region("region"))
				.argument(Arguments.integer().integer("interval", CommandSuggestions.of("<interval>")))
				.argument(Arguments.string().string("unit", CommandSuggestions.of("ms", "s", "m", "h")))

				.executes(context -> {
					if (!context.instanceOfPlayer()) {
						context.sender().sendRichMessage("<red>Players are only allowed to use this command!");
						return;
					}

					final Player player = context.senderAsPlayer();
					final Region region = context.resolve("region");
					final Integer intervalValue = context.resolve("interval");
					final String unit = context.resolve("unit");

					if (intervalValue == null || intervalValue <= 0 || unit == null) {
						player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Invalid arguments. Please use /blossom inverval <region> <interval> <unit></gray>");
						return;
					}

					long intervalMs = switch (unit) {
						case "ms" -> intervalValue;
						case "s" -> intervalValue * 1_000L;
						case "m" -> intervalValue * 60_000L;
						case "h" -> intervalValue * 3_600_000L;
						default -> {
							player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Invalid time unit. Example usage: /blossom interval <region> 1 m</gray>");
							yield -1L;
						}
					};

					if (intervalMs == -1L) {
						return;
					}

					RegionManager.instance().interval(region.getName(), intervalMs);

					player.sendRichMessage(" ");
					player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>(v" + plugin.getPluginMeta().getVersion() + ")</gray>");
					player.sendRichMessage(" ");
					player.sendRichMessage("<gray>→ Region <color:#f29db4>" + region.getName() + "</color></gray> " + "<gray>interval set to <color:#f29db4>" + intervalValue + unit + ".</color></gray>");
					player.sendRichMessage(" ");
				})
				.error(error -> {
					switch (error.type()) {
						case MISSING_ARGUMENT -> error.action(sender ->
								sender.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Missing arguments. Please use /blossom interval <region> <interval> <unit></gray>"));
						case INVALID_ARGUMENT -> error.action(sender ->
								sender.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Invalid arguments. Please use /blossom interval <region> <interval> <unit></gray>"));
					}
				});
	};
}