package auctionsniper;

import javax.xml.bind.Marshaller.Listener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {
	private AuctionEventListener listener = null;
	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener;
	}
	public void processMessage(Chat chat, Message message) {
		this.listener.auctionClosed();
	}

}
