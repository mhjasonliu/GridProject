package myPaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class DrawingArea extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8113926226973283201L;
	private static final int AREA_SIZE = 500;
	private ArrayList<ColoredRectangle> coloredRectangles = new ArrayList<ColoredRectangle>();
	private Rectangle shape;
	
	public DrawingArea()
	{
		setBackground(Color.WHITE);
		
		MyMouseListener m1 = new MyMouseListener();
		addMouseListener(m1);
		addMouseMotionListener(m1);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return isPreferredSizeSet() ? super.getPreferredSize() : new Dimension(AREA_SIZE,AREA_SIZE);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Color foreground = g.getColor();
		
		g.setColor(Color.BLACK);
		g.drawString("Add a rectangle by doing mouse press, drag, and release", 40, 15);
		
		for(ColoredRectangle cr : coloredRectangles) {
			g.setColor(cr.getForeground());
			Rectangle r = cr.getRectangle();
			g.drawRect(r.x, r.y, r.width, r.height);
		}
		
		//Paint the rectangle as mouse is being dragged
		
		if (shape != null)
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(foreground);
			g2d.draw(shape);
		}
	}
	
	public void addRectangle(Rectangle rectangle, Color color) {
		
		ColoredRectangle cr = new ColoredRectangle(color, rectangle);
		coloredRectangles.add(cr);
		repaint();
	}
	
	public void clear() {
		coloredRectangles.clear();
		repaint();
	}
	
	class MyMouseListener extends MouseInputAdapter {
		private Point startPoint;

		public void mousePressed(MouseEvent e) {
			startPoint = e.getPoint();
			shape = new Rectangle();
		}
		
		public void mouseDragged(MouseEvent e) {
			int x = Math.min(startPoint.x, e.getX());
			int y = Math.min(startPoint.y, e.getY());
			int width = Math.abs(startPoint.x - e.getX());
			int height = Math.abs(startPoint.y - e.getY());
			
			shape.setBounds(x, y, width, height);
			repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			if (shape.width != 0 || shape.height != 0)
			{
				addRectangle(shape, e.getComponent().getForeground());
				//System.out.println(e.getComponent());
			}
			
			shape = null;
		}
	}
	
	class ColoredRectangle {
		private Color foreground;
		private Rectangle rectangle;
		
		public ColoredRectangle(Color foreground, Rectangle rectangle) {
			this.foreground = foreground;
			this.rectangle = rectangle;
		}
		
		public Color getForeground() {
			return foreground;
		}
		
		public void setForeground(Color foreground)
		{
			this.foreground = foreground;
		}
		
		public Rectangle getRectangle()
		{
			return rectangle;
		}
	}
}
