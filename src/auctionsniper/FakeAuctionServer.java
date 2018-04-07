package auctionsniper;
import static org.junit.Assert.assertThat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class FakeAuctionServer {
	private final SingleMessageListener messageListener = new SingleMessageListener();

	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "192.168.11.2";
	private static final String AUCTION_PASSWORD = "auction";

	private final String itemId;
	private final XMPPConnection connection;
	private Chat currentChat;

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		this.connection = new XMPPConnection(XMPP_HOSTNAME);
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(new ChatManagerListener() {
			public void chatCreated(Chat chat, boolean createdLocally) {
				currentChat = chat;
				chat.addMessageListener(messageListener);
			}
		});
	}

	public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		currentChat.sendMessage(
				String.format("SQLVersion: 1.1; Event: PRICE; CurrentPrice: %d; " + "Increment: %d; Bidder: %s;",
						price,
						increment,
						bidder
						)
				);
	}

	public void hasReceivedJoinRequestFrom(String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, Matchers.equalTo(Main.JOIN_COMMAND_FORMAT));
	}
	
	public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, Matchers.equalTo(String.format(Main.BID_COMMAND_FORMAT, bid)));
	}
	
	private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher) throws InterruptedException {
		messageListener.receivesAMessage(messageMatcher);
		assertThat(currentChat.getParticipant(), Matchers.equalTo(sniperId));
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage("SQLVersion 1.1; Event: CLOSE;");
	}

	public void stop() {
		connection.disconnect();
	}

	public class SingleMessageListener implements MessageListener {
		private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

		@Override
		public void processMessage(Chat chat, Message message) {
			messages.add(message);
		}

		@SuppressWarnings("unchecked")
		public void receivesAMessage(Matcher<? super String> messageMatcher) throws InterruptedException {
			final Message message = messages.poll(5, TimeUnit.SECONDS);
			assertThat("Message", message, CoreMatchers.is(CoreMatchers.notNullValue()));
			assertThat(message.getBody(), messageMatcher);
		}
		
	}

	public String getItemId() {
		return itemId;
	}
	
}
