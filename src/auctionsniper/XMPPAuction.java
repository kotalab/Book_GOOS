package auctionsniper;

import java.sql.Connection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.util.Announcer;

public class XMPPAuction implements Auction {
	private final Announcer<AuctionEventListener> auctionEventListeners = Announcer.to(AuctionEventListener.class);
	private final Chat chat;
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
	
	public XMPPAuction(XMPPConnection connection, String itemId) {
		chat = connection.getChatManager().createChat(
				auctionId(itemId, connection),
				new AuctionMessageTranslator(
						connection.getUser(),
						auctionEventListeners.announce()
						)
				);
	}
	
	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}

	@Override
	public void bid(int amount) {
		sendMessage(String.format(Main.BID_COMMAND_FORMAT, amount));
	}
	
	@Override
	public void join() {
		sendMessage(Main.JOIN_COMMAND_FORMAT);
	}

	private void sendMessage(final String message) {
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void addAuctionEventListener(AuctionSniper auctionSniper) {
		auctionEventListeners.addListener(auctionSniper);
	}
}