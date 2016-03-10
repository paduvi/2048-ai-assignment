package com.chotoxautinh.game.model;

import java.util.List;

public class Board implements Cloneable {
	private int[][] cells;
	private int actualScore;
	private int size;
	private int numberOfEmptyCell;

	public Board(int size) {
		this.size = size;
		this.cells = new int[size][size];
		this.numberOfEmptyCell = size * size;
	}

	public void move(Direction direction) {

	}

	public boolean isTerminated() {
		if (hasWon())
			return true;
		return false;
	}

	public boolean hasWon() {
		return false;
	}

	public int getActualScore() {
		return actualScore;
	}

	private boolean moveUp() {
		return false;
	}

	private boolean moveDown() {
		return false;
	}

	private boolean moveLeft() {
		return false;
	}

	private boolean moveRight() {
		return false;
	}

	public void insert(int i, int j, int value) {
		if (cells[i][j] == 0) {
			cells[i][j] = value;
			this.numberOfEmptyCell--;
		}
	}

	public int getNumberOfEmptyCell() {
		return numberOfEmptyCell;
	}
	
	public int getClusteringScore(){
		return 0;
	}
	
	public List<Integer> getIdOfEmptyCell(){
		return null;
	}
	
}
