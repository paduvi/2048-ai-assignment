/**
 * @author chotoxautinh
 *
 * Apr 22, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.model;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.chotoxautinh.ai.GameAgent;
import com.chotoxautinh.game.view.ui.SimulationModeUI;

public class SimulatedTask extends SwingWorker<Board, Board> {

	private SimulationModeUI gameUI;
	private double percent = 0;
	private double proportion;
	private final static DecimalFormat df = new DecimalFormat("#.##");

	/**
	 * 
	 */
	public SimulatedTask(SimulationModeUI gameUI) {
		this.gameUI = gameUI;
		this.proportion = 100 * 1.0 / gameUI.getNumberOfGame();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Board doInBackground() throws Exception {
		Board board = new Board(4);
		GameAgent gameAgent = new GameAgent(gameUI.getDepth());
		while (!board.isTerminated()) {
			publish(board);
			Direction direction = gameAgent.process(board);
			if (direction == Direction.NONE)
				break;
			board.move(direction);
			if (board.hasWon())
				break;
			board.addRandomCell();
		}
		return board;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
	protected void process(List<Board> chunks) {
		Board board = chunks.get(0);
		double temp = gameUI.getProgressPercent() - percent;
		if (board.getActualScore() < Board.MINIMUM_WIN_SCORE) {
			percent = board.getActualScore() * proportion / Board.MINIMUM_WIN_SCORE;
		} else {
			percent = 0.99 * proportion;
		}
		gameUI.setProgressPercent(temp + percent);
		gameUI.getProgressLabel().setText(df.format(gameUI.getProgressPercent()) + "%");
		gameUI.getProgressBar().setValue((int) Math.floor(gameUI.getProgressPercent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		Board board;
		try {
			board = get();
			gameUI.setProgressPercent(gameUI.getProgressPercent() - percent + proportion);
			gameUI.getProgressLabel().setText(df.format(gameUI.getProgressPercent()) + "%");
			gameUI.getProgressBar().setValue((int) Math.floor(gameUI.getProgressPercent()));
			gameUI.showResult(board);
		} catch (InterruptedException e) {
			System.out.println("Canceled game");
		} catch (ExecutionException e) {
			gameUI.showResult(null);
		}
	}

}
