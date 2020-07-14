package main.java.edu.miu.cs.cs525.reversi.monitor;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;

import main.java.edu.miu.cs.cs525.reversi.ReversiSingleton;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.ActionEventFactory;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.BoardViewToolBarActionAdapter;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.MainFormMenuActionAdapter;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.MainFormWindowAdapter;
import main.java.edu.miu.cs.cs525.reversi.network.NetworkPlayer;
import main.java.edu.miu.cs.cs525.reversi.utils.ImageClass;

public class MainForm extends JFrame {
	private static final long serialVersionUID = 1L;
	
	ImageClass imgClass = new ImageClass();
	
	JPanel contentPane;
	JMenuBar menuBar = new JMenuBar();
	JMenu menuGame = new JMenu("Game");
	JMenu menuOptions = new JMenu("Options");
	JMenuItem menuGameNew = new JMenuItem(" New");
	JMenuItem menuGameExit = new JMenuItem(" Exit");
	JMenu menuPlayers = new JMenu(" Players");
	JMenu menuBlackPlayer = new JMenu(" Black ");
	JMenu menuWhitePlayer = new JMenu(" White ");

//	JRadioButtonMenuItem menuBlackPlayerHuman = new JRadioButtonMenuItem("Human");
//	JRadioButtonMenuItem menuBlackPlayerComputer = new JRadioButtonMenuItem("Computer");
//	JRadioButtonMenuItem menuWhitePlayerHuman = new JRadioButtonMenuItem("Human");
//	JRadioButtonMenuItem menuWhitePlayerComputer = new JRadioButtonMenuItem("Computer");

	ButtonGroup BGroup = new ButtonGroup(); // Black Player Group
	ButtonGroup WGroup = new ButtonGroup(); // White Player Group
	JMenu menuHelp = new JMenu("Help");
	JMenuItem menuHelpAbout = new JMenuItem(" About");
	BorderLayout borderLayout1 = new BorderLayout();
	JMenu menuSpeed = new JMenu(" Speed");
	JRadioButtonMenuItem menuSpeedItems[] = new JRadioButtonMenuItem[6];
	ButtonGroup SGroup = new ButtonGroup(); // Animation Speed Group
	
	JCheckBoxMenuItem menuShowMoveList = new JCheckBoxMenuItem(" Show Move List");

	JToolBar toolBar = new JToolBar();

	JLabel lblTeam4 = new JLabel("Team 4");
	JLabel lblOrForBlack = new JLabel("or");
	JLabel lblBlackPlayer = new JLabel("Black Player");
	JLabel lblOtherTeam = new JLabel("Other Team");
	JLabel lblOrForWhite = new JLabel("or");
	JLabel lblWhitePlayer = new JLabel("White Player");

	// Construct the frame
	public MainForm() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.setResizable(false);
		this.setSize(new Dimension(636 + 60, 543 + 95));
		this.setTitle("Java Othello / Reversi");
		
		this.addWindowListener(new MainFormWindowAdapter(this));
		menuGameNew.setIcon(imgClass.getNewGameIcon());
		menuGameNew.addActionListener(new MainFormMenuActionAdapter(this, "GameNew"));
		menuGameExit.setIcon(imgClass.getExitGameIcon());
		menuGameExit.addActionListener(new MainFormMenuActionAdapter(this, "GameExit"));
		ReversiSingleton.getMenuBlackPlayerComputer().addActionListener(new MainFormMenuActionAdapter(this, "BlackPlayerComputer"));
		ReversiSingleton.getMenuBlackPlayerHuman().addActionListener(new MainFormMenuActionAdapter(this, "BlackPlayerHuman"));
		ReversiSingleton.getMenuWhitePlayerComputer().addActionListener(new MainFormMenuActionAdapter(this, "WhitePlayerComputer"));
		ReversiSingleton.getMenuWhitePlayerComputer().addActionListener(new MainFormMenuActionAdapter(this, "WhitePlayerHuman"));
		menuHelpAbout.setIcon(imgClass.getAboutGameIcon());
		menuShowMoveList.addActionListener(new MainFormMenuActionAdapter(this, "ShowMoveList"));
		menuShowMoveList.setState(true);

		WGroup.add(ReversiSingleton.getMenuWhitePlayerComputer());
		WGroup.add(ReversiSingleton.getMenuWhitePlayerComputer());
		ReversiSingleton.getMenuWhitePlayerComputer().setSelected(true);
		ReversiSingleton.getMenuBlackPlayerComputer().setSelected(true);
		BGroup.add(ReversiSingleton.getMenuBlackPlayerComputer());
		BGroup.add(ReversiSingleton.getMenuBlackPlayerHuman());
		menuSpeedItems[0] = new JRadioButtonMenuItem("1.0 x");
		menuSpeedItems[1] = new JRadioButtonMenuItem("2.0 x");
		menuSpeedItems[2] = new JRadioButtonMenuItem("3.0 x");
		menuSpeedItems[3] = new JRadioButtonMenuItem("5.0 x", true);
		menuSpeedItems[4] = new JRadioButtonMenuItem("10.0 x");
		menuSpeedItems[5] = new JRadioButtonMenuItem("No Animation");
		menuSpeedItems[0].addActionListener(new MainFormMenuActionAdapter(this, "SpeedItems", 1));
		menuSpeedItems[1].addActionListener(new MainFormMenuActionAdapter(this, "SpeedItems", 2));
		menuSpeedItems[2].addActionListener(new MainFormMenuActionAdapter(this, "SpeedItems", 3));
		menuSpeedItems[3].addActionListener(new MainFormMenuActionAdapter(this, "SpeedItems", 5));
		menuSpeedItems[4].addActionListener(new MainFormMenuActionAdapter(this, "SpeedItems", 10));
		menuSpeedItems[5].addActionListener(new MainFormMenuActionAdapter(this, "SpeedItems", 30));
		menuHelpAbout.addActionListener(new MainFormMenuActionAdapter(this, "HelpAbout"));
		menuGame.add(menuGameNew);
		menuGame.add(menuGameExit);
		menuBlackPlayer.add(ReversiSingleton.getMenuBlackPlayerComputer());
		menuBlackPlayer.add(ReversiSingleton.getMenuBlackPlayerHuman());
		menuWhitePlayer.add(ReversiSingleton.getMenuWhitePlayerComputer());
		menuWhitePlayer.add(ReversiSingleton.getMenuWhitePlayerComputer());
		menuPlayers.add(menuBlackPlayer);
		menuPlayers.add(menuWhitePlayer);
		menuHelp.add(menuHelpAbout);
		for (int i = 0; i < menuSpeedItems.length; i++) {
			SGroup.add(menuSpeedItems[i]);
			menuSpeed.add(menuSpeedItems[i]);
		}
		menuPlayers.setIcon(imgClass.getPlayersIcon());
		menuSpeed.setIcon(imgClass.getSpeedIcon());
		menuBar.add(menuGame);
		menuOptions.add(menuPlayers);
		menuOptions.add(menuSpeed);
		menuOptions.add(menuShowMoveList);
		menuBar.add(menuOptions);
		menuBar.add(menuHelp);
		this.setJMenuBar(menuBar);
		ReversiSingleton.getFirstButton().setEnabled(false);
		ReversiSingleton.getFirstButton().setToolTipText("First Move");
		ReversiSingleton.getFirstButton().addActionListener(new BoardViewToolBarActionAdapter(this, "firstButton"));
		ReversiSingleton.getPrevButton().setEnabled(false);
		ReversiSingleton.getPrevButton().setToolTipText("Previous Move");
		ReversiSingleton.getPrevButton().addActionListener(new BoardViewToolBarActionAdapter(this, "prevButton"));
		ReversiSingleton.getNextButton().setEnabled(false);
		ReversiSingleton.getNextButton().setToolTipText("Next Move");
		ReversiSingleton.getNextButton().addActionListener(new BoardViewToolBarActionAdapter(this, "nextButton"));
		ReversiSingleton.getLastButton().setEnabled(false);
		ReversiSingleton.getLastButton().setToolTipText("Last Move");
		ReversiSingleton.getLastButton().addActionListener(new BoardViewToolBarActionAdapter(this, "lastButton"));
		ReversiSingleton.getPauseButton().setEnabled(false);
		ReversiSingleton.getPauseButton().setToolTipText("Pause Game");
		ReversiSingleton.getPauseButton().addActionListener(new BoardViewToolBarActionAdapter(this, "pauseButton"));
		ReversiSingleton.getFirstButton().setIcon(imgClass.getFirstImg());
		ReversiSingleton.getPrevButton().setIcon(imgClass.getPrevImg());
		ReversiSingleton.getPauseButton().setIcon(imgClass.getPauseImg());
		ReversiSingleton.getNextButton().setIcon(imgClass.getNextImg());
		ReversiSingleton.getLastButton().setIcon(imgClass.getLastImg());

		toolBar.add(ReversiSingleton.getFirstButton());
		toolBar.add(ReversiSingleton.getPrevButton());
		toolBar.add(ReversiSingleton.getPauseButton());
		toolBar.add(ReversiSingleton.getNextButton());
		toolBar.add(ReversiSingleton.getLastButton());

		lblTeam4.setForeground(ReversiSingleton.getAqua());
		lblOrForBlack.setForeground(ReversiSingleton.getAqua());
		lblBlackPlayer.setForeground(ReversiSingleton.getAqua());
		lblWhitePlayer.setForeground(ReversiSingleton.getAqua());
		lblOrForWhite.setForeground(ReversiSingleton.getAqua());
		lblOtherTeam.setForeground(ReversiSingleton.getAqua());

		ReversiSingleton.getLeftPane().add(lblTeam4);
		ReversiSingleton.getLeftPane().add(lblOrForBlack);
		ReversiSingleton.getLeftPane().add(lblBlackPlayer);
		ReversiSingleton.getRightPane().add(lblOtherTeam);
		ReversiSingleton.getRightPane().add(lblOrForWhite);
		ReversiSingleton.getRightPane().add(lblWhitePlayer);

		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(ReversiSingleton.getLeftPane(), BorderLayout.WEST);
		contentPane.add(ReversiSingleton.getBoardView(), BorderLayout.CENTER);
		contentPane.add(ReversiSingleton.getRightPane(), BorderLayout.EAST);
	}
	
	public void pauseGame() {
		ReversiSingleton.getPauseButton().setIcon(imgClass.getPlayImg());
		ReversiSingleton.getFirstButton().setEnabled(true);
		ReversiSingleton.getPrevButton().setEnabled(true);
		ReversiSingleton.getNextButton().setEnabled(true);
		ReversiSingleton.getLastButton().setEnabled(true);
		ReversiSingleton.getPauseButton().setToolTipText("Continue Game");
		ReversiSingleton.getBoardView().gamePaused = true;
	}

	public void newGame() {
		ReversiSingleton.getBoardView().board.initBoard();
		ReversiSingleton.getMainForm().repaint();
		ReversiSingleton.getPauseButton().setEnabled(true);
		continueGame();
	}

	public void continueGame() {
		ReversiSingleton.getPauseButton().setIcon(imgClass.getPauseImg());
		ReversiSingleton.getFirstButton().setEnabled(false);
		ReversiSingleton.getPrevButton().setEnabled(false);
		ReversiSingleton.getNextButton().setEnabled(false);
		ReversiSingleton.getLastButton().setEnabled(false);
		ReversiSingleton.getPauseButton().setToolTipText("Pause Game");
		ReversiSingleton.getBoardView().gamePaused = false;
		ReversiSingleton.getBoardView().updateTurn();
	}

	public void pauseButton_actionPerformed(ActionEvent e) {
		if (ReversiSingleton.getPauseButton().getToolTipText().equals("Pause Game")) {
			pauseGame();
		} else {
			continueGame();
		}
	}

	public void prevButton_actionPerformed(ActionEvent e) {
		if (ReversiSingleton.getBoardView().timer.isRunning()) {
			return;
		}
		ReversiSingleton.getBoardView().board.takeBackOneMove();
		ReversiSingleton.getBoardView().updateTurn();
		ReversiSingleton.getMainForm().repaint();
	}

	public void nextButton_actionPerformed(ActionEvent e) {
		if (ReversiSingleton.getBoardView().timer.isRunning()) {
			return;
		}
		ReversiSingleton.getBoardView().board.redoOneMove();
		ReversiSingleton.getBoardView().updateTurn();
		ReversiSingleton.getMainForm().repaint();
	}

	public void firstButton_actionPerformed(ActionEvent e) {
		if (ReversiSingleton.getBoardView().timer.isRunning()) {
			return;
		}
		ReversiSingleton.getBoardView().board.takeBackAllMoves();
		ReversiSingleton.getBoardView().updateTurn();
		ReversiSingleton.getMainForm().repaint();
	}

	public void lastButton_actionPerformed(ActionEvent e) {
		if (ReversiSingleton.getBoardView().timer.isRunning()) {
			return;
		}
		ReversiSingleton.getBoardView().board.redoAllMoves();
		ReversiSingleton.getBoardView().updateTurn();
		ReversiSingleton.getMainForm().repaint();
	}

	public void menuGameNew_actionPerformed(ActionEvent e) {
		newGame();
	}

	public void menuGameExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	public void menuHelpAbout_actionPerformed(ActionEvent e) {
		About dlg = new About(ReversiSingleton.getMainForm());
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = ReversiSingleton.getMainForm().getSize();
		Point loc = ReversiSingleton.getMainForm().getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.pack();
		dlg.show();
	}

	public void processWindowEvent(ActionEvent e) {
//		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			menuGameExit_actionPerformed(null);
		}
	}

	public void menuBlackPlayerComputer_actionPerformed(ActionEvent e) {
		ChoosePlayerType dlg = new ChoosePlayerType(ReversiSingleton.getMainForm(), "Choose Black Player", true);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = ReversiSingleton.getMainForm().getSize();
		Point loc = ReversiSingleton.getMainForm().getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.show();
		if (dlg.playerType == dlg.COMPUTER_PLAYER) {
			ReversiSingleton.getBoardView().playerBPointer = new ComputerPlayer();
			ReversiSingleton.getMenuBlackPlayerComputer().setText("Computer");

		} else if (dlg.playerType == dlg.NET_PLAYER) {
			ReversiSingleton.getBoardView().playerBPointer = new NetworkPlayer(dlg.hostAddress, dlg.portNumber, dlg.portNumber2);
			try {
				String id = ((NetworkPlayer) ReversiSingleton.getBoardView().playerBPointer).identify();
				ReversiSingleton.getMenuBlackPlayerComputer().setText(id + " @ ( " + dlg.hostAddress + ":" + dlg.portNumber + " )");
//				ReversiSingleton.txtTeam4=dlg.hostAddress ;
			} catch (Exception exc) {
			}
		} else if (ReversiSingleton.getBoardView().playerBPointer == null) {
			//Here we need to put the old code
			ReversiSingleton.getMenuBlackPlayerComputer().setSelected(true);
			ReversiSingleton.getMenuBlackPlayerComputer().setEnabled(true);
			ReversiSingleton.getBoardView().playerBPointer = new ComputerPlayer();
			dlg.radioComputerPlayer_actionPerformed(e);
			dlg.radioComputerPlayer.setSelected(true);
			ActionEventFactory.getActionPerformed("ChoosePlayerOkAction");
			dlg.radioComputerPlayer.addActionListener(ActionEventFactory.ac.initializeInstance(dlg));
			dlg.radioComputerPlayer.doClick();
		}
		ReversiSingleton.getBoardView().updateTurn();
	}

	public void menuBlackPlayerHuman_actionPerformed(ActionEvent e) {
		ReversiSingleton.getMenuBlackPlayerHuman().setText("Human");
	}

	public void menuWhitePlayerComputer_actionPerformed(ActionEvent e) {
		ChoosePlayerType dlg = new ChoosePlayerType(ReversiSingleton.getMainForm(), "Choose White Player", true);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = ReversiSingleton.getMainForm().getSize();
		Point loc = ReversiSingleton.getMainForm().getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.show();
		if (dlg.playerType == dlg.COMPUTER_PLAYER) {
			ReversiSingleton.getBoardView().playerWPointer = new ComputerPlayer();
			ReversiSingleton.getMenuWhitePlayerComputer().setText("Computer");

		} else if (dlg.playerType == dlg.NET_PLAYER) {
			ReversiSingleton.getBoardView().playerWPointer = new NetworkPlayer(dlg.hostAddress, dlg.portNumber, dlg.portNumber2);
			try {
				String id = ((NetworkPlayer) ReversiSingleton.getBoardView().playerWPointer).identify();
				ReversiSingleton.getMenuWhitePlayerComputer().setText(id + " @ ( " + dlg.hostAddress + ":" + dlg.portNumber + " )");
			} catch (Exception exc) {
			}
		} else if (ReversiSingleton.getBoardView().playerWPointer == null) {
			//Here we need to put the old code
			ReversiSingleton.getMenuWhitePlayerComputer().setSelected(true);
//			ReversiSingleton.getMenuWhitePlayerComputer().setEnabled(true);
			ReversiSingleton.getBoardView().playerWPointer = new ComputerPlayer();
			dlg.radioComputerPlayer_actionPerformed(e);
			dlg.radioComputerPlayer.setSelected(true);
			ActionEventFactory.getActionPerformed("ChoosePlayerOkAction");
			dlg.radioComputerPlayer.addActionListener(ActionEventFactory.ac.initializeInstance(dlg));
			dlg.radioComputerPlayer.doClick();

		}
		ReversiSingleton.getBoardView().updateTurn();
	}

	public void menuWhitePlayerHuman_actionPerformed(ActionEvent e) {
		//Here we need to put the old code
		ReversiSingleton.getMenuWhitePlayerHuman().setText("Human");
	}

	public void menuSpeedItems_actionPerformed(ActionEvent e, int n) {
		ReversiSingleton.getBoardView().animationSpeed = n;
	}

	public void menuShowMoveList_actionPerformed(ActionEvent e) {
		ReversiSingleton.getMoveList().setVisible(ReversiSingleton.getMenuShowMoveList().getState());
		ReversiSingleton.getMainForm().repaint();
	}
	
	public void this_windowOpened(WindowEvent e) {
		Dimension frmSize = getSize();
		Point l = this.getLocationOnScreen();
		ReversiSingleton.getMoveList().setLocation(l.x + frmSize.width, l.y);
		ReversiSingleton.getMoveList().show();
		this.toFront();
	}
}

