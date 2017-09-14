package myGrid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;


public class GridDrawPanel extends JPanel {

	/**
	 * Panel deals with drawing the grid
	 */
	private static final long serialVersionUID = 1L;
	private static final int NUMBER_OPS = 4;
	private Grid gr;
	private Timer t1;

	private int frameSize;
	private int gridLength;
	private int squareSize;

	private boolean showNumbers = true;
	private boolean operationToggles[] = new boolean[NUMBER_OPS];

	public GridDrawPanel(int gridLength, int frameSize) {
		this.frameSize = frameSize;
		this.gridLength = gridLength;
		squareSize = frameSize / gridLength;
		gr = new Grid(gridLength);
		
		setBackground(new Color(225,225,225));

		MyMouseListener m1 = new MyMouseListener();
		addMouseListener(m1);
		addMouseMotionListener(m1);

		t1 = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerOps();

			}
		});
	}

	public Dimension getPreferredSize() {
		return isPreferredSizeSet() ? super.getPreferredSize() : new Dimension(frameSize, frameSize);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Dimension frameDim = getSize();
		frameSize = (int) Math.min(frameDim.getWidth(), frameDim.getHeight());
		squareSize = frameSize / gridLength;

		for (int i = 0; i < gridLength; i++) {
			for (int j = 0; j < gridLength; j++) {
				if (gr.getValueAt(i, j) == 0) {
					g.setColor(Color.BLACK);
					g.drawRect(i * squareSize, j * squareSize, squareSize, squareSize);
					if (showNumbers)
						g.drawString("" + gr.getValueAt(i, j), i * squareSize + squareSize / 2,
								j * squareSize + squareSize / 2);
				} else {
					g.setColor(calculateThermalColor(gr.getValueAt(i, j)));
					g.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
					g.setColor(Color.BLACK);
					if (showNumbers)
						g.drawString("" + gr.getValueAt(i, j), i * squareSize + squareSize / 2,
								j * squareSize + squareSize / 2);
				}
			}
		}

	}
	

	//Algorithm for calculation found at
	//http://www.tannerhelland.com/4435/convert-temperature-rgb-algorithm-code/
	public static Color calculateThermalColor(int temperature) {
		double t = temperature;
		double red;
		double green;
		double blue;

		if (t <= 66) {
			red = 255;
			green = t;
			green = 99.4708025861 * Math.log(green) - 161.1195681661;

		} else {
			red = t - 60;
			red = 329.698727446 * Math.pow(red, -.1332047592);
			green = t - 60;
			green = 288.1221695283 * Math.pow(green, -.0755148492);
		}

		if (t >= 66)
			blue = 255;
		else {
			if (t <= 19)
				blue = 0;
			else {
				blue = temperature - 10;
				blue = 138.5177312231 * Math.log(blue) - 305.0447927307;

			}
		}

		if (red < 0)
			red = 0;
		else if (red > 255)
			red = 255;
		if (green < 0)
			green = 0;
		else if (green > 255)
			green = 255;
		if (blue < 0)
			blue = 0;
		else if (blue > 255)
			blue = 255;
		return new Color((int) red, (int) green, (int) blue);
	}

	public void toggleTimer() {
		if (t1.isRunning())
			t1.stop();
		else
			t1.start();
	}
	
	public void toggleNumbers() {
		showNumbers = !showNumbers;
		repaint();
	}

	public void toggleOps(boolean... toggles) {
		for (int i = 0; i < NUMBER_OPS; i++)
			if (toggles[i])
				operationToggles[i] = !operationToggles[i];
	}
	
	public void triggerOps() {
		
		if(operationToggles[0])
			gr.spreadOp();
		if(operationToggles[1])
			gr.fractionalSpreadOp();
		if(operationToggles[2])
			gr.decayOp();
		if(operationToggles[3])
			gr.gameOfLife();
		repaint();
	}
	
	
	public void clear() {
		gr.clear();
		repaint();
	}

	class MyMouseListener extends MouseInputAdapter {

		private Timer clickTimer;
		private int gridX;
		private int gridY;

		public void mousePressed(MouseEvent e) {
			gridX = e.getX() / squareSize;
			gridY = e.getY() / squareSize;

			if (gridX < 0 || gridX >= gridLength || gridY < 0 || gridY >= gridLength)
				return;
			int adder = 20;

			clickTimer = new Timer(100, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					gr.setValueAt(gridX, gridY, gr.getValueAt(gridX, gridY) + adder);
					repaint();
				}
			});
			clickTimer.start();

		}

		public void mouseDragged(MouseEvent e) {
			int newGridX = e.getX() / squareSize;
			int newGridY = e.getY() / squareSize;

			if (newGridX < 0 || newGridX >= gridLength || newGridY < 0 || newGridY >= gridLength)
				return;
			gridX = newGridX;
			gridY = newGridY;

		}

		public void mouseReleased(MouseEvent e) {
			if(clickTimer != null)
				clickTimer.stop();
		}
	}
}