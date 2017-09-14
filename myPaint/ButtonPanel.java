package myPaint;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingArea drawingArea;
	
	public ButtonPanel(DrawingArea drawingArea) {
		this.drawingArea = drawingArea;
		
		add(makeButton("     ",Color.BLACK));
		add(makeButton("     ",Color.RED));
		add(makeButton("     ",Color.GREEN));
		add(makeButton("     ",Color.BLUE));
		add(makeButton("     ",Color.ORANGE));
		add(makeButton("     ",Color.YELLOW));
		add(makeButton("Clear Drawing", null));
	}
	
	private JButton makeButton(String text, Color background) {
		JButton button = new JButton(text);
		button.setBackground(background);
		button.addActionListener(this);
		
		return button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		
		if ("Clear Drawing".equals(e.getActionCommand())) 
			drawingArea.clear();
		else
			drawingArea.setForeground(button.getBackground());
	}

}
