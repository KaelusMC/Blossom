package com.xyrisdev.blossom.command.subcommand;

import com.xyrisdev.blossom.exception.DuplicateRegionException;
import com.xyrisdev.blossom.menu.RegionManageMenu;
import com.xyrisdev.blossom.region.RegionManager;
import com.xyrisdev.blossom.util.command.RegisterableSubCommand;
import com.xyrisdev.library.command.Commands;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class CreateSubCommand implements RegisterableSubCommand {

	@Override
	public @NotNull Consumer<Commands.Builder> build(@NotNull JavaPlugin plugin) {
		return builder -> builder
				.executes(context -> {
					if (!context.instanceOfPlayer()) {
						context.sender().sendRichMessage("<red>Players are only allowed to use this command!");
						return;
					}

					final Player player = context.senderAsPlayer();
					final String name = "blossom16_" + (1000 + new SecureRandom().nextInt(9000));

					try {
						RegionManager.instance().create(name, "Blossom Region");
					} catch (DuplicateRegionException e) {
						// Extremely unlikely case since we generate random names,
						// but we handle it gracefully if it occurs
						player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Region <gradient:#8c75a5:#f46c90>" + name + "</gradient> already exists.</gray>");
					}

					RegionManageMenu.region(RegionManager.instance().region(name)).open(player);

					player.sendRichMessage(" ");
					player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>(v" + plugin.getPluginMeta().getVersion() + ")</gray>");
					player.sendRichMessage(" ");
					player.sendRichMessage("<gray>→ Region <gradient:#8c75a5:#f46c90>" + name + "</gradient> has been created.</gray>");
					player.sendRichMessage(" ");
				})
				.error(context -> context.action(sender -> sender.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Failed to create a region );.</gray>")));
	}
}
