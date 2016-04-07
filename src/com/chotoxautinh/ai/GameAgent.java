package com.chotoxautinh.ai;

import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.model.Node;

public class GameAgent {

	private Node treeRoot;
	private int depth;

	public GameAgent(int depth) {
		this.depth = depth;
	}

	public void build(Board board) throws CloneNotSupportedException {
		treeRoot = alphaBeta(board, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private Node alphaBeta(Board board, int depth, boolean playerTurn, int alpha, int beta) throws CloneNotSupportedException {
		Node node = new Node();
		Direction bestDirection = Direction.NONE; // NONE = computer's turn
		if (board.isTerminated()) {
			if (board.hasWon())
				node.setValue(Integer.MAX_VALUE);
			else
				node.setValue(Math.min(board.getActualScore(), 1));
		} else if (depth == 0) {
			setHeuristicScore(node, board);
		} else {
			// alpha-beta pruning
		}
		node.setDirection(bestDirection);
		return node;
	}

	private void setHeuristicScore(Node node, Board board) {
		int heuristicScore = 0;

		node.setValue(heuristicScore);
	}

	public Direction getDirection() {
		return treeRoot.getDirection();
	}
	
}
