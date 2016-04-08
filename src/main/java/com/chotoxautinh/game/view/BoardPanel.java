/**
 * @author chotoxautinh
 *
 * Apr 9, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.chotoxautinh.game.model.Board;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static int HGAP = 15;
	public static int VGAP = 15;
	public static int CELL_SIZE = 106;

	private Board board;
	Image bgImage;
	Map<Integer, Image> mapImage = new HashMap<>();

	public BoardPanel(Board board) {
		setBoard(board);
		loadImage();
	}

	private void loadImage() {
		bgImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/tiles/background.png"));
		mapImage.put(0, Toolkit.getDefaultToolkit().createImage(getClass().getResource("/tiles/0.gif")));
		for (int i = 2; i < 2048; i = i * 2) {
			mapImage.put(i, Toolkit.getDefaultToolkit().createImage(getClass().getResource("/tiles/" + i + ".gif")));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawBoard(g);
	}

	private void drawBackground(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bgImage, 0, 0, this);
	}

	private void drawBoard(Graphics g) {
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				int x = HGAP * (i + 1) + CELL_SIZE * i;
				int y = VGAP * (j + 1) + CELL_SIZE * j;
				int cellValue = board.getCells()[i][j];
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(mapImage.get(cellValue), x, y, this);
			}
		}
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
