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

import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

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
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Canvas;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSpinner;

public class GUI {
	private Database db;
	private BarcodePrinter printer;
	private Statistics stats;
	private final String dateFormat = "YY-MM-dd";

	public JFrame frmBicycleGarage;
	private JTextField textField_fName;
	private JTextField textField_lName;
	private JTextField textField_AddPID;
	private JTextField textField_AddTel;
	private JTextField textField_Name;
	private JTextField textField_PID;
	private JTextField textField_Tel;
	private JTextField textField_PIN;
	private JTextField textField_OwnerPIN;
	private JTextField textField_Barcode;
	private JTextField textField_Owner;
	private JDesktopPane desktopPane;
	private JInternalFrame internalFrame_AddMember;
	private JInternalFrame internalFrame_AddBike;
	private JInternalFrame internalFrame_Bikes;
	private JInternalFrame internalFrame_Members;
	private JInternalFrame internalFrame_Monitor;
	private JList<Member> list_Members;
	private JList<Bicycle> list_Bikes;
	private JCheckBox chckbxSuspended;
	private JCheckBox chckbxCheckedIn;
	private JCheckBox chckbxParked;
	private JTextField textField_AddOwner;
	private JComboBox<String> comboBox_Bikes;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;

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
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmMembers = new JMenuItem("Members");
		mntmMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internalFrame_Members.show();
			}
		});
		mnEdit.add(mntmMembers);
		
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
		internalFrame_Bikes.setResizable(true);
		internalFrame_Bikes.setClosable(true);
		internalFrame_Bikes.setBounds(240, 11, 220, 350);
		desktopPane.add(internalFrame_Bikes);
		internalFrame_Bikes.getContentPane().setLayout(new MigLayout("", "[grow][grow][][]", "[grow][][][][]"));
		
		JList<Bicycle> list_Bikes = new JList<Bicycle>();
		internalFrame_Bikes.getContentPane().add(list_Bikes, "cell 0 0 4 1,grow");
		
		JLabel lblBarCode_1 = new JLabel("Bar code");
		internalFrame_Bikes.getContentPane().add(lblBarCode_1, "cell 0 1,alignx center");
		
		textField_Barcode = new JTextField();
		textField_Barcode.setEditable(false);
		internalFrame_Bikes.getContentPane().add(textField_Barcode, "cell 1 1 3 1,growx");
		textField_Barcode.setColumns(10);
		
		JLabel lblOwner_1 = new JLabel("Owner");
		internalFrame_Bikes.getContentPane().add(lblOwner_1, "cell 0 2,alignx center");
		
		textField_Owner = new JTextField();
		textField_Owner.setEditable(false);
		internalFrame_Bikes.getContentPane().add(textField_Owner, "cell 1 2 3 1,growx");
		textField_Owner.setColumns(10);
		
		chckbxParked = new JCheckBox("Parked");
		internalFrame_Bikes.getContentPane().add(chckbxParked, "cell 0 3,alignx left");
		
		JButton btnRemoveBicycle = new JButton("Remove Bicycle");
		internalFrame_Bikes.getContentPane().add(btnRemoveBicycle, "cell 1 3 3 1,grow");
		
		JButton btnNewBarcode = new JButton("Print New Bar Code");
		internalFrame_Bikes.getContentPane().add(btnNewBarcode, "cell 0 4 4 1,grow");
		
		/**
		 * Internal Frame
		 * Add bicycle
		 */
		internalFrame_AddBike = new JInternalFrame("Add New Bicycle");
		internalFrame_AddBike.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_AddBike.setClosable(true);
		internalFrame_AddBike.setBounds(10, 207, 220, 118);
		desktopPane.add(internalFrame_AddBike);
		internalFrame_AddBike.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		JLabel lblAddOwner = new JLabel("Owner");
		internalFrame_AddBike.getContentPane().add(lblAddOwner, "cell 0 0,alignx trailing");
		
		textField_AddOwner = new JTextField();
		textField_AddOwner.setEditable(false);
		internalFrame_AddBike.getContentPane().add(textField_AddOwner, "cell 1 0,growx");
		textField_AddOwner.setColumns(10);
		
		JLabel lblOwnerPIN = new JLabel("Owner PIN");
		internalFrame_AddBike.getContentPane().add(lblOwnerPIN, "cell 0 1,alignx trailing");
		
		textField_OwnerPIN = new JTextField();
		textField_OwnerPIN.setEditable(false);
		internalFrame_AddBike.getContentPane().add(textField_OwnerPIN, "cell 1 1,growx");
		textField_OwnerPIN.setColumns(10);
		
		JButton btnAddNPrint = new JButton("Add & Print Bar Code");
		btnAddNPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addBicycle();
			}
		});
		internalFrame_AddBike.getContentPane().add(btnAddNPrint, "cell 0 2 2 1,grow");
		
		/**
		 * Internal frame
		 * Member list
		 */
		internalFrame_Members = new JInternalFrame("Members");
		internalFrame_Members.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_Members.setResizable(true);
		internalFrame_Members.setSize(440, 500);
		internalFrame_Members.setLocation(483, 11);
		internalFrame_Members.setClosable(true);
		desktopPane.add(internalFrame_Members);
		internalFrame_Members.getContentPane().setLayout(new MigLayout("", "[grow][][][grow][grow][grow][grow]", "[grow,fill][][][][]"));
		
		list_Members = new JList<Member>();
		list_Members.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				showSelectedMember();
			}
		});
		internalFrame_Members.getContentPane().add(list_Members, "cell 0 0 7 1,grow");
		if (db.getMemberSize() > 0) {
			fetchMemberList();
		}
		
		JLabel lblName = new JLabel("Name");
		internalFrame_Members.getContentPane().add(lblName, "cell 0 1,alignx center");
		
		textField_Name = new JTextField();
		textField_Name.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_Name, "cell 1 1 6 1,growx");
		textField_Name.setColumns(10);
		
		JLabel lblPid = new JLabel("PID");
		internalFrame_Members.getContentPane().add(lblPid, "cell 0 2,alignx center");
		
		textField_PID = new JTextField();
		textField_PID.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_PID, "cell 1 2 4 1,growx");
		textField_PID.setColumns(10);
		
		chckbxSuspended = new JCheckBox("Suspended");
		internalFrame_Members.getContentPane().add(chckbxSuspended, "cell 5 2,alignx center");
		
		chckbxCheckedIn = new JCheckBox("Checked in");
		internalFrame_Members.getContentPane().add(chckbxCheckedIn, "cell 6 2");
		
		JLabel lblPin = new JLabel("PIN");
		internalFrame_Members.getContentPane().add(lblPin, "cell 0 3,alignx center");
		
		textField_PIN = new JTextField();
		textField_PIN.setEditable(false);
		internalFrame_Members.getContentPane().add(textField_PIN, "cell 1 3");
		textField_PIN.setColumns(10);
		
		JButton btnChangePIN = new JButton("New PIN");
		btnChangePIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changePIN();
			}
		});
		internalFrame_Members.getContentPane().add(btnChangePIN, "cell 2 3,growx,aligny center");
		
		JLabel lblTel = new JLabel("Tel. No.");
		internalFrame_Members.getContentPane().add(lblTel, "cell 4 3,alignx center");
		
		textField_Tel = new JTextField();
		internalFrame_Members.getContentPane().add(textField_Tel, "cell 5 3,growx");
		textField_Tel.setColumns(10);
		
		JButton btnChangeTel = new JButton("Change");
		btnChangeTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		internalFrame_Members.getContentPane().add(btnChangeTel, "cell 6 3,growx");
		
		JLabel lblBicycles = new JLabel("Bicycles");
		internalFrame_Members.getContentPane().add(lblBicycles, "cell 0 4,alignx center");
		
		comboBox_Bikes = new JComboBox<String>();
		internalFrame_Members.getContentPane().add(comboBox_Bikes, "cell 1 4 4 1,grow");
		
		JButton btnManageBikes = new JButton("Manage");
		internalFrame_Members.getContentPane().add(btnManageBikes, "cell 5 4,grow");
		
		JButton btnAddBike = new JButton("Add Bike");
		btnAddBike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addBikeDialog();
			}
		});
		internalFrame_Members.getContentPane().add(btnAddBike, "cell 6 4,grow");
		
		JMenuBar menuBar_1 = new JMenuBar();
		internalFrame_Members.setJMenuBar(menuBar_1);
		
		JMenu mnMembersFile = new JMenu("File");
		menuBar_1.add(mnMembersFile);
		
		JMenuItem mntmSearch = new JMenuItem("Search");
		mnMembersFile.add(mntmSearch);
		
		JMenuItem mntmRemoveMember = new JMenuItem("Remove Member");
		mnMembersFile.add(mntmRemoveMember);
		
		JMenu mnMembersSuspension = new JMenu("Suspension");
		menuBar_1.add(mnMembersSuspension);
		
		JMenuItem mntmSuspend = new JMenuItem("Suspend");
		mnMembersSuspension.add(mntmSuspend);
		
		JMenuItem mntmUnsuspend = new JMenuItem("Unsuspend");
		mnMembersSuspension.add(mntmUnsuspend);
		
		JMenuItem mntmCheckStatus = new JMenuItem("Check Status");
		mnMembersSuspension.add(mntmCheckStatus);
		
		internalFrame_Monitor = new JInternalFrame("Garage Monitor");
		internalFrame_Monitor.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		internalFrame_Monitor.setResizable(true);
		internalFrame_Monitor.setClosable(true);
		internalFrame_Monitor.setBounds(20, 372, 409, 298);
		desktopPane.add(internalFrame_Monitor);
		internalFrame_Monitor.getContentPane().setLayout(new MigLayout("", "[grow][30px:n][grow][30px:n]", "[][grow][]"));
		
		JLabel lblMembersCheckedIn = new JLabel("Members checked in:");
		internalFrame_Monitor.getContentPane().add(lblMembersCheckedIn, "cell 0 0");
		
		JLabel label_CheckedIn = new JLabel("0");
		internalFrame_Monitor.getContentPane().add(label_CheckedIn, "cell 1 0,alignx center");
		
		JLabel lblBicyclesParked = new JLabel("Bicycles parked:");
		internalFrame_Monitor.getContentPane().add(lblBicyclesParked, "cell 2 0");
		
		JLabel label_Parked = new JLabel("0");
		internalFrame_Monitor.getContentPane().add(label_Parked, "cell 3 0,alignx center");
		
		JList<Member> list_CheckedIn = new JList<Member>();
		internalFrame_Monitor.getContentPane().add(list_CheckedIn, "cell 0 1 2 1,grow");
		
		JList<Bicycle> list_Parked = new JList<Bicycle>();
		internalFrame_Monitor.getContentPane().add(list_Parked, "cell 2 1 2 1,grow");
		
		JButton btnRefresh = new JButton("Refresh");
		internalFrame_Monitor.getContentPane().add(btnRefresh, "cell 0 2 4 1,grow");
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(new MigLayout("", "[grow][grow][][][][]", "[][][][][][][grow]"));
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblData, "cell 0 0,alignx left,aligny center");
		
		JLabel lblFormatYymmdd = new JLabel("Format: " + dateFormat);
		panel.add(lblFormatYymmdd, "cell 2 1 4 1,alignx center");
		
		table = new JTable();
		panel.add(table, "cell 0 2 2 5,grow");
		
		JLabel lblFrom = new JLabel("From");
		panel.add(lblFrom, "cell 2 3,alignx trailing");
		
		textField = new JTextField();
		panel.add(textField, "cell 3 3 3 1,growx");
		textField.setColumns(10);
		
		JLabel lblTo = new JLabel("To");
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
	
	@SuppressWarnings("deprecation")
	private void getStats(String from, String to) throws ParseException {
		String[] columnNames = {"Members", "Bicycles", "Bicycles parked", "Members checked in"};
//		String[] f = from.split("-");
//		String[] t = to.split("-");
//		Date fDate=new Date(Integer.parseInt(f[0]), Integer.parseInt(f[1]), Integer.parseInt(f[2]));
//		Date tDate=new Date(Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2]));
//		Integer[][] data = stats.getInfo(fDate, tDate);
		Integer[][] data = stats.getInfo(new Date(), new Date());
		table = new JTable(data, columnNames);
	}
	
	private void addMember() {
		db.addMember(textField_fName.getText(),
				textField_lName.getText(),
				textField_AddPID.getText(),
				textField_AddTel.getText());
		fetchMemberList();
	}

	private void addBicycle() {
		Member m = db.getMember(textField_OwnerPIN.getText());
		if (m.getBicycles().size() < 2) {
			JOptionPane.showMessageDialog(internalFrame_AddBike,
					"Member already has 2 bicycles.",
					"Capacity reached",
					JOptionPane.PLAIN_MESSAGE);
		} else if (db.getBicycleSize() >= db.getMaxBicycleSize()) {
			JOptionPane.showMessageDialog(internalFrame_AddBike,
					"Maximum bicycles in the system has been reached.",
					"Capacity reached",
					JOptionPane.PLAIN_MESSAGE);
		} else {
			db.addBicycle(m, printer);
			showSelectedMember();
		}
	}

	private void addBikeDialog() {
		Member m = list_Members.getSelectedValue();
		String[] info = m.getInfo();
		textField_AddOwner.setText(info[0] + " " + info[1]);
		textField_OwnerPIN.setText(m.getPIN());
		internalFrame_AddBike.show();
	}

	private void changePIN() {
		String PIN = db.changePIN(list_Members.getSelectedValue().getPIN());
		JOptionPane.showMessageDialog(internalFrame_Members, "New PIN is: " + PIN, "PIN Changed", JOptionPane.PLAIN_MESSAGE);
		fetchMemberList();
	}

	private void showSelectedMember() {
		Member selected = list_Members.getSelectedValue();
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
	
	private void fetchMemberList() {
//		list_Members.setListData(db.getMemberList());
	}
}

