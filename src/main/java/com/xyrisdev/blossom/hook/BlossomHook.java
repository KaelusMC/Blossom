package com.xyrisdev.blossom.hook;

import com.xyrisdev.library.logger.XLogger;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import java.util.function.Consumer;

@Getter
@Builder
@Accessors(fluent = true)
@RequiredArgsConstructor
public class BlossomHook {
	private final String plugin;
	private final Consumer<Plugin> execute;
	private final Runnable fail;
	private final boolean log;
	private final String success;
	private final String failure;

	public void register() {
		final Plugin plugin = Bukkit.getPluginManager().getPlugin(this.plugin);

		if (plugin != null && plugin.isEnabled()) {
			if (success != null) log(success);
			if (execute != null) execute.accept(plugin);
		} else {
			if (failure != null) log(failure);
			if (fail != null) fail.run();
		}
	}

	private void log(String message) {
		XLogger.custom().info("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ " + message);
	}
}
