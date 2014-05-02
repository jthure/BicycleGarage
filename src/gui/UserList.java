package gui;

import garage.User;

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
	public void valueChanged(ListSelectionEvent e) {
//		System.out.println("User selected");
		Object selectedValue = getSelectedValue();
		if(selectedValue instanceof User){
			User selectedUser = (User) selectedValue;
			gui.listBicycles(selectedUser);
		}
//		else{
//			throw new IllegalArgumentException("Selected value in list is not of type \"User\"");
//		}
		

	}

}
