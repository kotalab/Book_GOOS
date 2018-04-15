package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainWindow extends JFrame {
	private final SnipersTableModel snipers = new SnipersTableModel();

	public static final String MAIN_WINDOW_NAME = "Action Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String APPLICATION_TITLE = "Auction Sniper";
	private static final String SNIPERS_TABLE_NAME = null;
	
	public MainWindow(SnipersTableModel snipers) {
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
}