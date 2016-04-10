package com.chotoxautinh.game.controller;

import java.util.Enumeration;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractButton;
import javax.swing.SwingWorker;

import com.chotoxautinh.ai.GameAgent;
import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.view.GameUI;

public class GameController {
	private int depth = 3;
	private GameUI gameUI;
	private Board board;
	private Board oldBoard;
	private GameAgent gameAgent;
	private SwingWorker<Direction, Object> getHintTask;

	/**
	 * 
	 */
	public GameController(GameUI gameUI) {
		setGameUI(gameUI);
		gameAgent = new GameAgent(depth);
		setDepth();
		initialize();
	}

	public void initialize() {
		setBoard(new Board(4));
		getHint();
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
				setDepth(index * 2);
			}
		}
		gameAgent.setDepth(depth);
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

	public void moveResponse() {
		gameUI.receiveMoveResponse();
	}

	private void saveHistory() {
		try {
			setOldBoard((Board) board.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
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
			getHint();
			moveResponse();
		}
	}

	public Board getOldBoard() {
		return oldBoard;
	}

	public void setOldBoard(Board oldBoard) {
		this.oldBoard = oldBoard;
	}

	private void handleHint(Direction direction) {
		gameUI.receiveHint(direction);
	}

	public void getHint() {
		if (getHintTask != null)
			getHintTask.cancel(true);
		getHintTask = new GetHintTask();
		getHintTask.execute();
	}

	private class GetHintTask extends SwingWorker<Direction, Object> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.SwingWorker#doInBackground()
		 */
		@Override
		protected Direction doInBackground() throws Exception {
			return gameAgent.process(board);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.SwingWorker#done()
		 */
		@Override
		protected void done() {
			try {
				handleHint(get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

	}
}
