package com.xyrisdev.blossom.region;

import com.xyrisdev.blossom.exception.DuplicateRegionException;
import com.xyrisdev.blossom.exception.MissingRegionException;
import com.xyrisdev.blossom.region.model.Region;
import com.xyrisdev.blossom.region.model.RegionPointType;
import com.xyrisdev.blossom.region.storage.RegionStorage;
import com.xyrisdev.blossom.region.task.RegenerationTaskScheduler;
import org.bukkit.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RegionManager {

	private static final RegionManager INSTANCE = new RegionManager();
	private static final RegionStorage storage = new RegionStorage();
	private static final List<Region> regions = new ArrayList<>();

	public static void of() {
		regions.clear();
		regions.addAll(storage.load());
		RegenerationTaskScheduler.start();
	}

	public void create(@NotNull String name, @NotNull String displayName) {
		if (region(name) != null) {
			throw new DuplicateRegionException(name);
		}

		final Region region = Region.builder()
				.name(name)
				.displayName(displayName)
				.center(null)
				.min(null)
				.max(null)
				.interval(600000L)
				.build();

		regions.add(region);
		storage.save(region);
	}

	public void delete(@NotNull String name) {
		if (region(name) == null) {
			throw new MissingRegionException(name);
		}

		RegenerationTaskScheduler.stop(name);
		regions.stream()
				.filter(region -> region.getName().equalsIgnoreCase(name))
				.findFirst()
				.ifPresent(region -> {
					regions.remove(region);
					storage.delete(region.getName());
				});
	}

	public void rename(@NotNull String currentName, @NotNull String newName) {
		final Region region = region(currentName);

		if (region == null) {
			throw new MissingRegionException(currentName);
		}

		if (region(newName) != null) {
			throw new DuplicateRegionException(newName);
		}

		region.renameSchematic(newName);
		delete(currentName);

		final Region renamedRegion = Region.builder()
				.name(newName)
				.displayName(region.getDisplayName())
				.center(region.getCenter())
				.min(region.getMin())
				.max(region.getMax())
				.interval(region.getInterval())
				.build();

		regions.add(renamedRegion);
		storage.save(renamedRegion);

		if (renamedRegion.valid() && renamedRegion.schematic()) {
			RegenerationTaskScheduler.start(newName);
		}
	}

	public void display(@NotNull String name, @NotNull String display) {
		final Region region = region(name);

		if (region == null) {
			throw new MissingRegionException(name);
		}

		region.setDisplayName(display);
		storage.save(region);
	}

	public void point(@NotNull String name, @NotNull RegionPointType type, @NotNull Location location) {
		final Region region = region(name);

		if (region == null) {
			throw new MissingRegionException(name);
		}

		switch (type) {
			case CENTER -> region.setCenter(location);
			case MIN -> region.setMin(location);
			case MAX -> region.setMax(location);
		}

		storage.save(region);
	}

	public void interval(@NotNull String name, long interval) {
		final Region region = region(name);

		if (region == null) {
			throw new MissingRegionException(name);
		}

		region.setInterval(interval);
		storage.save(region);
		if (region.valid() && region.schematic()) RegenerationTaskScheduler.restart(name);
	}

	public @NotNull List<Region> regions() {
		return regions;
	}

	public Region region(@NotNull String name) {
		return regions.stream()
				.filter(region -> region.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
	}

	public Region at(@NotNull Location location) {
		return regions.stream()
				.filter(region -> region.contains(location))
				.findFirst()
				.orElse(null);
	}

	public void shutdown() {
		storage.shutdown();
	}

	@Contract(pure = true)
	public static @NotNull RegionManager instance() {
		return INSTANCE;
	}
}