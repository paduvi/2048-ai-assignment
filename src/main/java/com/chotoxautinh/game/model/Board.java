package com.chotoxautinh.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board implements Cloneable {

	public static final int TARGET_POINTS = 2048;
	public static final int MINIMUM_WIN_SCORE = 18432;
	private final Random randomGenerator = new Random();

	private int[][] cells;
	private int actualScore;
	private int size;
	private int numberOfEmptyCells;
	private int mergingPoints; // temporary store points after a merging step

	public Board() {
		this.setSize(0);
		this.cells = null;
		this.setNumberOfEmptyCells(0);
	}

	public Board(int size) throws Exception {
		this.setSize(size);
		this.cells = new int[size][size];
		
		initialize();
	}

	public void initialize() throws Exception {
		actualScore = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				cells[i][j] = 0;
		this.setNumberOfEmptyCells(size * size);

		// Initialize two cell at random
		this.addRandomCell();
		this.addRandomCell();
	}

	private boolean addRandomCell() throws Exception {
		List<Integer> emptyCells = this.getEmptyCellIds();
		int listSize = emptyCells.size();

		if (listSize > 0) {
			int randomCellId = emptyCells.get(this.randomGenerator.nextInt(listSize));
			int randomValue = (this.randomGenerator.nextDouble() < 0.9) ? 2 : 4;

			int row = randomCellId / this.getSize();
			int col = randomCellId % this.getSize();

			this.setValueToAnEmptyCell(randomValue, row, col);
			return true;
		} else {
			return false;
		}
	}

	private void setNumberOfEmptyCells(int numberOfEmptyCells) {
		this.numberOfEmptyCells = numberOfEmptyCells;
	}

	public int getSize() {
		return this.size;
	}

	private void setSize(int size) {
		this.size = (size < 1) ? 0 : size;
	}

	/**
	 * Moving method
	 * 
	 * @param direction
	 * @return the number of points after the move
	 */
	public int move(Direction direction) {
		switch (direction) {
		case UP:
			return this.moveUp();
		case DOWN:
			return this.moveDown();
		case LEFT:
			return this.moveLeft();
		case RIGHT:
			return this.moveRight();
		case NONE:
			return 0;
		default:
			System.out.println("Unknown Direction!");
			return 0;
		}
	}

	private int moveUp() {
		rotateLeft();
		mergeBoard();
		rotateRight();
		return this.getMergingPoints();
	}

	private int moveDown() {
		rotateRight();
		mergeBoard();
		rotateLeft();
		return this.getMergingPoints();
	}

	private int moveLeft() {
		mergeBoard();
		return this.getMergingPoints();
	}

	private int moveRight() {
		rotateLeft();
		rotateLeft();
		mergeBoard();
		rotateRight();
		rotateRight();
		return this.getMergingPoints();
	}

	// Board-rotating methods
	private void rotateLeft() {
		int[][] rotatedBoard = new int[this.getSize()][this.getSize()];

		for (int row = 0; row < this.getSize(); row++) {
			for (int col = 0; col < this.getSize(); col++) {
				rotatedBoard[this.getSize() - col - 1][row] = this.cells[row][col];
			}
		}

		this.cells = rotatedBoard;
	}

	private void rotateRight() {
		int[][] rotatedBoard = new int[this.getSize()][this.getSize()];

		for (int row = 0; row < this.getSize(); row++) {
			for (int col = 0; col < this.getSize(); col++) {
				rotatedBoard[row][col] = this.cells[this.getSize() - col - 1][row];
			}
		}

		this.cells = rotatedBoard;
	}

	private void mergeBoard() {
		this.setMergingPoints(0);

		for (int row = 0; row < this.getSize(); row++) {
			int lastMergePosition = 0;
			for (int col = 1; col < this.getSize(); col++) {
				// skip cells with zero value.
				if (this.cells[row][col] == 0) {
					continue;
				}

				int previousPosition = col - 1;

				while (previousPosition > lastMergePosition && this.cells[row][previousPosition] == 0) { // skip
																											// all
																											// the
																											// zeros
					--previousPosition;
				}

				if (previousPosition == col) {
					// we can't move this at all
				} else if (this.cells[row][previousPosition] == 0) {
					// move to empty value
					this.cells[row][previousPosition] = this.cells[row][col];
					this.cells[row][col] = 0;
				} else if (this.cells[row][previousPosition] == this.cells[row][col]) {
					// merge with matching value
					this.cells[row][previousPosition] *= 2;
					this.cells[row][col] = 0;
					setMergingPoints(this.cells[row][previousPosition]);
					lastMergePosition = previousPosition + 1;
				} else if (this.cells[row][previousPosition] != this.cells[row][col] && (previousPosition + 1 != col)) {
					this.cells[row][previousPosition + 1] = this.cells[row][col];
					this.cells[row][col] = 0;
				}
			}
		}

		this.actualScore += getMergingPoints();
	}

	private int getMergingPoints() {
		return this.mergingPoints;
	}

	private void setMergingPoints(int mergingPoints) {
		if (!(mergingPoints < 0))
			this.mergingPoints = mergingPoints;
	}

	public int getNumberOfEmptyCells() {
		if (this.numberOfEmptyCells == 0) {
			this.setNumberOfEmptyCells(this.getEmptyCellIds().size());
		}
		return this.numberOfEmptyCells;
	}

	public List<Integer> getEmptyCellIds() {
		List<Integer> cellList = new ArrayList<Integer>();

		for (int row = 0; row < this.getSize(); row++) {
			for (int col = 0; col < this.getSize(); col++) {
				if (this.cells[row][col] == 0) {
					cellList.add(this.getSize() * row + col);
				}
			}
		}

		return cellList;
	}

	public void setValueToAnEmptyCell(int value, int row, int col) throws Exception {
		if ((row < this.getSize()) && (col < this.getSize())) {
			if (cells[row][col] == 0) {
				cells[row][col] = value;
				this.setNumberOfEmptyCells(this.getNumberOfEmptyCells() - 1);
			}
		} else {
			throw new Exception("Invalid cell!");
		}
	}

	public boolean isTerminated() throws CloneNotSupportedException {
		boolean isTerminated = false;

		if (hasWon()) {
			return true;
		} else {
			if (this.getNumberOfEmptyCells() == 0) {
				// The only case that the game may be terminated is that there
				// is no empty cells.
				Board terminationTestBoard = (Board) this.clone();

				if (terminationTestBoard.move(Direction.LEFT) == 0 && terminationTestBoard.move(Direction.UP) == 0
						&& terminationTestBoard.move(Direction.DOWN) == 0
						&& terminationTestBoard.move(Direction.RIGHT) == 0) {
					isTerminated = true;
				}
			}
		}

		return isTerminated;
	}

	/**
	 * Player wins the game iff there is a cell whose value is greater than
	 * TARGET_POINT If player's actual score is not greater than
	 * MINIMUM_WIN_SCORE, he (definitely) has NOT won yet.
	 */
	public boolean hasWon() {
		if (this.getActualScore() < MINIMUM_WIN_SCORE) {
			return false;
		}

		for (int row = 0; row < this.getSize(); row++) {
			for (int col = 0; col < this.getSize(); col++) {
				if (this.cells[row][col] >= TARGET_POINTS) {
					return true;
				}
			}
		}
		return false;
	}

	public int getActualScore() {
		return this.actualScore;
	}

	public int getClusteringScore() {
		return this.getClusteringScoreByHorizontal() + this.getClusteringScoreByVertical();
	}

	private int getClusteringScoreByHorizontal() {
		int clusteringScoreByHorizontal = 0;
		for (int row = 0; row < this.getSize(); row++) {
			for (int col = 0; col < this.getSize() - 1; col++) {
				clusteringScoreByHorizontal += Math.abs(this.cells[row][col] - this.cells[row][col + 1]);
			}
		}
		clusteringScoreByHorizontal <<= 1;
		return clusteringScoreByHorizontal;
	}

	private int getClusteringScoreByVertical() {
		int clusteringScoreByVertical = 0;
		for (int col = 0; col < this.getSize(); col++) {
			for (int row = 0; row < this.getSize() - 1; row++) {
				clusteringScoreByVertical += Math.abs(this.cells[row][col] - this.cells[row + 1][col]);
			}
		}
		clusteringScoreByVertical <<= 1;
		return clusteringScoreByVertical;
	}

	public void display() {
		for (int row = 0; row < this.getSize(); row++) {
			for (int col = 0; col < this.getSize(); col++) {
				System.out.printf("%8s", this.cells[row][col]);
			}
			System.out.println();
		}
	}

	/**
	 * Deep clone method
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Board clone = (Board) super.clone();
		clone.cells = clone2dArray(this.cells);
		return clone;
	}

	private int[][] clone2dArray(int[][] originalArray) {
		int[][] copyArray = new int[originalArray.length][];
		for (int row = 0; row < originalArray.length; row++) {
			copyArray[row] = originalArray[row].clone();
		}
		return copyArray;
	}

	/**
	 * Get Board's Cells
	 */
	public int[][] getCells() {
		return this.cells;
	}

}
