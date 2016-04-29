package com.chotoxautinh.testunit;

import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.model.Node;

public class DirectionTest {

	public static void main(String[] args) {
		Node node = new Node(new Board(4));
		System.out.println(node.getDirection());
		System.out.println(node.getDirection() == Direction.NONE);
	}
}
