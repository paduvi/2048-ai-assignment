/**
 * @author chotoxautinh
 *
 * Apr 7, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.CardLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMenuBar();
		setLayout();
		showMainMenu();
	}
	
	private void setMenuBar(){
		MenuBar menuBar = new MenuBar(mainApp);
		setJMenuBar(menuBar);
	}
	
	private void setLayout(){
		layout = new CardLayout(0, 0);
		getContentPane().setLayout(layout);
	}
	
	private void showMainMenu(){
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
