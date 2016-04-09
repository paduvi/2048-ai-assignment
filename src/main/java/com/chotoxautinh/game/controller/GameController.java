package com.chotoxautinh.game.controller;

import java.util.Enumeration;

import javax.swing.AbstractButton;

import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.view.GameUI;

public class GameController {
	private int depth;
	private GameUI gameUI;
	private Board board;

	/**
	 * 
	 */
	public GameController(GameUI gameUI) {
		setGameUI(gameUI);
		setDepth();
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
	
	public void setDepth(){
		Enumeration<AbstractButton> list = gameUI.getBtnGroup().getElements();
		int index = 0;
		while (list.hasMoreElements()) {
			AbstractButton btn = list.nextElement();
			index++;
			if (btn.isSelected()) {
				setDepth(index * 2 + 1);
			}
		}
	}

	private void setDepth(int depth) {
		this.depth = depth;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public GameUI getGameUI() {
		return gameUI;
	}

	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}
	
	public void move(Direction direction){
		board.move(direction);
	}
}
