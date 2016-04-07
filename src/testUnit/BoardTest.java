package testUnit;

import com.chotoxautinh.game.model.Board;
import com.chotoxautinh.game.model.Direction;

public class BoardTest {
	public static void main(String args[]) throws Exception{
		Board board = new Board(4);
		
		System.out.println("The number of empty cells: " + board.getNumberOfEmptyCells());
		System.out.println("Empty cell IDs: " + board.getEmptyCellIds());
		
		// board.setValueToCell(10, 4, 2); // Exception: Invalid cell
		board.setValueToAnEmptyCell(2, 2, 3);
		board.setValueToAnEmptyCell(2, 3, 3);
		board.setValueToAnEmptyCell(2, 0, 0);
		
		System.out.println("The number of empty cells: " + board.getNumberOfEmptyCells());
		
		System.out.println("Original board");
		board.display();
		System.out.println("Clustering Score: " + board.getClusteringScore());
		System.out.println("Score: " + board.getActualScore());
		
		System.out.println("After move up");
		board.move(Direction.UP);
		board.display();
		System.out.println("Score: " + board.getActualScore());
		
		System.out.println("After move down");
		board.move(Direction.DOWN);
		board.display();
		System.out.println("Score: " + board.getActualScore());
		
		System.out.println("After move left");
		board.move(Direction.LEFT);
		board.display();
		System.out.println("Score: " + board.getActualScore());
		
		System.out.println("After move right");
		board.move(Direction.RIGHT);
		board.display();
		System.out.println("Score: " + board.getActualScore());
	}
}
