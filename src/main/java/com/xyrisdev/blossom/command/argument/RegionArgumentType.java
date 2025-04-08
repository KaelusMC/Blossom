package com.xyrisdev.blossom.command.argument;

import com.xyrisdev.blossom.region.RegionManager;
import com.xyrisdev.library.command.arguments.Argument;
import com.xyrisdev.library.command.model.CommandSuggestions;
import com.xyrisdev.blossom.region.model.Region;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public final class RegionArgumentType {

	@Contract("_ -> new")
	public @NotNull Argument<Region> region(@NotNull String name) {
		return region(name, null);
	}

	@Contract("_, _ -> new")
	public @NotNull Argument<Region> region(@NotNull String name, @Nullable CommandSuggestions override) {
		return new Argument<>(
				name,
				input -> {
					final Region region = RegionManager.instance().region(input);
					if (region == null) {
						throw new IllegalArgumentException("Region '" + input + "' not found.");
					}
					return region;
				},
				CommandSuggestions.defaultOrOverride(
						override,
						CommandSuggestions.dynamic(sender ->
								RegionManager.instance().regions().stream()
										.map(Region::getName)
										.collect(Collectors.toList())
						)
				)
		);
	}
}
