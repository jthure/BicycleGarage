package gui_new;

import entities.Bicycle;
import entities.Member;
import garage.Database;
import garage.Statistics;
import interfaces.BarcodePrinter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.Font;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

public class GUI {
	private Database db;
	private BarcodePrinter printer;
	private Statistics stats;
	private final String DATE_FORMAT = "YY-MM-DD";

	public JFrame frmBicycleGarage;
	private JTextField textField_fName;
	private JTextField textField_lName;
	private JTextField textField_AddPID;
	private JTextField textField_AddTel;
	private JTextField textField_Name;
	private JTextField textField_PID;
	private JTextField textField_Tel;
	private JTextField textField_PIN;
	private JTextField textField_Barcode;
	private JTextField textField_Owner;
	private JDesktopPane desktopPane;
	private JInternalFrame internalFrame_AddMember;
	private JInternalFrame internalFrame_Bikes;
	private JInternalFrame internalFrame_Members;
	private JInternalFrame internalFrame_Slots;
	private JList<Member> list_Members;
	private JCheckBox chckbxSuspended;
	private JCheckBox chckbxCheckedIn;
	private JCheckBox chckbxParked;
	private JComboBox<String> comboBox_Bikes;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_Search;
	private JTextField textField_FetchBike;
	private DefaultListModel<Member> memberListModel;
	private DefaultTableModel statsTableModel;
	private JTextField textField_inGarage;
	private JTextField textField_maxCap;

	/**
	 * Create the application.
	 */
	public GUI(Database db, BarcodePrinter printer) {
		this.db = db;
		this.printer = printer;
		stats = new Statistics(db);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBicycleGarage = new JFrame();
		frmBicycleGarage.setTitle("Bicycle Garage 2000");
		frmBicycleGarage.setBounds(100, 100, 1024, 768);
		frmBicycleGarage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/**
		 * Menu
		 */
		JMenuBar menuBar = new JMenuBar();
		frmBicycleGarage.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmAddMember = new JMenuItem("Add Member");
		mntmAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internalFrame_AddMember.show();
			}
		});

		mnFile.add(mntmAddMember);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmMembers = new JMenuItem("Members");
		mntmMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internalFrame_Members.show();
			}
		});
		mnEdit.add(mntmMembers);
		
		JMenuItem mntmBicycles = new JMenuItem("Bicycles");
		mntmBicycles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrame_Bikes.show();
			}
		});
		mnEdit.add(mntmBicycles);
		
		JMenuItem mntmGarageStatus = new JMenuItem("Garage Status");
		mntmGarageStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				garageStatus();
			}
		});
		mnEdit.add(mntmGarageStatus);
		
		/**
		 * Tabs
		 */
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmBicycleGarage.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		/**
		 * Desktop
		 */
		desktopPane = new JDesktopPane();
		tabbedPane.addTab("Workspace", null, desktopPane, null);
		
		/**
		 * Internal frame
		 * Add Member
		 */
		internalFrame_AddMember = new JInternalFrame("Add New Member");
		internalFrame_AddMember.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_AddMember.setClosable(true);
		internalFrame_AddMember.setBounds(10, 11, 220, 166);
		desktopPane.add(internalFrame_AddMember);
		internalFrame_AddMember.getContentPane().setLayout(new MigLayout("", "[][grow][grow]", "[][][][][]"));
		
		
		JLabel lblFirstName = new JLabel("First Name");
		internalFrame_AddMember.getContentPane().add(lblFirstName, "cell 0 0,alignx trailing");
		
		textField_fName = new JTextField();
		internalFrame_AddMember.getContentPane().add(textField_fName, "cell 1 0 2 1,growx");
		textField_fName.setColumns(10);
		
		JLabel lbllastName = new JLabel("Last Name");
		internalFrame_AddMember.getContentPane().add(lbllastName, "cell 0 1,alignx trailing");
		
		textField_lName = new JTextField();
		internalFrame_AddMember.getContentPane().add(textField_lName, "cell 1 1 2 1,growx");
		textField_lName.setColumns(10);
		
		JLabel lblPidNumber = new JLabel("PID Number");
		internalFrame_AddMember.getContentPane().add(lblPidNumber, "cell 0 2,alignx trailing");
		
		textField_AddPID = new JTextField();
		internalFrame_AddMember.getContentPane().add(textField_AddPID, "cell 1 2 2 1,growx");
		textField_AddPID.setColumns(10);
		
		JLabel lblTelNumber = new JLabel("Tel. Number");
		internalFrame_AddMember.getContentPane().add(lblTelNumber, "cell 0 3,alignx trailing");
		
		textField_AddTel = new JTextField();
		internalFrame_AddMember.getContentPane().add(textField_AddTel, "cell 1 3 2 1,growx");
		textField_AddTel.setColumns(10);
		
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addMember();
			}
		});

		internalFrame_AddMember.getContentPane().add(btnAddMember, "cell 0 4 3 1,growx,aligny center");
		
		/**
		 * Internal frame
		 * Bicycle list
		 */
		internalFrame_Bikes = new JInternalFrame("Bicycles");
		internalFrame_Bikes.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_Bikes.setClosable(true);
		internalFrame_Bikes.setBounds(240, 11, 220, 172);
		desktopPane.add(internalFrame_Bikes);
		internalFrame_Bikes.getContentPane().setLayout(new MigLayout("", "[grow][grow][][]", "[][][][][]"));
		
		textField_FetchBike = new JTextField();
		internalFrame_Bikes.getContentPane().add(textField_FetchBike, "cell 0 0 3 1,growx");
		textField_FetchBike.setColumns(10);
		
		JButton btnFetchBike = new JButton("Fetch");
		btnFetchBike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetchBike();
			}
		});
		internalFrame_Bikes.getContentPane().add(btnFetchBike, "cell 3 0");
		
		JLabel lblBarCode_1 = new JLabel("Bar code");
		internalFrame_Bikes.getContentPane().add(lblBarCode_1, "cell 0 1,alignx center");
		
		textField_Barcode = new JTextField();
		textField_Barcode.setEditable(false);
		internalFrame_Bikes.getContentPane().add(textField_Barcode, "cell 1 1 3 1,growx");
		textField_Barcode.setColumns(10);
		
		JLabel lblOwner_1 = new JLabel("Owner PIN");
		internalFrame_Bikes.getContentPane().add(lblOwner_1, "cell 0 2,alignx center");
		
		textField_Owner = new JTextField();
		textField_Owner.setEditable(false);
		internalFrame_Bikes.getContentPane().add(textField_Owner, "cell 1 2 3 1,growx");
		textField_Owner.setColumns(10);
		
		chckbxParked = new JCheckBox("Parked");
		chckbxParked.setEnabled(false);
		internalFrame_Bikes.getContentPane().add(chckbxParked, "cell 0 3,alignx left");
		
		JButton btnRemoveBicycle = new JButton("Remove Bicycle");
		btnRemoveBicycle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeBike(textField_Barcode.getText());
			}
		});
		internalFrame_Bikes.getContentPane().add(btnRemoveBicycle, "cell 1 3 3 1,grow");
		
		JButton btnNewBarcode = new JButton("Print New Bar Code");
		btnNewBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printNewBarcode();
			}
		});
		internalFrame_Bikes.getContentPane().add(btnNewBarcode, "cell 0 4 4 1,growx,aligny center");
		
		/**
		 * Internal frame
		 * Member list
		 */
		internalFrame_Members = new JInternalFrame("Members");
		internalFrame_Members.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_Members.setResizable(true);
		internalFrame_Members.setSize(450, 400);
		internalFrame_Members.setLocation(480, 11);
		internalFrame_Members.setClosable(true);
		desktopPane.add(internalFrame_Members);
		internalFrame_Members.getContentPane().setLayout(new MigLayout("", "[grow][][][grow][grow][grow][grow]", "[grow,fill][][][][][]"));
		
		memberListModel = new DefaultListModel<Member>();
		list_Members = new JList<Member>(memberListModel);
		list_Members.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				showSelectedMember();
			}
		});
		internalFrame_Members.getContentPane().add(list_Members, "cell 0 0 7 1,grow");
		
		textField_Search = new JTextField();
		internalFrame_Members.getContentPane().add(textField_Search, "cell 0 1 6 1,growx");
		textField_Search.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchMember(textField_Search.getText());
			}
		});
		internalFrame_Members.getContentPane().add(btnSearch, "cell 6 1,growx");
		
		JLabel lblName = new JLabel("Name");
		internalFrame_Members.getContentPane().add(lblName, "cell 0 2,alignx center");
		
		textField_Name = new JTextField();
		textField_Name.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_Name, "cell 1 2 6 1,growx");
		textField_Name.setColumns(10);
		
		JLabel lblPid = new JLabel("PID");
		internalFrame_Members.getContentPane().add(lblPid, "cell 0 3,alignx center");
		
		textField_PID = new JTextField();
		textField_PID.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_PID, "cell 1 3 3 1,growx");
		textField_PID.setColumns(10);
		
		JLabel lblPin = new JLabel("PIN");
		internalFrame_Members.getContentPane().add(lblPin, "cell 4 3,alignx center");
		
		textField_PIN = new JTextField();
		textField_PIN.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_PIN, "cell 5 3,growx");
		textField_PIN.setColumns(10);
		
		JButton btnChangePIN = new JButton("New PIN");
		btnChangePIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePIN();
			}
		});
		internalFrame_Members.getContentPane().add(btnChangePIN, "cell 6 3,growx,aligny center");
		
		JLabel lblTel = new JLabel("Tel. No.");
		internalFrame_Members.getContentPane().add(lblTel, "cell 0 4,alignx center");
		
		textField_Tel = new JTextField();
		textField_Tel.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_Tel, "cell 1 4 4 1,growx");
		textField_Tel.setColumns(10);
		
		chckbxSuspended = new JCheckBox("Suspended");
		chckbxSuspended.setEnabled(false);
		internalFrame_Members.getContentPane().add(chckbxSuspended, "cell 5 4,alignx center");
		
		chckbxCheckedIn = new JCheckBox("Checked in");
		chckbxCheckedIn.setEnabled(false);
		internalFrame_Members.getContentPane().add(chckbxCheckedIn, "cell 6 4,alignx center");
		
		JLabel lblBicycles = new JLabel("Bicycles");
		internalFrame_Members.getContentPane().add(lblBicycles, "cell 0 5,alignx center");
		
		comboBox_Bikes = new JComboBox<String>();
		internalFrame_Members.getContentPane().add(comboBox_Bikes, "cell 1 5 4 1,grow");
		
		JButton btnRemoveBike = new JButton("Remove Bicycle");
		btnRemoveBike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeBike(comboBox_Bikes.getItemAt(comboBox_Bikes.getSelectedIndex()));
			}
		});
		internalFrame_Members.getContentPane().add(btnRemoveBike, "cell 5 5,grow");
		
		JButton btnAddBike = new JButton("Add Bicycle");
		btnAddBike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addBicycle();
			}
		});
		internalFrame_Members.getContentPane().add(btnAddBike, "cell 6 5,grow");
		
		JMenuBar menuBar_1 = new JMenuBar();
		internalFrame_Members.setJMenuBar(menuBar_1);
		
		internalFrame_Slots = new JInternalFrame("Garage Status");
		internalFrame_Slots.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_Slots.setClosable(true);
		internalFrame_Slots.setBounds(240, 207, 229, 118);
		desktopPane.add(internalFrame_Slots);
		internalFrame_Slots.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		JLabel lblBicyclesInGarage = new JLabel("Bicycles in Garage");
		internalFrame_Slots.getContentPane().add(lblBicyclesInGarage, "cell 0 0,alignx trailing");
		
		textField_inGarage = new JTextField();
		textField_inGarage.setEditable(false);
		internalFrame_Slots.getContentPane().add(textField_inGarage, "cell 1 0,growx");
		textField_inGarage.setColumns(10);
		
		JLabel lblMaximumCapacity = new JLabel("Maximum Capacity");
		internalFrame_Slots.getContentPane().add(lblMaximumCapacity, "cell 0 1,alignx trailing");
		
		textField_maxCap = new JTextField();
		internalFrame_Slots.getContentPane().add(textField_maxCap, "cell 1 1,growx");
		textField_maxCap.setColumns(10);
		
		JButton btnChangeMax = new JButton("Change Max");
		btnChangeMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMaxCap();
			}
		});
		internalFrame_Slots.getContentPane().add(btnChangeMax, "cell 0 2 2 1,growx,aligny center");
		
		JMenu mnMembersFile = new JMenu("File");
		menuBar_1.add(mnMembersFile);
		
		JMenuItem mntmRemoveMember = new JMenuItem("Remove Member");
		mntmRemoveMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeMember();
			}
		});
		mnMembersFile.add(mntmRemoveMember);
		
		JMenu mnMembersSuspension = new JMenu("Suspension");
		menuBar_1.add(mnMembersSuspension);
		
		JMenuItem mntmSuspend = new JMenuItem("Suspend");
		mntmSuspend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					suspendMember();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnMembersSuspension.add(mntmSuspend);
		
		JMenuItem mntmUnsuspend = new JMenuItem("Unsuspend");
		mntmUnsuspend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unsuspendMember();
			}
		});
		mnMembersSuspension.add(mntmUnsuspend);
		
		JMenuItem mntmCheckStatus = new JMenuItem("Check Status");
		mntmCheckStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suspensionStatus();
			}
		});
		mnMembersSuspension.add(mntmCheckStatus);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Statistics", null, panel, null);
		panel.setLayout(new MigLayout("", "[grow][grow][][][][]", "[][][][][][][grow]"));
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblData, "cell 0 0,alignx left,aligny center");
		
		JLabel lblFormatYymmdd = new JLabel("Format: " + DATE_FORMAT);
		panel.add(lblFormatYymmdd, "cell 2 1 4 1,alignx center");
		
		String[] columnNames = {"Date", "Members", "Bicycles", "Bicycles parked", "Members checked in"};
		statsTableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(statsTableModel);
		panel.add(new JScrollPane(table), "cell 0 2 2 5,grow");
		
		JLabel lblFrom = new JLabel("From");
		panel.add(lblFrom, "cell 2 3,alignx trailing");
		
		textField = new JTextField();
		panel.add(textField, "cell 3 3 3 1,growx");
		textField.setColumns(10);
		
		JLabel lblTo = new JLabel("To (excl.)");
		panel.add(lblTo, "cell 2 4,alignx trailing");
		
		JButton btnStats = new JButton("Fetch Data");
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getStats(textField.getText(), textField_1.getText());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
		textField_1 = new JTextField();
		panel.add(textField_1, "cell 3 4 3 1,growx");
		textField_1.setColumns(10);
		panel.add(btnStats, "cell 2 5 4 1,growx");
	}

	private void printNewBarcode() {
		String barcode = textField_Barcode.getText();
		if (db.removeBicycle(barcode)) {
			db.addBicycle(db.getMember(textField_Owner.getText()), printer);
			JOptionPane.showMessageDialog(internalFrame_Members,
					"A new barcode has been generated.",
					"New barcode",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(internalFrame_Members,
					"Bicycle does not exist or is parked in the garage.",
					"Error",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	private void setMaxCap() {
		String max = textField_maxCap.getText();
		if (max != null) {
			if (db.setMaxParkingSlots(Integer.parseInt(max))) {
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Capacity successfully changed.",
						"Success",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Invalid input.",
						"Error",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	private void garageStatus() {
		textField_inGarage.setText(String.valueOf(db.getBicyclesInGarage()));
		textField_maxCap.setText(String.valueOf(db.getMaxParkingSlots()));
		internalFrame_Slots.show();
	}

	private void suspensionStatus() {
		Member m = db.getMember(textField_PIN.getText());
		if (m != null) {
			if (m.isSuspended()) {
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Member is suspended until " + m.suspendedUntil().toString(),
						"Member is suspended",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Member is not suspended",
						"Member is not suspended",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	private void unsuspendMember() {
		if (JOptionPane.showConfirmDialog(internalFrame_Members,
				"Are you sure you would like to unsuspend this member?",
				"Unsuspend member", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			db.unsuspendMember(textField_PIN.getText());
		}
	}

	private void suspendMember() throws ParseException {
		String until = JOptionPane.showInputDialog("Suspend until (" + DATE_FORMAT + "):");
		if (until != null) {
			Date date = parseToStupidArbitraryDateFormat(until);
			db.suspendMember(textField_PIN.getText(), date);
		}
	}

	private void removeMember() {
		if (JOptionPane.showConfirmDialog(internalFrame_Members,
				"Are you sure you would like to remove this member?",
				"Remove member", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			Member m = db.getMember(textField_PIN.getText());
			if (db.removeMember(m.getPIN())) {
				textField_Name.setText("");
				textField_PID.setText("");
				textField_PIN.setText("");
				textField_Tel.setText("");
				comboBox_Bikes.removeAllItems();
				chckbxSuspended.setSelected(false);
				chckbxCheckedIn.setSelected(false);
				memberListModel.clear();
			} else {
				JOptionPane.showMessageDialog(internalFrame_AddMember,
						"Member does not exist or has bicycles parked in the garage.",
						"Error",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	private void fetchBike() {
		Bicycle b = db.getBicycle(textField_FetchBike.getText());
		if (b != null) {
			textField_Barcode.setText(b.getBarcode());
			textField_Owner.setText(b.getOwnerPIN());
			chckbxParked.setSelected(b.isParked());
		}else{
			textField_Barcode.setText("");
			textField_Owner.setText("");
			chckbxParked.setSelected(false);
		}
	}
	
	private void removeBike(String barcode) {
		if (JOptionPane.showConfirmDialog(internalFrame_Members,
				"Are you sure you would like to remove this bicycle?",
				"Remove bicycle", 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (db.removeBicycle(barcode)) {
				JOptionPane.showMessageDialog(internalFrame_AddMember,
						"Bicycle was successfully removed from member.",
						"Bicycle removed",
						JOptionPane.PLAIN_MESSAGE);
			} else if (db.getBicycle(barcode).isParked()) {
				JOptionPane.showMessageDialog(internalFrame_AddMember,
						"Bicycle is parked and cannot be removed.",
						"Cannot remove bicycle",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	private void searchMember(String name) {
		LinkedList<Member> matched = db.findMembersByName(name);
		memberListModel.clear();
		for (Member m : matched) {
			memberListModel.addElement(m);
		}
	}

	private void getStats(String from, String to) throws ParseException {
		int rowCount = statsTableModel.getRowCount();
		for (int r = 0; r < rowCount; r++) {
			statsTableModel.removeRow(0);
		}
		Date fDate = parseToStupidArbitraryDateFormat(from);
		Date tDate = parseToStupidArbitraryDateFormat(to);
		String[][] data = stats.getInfo(fDate, tDate);
		for (String[] i : data) {
			statsTableModel.addRow(i);
		}
	}
	
	private void addMember() {
		String PID = textField_AddPID.getText();
		if (db.getMemberSize() < db.getMaxMemberSize()) {
			if (!db.checkForDuplicateMember(PID)) {
				if (db.addMember(textField_fName.getText(),
						textField_lName.getText(),
						PID,
						textField_AddTel.getText())) {
					JOptionPane.showMessageDialog(internalFrame_AddMember,
							"Member successfully added.",
							"Member added",
							JOptionPane.PLAIN_MESSAGE);
					textField_fName.setText("");
					textField_lName.setText("");
					textField_AddPID.setText("");
					textField_AddTel.setText("");
				} else {
					JOptionPane.showMessageDialog(internalFrame_AddMember,
							"Member capacity reached.",
							"Cannot add member",
							JOptionPane.PLAIN_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(internalFrame_AddMember,
						"Member already exists.",
						"Duplicate member",
						JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	private void addBicycle() {
		String name = textField_Name.getText();
		String pin = textField_PIN.getText();
		Member m = db.getMember(pin);
		if (name != null && pin != null) {
			if (m.getBicycles().size() == 2) {
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Member already has 2 bicycles.",
						"Capacity reached",
						JOptionPane.PLAIN_MESSAGE);
			} else if (db.getBicycleSize() >= db.getMaxBicycleSize()) {
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Maximum bicycles in the system has been reached.",
						"Capacity reached",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				db.addBicycle(m, printer);
				JOptionPane.showMessageDialog(internalFrame_Members,
						"Bicycle successfully added.",
						"Bicycle added",
						JOptionPane.PLAIN_MESSAGE);
				showSelectedMember();
			}
		}
	}

	private void changePIN() {
		String PIN = db.changePIN(list_Members.getSelectedValue().getPIN());
		memberListModel.clear();
		JOptionPane.showMessageDialog(internalFrame_Members, "New PIN is: " + PIN, "PIN Changed", JOptionPane.PLAIN_MESSAGE);
	}

	private void showSelectedMember() {
		Member selected = list_Members.getSelectedValue();
		if (selected != null) {
			String[] info = selected.getInfo();
			textField_Name.setText(info[0] + " " + info[1]);
			textField_Tel.setText(info[2]);
			textField_PID.setText(selected.getPIDNbr());		
			textField_PIN.setText(selected.getPIN());
			comboBox_Bikes.removeAllItems();
			for (String s : selected.getBicycles()) {
				comboBox_Bikes.addItem(s);
			}
			chckbxSuspended.setSelected(selected.isSuspended());
			chckbxCheckedIn.setSelected(selected.isCheckedIn());
		}
	}
	
	@SuppressWarnings("deprecation")
	private Date parseToStupidArbitraryDateFormat(String date) {
		String[] yymmdd = date.split("-");
		int year = Integer.parseInt(yymmdd[0]) + 100;
		int month = Integer.parseInt(yymmdd[1]) - 1;
		int day = Integer.parseInt(yymmdd[2]);
		return new Date(year, month, day);
	}
}

