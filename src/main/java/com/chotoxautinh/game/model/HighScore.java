/**
 * @author chotoxautinh
 *
 * Apr 11, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.model;

public class HighScore {
	
	private String name; // Name of Player
	private int score;	// score after game completed
	
	public HighScore(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}
	
}
