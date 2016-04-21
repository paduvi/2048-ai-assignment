/**
 * @author chotoxautinh
 *
 * Apr 15, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view.ui;

import com.chotoxautinh.game.Application;
import com.chotoxautinh.game.model.Direction;

public interface GameUI {
	
	void initialize();

	void setMainApp(Application mainApp);

	/**
	 * @return
	 */
	int getDepth();

	/**
	 * 
	 */
	void receiveMoveResponse();

	/**
	 * @param actualScore
	 */
	void setScore(int actualScore);

	/**
	 * 
	 */
	void displayWinResult();

	/**
	 * @param direction
	 */
	void receiveHint(Direction direction);

	/**
	 * 
	 */
	void displayLoseResult();

}
