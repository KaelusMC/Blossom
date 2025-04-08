package com.xyrisdev.blossom.exception;

public class DuplicateRegionException extends RuntimeException {

	public DuplicateRegionException(String name) {
		super("Region " + name + " already exists.");
	}
}
