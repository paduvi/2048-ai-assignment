package com.chotoxautinh.testunit;

import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.model.Node;

public class DirectionTest {

	public static void main(String[] args) {
		Node node = new Node();
		System.out.println(node.getDirection());
		System.out.println(node.getDirection() == Direction.NONE);
	}
}
