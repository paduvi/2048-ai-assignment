package com.chotoxautinh.game.controller;

import com.chotoxautinh.game.model.Board;

public class GameController {
	private int depth;
	private Board board;

	/**
	 * 
	 */
	public GameController(int level) {
		setDepth((level + 1) * 2);
		initialize();
	}

	public void initialize() {
		try {
			setBoard(new Board(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
