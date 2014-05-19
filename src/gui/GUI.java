package gui;

import java.awt.Dimension;
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
import javax.swing.text.JTextComponent;

import entities.*;
import garage.Database;

public class GUI {
	private Database db;

	private Dimension windowSize = new Dimension(600, 400);

	private JTextField nameField;
	private DefaultListModel<Member> userListModel;
	private UserList userList;
	private DefaultListModel<Bicycle> bicycleListModel;
	private BicycleList bicycleList;
	private JTextField tab2UserSearchField;
	private JTextComponent tab2BicycleStatusField;

	public GUI(Database db) {
		this.db = db;

		JFrame frame = new JFrame("Bicycle Garage 2000");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		menu.add(new ExitMenuItem(db));
		frame.setJMenuBar(menuBar);

		JTabbedPane tabbedPane = new JTabbedPane();
		frame.add(tabbedPane);

		// Tab1: Home
		JPanel tab1Wrapper = new JPanel();

		JPanel addUserWrapper = new JPanel();
		nameField = new JTextField(10);
		AddUserButton addUserButton = new AddUserButton(this);
		addUserWrapper.add(nameField);
		addUserWrapper.add(addUserButton);
		tab1Wrapper.add(addUserWrapper);

		// JPanel addBicycleWrapper = new JPanel();
		// bicycleField = new JTextField(10);
		// AddBicycleButton addBicycleButton = new AddBicycleButton(this);
		// addBicycleWrapper.add(bicycleField);
		// addBicycleWrapper.add(addBicycleButton);
		// tab1Wrapper.add(addBicycleWrapper);

		// Tab2: Search
		JPanel tab2Wrapper = new JPanel();
		// Create userpart components
		JPanel userWrapper = new JPanel();
		userWrapper.setLayout(new BoxLayout(userWrapper, BoxLayout.Y_AXIS));
		;
		JPanel userSearchWrapper = new JPanel();
		SearchUserButton tab2UserSearchButton = new SearchUserButton(this);
		tab2UserSearchField = new JTextField(10);
		userListModel = new DefaultListModel<>();
		userList = new UserList(this, userListModel);
		JScrollPane tab2UserScrollPane = new JScrollPane(userList);
		// Add userpart components
		userSearchWrapper.add(tab2UserSearchField);
		userSearchWrapper.add(tab2UserSearchButton);
		userWrapper.add(userSearchWrapper);
		userWrapper.add(tab2UserScrollPane);
		// Create bicyclepart components
		JPanel bicycleWrapper = new JPanel();
		bicycleWrapper
				.setLayout(new BoxLayout(bicycleWrapper, BoxLayout.Y_AXIS));
		JTextArea tab2BicycleTitle = new JTextArea("Bicycles", 1, 10);
		bicycleListModel = new DefaultListModel<>();
		bicycleList = new BicycleList(this, bicycleListModel);
		JScrollPane tab2BicycleScrollPane = new JScrollPane(bicycleList);
		AddBicycleButton tab2AddBicycleButton = new AddBicycleButton(this);
		RemoveBicycleButton tab2RemoveBicycleButton = new RemoveBicycleButton(
				this);
		tab2BicycleStatusField = new JTextArea();
		// Add bicyclepart components
		bicycleWrapper.add(tab2BicycleTitle);
		bicycleWrapper.add(tab2BicycleScrollPane);
		bicycleWrapper.add(tab2AddBicycleButton);
		bicycleWrapper.add(tab2RemoveBicycleButton);
		bicycleWrapper.add(tab2BicycleStatusField);
		// Add tab components
		tab2Wrapper.add(userWrapper);
		tab2Wrapper.add(bicycleWrapper);
		// Add tabs
		tabbedPane.addTab("Home", tab1Wrapper);
		tabbedPane.addTab("Search", tab2Wrapper);

		frame.setPreferredSize(windowSize);
		frame.pack();
		frame.setVisible(true);

	}

	public void addUser() {
//		String name = nameField.getText();
//		if (name != null) {
//			Member userAdded = db.addUser(name);
//			System.out.println(userAdded.getName() + ": " + userAdded.getPin());
//		}
//		nameField.setText(null);
	}

	public void addBicycle() {
//		Member selectedUser = (Member) userList.getSelectedValue();
//		if (selectedUser != null) {
//			Bicycle bicycleAdded = db.addBicycle(selectedUser);
//			if (bicycleAdded != null) {
//				listBicycles(selectedUser);
//			}
//		}
	}

	public void removeBicycle() {
//		Member selectedUser = (Member) userList.getSelectedValue();
//		if (selectedUser != null) {
//			Bicycle selectedBicycle = (Bicycle) bicycleList.getSelectedValue();
//			if (selectedBicycle != null) {
//				db.removeBicycle(selectedBicycle);
//				listBicycles(selectedUser);
//			}
//		}
	}

	public void searchUser() {
//		LinkedList<Member> matchedUsers = db.getUsersWithNameRegex(tab2UserSearchField.getText());
//		tab2UserSearchField.setText(null);
//		userListModel.clear();
//		for (Member u : matchedUsers) {
//			userListModel.addElement(u);
//		}
	}

	public void listBicycles(Member member) {
		bicycleListModel.clear();
		for (Bicycle b : member.getBicycles()) {
			bicycleListModel.addElement(b);
		}
	}

	public void onBicycleSelect() {
		Bicycle b = bicycleList.getSelectedValue();
		if (b != null) {
			String s = String.valueOf(b.isParked());
			tab2BicycleStatusField.setText(s);
		}
	}

}
