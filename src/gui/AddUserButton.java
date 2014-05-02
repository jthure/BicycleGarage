package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class AddUserButton extends JButton implements ActionListener {
	GUI gui;
	
	public AddUserButton(GUI gui){
		super("Add User");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.addUser();

	}

}
