package gui;

import garage.Bicycle;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class BicycleList extends JList<Bicycle> implements ListSelectionListener {
	GUI gui;
	
	public BicycleList(GUI gui, DefaultListModel<Bicycle> list){
		super(list);
		this.gui = gui;
		addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		gui.onBicycleSelect();

	}

}
