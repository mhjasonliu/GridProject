package myGrid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class GridButtonPanel extends JPanel implements ActionListener, ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridDrawPanel gdp;
	private ArrayList<JCheckBox> boxList = new ArrayList<JCheckBox>();
	
	public GridButtonPanel(GridDrawPanel gdp) {
		this.gdp = gdp;
		
		add(makeButton("Step"));
		add(makeButton("Toggle Clock"));
		add(makeButton("Clear"));
		add(makeButton("Toggle Numbers"));
		boxList.add(makeCheckBox("Spread"));
		boxList.add(makeCheckBox("Fractional Spread"));
		boxList.add(makeCheckBox("Decay"));
		boxList.add(makeCheckBox("Game of Life"));
		
		for(JCheckBox x : boxList) {
			add(x);
		}
	}
	
	private JButton makeButton(String text) {
		JButton button = new JButton(text);
		button.addActionListener(this);
		
		return button;
	}
	
	private JCheckBox makeCheckBox(String text) {
		JCheckBox box = new JCheckBox(text);
		box.addItemListener(this);
		
		return box;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String text = e.getActionCommand();
		switch(text) {
		case "Step":
			gdp.triggerOps();
			break;
		case "Toggle Clock":
			gdp.toggleTimer();
			break;
		case "Clear":
			gdp.clear();
			break;
		case "Toggle Numbers":
			gdp.toggleNumbers();
			break;
		default:
			break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object quest = e.getItemSelectable();
		String text = "";
		
		for(JCheckBox x : boxList) {
			if(quest == x) {
				text = x.getActionCommand();
			}
		}
		
		if(text == "Spread") {
			gdp.toggleOps(true,false,false,false);
		} else if (text == "Fractional Spread") {
			gdp.toggleOps(false,true,false,false);
		} else if (text == "Decay") {
			gdp.toggleOps(false,false,true,false);
		} else if (text == "Game of Life") {
			gdp.toggleOps(false,false,false,true);
		}
	}
	
}
