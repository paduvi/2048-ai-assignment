package com.chotoxautinh.game.model;

public enum Direction {
	NONE(0, "none"), UP(1, "up"), RIGHT(2, "right"), DOWN(3, "down"), LEFT(4, "left");

	private final int code;
	private final String description;

	private Direction(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}
}
