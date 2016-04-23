package com.chotoxautinh.game.controller;

import com.chotoxautinh.ai.GameAgent;
import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.model.GetHintTask;
import com.chotoxautinh.game.view.ui.NormalGameModeUI;

public class GameController {
	private int depth = 3;
	private NormalGameModeUI gameUI;
	private Board board;
	private Board oldBoard;
	private GameAgent gameAgent;
	private GetHintTask getHintTask;

	/**
	 * 
	 */
	public GameController(NormalGameModeUI gameUI) {
		setGameUI(gameUI);
		gameAgent = new GameAgent(depth);
		setDepth();
		initialize();
	}

	public void initialize() {
		setOldBoard(null);
		setBoard(new Board(4));
		getHint();
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth() {
		setDepth(gameUI.getDepth());
		gameAgent.setDepth(depth);
		System.out.println(depth);
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

	public NormalGameModeUI getGameUI() {
		return gameUI;
	}

	public void setGameUI(NormalGameModeUI gameUI) {
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

	public Board getOldBoard() {
		return oldBoard;
	}

	public void setOldBoard(Board oldBoard) {
		this.oldBoard = oldBoard;
	}

	public void handleHint(Direction direction) {
		gameUI.receiveHint(direction);
	}
	
	public GameAgent getGameAgent(){
		return gameAgent;
	}

	public void getHint() {
		if (getHintTask != null)
			getHintTask.cancel(true);
		getHintTask = new GetHintTask(this);
		getHintTask.execute();
	}

}
