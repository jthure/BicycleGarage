package gui;

import entities.Member;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class UserList extends JList<Member> implements ListSelectionListener {
	GUI gui;
	
	public UserList(GUI gui, DefaultListModel<Member> list){
		super(list);
		this.gui = gui;
		addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
//		System.out.println("User selected");
		Object selectedValue = getSelectedValue();
		if(selectedValue instanceof Member){
			Member selectedMember = (Member) selectedValue;
			gui.listBicycles(selectedMember);
			gui.onMemberSelect();
		}
//		else{
//			throw new IllegalArgumentException("Selected value in list is not of type \"User\"");
//		}
		

	}

}
