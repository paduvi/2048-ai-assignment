/**
 * @author chotoxautinh
 *
 * Apr 8, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import com.chotoxautinh.game.Application;
import com.chotoxautinh.game.controller.GameController;
import com.chotoxautinh.game.model.Direction;

public class GameUI extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int LOW = 1;
	public static final int MEDIUM = 2;
	public static final int HIGH = 3;

	private Application mainApp;

	private GameController gameController;

	private JPanel controlPanel;
	private JPanel gamePanel;

	private CardLayout gameLayout;
	private BoardPanel boardPanel;

	private JLabel scoreLbl;
	private JLabel hintLbl;
	private JProgressBar progressBar;
	private ButtonGroup btnGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public GameUI(Application mainApp) {
		this.setMainApp(mainApp);
		initialize();

		setKeyBindings();
	}

	private void initialize() {
		gameController = new GameController(this);
		setLayout();
		createGamePanel();
		createControlPanel();
	}

	private void setKeyBindings() {
		ActionMap actionMap = getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), Direction.LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), Direction.RIGHT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Direction.UP);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), Direction.DOWN);

		actionMap.put(Direction.LEFT, new MoveAction(Direction.LEFT));
		actionMap.put(Direction.RIGHT, new MoveAction(Direction.RIGHT));
		actionMap.put(Direction.UP, new MoveAction(Direction.UP));
		actionMap.put(Direction.DOWN, new MoveAction(Direction.DOWN));

	}

	private void setLayout() {
		setAlignmentY(Component.TOP_ALIGNMENT);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 1.0 * 5 / 7, 1.0 * 2 / 7 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		setLayout(gridBagLayout);
	}

	private void createGamePanel() {
		gamePanel = new JPanel();
		gamePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_gamePanel = new GridBagConstraints();
		gbc_gamePanel.fill = GridBagConstraints.BOTH;
		gbc_gamePanel.insets = new Insets(0, 0, 0, 0);
		gbc_gamePanel.gridx = 0;
		gbc_gamePanel.gridy = 0;
		add(gamePanel, gbc_gamePanel);

		gameLayout = new CardLayout(0, 0);
		gamePanel.setLayout(gameLayout);

		boardPanel = new BoardPanel(gameController.getBoard());
		gamePanel.add(boardPanel, "boardPanel");

		ImagePanel winPanel = new ImagePanel(getClass().getResource("/tiles/2048.gif"));
		gamePanel.add(winPanel, "winPanel");

		ImagePanel losePanel = new ImagePanel(getClass().getResource("/tiles/game-over.gif"));
		gamePanel.add(losePanel, "losePanel");

		displayGameLayout("boardPanel");
		// displayGameLayout("winPanel");
		// displayGameLayout("losePanel");
	}

	public void displayGameLayout(String name) {
		gameLayout.show(gamePanel, name);
	}

	private void createControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setLayout(null);
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.insets = new Insets(0, 0, 0, 0);
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 1;
		gbc_controlPanel.gridy = 0;
		add(controlPanel, gbc_controlPanel);

		addComponentToControlPanel();
	}

	private void addComponentToControlPanel() {
		JLabel label = new JLabel("Score: ");
		label.setBounds(37, 11, 53, 14);
		controlPanel.add(label);

		scoreLbl = new JLabel("0");
		scoreLbl.setBounds(83, 11, 89, 14);
		controlPanel.add(scoreLbl);

		JButton btnUndo = new JButton("Undo");
		btnUndo.setBounds(20, 36, 70, 23);
		btnUndo.setFocusable(false);
		btnUndo.setBackground(SystemColor.info);
		controlPanel.add(btnUndo);

		JButton btnAuto = new JButton("Auto");
		btnAuto.setBounds(102, 36, 70, 23);
		btnAuto.setFocusable(false);
		btnAuto.setBackground(SystemColor.info);
		controlPanel.add(btnAuto);

		JLabel label1 = new JLabel("Hint:");
		label1.setBounds(37, 70, 46, 14);
		controlPanel.add(label1);

		hintLbl = new JLabel("Loading...");
		hintLbl.setBounds(83, 70, 89, 14);
		controlPanel.add(hintLbl);

		progressBar = new JProgressBar();
		progressBar.setBounds(20, 101, 152, 8);
		controlPanel.add(progressBar);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 141, 197, 2);
		controlPanel.add(separator);

		JLabel lblResolverLevel = new JLabel("Resolver Level:");
		lblResolverLevel.setBounds(20, 154, 152, 14);
		controlPanel.add(lblResolverLevel);

		JRadioButton rdbtnLow = new JRadioButton("Low");
		rdbtnLow.setBounds(20, 175, 109, 23);
		rdbtnLow.setFocusable(false);
		rdbtnLow.setSelected(true);
		controlPanel.add(rdbtnLow);
		btnGroup.add(rdbtnLow);

		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setBounds(20, 201, 109, 23);
		rdbtnMedium.setFocusable(false);
		controlPanel.add(rdbtnMedium);
		btnGroup.add(rdbtnMedium);

		JRadioButton rdbtnHigh = new JRadioButton("High");
		rdbtnHigh.setBounds(20, 227, 109, 23);
		rdbtnHigh.setFocusable(false);
		controlPanel.add(rdbtnHigh);
		btnGroup.add(rdbtnHigh);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(56, 257, 89, 23);
		btnRefresh.setFocusable(false);
		btnRefresh.setBackground(SystemColor.inactiveCaption);
		controlPanel.add(btnRefresh);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 303, 197, 2);
		controlPanel.add(separator_1);

		JButton btnBackToMain = new JButton("Main Menu");
		btnBackToMain.setBounds(20, 431, 152, 23);
		btnBackToMain.setFocusable(false);
		btnBackToMain.setBackground(SystemColor.controlLtHighlight);
		btnBackToMain.addActionListener(o -> {
			mainApp.getFrame().backToMainMenu();
		});
		controlPanel.add(btnBackToMain);

		JButton btnReplay = new JButton("New Game");
		btnReplay.setBounds(20, 397, 152, 23);
		btnReplay.setFocusable(false);
		btnReplay.setBackground(SystemColor.controlLtHighlight);
		controlPanel.add(btnReplay);
	}

	public Application getMainApp() {
		return mainApp;
	}

	public void setMainApp(Application mainApp) {
		this.mainApp = mainApp;
	}

	public GameController getGameController() {
		return gameController;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public ButtonGroup getBtnGroup() {
		return btnGroup;
	}

	private class MoveAction extends AbstractAction {
		private static final long serialVersionUID = 3334610052515363537L;
		private Direction direction;

		public MoveAction(Direction direction) {
			this.direction = direction;
		}

		@Override
		public void actionPerformed(ActionEvent actionEvt) {
			switch (direction) {
			case UP:
				System.out.println("UP");
				break;

			default:
				break;
			}
			gameController.move(direction);
		}
	}
}
