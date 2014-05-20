package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SetMaxParkingButton extends JButton implements ActionListener {
	GUI gui;
	
	public SetMaxParkingButton(GUI gui){
		super("Set max parking slots");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.setMaxParkingSlots();
	}

}
