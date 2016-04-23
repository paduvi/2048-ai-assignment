/**
 * @author chotoxautinh
 *
 * Apr 8, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.game.view.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import com.chotoxautinh.game.Application;
import com.chotoxautinh.game.controller.GameController;
import com.chotoxautinh.game.model.Direction;
import com.chotoxautinh.game.view.component.BoardPanel;
import com.chotoxautinh.game.view.component.CardPanel;
import com.chotoxautinh.game.view.component.ImagePanel;

public class NewGameModeUI extends JPanel implements CardPanel{

	private static final long serialVersionUID = 1L;

	public static final String WIN = "win";
	public static final String LOSE = "lose";
	public static final String INGAME = "board";

	private Application mainApp;

	private GameController gameController;

	private JPanel controlPanel;
	private JPanel gamePanel;

	private CardLayout gameLayout;
	private BoardPanel boardPanel;

	private JButton btnUndo;
	private JButton btnAuto;
	private JLabel scoreLbl;
	private JLabel hintLbl;
	private JProgressBar progressBar;
	private ButtonGroup btnGroup = new ButtonGroup();

	private boolean auto = false;
	private Direction hintDirection;

	/**
	 * Create the panel.
	 */
	public NewGameModeUI(Application mainApp) {
		this.setMainApp(mainApp);
		initLayout();
		initialize();

		setKeyBindings();
	}

	private void initLayout() {
		setLayout();
		createControlPanel();
		createGamePanel();
	}

	public void initialize() {
		gameController = new GameController(this);
		postGameControllerInit();
	}

	private void postGameControllerInit() {
		boardPanel.setBoard(gameController.getBoard());
		setScore(0);
		displayBoardPanel();
		loading();
		btnUndo.setEnabled(false);
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

		boardPanel = new BoardPanel(null);
		gamePanel.add(boardPanel, INGAME);

		ImagePanel winPanel = new ImagePanel(getClass().getResource("/tiles/2048.gif"));
		gamePanel.add(winPanel, WIN);

		ImagePanel losePanel = new ImagePanel(getClass().getResource("/tiles/game-over.gif"));
		gamePanel.add(losePanel, LOSE);
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

		btnUndo = new JButton("Undo");
		btnUndo.setBounds(20, 36, 70, 23);
		btnUndo.setFocusable(false);
		btnUndo.setBackground(SystemColor.info);
		btnUndo.addActionListener(undoHandler);
		controlPanel.add(btnUndo);

		btnAuto = new JButton("Auto");
		btnAuto.setBounds(102, 36, 70, 23);
		btnAuto.setFocusable(false);
		btnAuto.addActionListener(autoHandler);
		btnAuto.setBackground(SystemColor.info);
		controlPanel.add(btnAuto);

		JLabel label1 = new JLabel("Hint:");
		label1.setBounds(37, 70, 46, 14);
		controlPanel.add(label1);

		hintLbl = new JLabel("Loading...");
		hintLbl.setBounds(69, 70, 103, 14);
		controlPanel.add(hintLbl);

		progressBar = new JProgressBar();
		progressBar.setBounds(20, 101, 152, 14);
		controlPanel.add(progressBar);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 158, 197, 2);
		controlPanel.add(separator);

		JLabel lblResolverLevel = new JLabel("Resolver Level:");
		lblResolverLevel.setBounds(20, 171, 152, 14);
		controlPanel.add(lblResolverLevel);

		JRadioButton rdbtnLow = new JRadioButton("6");
		rdbtnLow.setBounds(20, 192, 109, 23);
		rdbtnLow.setFocusable(false);
		rdbtnLow.setSelected(true);
		rdbtnLow.addActionListener(changeListener);
		controlPanel.add(rdbtnLow);
		btnGroup.add(rdbtnLow);

		JRadioButton rdbtnMedium = new JRadioButton("8");
		rdbtnMedium.setBounds(20, 218, 109, 23);
		rdbtnMedium.setFocusable(false);
		rdbtnMedium.addActionListener(changeListener);
		controlPanel.add(rdbtnMedium);
		btnGroup.add(rdbtnMedium);

		JRadioButton rdbtnHigh = new JRadioButton("10");
		rdbtnHigh.setBounds(20, 244, 109, 23);
		rdbtnHigh.setFocusable(false);
		rdbtnHigh.addActionListener(changeListener);
		controlPanel.add(rdbtnHigh);
		btnGroup.add(rdbtnHigh);

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
		btnReplay.addActionListener(newGameHandler);
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

	public int getDepth() {
		Enumeration<AbstractButton> list = btnGroup.getElements();
		int start = 4;
		int index = 0;
		while (list.hasMoreElements()) {
			AbstractButton btn = list.nextElement();
			index++;
			if (btn.isSelected()) {
				break;
			}
		}
		return start + index * 2;
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setScore(int score) {
		scoreLbl.setText(String.valueOf(score));
	}

	private void displayBoardPanel() {
		displayGameLayout(INGAME);
		mainApp.setIngame(true);
	}

	public void displayLoseResult() {
		JOptionPane.showMessageDialog(mainApp.getFrame(), "Muahahahahaha!",
				"GAME OVER! Your score is: " + gameController.getBoard().getActualScore(),
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(MenuBar.class.getResource("/stuff/12_50x50.png")));
		displayGameLayout(LOSE);
		endGame();
	}

	public void displayWinResult() {
		displayGameLayout(WIN);
		endGame();
	}

	private void endGame() {
		btnUndo.setEnabled(false);
		mainApp.setIngame(false);
		hintLbl.setText("");
		progressBar.setIndeterminate(false);
		progressBar.setValue(0);
	}

	public void receiveMoveResponse() {
		if (!auto)
			btnUndo.setEnabled(true);
	}

	public void receiveHint(Direction direction) {
		if (direction == Direction.NONE) {
			hintLbl.setText("Cannot calculate");
		} else {
			hintLbl.setText(direction.getDescription());
		}
		hintDirection = direction;
		if (!auto) {
			progressBar.setIndeterminate(false);
			progressBar.setValue(0);
		} else {
			move(direction);
		}
	}

	public boolean isAuto() {
		return auto;
	}

	private ActionListener undoHandler = o -> {
		if (mainApp.isIngame()) {
			gameController.setBoard(gameController.getOldBoard());
			boardPanel.setBoard(gameController.getBoard());
			setScore(gameController.getBoard().getActualScore());
			btnUndo.setEnabled(false);
		}
	};

	private ActionListener newGameHandler = o -> {
		if (mainApp.isIngame()) {
			Object objButtons[] = { "Yes", "No" };
			int promptResult = JOptionPane.showOptionDialog(mainApp.getFrame(), "Do you want to start a new game?",
					"Hello... It's me!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
					new ImageIcon(Application.class.getResource("/stuff/8_50x50.png")), objButtons, objButtons[1]);
			if (promptResult == JOptionPane.YES_OPTION) {
				initialize();
			}
		} else {
			initialize();
		}
	};

	private ActionListener autoHandler = o -> {
		toggleAutoBtn(!auto);
		if (auto) {
			btnUndo.setEnabled(false);
			if (hintDirection != null)
				move(hintDirection);
		} else if (gameController.getOldBoard() != null) {
			btnUndo.setEnabled(true);
		}
	};

	private void toggleAutoBtn(boolean enabled) {
		if (!enabled) {
			btnAuto.setBackground(SystemColor.info);
			auto = false;
		} else {
			btnAuto.setBackground(Color.GREEN);
			auto = true;
		}
	}

	private ActionListener changeListener = o -> {
		gameController.setDepth();
		if (!auto) {
			loading();
			gameController.getHint();
		}
	};

	private void loading() {
		hintDirection = null;
		hintLbl.setText("Loading...");
		progressBar.setIndeterminate(true);
	}

	private void move(Direction direction) {
		try {
			if (mainApp.isIngame()) {
				if (direction == Direction.NONE && auto)
					toggleAutoBtn(false);
				if (gameController.getBoard().canMove(direction)) {
					loading();
					gameController.move(direction);
				}
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	private class MoveAction extends AbstractAction {
		private static final long serialVersionUID = 3334610052515363537L;
		private Direction direction;

		public MoveAction(Direction direction) {
			this.direction = direction;
		}

		@Override
		public void actionPerformed(ActionEvent actionEvt) {
			if (auto)
				return;
			move(direction);
		}
	}

	/* (non-Javadoc)
	 * @see com.chotoxautinh.game.view.component.CardPanel#closed()
	 */
	@Override
	public void closed() {
		// TODO Auto-generated method stub
		
	}

}
