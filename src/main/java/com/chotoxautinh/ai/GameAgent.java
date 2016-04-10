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

	public Direction process(Board board) throws CloneNotSupportedException {
		treeRoot = alphaBeta(board, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return treeRoot.getDirection();
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private Node alphaBeta(Board board, int depth, boolean playerTurn, int alpha, int beta)
			throws CloneNotSupportedException {
		Node node = new Node();
		Direction bestDirection = Direction.NONE; // NONE = computer's turn
		if (board.isTerminated()) {
			if (board.hasWon())
				node.setValue(Integer.MAX_VALUE);
			else
				node.setValue(Math.min(board.getActualScore(), 1));
		} else if (depth == 0) {
			setHeuristicScore(node, board);
			node.setLeaf(true);
		} else {
			// alpha-beta pruning
			if (playerTurn) {
				for (int i = 1; i < Direction.values().length; i++) {
					Board boardOfChild = (Board) board.clone();
					// boardOfChild = board;
					Node child = new Node();
					boardOfChild.move(Direction.values()[i]);
					child = alphaBeta(boardOfChild, depth - 1, false, alpha, beta);
					alpha = Math.max(alpha, child.getValue());
					node.appendChild(child);
					if (beta <= alpha)
						break;
				}
				node.setValue(alpha);

			} else {
				int[] newvalue = { 2, 4 };
				for (int i = 0; i < board.getEmptyCellIds().size(); i++) {
					Board boardOfChild = (Board) board.clone();
					int row = board.getEmptyCellIds().get(i) / 4;
					int col = board.getEmptyCellIds().get(i) % 4;
					for (int valueOfCell : newvalue) {
						try {
							boardOfChild.setValueToCell(valueOfCell, row, col);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Node child = new Node();
						child = alphaBeta(boardOfChild, depth - 1, true, alpha, beta);
						beta = Math.min(beta, child.getValue());
						node.appendChild(child);
						if (beta <= alpha)
							break;
					}
					if (beta <= alpha)
						break;
				}
				node.setValue(beta);
			}
		}

		// find bestDirection of Player
		if (playerTurn && !node.isLeaf()) {
			int maxHeuristic = node.getChildren().get(0).getValue();
			bestDirection = Direction.values()[1];
			for (int i = 0; i < node.getChildren().size(); i++) {
				if (node.getChildren().get(i).getValue() > maxHeuristic) {
					maxHeuristic = node.getChildren().get(i).getValue();
					bestDirection = Direction.values()[i + 1];
				}
			}
		}
		node.setDirection(bestDirection);
		return node;
	}

	private void setHeuristicScore(Node node, Board board) {
		int heuristicScore = 0;
		// if ActualScore = 0 then heuristic is math error
		if (board.getActualScore() == 0) {
			node.setValue(0);
		} else {
			heuristicScore = (int) (board.getActualScore()
					+ Math.log(board.getActualScore()) * board.getNumberOfEmptyCells() - board.getClusteringScore());
			// min of heuristic score is 1
			node.setValue(Math.max(heuristicScore, 1));
		}
	}

	public Node getTreeRoot() {
		return treeRoot;
	}
}
