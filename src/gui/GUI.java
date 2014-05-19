package gui;

import interfaces.BarcodePrinter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import entities.*;
import garage.Database;

public class GUI {
	private Database db;
	private BarcodePrinter printer;

	private Dimension windowSize = new Dimension(600, 500);

	private JTextField tab1FnameField;
	private JTextField tab1LnameField;
	private JTextField tab1PIDField;
	private JTextField tab1TelField;

	private JTextField tab2UserSearchField;

	private JTextComponent tab2FnameField;
	private JTextComponent tab2LnameField;
	private JTextComponent tab2PIDField;
	private JTextComponent tab2TelField;
	private JTextComponent tab2SuspendedField;
	private JTextComponent tab2PINField;
	private JTextComponent tab2BicycleStatusField;
	private JTextComponent tab2BarcodeField;

	private DefaultListModel<Member> userListModel;
	private UserList userList;
	private DefaultListModel<Bicycle> bicycleListModel;
	private BicycleList bicycleList;

	public GUI(Database db, BarcodePrinter printer) {
		this.db = db;
		this.printer = printer;

		JFrame frame = new JFrame("Bicycle Garage 2000");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		menu.add(new ExitMenuItem(db));
		frame.setJMenuBar(menuBar);
		Color defaultColor = frame.getBackground();

		JTabbedPane tabbedPane = new JTabbedPane();
		frame.add(tabbedPane);

		// Tab1: Home
		JPanel tab1Wrapper = new JPanel(new BorderLayout());

		JTextArea textArea = new JTextArea("Bicycle Garage 2000");
		textArea.setBackground(defaultColor);
		textArea.setFont(new Font("Arial", Font.BOLD, 20));
		tab1Wrapper.add(textArea, BorderLayout.NORTH);

		JPanel addUserWrapper = new JPanel(new GridLayout(0, 2));
		tab1FnameField = new JTextField(10);
		tab1LnameField = new JTextField(10);
		tab1PIDField = new JTextField(10);
		tab1TelField = new JTextField(10);
		AddUserButton addUserButton = new AddUserButton(this);
		textArea = new JTextArea("First name: ");
		textArea.setBackground(defaultColor);
		addUserWrapper.add(textArea);
		addUserWrapper.add(tab1FnameField);
		textArea = new JTextArea("Last name: ");
		textArea.setBackground(defaultColor);
		addUserWrapper.add(textArea);
		addUserWrapper.add(tab1LnameField);
		textArea = new JTextArea("Personal ID: ");
		textArea.setBackground(defaultColor);
		addUserWrapper.add(textArea);
		addUserWrapper.add(tab1PIDField);
		textArea = new JTextArea("Tel. number: ");
		textArea.setBackground(defaultColor);
		addUserWrapper.add(textArea);
		addUserWrapper.add(tab1TelField);

		addUserWrapper.add(addUserButton);
		tab1Wrapper.add(addUserWrapper, BorderLayout.SOUTH);

		// Tab2: Manage
		JPanel tab2Wrapper = new JPanel(new BorderLayout());

		JPanel topWrapper = new JPanel(new GridLayout(0, 2));
		textArea = new JTextArea("Users");
		textArea.setBackground(defaultColor);
		textArea.setFont(new Font("Arial", Font.BOLD, 14));
		topWrapper.add(textArea);

		textArea = new JTextArea("Bicycles");
		textArea.setBackground(defaultColor);
		textArea.setFont(new Font("Arial", Font.BOLD, 14));
		topWrapper.add(textArea);

		// Row 2
		JPanel userSearchWrapper = new JPanel();
		SearchUserButton tab2UserSearchButton = new SearchUserButton(this);
		tab2UserSearchField = new JTextField(10);
		userSearchWrapper.add(tab2UserSearchField);
		userSearchWrapper.add(tab2UserSearchButton);
		topWrapper.add(userSearchWrapper);

		topWrapper.add(new JPanel());

		JPanel midWrapper = new JPanel(new GridLayout(0, 2));
		userListModel = new DefaultListModel<>();
		userList = new UserList(this, userListModel);
		JScrollPane tab2UserScrollPane = new JScrollPane(userList);
		midWrapper.add(tab2UserScrollPane);

		bicycleListModel = new DefaultListModel<>();
		bicycleList = new BicycleList(this, bicycleListModel);
		JScrollPane tab2BicycleScrollPane = new JScrollPane(bicycleList);
		midWrapper.add(tab2BicycleScrollPane);

		JPanel userButtonWrapper = new JPanel();
		ChangePINButton tab2ChangePINButton = new ChangePINButton(this);
		userButtonWrapper.add(tab2ChangePINButton);
		midWrapper.add(userButtonWrapper);

		JPanel bicycleButtonWrapper = new JPanel();
		AddBicycleButton tab2AddBicycleButton = new AddBicycleButton(this);
		RemoveBicycleButton tab2RemoveBicycleButton = new RemoveBicycleButton(
				this);
		PrintBarcodeButton tab2PrintBarcodeButton = new PrintBarcodeButton(this);
		bicycleButtonWrapper.add(tab2RemoveBicycleButton);
		bicycleButtonWrapper.add(tab2AddBicycleButton);
		bicycleButtonWrapper.add(tab2PrintBarcodeButton);
		midWrapper.add(bicycleButtonWrapper);

		JPanel botWrapper = new JPanel(new GridLayout(0, 2));
		JPanel userInfoWrapper = new JPanel(new GridLayout(0, 2));
		tab2FnameField = new JTextArea();
		tab2LnameField = new JTextArea();
		tab2PIDField = new JTextArea();
		tab2TelField = new JTextArea();
		tab2PINField = new JTextArea();
		tab2SuspendedField = new JTextArea();
		textArea = new JTextArea("First name: ");
		textArea.setBackground(defaultColor);
		userInfoWrapper.add(textArea);
		userInfoWrapper.add(tab2FnameField);
		textArea = new JTextArea("Last name: ");
		textArea.setBackground(defaultColor);
		userInfoWrapper.add(textArea);
		userInfoWrapper.add(tab2LnameField);
		textArea = new JTextArea("Personal ID: ");
		textArea.setBackground(defaultColor);
		userInfoWrapper.add(textArea);
		userInfoWrapper.add(tab2PIDField);
		textArea = new JTextArea("Tel. number: ");
		textArea.setBackground(defaultColor);
		userInfoWrapper.add(textArea);
		userInfoWrapper.add(tab2TelField);
		textArea = new JTextArea("Suspended: ");
		textArea.setBackground(defaultColor);
		userInfoWrapper.add(textArea);
		userInfoWrapper.add(tab2SuspendedField);
		textArea = new JTextArea("PIN-code: ");
		textArea.setBackground(defaultColor);
		userInfoWrapper.add(textArea);
		userInfoWrapper.add(tab2PINField);
		botWrapper.add(userInfoWrapper);

		JPanel bicycleInfoWrapper = new JPanel(new GridLayout(0, 2));
		tab2BicycleStatusField = new JTextArea();
		tab2BarcodeField = new JTextArea();
		textArea = new JTextArea("Parked in garage: ");
		textArea.setBackground(defaultColor);
		bicycleInfoWrapper.add(textArea);
		bicycleInfoWrapper.add(tab2BicycleStatusField);
		textArea = new JTextArea("Barcode: ");
		textArea.setBackground(defaultColor);
		bicycleInfoWrapper.add(textArea);
		bicycleInfoWrapper.add(tab2BarcodeField);
		botWrapper.add(bicycleInfoWrapper);

		tab2Wrapper.add(topWrapper, BorderLayout.NORTH);
		tab2Wrapper.add(midWrapper, BorderLayout.CENTER);
		tab2Wrapper.add(botWrapper, BorderLayout.SOUTH);

		// Tab 3: Statistics
		JPanel tab3Wrapper = new JPanel();

		// Add tabs
		tabbedPane.addTab("Add User", tab1Wrapper);
		tabbedPane.addTab("Manage", tab2Wrapper);
		tabbedPane.addTab("Satistics", tab3Wrapper);

		frame.setPreferredSize(windowSize);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Method called by the GUI to add a new member to the database.
	 */
	public void addUser() {

		if (tab1FnameField.getText() != null) {
			if (db.addMember(tab1FnameField.getText(),
					tab1LnameField.getText(), tab1PIDField.getText(),
					tab1TelField.getText())) {
				System.out.println("User added");
			}
		}
		tab1FnameField.setText(null);
		tab1LnameField.setText(null);
		tab1PIDField.setText(null);
		tab1TelField.setText(null);
	}

	/**
	 * Method called by the GUI to add a new bicycle to the database.
	 */
	public void addBicycle() {
		Member selectedUser = (Member) userList.getSelectedValue();
		if (selectedUser != null) {
			if (db.addBicycle(selectedUser)) {
				listBicycles(selectedUser);
			}
		}
	}
	/**
	 * Method called by the GUI to remove a bicycle from the database.
	 */
	public void removeBicycle() {
		Bicycle selectedBicycle = (Bicycle) bicycleList.getSelectedValue();
		if (selectedBicycle != null) {
			db.removeBicycle(selectedBicycle.getBarcode());
			listBicycles((Member) userList.getSelectedValue());
		}

	}

	public void searchUser() {
		LinkedList<Member> matchedUsers = db
				.getUsersWithNameRegex(tab2UserSearchField.getText());
		tab2UserSearchField.setText(null);
		userListModel.clear();
		for (Member u : matchedUsers) {
			userListModel.addElement(u);
		}
	}

	/**
	 * Method called by the GUI to list bicycles belonging to member.
	 */
	public void listBicycles(Member member) {
		bicycleListModel.clear();
		for (String b : member.getBicycles()) {
			bicycleListModel.addElement(db.getBicycle(b));
		}
	}
	/**
	 * Method called by the GUI when a listed bicycle is selected.
	 */
	public void onBicycleSelect() {
		Bicycle b = bicycleList.getSelectedValue();
		if (b != null) {
			String s = String.valueOf(b.isParked());
			tab2BicycleStatusField.setText(s);
		}
	}
	/**
	 * Method called by the GUI when a listed member is selected.
	 */
	public void onMemberSelect() {
		Member m = userList.getSelectedValue();
		if (m != null) {
			tab2FnameField.setText(m.getInfo()[0]);
			tab2LnameField.setText(m.getInfo()[1]);
			tab2TelField.setText(m.getInfo()[2]);
			tab2PIDField.setText(m.getPIDNbr());
			tab2PINField.setText(m.getPIN());
			tab2SuspendedField.setText(String.valueOf(m.isSuspended()));
		}
	}
	/**
	 * Method called by the GUI to print the barcode of a selected bicycle.
	 */
	public void printBarcode() {
		Bicycle b = bicycleList.getSelectedValue();
		if (b != null) {
			printer.printBarcode(b.getBarcode());
		}

	}
	/**
	 * Method called by the GUIto change the PIN of a selected member.
	 */
	public void changePIN() {
		// TODO Auto-generated method stub

	}

}
