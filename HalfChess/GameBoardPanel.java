package HalfChess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


public class GameBoardPanel extends JPanel {
	
	private GameBoard gb;
	private int frameSize;
	private int squareSize;
	private int[] selected1= null;
	private int[] selected2= null;
	
	public GameBoardPanel(int frameSize) {
		this.frameSize = frameSize;
		this.squareSize = frameSize/GameBoard.BOARD_WIDTH;
		gb = new GameBoard();
		
		MyMouseListener m1 = new MyMouseListener();
		addMouseListener(m1);
		addMouseMotionListener(m1);
		
		setBackground(new Color(225,225,225));
		
	}
	
	public Dimension getPreferredSize() {
		return isPreferredSizeSet() ? super.getPreferredSize() : new Dimension(frameSize,frameSize/2);

	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int squareSize = frameSize/GameBoard.BOARD_WIDTH;
		Graphics2D g2 = (Graphics2D) g;
		Font currentFont = g.getFont();
		g2.setFont(currentFont.deriveFont(currentFont.getSize() * 1.6F));
		
		g.setColor(Color.BLACK);
		for(int i = 0; i < GameBoard.BOARD_WIDTH; i++) {
			for(int j = 0; j < GameBoard.BOARD_LENGTH; j++) {
				g2.setColor(Color.BLACK);
				g.drawRect(i*squareSize,j*squareSize,squareSize,squareSize);
				PlayerType tempOwner = gb.getOwner(j, i);
				if(tempOwner == PlayerType.UNKNOWN)
					g.setColor(Color.YELLOW);
				if(tempOwner == PlayerType.BLACK)
					g.setColor(Color.DARK_GRAY);
				if(tempOwner == PlayerType.WHITE)
					g.setColor(Color.WHITE);
				if(tempOwner != null)
					g2.fill(new Ellipse2D.Double(i*squareSize,j*squareSize,squareSize,squareSize));
				else
					continue;
				int tempRank = gb.getRank(j, i);
				if(tempRank == 0)
					continue;
				else {
					g2.setColor(Color.BLACK);
					g2.drawString("" + tempRank, i*squareSize + squareSize/2, j*squareSize + squareSize/2);
				}
					
			}
		}
		int thickness = 4;
		if(selected1 != null) {
			g2.setColor(Color.BLUE);
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(thickness+1));
			g2.draw(new Rectangle2D.Double(selected1[0]*squareSize,selected1[1] * squareSize,squareSize,squareSize));
			g2.setStroke(oldStroke);
		}
		if(selected2 != null) {
			g2.setColor(Color.RED);
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(thickness+1));
			g2.draw(new Rectangle2D.Double(selected2[0]*squareSize,selected2[1] * squareSize,squareSize,squareSize));
			g2.setStroke(oldStroke);
		}
		
		
	}
	
	public void reset() {
		gb.resetBoard();
		repaint();
	}
	
	public void flipUp(int x, int y) throws GamePlayException{
		if(gb.getOwner(y,x) != PlayerType.UNKNOWN)
			throw new GamePlayException("Piece is already face up.");
		gb.flipUp(y,x);
		repaint();
	}
	
	public void attemptCapture(int x1, int y1, int x2, int y2, PlayerType turnPlayer) throws GamePlayException {
		if(gb.getOwner(y1, x1) != turnPlayer) {
			throw new GamePlayException("You can only capture with your own pieces.");
		}
		if(Math.abs(x1 - x2) != 1 && Math.abs(y1 - y2) != 1) {
			throw new GamePlayException("Pieces can only capture adjacent pieces.");
		}
		String errorText = gb.attemptCapture(y1,x1,y2,x2);
		if(!errorText.equals("Capture Successful.")) {
			throw new GamePlayException(errorText);
		}
	}
	
	public int[] getSelected1() {
		return selected1;
	}
	
	public int[] getSelected2() {
		return selected2;
	}
	
	public void clearSelected() {
		selected1 = null;
		selected2 = null;
		repaint();
	}
	
	class MyMouseListener extends MouseInputAdapter {
		public void mousePressed(MouseEvent e) {
			int gridX = e.getX()/squareSize;
			int gridY = e.getY()/squareSize;
			
			if(gridY < 0 || gridY >= GameBoard.BOARD_LENGTH || gridX < 0 || gridX >= GameBoard.BOARD_WIDTH)
				return;
			if(SwingUtilities.isLeftMouseButton(e)) {
				if(selected1 == null) {
					selected1 = new int[2];
					selected1[0] = gridX;
					selected1[1] = gridY;
				} else {
					if(selected1[0] != gridX || selected1[1] != gridY) {
						selected2 = new int[2];
						selected2[0] = gridX;
						selected2[1] = gridY;
					}
				}
			}
			if(SwingUtilities.isRightMouseButton(e)) {
				clearSelected();
			}
			repaint();
		}
	}
	
	
}
