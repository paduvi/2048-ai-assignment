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
		gameUI.toggleUndoBtn(false);
		gameUI.setScore(0);
		gameUI.setBoard(board);
		gameUI.displayBoardPanel();
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
	}

	public GameUI getGameUI() {
		return gameUI;
	}

	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}

	public void toggleUndoBtn(boolean enabled) {
		if (enabled) {
			try {
				setOldBoard((Board) board.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		} else {
			setBoard(oldBoard);
			gameUI.setBoard(oldBoard);
			gameUI.setScore(oldBoard.getActualScore());
		}
		gameUI.toggleUndoBtn(enabled);
	}

	public void move(Direction direction) throws CloneNotSupportedException {
		if (board.canMove(direction)) {
			toggleUndoBtn(true);
			board.move(direction);
			gameUI.setScore(board.getActualScore());
			if (board.hasWon()) {
				gameUI.displayWinLayout();
				return;
			}
			board.addRandomCell();
			if (board.isTerminated()) {
				gameUI.displayLosePanel(board.getActualScore());
				return;
			}
		}
	}

	public Board getOldBoard() {
		return oldBoard;
	}

	public void setOldBoard(Board oldBoard) {
		this.oldBoard = oldBoard;
	}
}
