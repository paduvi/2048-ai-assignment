/**
 * @author chotoxautinh
 *
 * Apr 7, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.chotoxautinh.game.Application;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Application mainApp;
	private CardLayout layout;

	public GameWindow(Application mainApp) {
		this.setMainApp(mainApp);
		initialize();
	}

	private void initialize() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Application.class.getResource("/com/chotoxautinh/game/asset/logo.jpg")));
		setTitle("2048");
		setResizable(false);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				Object objButtons[] = { "Yes", "No" };
				int promptResult = JOptionPane.showOptionDialog(mainApp.getFrame(), "Are you sure you want to exit?",
						"Hello... It's me!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
						new ImageIcon(Application.class.getResource("/com/chotoxautinh/game/asset/9_50x50.png")),
						objButtons, objButtons[1]);
				if (promptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		setMenuBar();
		setLayout();
		showMainMenu();
	}

	private void setMenuBar() {
		MenuBar menuBar = new MenuBar(mainApp);
		setJMenuBar(menuBar);
	}

	private void setLayout() {
		layout = new CardLayout(0, 0);
		getContentPane().setLayout(layout);
	}

	private void showMainMenu() {
		add(new MainMenu(mainApp), "Main Menu");
		layout.show(getContentPane(), "Main Menu");
	}

	public Application getMainApp() {
		return mainApp;
	}

	public void setMainApp(Application mainApp) {
		this.mainApp = mainApp;
	}

	public CardLayout getLayout() {
		return layout;
	}
}
