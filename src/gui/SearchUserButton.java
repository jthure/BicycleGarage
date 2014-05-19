package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SearchUserButton extends JButton implements ActionListener {
	GUI gui;
	
	public SearchUserButton(GUI gui){
		super("Search user");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.searchUser();

	}

}
