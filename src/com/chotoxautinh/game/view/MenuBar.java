/**
 * @author chotoxautinh
 *
 * Apr 7, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
		addMenuItem(mnFile, "Exit", KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK), exitHandler);
		add(mnFile);

		JMenu mnAbout = new JMenu("About");
		addMenuItem(mnAbout, "Help", KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), helpHandler);
		addMenuItem(mnAbout, "Copyleft", KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), copyleftHandler);
		add(mnAbout);
	}

	private void addMenuItem(JMenu menu, String title, KeyStroke key, ActionListener listener) {
		JMenuItem mnItem = new JMenuItem(title);
		mnItem.setAccelerator(key);
		mnItem.addActionListener(listener);
		menu.add(mnItem);
	}

	private ActionListener exitHandler = o -> mainApp.getFrame()
			.dispatchEvent(new WindowEvent(mainApp.getFrame(), WindowEvent.WINDOW_CLOSING));

	private ActionListener helpHandler = o -> JOptionPane.showMessageDialog(mainApp.getFrame(),
			"Wut? This is 2048 Resolver App\nAnd still want more fucking help?\nGO TO HELL, FUCKING IDIOT!!!", "Help",
			JOptionPane.INFORMATION_MESSAGE,
			new ImageIcon(Application.class.getResource("/com/chotoxautinh/game/asset/36_50x50.png")));

	private ActionListener copyleftHandler = o -> JOptionPane.showMessageDialog(mainApp.getFrame(), "Chó To Xấu Tính",
			"Copyleft", JOptionPane.INFORMATION_MESSAGE,
			new ImageIcon(Application.class.getResource("/com/chotoxautinh/game/asset/17_50x50.png")));

	public Application getMainApp() {
		return mainApp;
	}

	public void setMainApp(Application mainApp) {
		this.mainApp = mainApp;
	}
}
