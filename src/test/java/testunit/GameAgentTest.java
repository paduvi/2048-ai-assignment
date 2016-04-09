package testunit;

import com.chotoxautinh.ai.GameAgent;
import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Node;

public class GameAgentTest {
	public static void main(String[] args) throws Exception {
		Board board = new Board(4);
		for (int i=2; i < 7; i++) {
			System.out.println("If use a tree with depth = " + i);
			long start = System.currentTimeMillis();
			GameAgent gameAgent = new GameAgent(i);
			Board copyBoard = new Board(board);
			System.out.println("Original board");
			copyBoard.display();
			gameAgent.build(copyBoard);
			Node treeRoot = gameAgent.getTreeRoot();
			//printTree(treeRoot, i);
			System.out.println("Best Direction of TreeRoot: "+treeRoot.getDirection());
			long time = System.currentTimeMillis() - start;
			System.out.println("Run time is: " + time);
			System.out.println();
		}
	}
	
	public static void printTree(Node root, int depth) {
		System.out.println("Value of node in depth = " + depth);
		System.out.println(root.getValue());
		if (root.isLeaf()) return;
		for (Node child : root.getChildren()) {
			printTree(child, depth-1);
		}
	}
}
