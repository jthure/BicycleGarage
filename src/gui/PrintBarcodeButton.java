package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class PrintBarcodeButton extends JButton implements ActionListener {
	GUI gui;
	
	public PrintBarcodeButton(GUI gui){
		super("Print Barcode");
		this.gui = gui;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gui.printBarcode();
	}

}
