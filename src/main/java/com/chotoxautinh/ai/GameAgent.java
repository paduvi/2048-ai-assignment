package com.chotoxautinh.ai;

import java.util.Collections;

import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.model.Node;

public class GameAgent {

	private Node treeRoot;
	private int depth;
	private boolean cancelled;

	public GameAgent(int depth) {
		this.setDepth(depth);
	}

	public Direction process(Board board) throws CloneNotSupportedException {
		this.treeRoot = new Node();
		setCancelled(false);
		treeRoot.setBoard(board);
//		Long start = System.currentTimeMillis();
		alphaBeta(treeRoot, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
//		System.out.println(System.currentTimeMillis() - start);
		return findBestDirectionForPlayer(treeRoot);
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private void alphaBeta(Node root, int depth, int alpha, int beta) throws CloneNotSupportedException {
		if (isCancelled())
			return;
		Board board = root.getBoard();

		if (board.isTerminated()) {
			if (board.hasWon()) {
				root.setValue(Integer.MAX_VALUE);
				return;
			}
			root.setValue(Math.min(board.getActualScore(), 0));
			root.setLeaf(true);
			return;
		}
		if (root.getDirection() == Direction.NONE && board.getNumberOfEmptyCells() == 0) {
			root.setValue(Math.min(board.getActualScore(), 0));
			root.setLeaf(true);
			return;
		}
		if (depth == 0) {
			setHeuristicScore(root);
			root.setLeaf(true);
			return;
		}
		// alpha-beta pruning
		if (root.getDirection() != Direction.NONE) {
			for (int i = 1; i < Direction.values().length; i++) {
				Direction direction = Direction.values()[i];
				if (board.canMove(direction)) {
					Node child = new Node();
					child.setDirection(direction);
					Board boardOfChildNode = (Board) board.clone();
					boardOfChildNode.move(direction);
					child.setBoard(boardOfChildNode);
					setHeuristicScore(child);
					root.appendChild(child);
				}
			}

			// sort list
			Collections.sort(root.getChildren(), Node.DESC_COMPARATOR);

			for (Node node : root.getChildren()) {
				alphaBeta(node, depth - 1, alpha, beta);
				alpha = Math.max(alpha, node.getValue());
				if (beta <= alpha)
					break;
			}
			root.setValue(alpha);
		} else {
			int[] newvalue = { 2, 4 };
			for (int emptyCellPos = 0; emptyCellPos < board.getNumberOfEmptyCells(); emptyCellPos++) {

				int row = board.getEmptyCellIds().get(emptyCellPos) / 4;
				int col = board.getEmptyCellIds().get(emptyCellPos) % 4;

				for (int valueOfCell : newvalue) {
					Node child = new Node();
					child.setDirection(Direction.NONE);
					Board boardOfChild = (Board) board.clone();
					boardOfChild.setValueToCell(valueOfCell, row, col);
					child.setBoard(boardOfChild);
					setHeuristicScore(child);
					root.appendChild(child);
				}
			}

			// sort list
			Collections.sort(root.getChildren(), Node.ASC_COMPARATOR);

			for (Node node : root.getChildren()) {
				alphaBeta(node, depth - 1, alpha, beta);
				beta = Math.min(beta, node.getValue());
				if (beta <= alpha)
					break;
			}
			root.setValue(beta);
		}
	}

	private static Direction findBestDirectionForPlayer(Node node) {
		for (Node child : node.getChildren()) {
			if (child.getValue() == node.getValue())
				return child.getDirection();
		}
		return Direction.NONE;
	}

	private void setHeuristicScore(Node node) {
		int heuristicScore = 0;
		Board board = node.getBoard();
		// if ActualScore = 0 then heuristic will lead to a math error
		if (board.getActualScore() != 0) {
			heuristicScore = (int) (board.getActualScore()
					+ Math.log(board.getActualScore()) * board.getNumberOfEmptyCells() - board.getClusteringScore());
			// min of heuristic score is 1
			node.setValue(Math.max(heuristicScore, 1));
		} else {
			node.setValue(0);
		}
	}

	public Node getTreeRoot() {
		return this.treeRoot;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	private void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public void cancel() {
		setCancelled(true);
	}

}
