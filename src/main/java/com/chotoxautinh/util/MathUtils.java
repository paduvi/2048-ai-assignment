/**
 * @author chotoxautinh
 *
 * Apr 10, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.util;

public class MathUtils {

	public static int log2floor(int number) {
		return 31 - Integer.numberOfLeadingZeros(number);
	}

	public static int log2ceil(int number) {
		return 32 - Integer.numberOfLeadingZeros(number - 1);
	}
}
