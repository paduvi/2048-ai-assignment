/**
 * @author chotoxautinh
 *
 * Apr 10, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.testunit;

public class LogarithTest {

	public static int log2(int number) {
		return 31 - Integer.numberOfLeadingZeros(number);
	}

	public static void main(String[] args) {
		int number = 2048;
		System.out.println("Log 2 of " + number + " is : " + log2(number));
	}
}
