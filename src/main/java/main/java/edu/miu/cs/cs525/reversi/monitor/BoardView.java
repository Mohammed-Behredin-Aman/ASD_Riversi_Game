package main.java.edu.miu.cs.cs525.reversi.monitor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import main.java.edu.miu.cs.cs525.reversi.ReversiSingleton;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.ReversiActionEventFactory;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.BoardView_PBMTimer_Action;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.BoardView_this_mouseAdapter;
import main.java.edu.miu.cs.cs525.reversi.action_adapters.BoardView_Timer_Action;
import main.java.edu.miu.cs.cs525.reversi.common.*;
import main.java.edu.miu.cs.cs525.reversi.network.NetworkPlayer;

public class BoardView extends JPanel {

	private static final long serialVersionUID = 1L;
	public BoardInfo board = new BoardInfo(); // Game Board Information
	public AnimationMatrix am = new AnimationMatrix(); // Animation Direction for each cell : -1 , 0 , +1
	public int animationSpeed = 5;
	public GeneralPlayer playerBPointer;
	public GeneralPlayer playerWPointer;
	public boolean gamePaused = true;
	public ImageIcon pieces = new ImageIcon();
	public ImageIcon border = new ImageIcon();
	public Timer timer;
	public Timer pauseBeforeMoveTimer;
	public JLabel statusBar = new JLabel();
	public JLabel lblScoreBlack = new JLabel();
	public JLabel lblScoreWhite = new JLabel();
	TitledBorder titledBorder1;
	BorderLayout borderLayout1 = new BorderLayout();
	public MoveList mlPointer;
	public JFrame parent;
	public int deltaX = 0, deltaY = 0;
	public int boardBorder;

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(200, 200, 200));
		g.setFont(new Font("Default", 1, 12));
		int x, y, sx, sy, b;
		int nr = board.ROW_COUNT;
		int nc = board.COL_COUNT;
		BoardMatrix m = new BoardMatrix();
		if (!timer.isRunning()) {
			m = board.getGainMatrix();
		}
		g.drawImage(border.getImage(), deltaX, deltaY, this);
		for (int i = 0; i < nr; i++) {
			for (int j = 0; j < nc; j++) {
				x = deltaX + 31 + j * 60;
				y = deltaY + 31 + i * 60;
				b = board.board[i][j];
				sx = (b % 8) * 60;
				sy = (b / 8) * 60;
				g.drawImage(pieces.getImage(), x, y, x + 60, y + 60, sx, sy, sx + 60, sy + 60, this);
				if (!timer.isRunning()) {
					b = m.get(i, j);
					if (b != 0 && b != -1) {
						g.drawString("" + b, x + 25, y + 35);
					}
				}
			}
		}
	}

	public BoardView(MoveList ml, JFrame parent, int boardBorderSelector) {
		try {
			boardBorder = boardBorderSelector;
			mlPointer = ml;
			this.parent = parent;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		ReversiActionEventFactory.getActionPerformed("BoardView_Timer_Action");
		timer = new Timer(50, ReversiActionEventFactory.ac.initializeInstance(this));
		
		ReversiActionEventFactory.getActionPerformed("BoardView_PBMTimer_Action");
		pauseBeforeMoveTimer = new Timer(100, ReversiActionEventFactory.ac.initializeInstance(this));
		
		this.addMouseListener(new BoardView_this_mouseAdapter(this));
		pieces = new ImageIcon(MainForm.class.getResource("../images/standard-1.png"));
		border = new ImageIcon(MainForm.class.getResource("../images/540px-board-" + boardBorder + ".png"));
		setLayout(borderLayout1);
		titledBorder1 = new TitledBorder("");
		statusBar.setBorder(titledBorder1);
		statusBar.setText(" ");
		add(statusBar, BorderLayout.SOUTH);
	}
	/*public void updatePassTurn(BoardInfo board) {
		NetworkPlayer.getMove1(board);
	}*/
	public void updateTurn() {
		if (mlPointer != null) {
			mlPointer.updateMoveList(board);
		}
		if (board.turn == BoardInfo.NO_GAME) {
			statusBar.setText("To Start New Game choose Game | New.");
			return;
		}
		String s = board.getTurnString();
		s += " ( ";
		lblScoreBlack.setText("Score: " + board.getPieceCount(board.PLAYER_BLACK));
		lblScoreBlack.setForeground(ReversiSingleton.getAqua());
		ReversiSingleton.getLeftPane().add(lblScoreBlack);

		lblScoreWhite.setText("Score: " + board.getPieceCount(board.PLAYER_WHITE));
		lblScoreWhite.setForeground(ReversiSingleton.getAqua());
		ReversiSingleton.getRightPane().add(lblScoreWhite);

		ReversiSingleton.getLeftPane().add(ReversiSingleton.getBlackTurn());
		ReversiSingleton.getRightPane().add(ReversiSingleton.getWhiteTurn());
		ReversiSingleton.getWinner().setVisible(false);
		ReversiSingleton.getGameOverLabel().setVisible(false);
		s += " ) ";
		statusBar.setText(s);
		if (gamePaused) {
			return;
		}
		if (board.turn == board.PLAYER_BLACK && playerBPointer != null) {

			pauseBeforeMoveTimer.start();
		} else if (board.turn == board.PLAYER_WHITE && playerWPointer != null) {
			pauseBeforeMoveTimer.start();
		}

	}

	public void startMove(Location move) {
		if (gamePaused) {
			return;
		}
		if (move.row < 0 || move.row > 7 || move.column < 0 || move.column > 7) {
			return;
		}
		if (!board.isValidMove(move.row, move.column)) {
			System.err.println("Please select a valid move! Col: "+move.column+" Row: "+move.row);
			return;
		}
		am = board.calculateMoveAnimation(move.row, move.column);
		repaint();
		timer.start();
	}

	public void this_mouseClicked(MouseEvent e) {
		if (timer.isRunning()) {
			return;
		}
		if (board.turn == BoardInfo.NO_GAME || board.turn == BoardInfo.GAME_OVER) {
			statusBar.setText("To Start New Game choose Game | New.");
			return;
		}
		int r, c, br, bc;
		r = (e.getY() - 30 - deltaY) / 60;
		c = (e.getX() - 30 - deltaX) / 60;
		br = (e.getY() - 30 - deltaY) % 60;
		bc = (e.getX() - 30 - deltaX) % 60;
		if (Math.abs(br - 30) > 28 || Math.abs(bc - 30) > 28) {
			return;
		}
		if (board.turn == board.PLAYER_BLACK && playerBPointer == null) {
			startMove(new Location(r, c));
		} else if (board.turn == board.PLAYER_WHITE && playerWPointer == null) {
			startMove(new Location(r, c));
		}
	}

	public void PBMTimer_actionPerformed(ActionEvent e) {
		pauseBeforeMoveTimer.stop();
		if (board.turn == board.PLAYER_BLACK && playerBPointer != null) {
			startMove(playerBPointer.getMove(board));
		} else if (board.turn == board.PLAYER_WHITE && playerWPointer != null) {
			startMove(playerWPointer.getMove(board));
		}
	}

	public void timer_actionPerformed(ActionEvent e) {
		int count = am.perform(board, animationSpeed);
		repaint();
		if (count == board.ROW_COUNT * board.COL_COUNT) {
			timer.stop();
			//WE NEED SOME MODIFICATION HERE FOR HUMAN TO HUMAN PLAYER THROWS ERROR
			//AT THE END OF THE GAME
			if (board.correctTurn() == 2) {
				String s = "Game Over";
				int p1 = board.getPieceCount(board.PLAYER_BLACK);
				int p2 = board.getPieceCount(board.PLAYER_WHITE);
				s += " ( ";
				s += p1 + " : " + p2;
				lblScoreBlack.setText("Score: " + p1);
				lblScoreWhite.setText("Score: " + p2);
				s += " ) ";
				if (p1 == p2 && board.turn == board.PLAYER_BLACK) {
					s += " It's a Draw ! ";
					playerBPointer.getMove(board);

				} else if (p1 == p2 && board.turn == board.PLAYER_WHITE) {
					s += " It's a Draw ! ";
					playerWPointer.getMove(board);

				} else if (p1 > p2) {
					s += " Black is Winner ! ";
					ReversiSingleton.setCurrentPlayer(new BlackPlayer());
					ReversiSingleton.getCurrentPlayer().winner();
					playerBPointer.getMove(board);
				} else {
					s += " White is Winner ! ";
					System.out.println(p2);
					ReversiSingleton.setCurrentPlayer(new WhitePlayer());
					ReversiSingleton.getCurrentPlayer().winner();
					playerWPointer.getMove(board);
				}
				statusBar.setText(s);
				if (mlPointer != null) {
					mlPointer.updateMoveList(board);
				}

			} else {
				updateTurn();
			}
		}
	}
}
