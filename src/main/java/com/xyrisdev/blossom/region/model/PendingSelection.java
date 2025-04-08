package com.xyrisdev.blossom.region.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PendingSelection {
	private final Region region;
	private final RegionPointType pointType;
}