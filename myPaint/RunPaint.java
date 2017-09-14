package myPaint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class RunPaint {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI() {
		DrawingArea drawingArea = new DrawingArea();
		ButtonPanel buttonPanel = new ButtonPanel(drawingArea);
		
		//Dumb test
		JButton testbutton = new JButton("Summon Magenta Rectangle");
		testbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingArea.addRectangle(new Rectangle((int)(300* Math.random()),(int)(300 * Math.random()),100,100), Color.MAGENTA);
			}
		});
		//end of dumb test
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Draw On Component");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(drawingArea);
		frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		frame.getContentPane().add(testbutton,BorderLayout.NORTH);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
}
