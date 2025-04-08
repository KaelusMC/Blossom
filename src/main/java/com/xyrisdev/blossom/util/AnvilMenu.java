package com.xyrisdev.blossom.util;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class AnvilMenu {

	private final AnvilGUI.Builder builder;

	private AnvilMenu(Plugin plugin) {
		this.builder = new AnvilGUI.Builder().plugin(plugin);
	}

	@Contract("_ -> new")
	public static @NotNull AnvilMenu of(Plugin plugin) {
		return new AnvilMenu(plugin);
	}

	public AnvilMenu title(String title) {
		builder.title(title);
		return this;
	}

	public AnvilMenu text(String text) {
		builder.text(text);
		return this;
	}

	public AnvilMenu close(Consumer<Player> onClose) {
		builder.onClose(state -> onClose.accept(state.getPlayer()));
		return this;
	}

	public AnvilMenu click(@NotNull BiFunction<Integer, AnvilGUI.StateSnapshot, List<AnvilGUI.ResponseAction>> onClick) {
		builder.onClick(onClick);
		return this;
	}

	public AnvilMenu preventClose() {
		builder.preventClose();
		return this;
	}

	public void open(Player player) {
		builder.open(player);
	}
}
