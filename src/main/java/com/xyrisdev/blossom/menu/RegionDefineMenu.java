package com.xyrisdev.blossom.menu;

import com.xyrisdev.blossom.region.model.PendingSelection;
import com.xyrisdev.blossom.region.model.Region;
import com.xyrisdev.blossom.region.model.RegionPointType;
import com.xyrisdev.blossom.region.task.RegenerationTaskScheduler;
import com.xyrisdev.library.item.ItemBuilder;
import com.xyrisdev.library.menu.Menu;
import com.xyrisdev.library.text.XText;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class RegionDefineMenu {

	@Getter
	private static final Map<UUID, PendingSelection> selections = new HashMap<>();

	public static @NotNull Menu define(@NotNull Region region) {
		final Menu menu = new Menu(27, Component.text("Defining > " + region.getName()));

		ItemStack minStack = new ItemBuilder(Material.ENDER_PEARL)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Minimum</gradient>").small().component())
				.lore(Stream.concat(
						Stream.of(
								XText.text("<gray>Click to define the min position.</gray>").component(),
								XText.text("<gray>Current Position:</gray>").component(),
								XText.text("<gray>X: <gradient:#8c75a5:#f46c90>" + (region.getMin() != null ? region.getMin().getX() : "None") + "</gradient></gray>").component(),
								XText.text("<gray>Y: <gradient:#8c75a5:#f46c90>" + (region.getMin() != null ? region.getMin().getY() : "None") + "</gradient></gray>").component(),
								XText.text("<gray>Z: <gradient:#8c75a5:#f46c90>" + (region.getMin() != null ? region.getMin().getZ() : "None") + "</gradient></gray>").component(),
								XText.text(" ").component(),
								XText.text("<gray>World: <gradient:#8c75a5:#f46c90>" +
										(region.getMin() != null && region.getMin().getWorld() != null ?
												region.getMin().getWorld().getName() : "None") + "</gradient></gray>").component()
						),
						RegenerationTaskScheduler.running(region.getName())
								? Stream.of(
								XText.text(" ").component(),
								XText.text("<gray>The regeneration task is running. You're not allowed to define a region while it is active.</gray>").component()
						)
								: Stream.empty()
				).toList())
				.build();

		menu.item(11, minStack, event -> {
			if (RegenerationTaskScheduler.running(region.getName())) {
				return;
			}

			Player player = (Player) event.getWhoClicked();
			player.closeInventory();
			selections.put(player.getUniqueId(), new PendingSelection(region, RegionPointType.MIN));

			player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Shift + Left-Click to set the minimum at your location.</gray>");
			Title.Times times = Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(500), Duration.ofSeconds(0));
			player.showTitle(Title.title(
					XText.text("<gradient:#8c75a5:#f46c90>Waiting...</gradient>").small().component(),
					XText.text("<gray>Shift + Left-Click</gray>").small().component(),
					times
			));
		});

		ItemStack centerStack = new ItemBuilder(Material.BEACON)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Center</gradient>").small().component())
				.lore(Stream.concat(
						Stream.of(
								XText.text("<gray>Click to define the center position.</gray>").component(),
								XText.text("<gray>Current Position:</gray>").component(),
								XText.text("<gray>X: <gradient:#8c75a5:#f46c90>" + (region.getCenter() != null ? region.getCenter().getX() : "None") + "</gradient></gray>").component(),
								XText.text("<gray>Y: <gradient:#8c75a5:#f46c90>" + (region.getCenter() != null ? region.getCenter().getY() : "None") + "</gradient></gray>").component(),
								XText.text("<gray>Z: <gradient:#8c75a5:#f46c90>" + (region.getCenter() != null ? region.getCenter().getZ() : "None") + "</gradient></gray>").component(),
								XText.text(" ").component(),
								XText.text("<gray>World: <gradient:#8c75a5:#f46c90>" +
										(region.getCenter() != null && region.getCenter().getWorld() != null ?
												region.getCenter().getWorld().getName() : "None") + "</gradient></gray>").component()
						),
						RegenerationTaskScheduler.running(region.getName())
								? Stream.of(
								XText.text(" ").component(),
								XText.text("<gray>The regeneration task is running. You're not allowed to define a region while it is active.</gray>").component()
						)
								: Stream.empty()
				).toList())
				.build();

		menu.item(13, centerStack, event -> {
			if (RegenerationTaskScheduler.running(region.getName())) {
				return;
			}

			Player player = (Player) event.getWhoClicked();
			player.closeInventory();
			selections.put(player.getUniqueId(), new PendingSelection(region, RegionPointType.CENTER));

			player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Shift + Left-Click to set the center at your location.</gray>");
			Title.Times times = Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(500), Duration.ofSeconds(0));
			player.showTitle(Title.title(
					XText.text("<gradient:#8c75a5:#f46c90>Waiting...</gradient>").small().component(),
					XText.text("<gray>Shift + Left-Click</gray>").small().component(),
					times
			));
		});

		ItemStack maxStack = new ItemBuilder(Material.ENDER_PEARL)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Maximum</gradient>").small().component())
				.lore(Stream.concat(
						Stream.of(
								XText.text("<gray>Click to define the max position.</gray>").component(),
								XText.text("<gray>Current Position:</gray>").component(),
								XText.text("<gray>X: <gradient:#8c75a5:#f46c90>" + (region.getMax() != null ? region.getMax().getX() : "None") + "</gradient></gray>").component(),
								XText.text("<gray>Y: <gradient:#8c75a5:#f46c90>" + (region.getMax() != null ? region.getMax().getY() : "None") + "</gradient></gray>").component(),
								XText.text("<gray>Z: <gradient:#8c75a5:#f46c90>" + (region.getMax() != null ? region.getMax().getZ() : "None") + "</gradient></gray>").component(),
								XText.text(" ").component(),
								XText.text("<gray>World: <gradient:#8c75a5:#f46c90>" +
										(region.getMax() != null && region.getMax().getWorld() != null ?
												region.getMax().getWorld().getName() : "None") + "</gradient></gray>").component()
						),
						RegenerationTaskScheduler.running(region.getName())
								? Stream.of(
								XText.text(" ").component(),
								XText.text("<gray>The regeneration task is running. You're not allowed to define a region while it is active.</gray>").component()
						)
								: Stream.empty()
				).toList())
				.build();

		menu.item(15, maxStack, event -> {
			if (RegenerationTaskScheduler.running(region.getName())) {
				return;
			}

			Player player = (Player) event.getWhoClicked();
			player.closeInventory();
			selections.put(player.getUniqueId(), new PendingSelection(region, RegionPointType.MAX));

			player.sendRichMessage("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ Shift + Left-Click to set the maximum at your location.</gray>");
			Title.Times times = Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(500), Duration.ofSeconds(0));
			player.showTitle(Title.title(
					XText.text("<gradient:#8c75a5:#f46c90>Waiting...</gradient>").small().component(),
					XText.text("<gray>Shift + Left-Click</gray>").small().component(),
					times
			));
		});

		ItemStack backStack = new ItemBuilder(Material.ARROW)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Back</gradient>".toLowerCase()).small().component())
				.build();

		menu.item(22, backStack, event -> RegionManageMenu.region(region).open((Player) event.getWhoClicked()));

		return menu;
	}
}
