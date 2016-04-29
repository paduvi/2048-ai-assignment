/**
 * @author chotoxautinh
 *
 * Apr 24, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.config;

import java.net.URL;

public class Constant {
	public static final URL DATABASE = Constant.class.getResource("/score/db.json");
	public static final URL TILES = Constant.class.getResource("/tiles/");
	public static final URL STUFF = Constant.class.getResource("/stuff/");
	public static final int SIMULATED_START_DEPTH = 10;
	public static final int NORMAL_START_DEPTH = 10;
	public static final int MAX_THREAD_POOL = 5;
}
