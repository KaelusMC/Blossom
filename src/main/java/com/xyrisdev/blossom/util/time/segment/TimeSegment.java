package com.xyrisdev.blossom.util.time.segment;

public record TimeSegment(TimeSymbol symbol, int length) {

	public String format(long value) {
		return length > 1 ? String.format("%0" + length + "d", value) : Long.toString(value);
	}
}
