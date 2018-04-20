package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import auctionsniper.util.Announcer;

public class MainWindow extends JFrame {
	public static final String MAIN_WINDOW_NAME = "Action Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String APPLICATION_TITLE = "Auction Sniper";
	private static final String SNIPERS_TABLE_NAME = null;
	private final Announcer<UserRequestListener> userRequests = Announcer.to(UserRequestListener.class);

	public static final String JOIN_BUTTON_NAME = "Join";

	public static final String NEW_ITEM_ID_NAME = "item id";
	
	public MainWindow(SnipersTableModel snipers) {
		super(APPLICATION_TITLE);
		setName(MAIN_WINDOW_NAME);
		fillContentPanel(makeSniperTable(snipers), makeControls(snipers));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private JPanel makeControls(final SnipersTableModel snipers) {
		JPanel controls = new JPanel(new FlowLayout());
		final JTextField itemIdFieald = new JTextField();
		itemIdFieald.setColumns(25);
		itemIdFieald.setName(NEW_ITEM_ID_NAME);
		controls.add(itemIdFieald);
		
		JButton joinAuctionButton = new JButton("Join Auction");
		joinAuctionButton.setName(JOIN_BUTTON_NAME);
		joinAuctionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userRequests.announce().joinAuction(itemIdFieald.getText());				
			}
		});
		controls.add(joinAuctionButton);
		
		return controls;
	}
	private void fillContentPanel(JTable sniperTable, JPanel controls) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(controls, BorderLayout.NORTH);
		contentPane.add(new JScrollPane(sniperTable), BorderLayout.CENTER);
	}

	private JTable makeSniperTable(SnipersTableModel snipers) {
		final JTable snipersTable = new JTable(snipers);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}

	public void addUserRequestListner(UserRequestListener userRequestListener) {
		userRequests.addListener(userRequestListener);
	}
}