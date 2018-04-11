package auctionsniper;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.AuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {
	private final String sniperId;
	private final AuctionEventListener listener;
	
	public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
		this.sniperId = sniperId;
		this.listener = listener;
	}
	
	@Override
	public void processMessage(Chat chat, Message message) {
		AuctionEvent event = AuctionEvent.from(message.getBody());
		String eventType = event.type();

		if ("CLOSE".equals(eventType)) {
			listener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
			listener.currentPrice(event.currentPrice(), event.increment(), event.isFrom(sniperId));
		}		
	}

	private static class AuctionEvent {
		private final Map<String, String> fields = new HashMap<String, String>();

		public static AuctionEvent from(String messageBody) {
			AuctionEvent event = new AuctionEvent();
			for (String field : fieldsIn(messageBody)) {
				event.addField(field);
			}
			return event;
		}

		private void addField(String field) {
			String[] pair = field.split(":");
			fields.put(pair[0].trim(), pair[1].trim());
		}

		private static String[] fieldsIn(String messageBody) {
			return messageBody.split(";");
		}

		public int currentPrice() {
			return getInt("CurrentPrice");
		}

		private int getInt(String fieldName) {
			return Integer.parseInt(get(fieldName));
		}

		public int increment() {
			return getInt("Increment");
		}

		public String type() {
			return get("Event");
		}

		private String get(String fieldName) {
			return fields.get(fieldName);
		}
		
		public PriceSource isFrom(String sniperId) {
			return sniperId.equals(bidder()) ? PriceSource.FromSniper : PriceSource.FromOtherBidder;
		}

		private String bidder() {
			return get("Bidder");
		}
	}
}
