package myGrid;

import java.awt.BorderLayout;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RunGrid {
	
	private static final int GRID_SIZE = 30;
	private static final int FRAME_SIZE = 800;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI() {
		GridDrawPanel myGrid = new GridDrawPanel(GRID_SIZE,FRAME_SIZE);
		GridButtonPanel gbp = new GridButtonPanel(myGrid);
		String about = "<html>This project demonstrates concentrations spreading across adjacent squares of a grid.<br>"
				+ "Fractional spread most resembles fluid flow. And then of course I couldn't resist but throw in<br>"
				+ "Conway's game of life <html>";
		JLabel aboutLabel = new JLabel(about);
		
		JFrame frame = new JFrame("My Grid Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(myGrid);
		frame.getContentPane().add(gbp, BorderLayout.SOUTH);
		frame.getContentPane().add(aboutLabel, BorderLayout.NORTH);
		frame.setSize(FRAME_SIZE, FRAME_SIZE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
}
