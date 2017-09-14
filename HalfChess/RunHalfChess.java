package HalfChess;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RunHalfChess {

	public static final int FRAME_SIZE =  800;
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}
	
	public static void createAndShowGUI() {
		GameBoardPanel myBoard = new GameBoardPanel(FRAME_SIZE);
		GameButtonPanel buttonPanel = new GameButtonPanel(myBoard);
		JLabel about = new JLabel("<html>The game coded in this package is called Ban Qi, a variant of Chinese Chess, Xiang Qi.<br>"
				+ "The game is played by 2 players, in which each player captures other pieces with other pieces of higher rank <br>"
				+ "Rank 9 is the highest rank and rank 1 is the lowest rank. However, in order to balance the game, rank 1 troops <br>"
				+ "have the ability to take down rank 9 (This is just one version of many). This is the only troop capable of doing so. <br>"
				+ "The game is played as follows: Turns alternate between players in which players can either flip a face-down piece <br>"
				+ "to reveal its owner, black or white. Players are allowed to move their pieces to adjacent squares or capture another piece <br>"
				+ "The game goes like this until a player runs out of pieces, or resigns. Have fun!</html>");
		
		JFrame frame = new JFrame("Half Chess Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(myBoard,BorderLayout.NORTH);
		frame.getContentPane().add(buttonPanel);
		frame.getContentPane().add(about, BorderLayout.SOUTH);
		frame.setSize(FRAME_SIZE, FRAME_SIZE/2);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
