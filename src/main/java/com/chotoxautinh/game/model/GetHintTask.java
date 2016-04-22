/**
 * @author chotoxautinh
 *
 * Apr 22, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.model;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.chotoxautinh.game.controller.GameController;

public class GetHintTask extends SwingWorker<Direction, Object> {

	public GameController gameController;

	/**
	 * 
	 */
	public GetHintTask(GameController gameController) {
		this.gameController = gameController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Direction doInBackground() throws Exception {
		return gameController.getGameAgent().process(gameController.getBoard());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		try {
			gameController.handleHint(get());
		} catch (InterruptedException e) {
			gameController.handleHint(Direction.NONE);
			e.printStackTrace();
		} catch (ExecutionException e) {
			gameController.handleHint(Direction.NONE);
			e.printStackTrace();
		}
	}

}