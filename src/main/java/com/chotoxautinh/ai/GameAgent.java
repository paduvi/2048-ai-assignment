package com.chotoxautinh.ai;

import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.model.Node;

public class GameAgent {

	private Node treeRoot;
	private int depth;

	public GameAgent(int depth) {
		this.setDepth(depth);
		this.treeRoot = new Node();
	}

	public Direction process(Board board) throws Exception {
		System.out.println(depth);
		treeRoot = alphaBeta(board, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return treeRoot.getDirection();
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	private Node alphaBeta(Board board, int depth, boolean isPlayerTurn, int alpha, int beta)
			throws Exception {
		Node node = new Node();
		
		if (board.isTerminated()) {
			if (board.hasWon())
				node.setValue(Integer.MAX_VALUE);
			else {
				node.setValue(Math.min(board.getActualScore(), 0));
				node.setLeaf(true);
			}
		} else if (depth == 0) {
			this.setHeuristicScore(node, board);
			node.setLeaf(true);
		} else {
			// alpha-beta pruning
			if (isPlayerTurn) {
				for (int i = 1; i < Direction.values().length; i++) {
					Board boardOfChildNode = (Board) board.clone();
					Node childNode = new Node();
					if (boardOfChildNode.canMove(Direction.values()[i])) {
						boardOfChildNode.move(Direction.values()[i]);
						childNode = alphaBeta(boardOfChildNode, depth - 1, false, alpha, beta);
						alpha = Math.max(alpha, childNode.getValue());
						childNode.setValueDirOfChild(Direction.values()[i]);
						childNode.setActualScore(boardOfChildNode.getActualScore());
						node.appendChild(childNode);
						if (beta <= alpha) break;
					}
				}
				node.setValue(alpha);
			} else {
				int[] newvalue = { 2, 4 };
				for (int emptyCellPos = 0; emptyCellPos < board.getNumberOfEmptyCells(); emptyCellPos++) {
					Board boardOfChild = (Board) board.clone();
					
					int row = board.getEmptyCellIds().get(emptyCellPos) / 4;
					int col = board.getEmptyCellIds().get(emptyCellPos) % 4;
					
					for (int valueOfCell : newvalue) {
						boardOfChild.setValueToCell(valueOfCell, row, col);
						Node child = new Node();
						child = alphaBeta(boardOfChild, depth - 1, true, alpha, beta);
						beta = Math.min(beta, child.getValue());
						node.appendChild(child);
						if (beta <= alpha) break;
					}
					if (beta <= alpha) break;
				}
				node.setValue(beta);
			}
		}
		
		Direction bestDirection = this.findBestDirectionForPlayer(isPlayerTurn, node);
		node.setDirection(bestDirection);
		
		return node;
	}
	
	private Direction findBestDirectionForPlayer(boolean isPlayerTurn, Node node){
		Direction bestDirection = Direction.NONE;
		// Direction.NONE means it is the computer's turn
		if (isPlayerTurn && !node.isLeaf()) {
			int maxHeuristic = node.getChildren().get(0).getValue();
			bestDirection = node.getChildren().get(0).getValueDirOfChild();
			//int maxActualScore = node.getChildren().get(0).getActualScore();
			for (int i = 0; i < node.getChildren().size(); i++) {
				Node child = new Node();
				child = node.getChildren().get(i);
				if (child.getValue() > maxHeuristic) {
					maxHeuristic = child.getValue();
					bestDirection = child.getValueDirOfChild();
<<<<<<< HEAD
					maxActualScore = child.getActualScore();
				} else if (child.getValue() == maxHeuristic) {
					// All children whose maxheuristic are the same,
					// choose the child with maximum ActualScore
=======
					//maxActualScore = child.getActualScore();
				}
				// Try with this
				/*else if (child.getValue() == maxHeuristic) {
					// In all child who have maxheuristic equal each other,
					// choose child have ActualScore is maximum
>>>>>>> f1f05b0ffdc17141dcd3d3bc2feb61cef6e118d4
					if (child.getActualScore() > maxActualScore) {
						bestDirection = child.getValueDirOfChild();
					}
				}*/
			}
		}
		return bestDirection;
	}
	
	private void setHeuristicScore(Node node, Board board) {
		int heuristicScore = 0;
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
}
