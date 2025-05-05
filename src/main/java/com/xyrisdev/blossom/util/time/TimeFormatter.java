package com.xyrisdev.blossom.util.time;

import com.xyrisdev.blossom.util.time.format.TimeFormat;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class TimeFormatter {

	public static String format(long millis, @NotNull TimeFormat format) {
		final long days = TimeUnit.MILLISECONDS.toDays(millis);
		final long hours = TimeUnit.MILLISECONDS.toHours(millis) % 24;
		final long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
		final long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
		final long milliseconds = millis % 1000;

		final StringJoiner joiner = new StringJoiner(" ");

		format.getSegments().forEach(segment -> {
			long value = switch (segment.symbol()) {
				case DAYS -> days;
				case HOURS -> hours;
				case MINUTES -> minutes;
				case SECONDS -> seconds;
				case MILLISECONDS -> milliseconds;
			};

			if (value > 0) {
				final String formatted = segment.format(value) + segment.symbol().getSuffix();
				joiner.add(formatted);
			}
		});

		return joiner.toString();
	}
}