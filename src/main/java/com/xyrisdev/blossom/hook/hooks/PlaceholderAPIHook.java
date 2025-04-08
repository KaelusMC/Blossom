package com.xyrisdev.blossom.hook.hooks;

import com.xyrisdev.blossom.RegenerationPlugin;
import com.xyrisdev.blossom.hook.BlossomHook;
import com.xyrisdev.blossom.region.task.RegenerationTaskScheduler;
import com.xyrisdev.library.time.XTime;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnstableApiUsage")
public class PlaceholderAPIHook extends PlaceholderExpansion {

	public static BlossomHook of() {
		return BlossomHook.builder()
				.plugin("PlaceholderAPI")
				.execute(plugin -> new PlaceholderAPIHook().register())
				.success("Successfully hooked into PlaceholderAPI!")
				.failure("PlaceholderAPI not found. You will not be able to use any placeholders.")
				.log(true)
				.build();
	}

	@Override
	public @NotNull String getIdentifier() {
		return RegenerationPlugin.getInstance().getPluginMeta().getName().toLowerCase();
	}

	@Override
	public @NotNull String getAuthor() {
		return RegenerationPlugin.getInstance().getPluginMeta().getAuthors().stream().findFirst().toString();
	}

	@Override
	public @NotNull String getVersion() {
		return RegenerationPlugin.getInstance().getPluginMeta().getVersion();
	}

	@Override
	public String onPlaceholderRequest(Player player, @NotNull String identifier) {
		if (identifier.startsWith("interval_")) {
			String regionName = identifier.substring("interval_".length());
			return XTime.format(RegenerationTaskScheduler.left(regionName), TimeUnit.MILLISECONDS);
		}

		return "";
	}
}