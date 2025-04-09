package com.xyrisdev.blossom.util;

import com.xyrisdev.library.config.CachableConfiguration;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;

import com.xyrisdev.blossom.RegenerationPlugin;

public final class SoundUtil {

	private SoundUtil() {}

	public static void play(Player player, String path) {
		final CachableConfiguration config = RegenerationPlugin.getInstance().config();

		final Boolean enabled = config.get(path + ".enabled");
		if (enabled == null || !enabled) return;

		final Number volumeValue = config.get(path + ".volume");
		final Number pitchValue = config.get(path + ".pitch");
		final String key = config.get(path + ".key");
		final String source = config.get(path + ".source");

		if (volumeValue == null || pitchValue == null || key == null || source == null) {
			return;
		}

		final float volume = volumeValue.floatValue();
		final float pitch = pitchValue.floatValue();

		Sound sound = Sound.sound(
				Key.key(key),
				Sound.Source.valueOf(source),
				volume,
				pitch
		);

		player.playSound(sound);
	}
}
