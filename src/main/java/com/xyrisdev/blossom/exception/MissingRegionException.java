package com.xyrisdev.blossom.exception;

public class MissingRegionException extends RuntimeException {

	public MissingRegionException(String name) {
		super("Region " + name + " does not exist.");
	}
}
