package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class ChangePINButton extends JButton implements ActionListener {
	GUI gui;
	
	public ChangePINButton(GUI gui){
		super("Change PIN Code");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.changePIN();
	}

}
