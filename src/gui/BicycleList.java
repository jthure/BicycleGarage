package gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class BicycleList extends JList implements ListSelectionListener {
	GUI gui;
	
	public BicycleList(GUI gui, DefaultListModel list){
		super(list);
		this.gui = gui;
		addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		System.out.println("Bicycle Selected");

	}

}
