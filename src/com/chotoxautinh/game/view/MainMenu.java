/**
 * @author chotoxautinh
 *
 * Apr 7, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.chotoxautinh.game.Application;

public class MainMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int NEW_GAME_ICON = 1;
	private static final int SIMULATION_ICON = 8;
	private static final int HIGH_SCORE_ICON = 24;

	private Application mainApp;
	private JPanel mainMn;

	/**
	 * Create the panel.
	 */
	public MainMenu(Application mainApp) {
		this.setMainApp(mainApp);
		initialize();
	}

	private void initialize() {
		setLayout();
		initComponent();
	}

	private void setLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.3, 0.4, 0.3 };
		gridBagLayout.rowWeights = new double[] { 0.3, 0.4, 0.3 };
		setLayout(gridBagLayout);
	}

	private void initComponent() {
		mainMn = new JPanel();
		mainMn.setOpaque(false);
		GridBagConstraints gbc_mainMn = new GridBagConstraints();
		gbc_mainMn.fill = GridBagConstraints.BOTH;
		gbc_mainMn.gridx = 1;
		gbc_mainMn.gridy = 1;
		add(mainMn, gbc_mainMn);
		mainMn.setLayout(new GridLayout(0, 1, 0, 30));

		addBtn("New Game", NEW_GAME_ICON, null);
		addBtn("Simulation", SIMULATION_ICON, null);
		addBtn("High Score", HIGH_SCORE_ICON, null);
	}

	private void addBtn(String title, int imgNum, ActionListener listener) {
		JButton btn = new JButton(title);
		btn.setIcon(new ImageIcon(MainMenu.class.getResource("/com/chotoxautinh/game/asset/" + imgNum + "_50x50.png")));
		btn.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		btn.setFocusable(false);
		btn.setBackground(SystemColor.inactiveCaption);
		btn.addActionListener(listener);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMn.add(btn);
	}

	public Application getMainApp() {
		return mainApp;
	}

	public void setMainApp(Application mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	protected void paintComponent(Graphics g) {

		BufferedImage bgImage;
		try {
			bgImage = ImageIO.read(MainMenu.class.getResource("/com/chotoxautinh/game/asset/background.jpg"));
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
		} catch (IOException e) {
			super.paintComponent(g);
		}
	}

}
