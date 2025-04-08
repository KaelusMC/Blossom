package com.xyrisdev.blossom;

import com.xyrisdev.blossom.command.BlossomCommand;
import com.xyrisdev.blossom.hook.hooks.PlaceholderAPIHook;
import com.xyrisdev.blossom.listener.PlayerInteractListener;
import com.xyrisdev.blossom.listener.PlayerQuitListener;
import com.xyrisdev.blossom.region.RegionManager;
import com.xyrisdev.blossom.region.task.RegenerationTaskScheduler;
import com.xyrisdev.library.AbstractPlugin;
import com.xyrisdev.library.config.CachableConfiguration;
import com.xyrisdev.library.lib.Library;
import com.xyrisdev.library.lib.feature.FeatureFlags;
import com.xyrisdev.library.lib.feature.FeatureRegistry;
import com.xyrisdev.library.logger.XLogger;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public final class RegenerationPlugin extends AbstractPlugin {

	@Getter
	private static RegenerationPlugin instance;
	private CachableConfiguration config;

	@Override
	protected void run() {
		instance = this;
		Library.of(this, "blossom");

		config = CachableConfiguration.builder()
				.file("config.yml")
				.build();

		RegionManager.of();

		plugins().registerEvents(new PlayerQuitListener(), this);
		plugins().registerEvents(new PlayerInteractListener(), this);
		BlossomCommand.blossom(this).register();
		PlaceholderAPIHook.of().register();
	}

	@Override
	protected void shutdown() {
		RegenerationTaskScheduler.stop();
		RegionManager.instance().shutdown();
	}

	@Override
	protected void feature(@NotNull FeatureRegistry registry) {
		registry.registrar()
				.disable(FeatureFlags.Conversation.PROCESS)
				.disable(FeatureFlags.CallBack.PROCESS)
				.disable(FeatureFlags.WorldGuard.REGION)
				.disable(FeatureFlags.Game.CRYSTAL)
				.disable(FeatureFlags.Game.ANCHOR);
	}

	public @NotNull CachableConfiguration config() {
		return config;
	}

	public void debug(@NotNull String message) {
		if (config().get("debug", false)) {
			XLogger.custom().info("<b><gradient:#8c75a5:#f46c90>Blossom</gradient></b> <gray>→ " + message);
		}
	}
}
