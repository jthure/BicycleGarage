package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


public class ExitMenuItem extends JMenuItem implements ActionListener {
	
	public ExitMenuItem(){
		super("Exit");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Exit");
		
	}

}
