package com.chotoxautinh.game.controller;

import com.chotoxautinh.game.model.Board;

public class BoardStackElement {
	
	private Board board;
	private int previousScore;
	
	public BoardStackElement(Board board) throws Exception{
		this.board = new Board(4);
		this.setBoard(board);
		this.setPreviousScore(board.getActualScore());
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public int getPreviousScore() {
		return previousScore;
	}

	public void setPreviousScore(int previousScore) {
		this.previousScore = previousScore;
	}	
}
