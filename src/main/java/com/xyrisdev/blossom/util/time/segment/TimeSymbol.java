package com.xyrisdev.blossom.util.time.segment;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum TimeSymbol {
	DAYS('d', "d"),
	HOURS('h', "h"),
	MINUTES('m', "m"),
	SECONDS('s', "s"),
	MILLISECONDS('S', "ms");

	private static final Map<Character, TimeSymbol> SYMBOL_MAP = Stream.of(values()).collect(Collectors.toUnmodifiableMap(TimeSymbol::getSymbol, s -> s));

	private final char symbol;
	private final String suffix;

	TimeSymbol(char symbol, String suffix) {
		this.symbol = symbol;
		this.suffix = suffix;
	}

	public static @NotNull TimeSymbol from(char symbol) {
		return Optional.ofNullable(SYMBOL_MAP.get(symbol))
				.orElseThrow(() -> new IllegalArgumentException("Unsupported time symbol: " + symbol));
	}
}
