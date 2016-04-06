/**
 * @author chotoxautinh
 *
 * Apr 7, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.chotoxautinh.game.Application;

public class MenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Application mainApp;

	/**
	 * 
	 */
	public MenuBar(Application mainApp) {
		this.setMainApp(mainApp);
		initComponent();
	}

	private void initComponent() {
		JMenu mnFile = new JMenu("File");
		addMenuItem(mnFile, "Main Menu", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), null);
		addMenuItem(mnFile, "Exit", KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK), null);
		add(mnFile);

		JMenu mnAbout = new JMenu("About");
		addMenuItem(mnAbout, "Help", KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), null);
		addMenuItem(mnAbout, "Copyleft", KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), null);
		add(mnAbout);
	}

	private void addMenuItem(JMenu menu, String title, KeyStroke key, ActionListener listener) {
		JMenuItem mnItem = new JMenuItem(title);
		mnItem.setAccelerator(key);
		mnItem.addActionListener(listener);
		menu.add(mnItem);
	}

	public Application getMainApp() {
		return mainApp;
	}

	public void setMainApp(Application mainApp) {
		this.mainApp = mainApp;
	}
}
