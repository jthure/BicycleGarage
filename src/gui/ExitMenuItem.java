package gui;

import garage.Database;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


@SuppressWarnings("serial")
public class ExitMenuItem extends JMenuItem implements ActionListener {
	Database db;
	public ExitMenuItem(Database db){
		super("Exit");
		this.db = db;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		db.saveDatabase();
		
	}

}
