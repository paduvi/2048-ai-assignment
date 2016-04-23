package com.chotoxautinh.util;

import java.util.HashMap;
import java.util.List;

import com.chotoxautinh.game.model.HighScore;

public class ListUtils {
	
	public static HashMap<List<HighScore>, Integer> insert(List<HighScore> list, Object object) {
		HighScore highScore = (HighScore) object;
		HashMap<List<HighScore>,Integer> map = new HashMap<List<HighScore>,Integer>();
		int length = list.size();
		int newScore = highScore.getScore();
		if (length == 0) {
			list.add(highScore);
			map.put(list, 0);
		} else {
			for (int i = 0; i < length; i++) {
				if (newScore <= list.get(i).getScore()) {
					if (i+1 == length) {
						if (length < 10) {
							list.add(highScore);
							map.put(list, i+1);
						} else //if length = 10 then remove this element 
							map.put(list, -1);
					} else if (newScore > list.get(i+1).getScore()) {
						list.add(i+1, highScore);
						if (list.size() <= 10) {
							map.put(list, i+1);
						} else {
							//if newlist have length > 10 then remove the last element
							list.remove(10);
							map.put(list, i+1);
						}
						break;
					}
				}
			}
		}
			
		return map;
	}
}
