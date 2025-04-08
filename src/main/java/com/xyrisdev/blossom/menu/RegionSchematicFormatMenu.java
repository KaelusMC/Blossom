package com.xyrisdev.blossom.menu;

import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.xyrisdev.blossom.region.model.Region;
import com.xyrisdev.blossom.util.AsyncWorldEditUtil;
import com.xyrisdev.library.item.ItemBuilder;
import com.xyrisdev.library.menu.Menu;
import com.xyrisdev.library.text.XText;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RegionSchematicFormatMenu {

	@SuppressWarnings("deprecation")
	public static @NotNull Menu format(@NotNull Region region) {
		final Menu menu = new Menu(27, Component.text("Format > " + region.getName()));

		ItemStack fast = new ItemBuilder(Material.PRISMARINE_CRYSTALS)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Fast</gradient>".toLowerCase()).small().component())
				.lore(
						XText.text("<gray>Click to save the schematic</gray>").component(),
						XText.text("<gray>using the fast format.</gray>").component(),
						XText.text(" ").component(),
						XText.text("<red>Deprecated</red>").small().component()
				)
				.build();

		menu.item(11, fast, event -> {
			AsyncWorldEditUtil.schematic(region.getName(), BuiltInClipboardFormat.FAST);
			RegionManageMenu.region(region).open((Player) event.getWhoClicked());
		});

		ItemStack fastV2 = new ItemBuilder(Material.PRISMARINE_CRYSTALS)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Fast v2</gradient>".toLowerCase()).small().component())
				.lore(
						XText.text("<gray>Click to save the schematic</gray>").component(),
						XText.text("<gray>using the Fast v2 format.</gray>").component()
				)
				.build();

		menu.item(13, fastV2, event -> {
			AsyncWorldEditUtil.schematic(region.getName(), BuiltInClipboardFormat.FAST_V2);
			RegionManageMenu.region(region).open((Player) event.getWhoClicked());
		});

		ItemStack fastV3 = new ItemBuilder(Material.PRISMARINE_CRYSTALS)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Fast v3</gradient>".toLowerCase()).small().component())
				.lore(
						XText.text("<gray>Click to save the schematic</gray>").component(),
						XText.text("<gray>using the Fast v3 format.</gray>").component()
				)
				.build();

		menu.item(15, fastV3, event -> {
			AsyncWorldEditUtil.schematic(region.getName(), BuiltInClipboardFormat.FAST_V3);
			RegionManageMenu.region(region).open((Player) event.getWhoClicked());
		});

		ItemStack backStack = new ItemBuilder(Material.ARROW)
				.name(XText.text("<gradient:#8c75a5:#f46c90>Back</gradient>".toLowerCase()).small().component())
				.build();

		menu.item(22, backStack, event ->
				RegionManageMenu.region(region).open((Player) event.getWhoClicked())
		);

		return menu;
	}
}
