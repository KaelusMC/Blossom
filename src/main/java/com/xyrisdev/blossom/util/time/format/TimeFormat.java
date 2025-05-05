package com.xyrisdev.blossom.util.time.format;

import com.xyrisdev.blossom.util.time.segment.TimeSegment;
import lombok.Getter;

import java.util.List;

@Getter
public class TimeFormat {

	private final List<TimeSegment> segments;

	public TimeFormat(List<TimeSegment> segments) {
		this.segments = segments;
	}

	public static TimeFormat of(String pattern) {
		return new TimeFormatParser().parse(pattern);
	}
}
