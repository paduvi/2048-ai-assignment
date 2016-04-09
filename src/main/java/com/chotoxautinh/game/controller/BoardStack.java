package com.chotoxautinh.game.controller;

public class BoardStack {
	private BoardStackElement[] boardStack;
	private final int MAX_CAPACITY = 5;
	private int top;
	
	public BoardStack(){
		boardStack = new BoardStackElement[MAX_CAPACITY];
		this.setTop(-1);
	}
	
	public int getTop() {
		return top;
	}
	
	public void setTop(int top) {
		this.top = top;
	}
	
	public void push(BoardStackElement element){
		if(this.getTop() == MAX_CAPACITY - 1){
			for(int i = 0; i < MAX_CAPACITY - 1; i++){
				boardStack[i] = boardStack[i + 1];
			}
			boardStack[MAX_CAPACITY - 1] = element;
		} else {
			boardStack[++top] = element;
		}
	}
	
	public BoardStackElement pop(){
		if(top < 0) throw new IndexOutOfBoundsException();
		BoardStackElement poppedBoard = boardStack[top --];
		boardStack[top + 1] = null;
		return poppedBoard;
	}
}
