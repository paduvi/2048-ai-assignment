package com.chotoxautinh.game.model;

import java.util.List;

public class Board implements Cloneable {
	private int[][] cells;
	private int currentScore;
	private int size;
	private int numberOfEmptyCells;

	public Board(int size) {
		this.setSize(size);
		this.cells = new int[size][size];
		this.setNumberOfEmptyCells(size*size);
	}

	private void setNumberOfEmptyCells(int numberOfEmptyCells){
		this.numberOfEmptyCells = numberOfEmptyCells;
	}
	
	private int getSize(){
		return this.size;
	}
	
	private void setSize(int size){
		this.size = (size < 1) ? 0 : size;
	}
	
	public void move(Direction direction) {
		switch(direction){
		case UP:
			this.moveUp(); break;
		case DOWN:
			this.moveDown(); break;
		case LEFT:
			this.moveLeft(); break;
		case RIGHT:
			this.moveRight(); break;
		case NONE:
			break;
		default:
			System.out.println("Unknown Direction!"); break;
		}
	}

	// Moving methods
	private void moveUp() {
		rotateLeft();
		mergeBoard();
		rotateRight();
	}

	private void moveDown() {
		rotateRight();
		mergeBoard();
		rotateLeft();
	}

	private void moveLeft() {
		mergeBoard();
	}

	private void moveRight() {
		rotateLeft();
		rotateLeft();
		mergeBoard();
		rotateRight();
		rotateRight();
	}
	
	// Board-rotating methods
	private void rotateLeft(){
		int[][] rotatedBoard = new int[this.getSize()][this.getSize()];
		
		for(int row = 0; row < this.getSize(); row++){
			for(int col = 0; col < this.getSize(); col++){
				rotatedBoard[this.getSize() - col - 1][row] = this.cells[row][col];
			}
		}
		
		this.cells = rotatedBoard;
	}
	
	private void rotateRight(){
		int[][] rotatedBoard = new int[this.getSize()][this.getSize()];
		
		for(int row = 0; row < this.getSize(); row++){
			for(int col = 0; col < this.getSize(); col++){
				rotatedBoard[row][col] = this.cells[this.getSize() - col - 1][row];
			}
		}
		
		this.cells = rotatedBoard;
	}
	
	private void mergeBoard(){
        int mergingPoints = 0;
        
        for(int row = 0; row < this.getSize(); row++) {
            int lastMergePosition = 0;
            for(int col = 1; col < this.getSize(); col++) {
                
            	if(this.cells[row][col] == 0) {
                    continue; //skip moving zeros
                }
                
                int previousPosition = col - 1;
                
                while(previousPosition>lastMergePosition && this.cells[row][previousPosition]==0) { //skip all the zeros
                    --previousPosition;
                }
                
                if(previousPosition == col) {
                    //we can't move this at all
                }
                else if(this.cells[row][previousPosition] == 0) {
                    //move to empty value
                    this.cells[row][previousPosition] = this.cells[row][col];
                    this.cells[row][col] = 0;
                }
                else if(this.cells[row][previousPosition] == this.cells[row][col]){
                    //merge with matching value
                    this.cells[row][previousPosition] *= 2;
                    this.cells[row][col] = 0;
                    mergingPoints += this.cells[row][previousPosition];
                    lastMergePosition = previousPosition + 1;
                    
                }
                else if(this.cells[row][previousPosition]!=this.cells[row][col] && (previousPosition+1 != col)){
                    this.cells[row][previousPosition+1] = this.cells[row][col];
                    this.cells[row][col] = 0;
                }
            }
        }
        
        this.currentScore += mergingPoints;
	}
	

	
	public void insert(int i, int j, int value) {
		if (cells[i][j] == 0) {
			cells[i][j] = value;
			this.numberOfEmptyCells--;
		}
	}

	public int getNumberOfEmptyCells() {
		return this.numberOfEmptyCells;
	}
	
	public int getClusteringScore(){
		return 0;
	}
	
	public List<Integer> getIdOfEmptyCell(){
		return null;
	}
	public boolean isTerminated() {
		if (hasWon())
			return true;
		return false;
	}

	public boolean hasWon() {
		return false;
	}

	public int getCurrentScore() {
		return this.currentScore;
	}
}
