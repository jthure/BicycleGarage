package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class AddBicycleButton extends JButton implements ActionListener {
	GUI gui;
	
	public AddBicycleButton(GUI gui){
		super("Add Bicycle");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.addBicycle();
	}

}
