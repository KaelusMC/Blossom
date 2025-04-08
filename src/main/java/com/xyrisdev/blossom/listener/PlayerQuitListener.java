package com.xyrisdev.blossom.listener;

import com.xyrisdev.blossom.menu.RegionDefineMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
		final Player player = event.getPlayer();

		// Check if the player left while defining a region.
		// If so, clear their selection and reset the title.
		if (RegionDefineMenu.getSelections().containsKey(player.getUniqueId())) {
			RegionDefineMenu.getSelections().remove(player.getUniqueId());
			player.resetTitle();
		}
	}
}
