package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;
import auctionsniper.ui.UserRequestListener;

public class Main {
	private List<Auction> notToBeGCd = new ArrayList<Auction>();

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private MainWindow ui;
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String JOIN_COMMAND_FORMAT = "SQLVersion: 1.1; Command: Join;";
	public static final String BID_COMMAND_FORMAT = "SQLVersion: 1.1; Command: Bid; Price: %d;";

	
	public Main() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				ui = new MainWindow(snipers);
			}
		});
	}
	
	public static void main(String... args) throws Exception {
		Main main = new Main();
		XMPPConnection connection = connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]); 
		main.disconnectWhenUICloses(connection);
		main.addUserRequestListenerFor(connection);
	}
	
	private void addUserRequestListenerFor(final XMPPConnection connection) {
		ui.addUserRequestListner(new UserRequestListener() {
			@Override
			public void joinAuction(String itemId) {
				snipers.addSniper(SniperSnapshot.joining(itemId));
				
				Auction auction = new XMPPAuction(connection, itemId);
				notToBeGCd.add(auction);

				auction.addAuctionEventListener(
						new AuctionSniper(
								auction,
								new SwingThreadSniperListener(snipers),
								itemId)
						);
				auction.join();
			}
		});
		
	}

	private void disconnectWhenUICloses(XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				connection.disconnect();	
			}
		});
	}

	private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, XMPPAuction.AUCTION_RESOURCE);
		
		return connection;
	}
	
}