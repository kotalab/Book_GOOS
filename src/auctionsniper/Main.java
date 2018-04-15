package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.junit.experimental.theories.Theories;
import org.junit.internal.ArrayComparisonFailure;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.objogate.wl.gesture.StaticTracker;

import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;

public class Main {
	@SuppressWarnings("unused")
	private Chat notToBeGCD;

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID = 3;
	private MainWindow ui;
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	public static final String SNIPER_STATUS_NAME = "sniper status";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
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
		main.joinAuction(connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
	}
	
	private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
		disconnectWhenUICloses(connection);
		final Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection),
				null);
		notToBeGCD = chat;

		Auction auction = new XMPPAuction(chat);
		chat.addMessageListener(
				new AuctionMessageTranslator(
						connection.getUser(),
						new AuctionSniper(
								auction,
								new SwingThreadSniperListener(snipers),
								itemId)
						)
				);
		auction.join();
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
		connection.login(username, password, AUCTION_RESOURCE);
		
		return connection;
	}
	
	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}
}