package HalfChess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameButtonPanel extends JPanel implements ActionListener{

	private GameBoardPanel gbp;
	private JLabel labelText;
	private PlayerType turnPlayer;
	private JLabel alertText;
	
	public GameButtonPanel(GameBoardPanel gbp) {
		this.gbp = gbp;
		turnPlayer = PlayerType.WHITE;
		labelText = new JLabel("Turn Player: " + turnPlayer);
		alertText = new JLabel("");
		
		add(labelText);
		add(makeButton("Confirm"));
		add(makeButton("Resign"));
		add(alertText);
		
	}
	
	private JButton makeButton(String text) {
		JButton button = new JButton(text);
		button.addActionListener(this);
		
		return button;
	}
	
	private void changeTurn() {
		gbp.clearSelected();
		if(turnPlayer == PlayerType.WHITE) {
			turnPlayer = PlayerType.BLACK;
			labelText.setText("Turn Player: " + turnPlayer);
		}
		else if(turnPlayer == PlayerType.BLACK) {
			turnPlayer = PlayerType.WHITE;
			labelText.setText("Turn Player: " + turnPlayer);
		}
		alertText.setText("");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Button Pressed");
		String text = e.getActionCommand();
		if(text.equals("Confirm")) {
			int[] selected1 = gbp.getSelected1();
			int[] selected2 = gbp.getSelected2();
			if(selected1 == null && selected2 == null) {
				alertText.setText("Please make a selection.");
				return;
			} else if (selected2 == null){
				try {
					gbp.flipUp(selected1[0],selected1[1]);
					changeTurn();
				} catch (GamePlayException e1) {
					alertText.setText(e1.getMessage());
				}
			} else {
				try {
					gbp.attemptCapture(selected1[0], selected1[1], selected2[0], selected2[1], turnPlayer);
					changeTurn();
				} catch (GamePlayException e1) {
					alertText.setText(e1.getMessage());
				}
			}
		}
		else {
			gbp.reset();
			turnPlayer = PlayerType.WHITE;
			labelText.setText("Turn Player: " + turnPlayer);
			alertText.setText("");
		}
	}

}
