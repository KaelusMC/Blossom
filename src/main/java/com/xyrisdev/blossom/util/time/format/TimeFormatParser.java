package com.xyrisdev.blossom.util.time.format;

import com.xyrisdev.blossom.util.time.segment.TimeSegment;
import com.xyrisdev.blossom.util.time.segment.TimeSymbol;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeFormatParser {

	public TimeFormat parse(@NotNull String pattern) {
		final List<TimeSegment> segments = new ArrayList<>();

		Arrays.stream(pattern.split(":")).filter(part -> !part.isBlank()).forEach(part -> {
			final char symbolChar = part.charAt(part.length() - 1);
			final int length = part.length();
			final TimeSymbol symbol = TimeSymbol.from(symbolChar);

			segments.add(new TimeSegment(symbol, length));
		});

		return new TimeFormat(segments);
	}
}
