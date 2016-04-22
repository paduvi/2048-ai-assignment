/**
 * @author chotoxautinh
 *
 * Apr 22, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.util;

public class StringUtils {
	public static boolean isPositiveInteger(String input) {
		String regex = "^[1-9]\\d*$";
		if (input.matches(regex))
			return true;
		return false;
	}
}
