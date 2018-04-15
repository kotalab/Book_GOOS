package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import auctionsniper.Main.SniperStateDisplayer;
import auctionsniper.SniperSnapshot;

public class MainWindow extends JFrame {
	private final SnipersTableModel snipers = new SnipersTableModel();

	public static final String MAIN_WINDOW_NAME = "Action Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding";
	public static final String STATUS_WINNING = "Winning";
	public static final String STATUS_WON = "Won";

	private static final String APPLICATION_TITLE = "Auction Sniper";

	private static final String SNIPERS_TABLE_NAME = null;
	private final JLabel snipetStatus = createLabel(STATUS_JOINING);
	
	public MainWindow() {
		super(APPLICATION_TITLE);
		setName(MAIN_WINDOW_NAME);
		fillContentPanel(makeSniperTable());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void fillContentPanel(JTable sniperTable) {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(new JScrollPane(sniperTable), BorderLayout.CENTER);
	}

	private JTable makeSniperTable() {
		final JTable snipersTable = new JTable(snipers);
		snipersTable.setName(SNIPERS_TABLE_NAME);
		return snipersTable;
	}

	public void showStatusText(String statusText) {
		snipers.setStatusText(statusText);
	}
	
	private JLabel createLabel(String initialText) {
		JLabel result = new JLabel(initialText);
		result.setName(SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		return result;
	}

	public void sniperStatusChanged(SniperSnapshot sniperState, String statusText) {
		snipers.sniperStatusChanged(sniperState, statusText);
		
	}
}