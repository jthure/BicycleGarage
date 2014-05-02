package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import garage.*;

public class GUI {
	private Database db;

	private Dimension windowSize = new Dimension(400, 400);

	private JTextField nameField;
	private JTextField bicycleField;
	private DefaultListModel<User> userListModel;
	private DefaultListModel<String> bicycleListModel;
	private JTextField tab2UserSearchField;

	public GUI(Database db) {
		this.db = db;

		JFrame frame = new JFrame("Bicycle Garage 2000");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setLayout(new GridLayout(0, 1));

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

		JPanel addBicycleWrapper = new JPanel();
		bicycleField = new JTextField(10);
		AddBicycleButton addBicycleButton = new AddBicycleButton(this);
		addBicycleWrapper.add(bicycleField);
		addBicycleWrapper.add(addBicycleButton);
		tab1Wrapper.add(addBicycleWrapper);

		// Tab2:
		JPanel tab2Wrapper = new JPanel();

		JPanel userWrapper = new JPanel(new BorderLayout());
		JPanel userSearchWrapper = new JPanel();
//		JTextField tab2UserSearchTitle = new JTextField("Search Users: ");
//		tab2UserSearchTitle.setEditable(false);
		SearchUserButton tab2UserSearchButton = new SearchUserButton(this);
		tab2UserSearchField = new JTextField(10);
		userListModel = new DefaultListModel<>();
		UserList userList = new UserList(this, userListModel);
		JScrollPane tab2UserScrollPane = new JScrollPane(userList);
//		userSearchWrapper.add(tab2UserSearchTitle);
		userSearchWrapper.add(tab2UserSearchField);
		userSearchWrapper.add(tab2UserSearchButton);
		userWrapper.add(userSearchWrapper, BorderLayout.NORTH);
		userWrapper.add(tab2UserScrollPane, BorderLayout.SOUTH);

		JPanel bicycleWrapper = new JPanel();
		JTextField tab2BicycleTitle = new JTextField("Users:");
		bicycleListModel = new DefaultListModel<String>();
		BicycleList bicycleList = new BicycleList(this, bicycleListModel);
		JScrollPane tab2BicycleScrollPane = new JScrollPane(bicycleList);
		bicycleWrapper.add(tab2BicycleTitle);
		bicycleWrapper.add(tab2BicycleScrollPane);

		tab2Wrapper.add(userWrapper);

		// Add tabs
		tabbedPane.addTab("Home", tab1Wrapper);
		tabbedPane.addTab("Search", tab2Wrapper);

		frame.setPreferredSize(windowSize);
		frame.pack();
		frame.setVisible(true);

	}

	public void addUser() {
		String name = nameField.getText();
		if (name != null) {
			User userAdded = db.addUser(name);
			System.out.println(userAdded.getName() + ": " + userAdded.getPin());
		}
		nameField.setText(null);

	}

	public void addBicycle() {
		String userPin = bicycleField.getText();
		if (userPin != null) {
			User user = db.getUserWithPin(userPin);
			if (user != null) {
				Bicycle bicycleAdded = db.addBicycle(user);
				if (bicycleAdded != null) {
					System.out.println(bicycleAdded.getId());
				}
			}
		}
		nameField.setText(null);
	}

	public void searchUser() {
		LinkedList<User> matchedUsers = db
				.getUsersWithNameRegex(tab2UserSearchField.getText());
		tab2UserSearchField.setText(null);
		userListModel.clear();
		for(User u : matchedUsers){
			userListModel.addElement(u);
		}

	}

}
