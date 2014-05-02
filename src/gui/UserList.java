package gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UserList extends JList implements ListSelectionListener {
	GUI gui;
	
	public UserList(GUI gui, DefaultListModel list){
		super(list);
		this.gui = gui;
		addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		System.out.println("User selected");

	}

}
