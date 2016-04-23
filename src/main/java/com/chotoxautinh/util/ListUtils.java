package com.chotoxautinh.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtils {
	
	public static <T extends Comparable<T>> Map<List<T>, Integer> insert(List<T> list, T object) {
		Map<List<T>,Integer> map = new HashMap<List<T>,Integer>();
		int index = 0;
		for(T item : list){
			if(object.compareTo(item) > 0)
				break;
			index++;
		}
		list.add(index, object);
		map.put(list, index);
			
		return map;
	}
}
