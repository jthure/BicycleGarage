package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RemoveBicycleButton extends JButton implements ActionListener {
	GUI gui;
	
	public RemoveBicycleButton(GUI gui){
		super("Remove Bicycle");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.removeBicycle();

	}

}
