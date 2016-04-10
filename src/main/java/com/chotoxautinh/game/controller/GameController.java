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
	private Board oldBoard;

	/**
	 * 
	 */
	public GameController(GameUI gameUI) {
		setGameUI(gameUI);
		setDepth();
		initialize();
	}

	public void initialize() {
		setBoard(new Board(4));
		gameUI.postGameControllerInit();
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth() {
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
		gameUI.setBoard(board);
	}

	public GameUI getGameUI() {
		return gameUI;
	}

	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}

	public void toggleBtn(boolean enabled) {
		gameUI.toggleBtn(enabled);
	}

	private void saveHistory() {
		try {
			setOldBoard((Board) board.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public void backToPrevious() {
		setBoard(oldBoard);
		gameUI.toggleBtn(false);
	}

	public void move(Direction direction) throws CloneNotSupportedException {
		if (board.canMove(direction)) {
			saveHistory();
			board.move(direction);
			gameUI.setScore(board.getActualScore());
			if (board.hasWon()) {
				gameUI.displayWinResult();
				return;
			}
			board.addRandomCell();
			if (board.isTerminated()) {
				gameUI.displayLoseResult();
				return;
			}
			toggleBtn(true);
		}
	}

	public Board getOldBoard() {
		return oldBoard;
	}

	public void setOldBoard(Board oldBoard) {
		this.oldBoard = oldBoard;
	}
}
