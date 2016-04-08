package com.chotoxautinh.game.model;

import java.util.List;

public class Node {

	private int value;
	private List<Node> children;
	private boolean leaf = false;
	private Direction direction;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void appendChild(Node child) {
		this.children.add(child);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
