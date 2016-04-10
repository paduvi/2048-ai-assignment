package com.chotoxautinh.game.model;

public class BoardStack {
	private Board[] boardStack;
	private final int MAX_CAPACITY = 5;
	private int top;
	
	public BoardStack(){
		boardStack = new Board[MAX_CAPACITY];
		this.setTop(-1);
	}
	
	public int getTop() {
		return top;
	}
	
	public void setTop(int top) {
		this.top = top;
	}
	
	public void push(Board element){
		if(this.getTop() == MAX_CAPACITY - 1){
			for(int i = 0; i < MAX_CAPACITY - 1; i++){
				boardStack[i] = boardStack[i + 1];
			}
			boardStack[MAX_CAPACITY - 1] = element;
		} else {
			boardStack[++top] = element;
		}
	}
	
	public Board pop(){
		if(top < 0) throw new IndexOutOfBoundsException();
		Board poppedBoard = boardStack[top --];
		boardStack[top + 1] = null;
		return poppedBoard;
	}
}
