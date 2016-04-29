package com.chotoxautinh.game.model;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

	private int value;
	private List<Node> children;
	private boolean leaf = false;
	private Direction direction;
	private Direction valueDirOfChild;
	private int actualScore;
	private int parentMove;
	private int row = 0;
	private int col = 0;
	private int valueOfCell = 0;

	public Node() {
		children = new ArrayList<Node>();
	}

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

	public void setValueDirOfChild(Direction dir) {
		this.valueDirOfChild = dir;
	}

	public Direction getValueDirOfChild() {
		return this.valueDirOfChild;
	}

	public void setActualScore(int score) {
		this.actualScore = score;
	}

	public int getActualScore() {
		return actualScore;
	}

	@Override
	public int compareTo(Node node) {
		// TODO Auto-generated method stub
		int compareValue = ((Node) node).getValue();
		return -(this.getValue() - compareValue);
	}

	public int getParentMove() {
		return parentMove;
	}

	public void setParentMove(int parentMove) {
		this.parentMove = parentMove;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getValueOfCell() {
		return valueOfCell;
	}

	public void setValueOfCell(int valueOfCell) {
		this.valueOfCell = valueOfCell;
	}
	
}
