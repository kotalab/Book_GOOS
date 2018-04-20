package test.auctionsniper;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;
import com.objogate.wl.swing.matcher.JLabelTextMatcher;

import auctionsniper.ui.MainWindow;

public class AuctionSniperDriver extends JFrameDriver {
	
	@SuppressWarnings("unchecked")
	public AuctionSniperDriver(int timeoutMillis) {
		super(
				new GesturePerformer(),
				JFrameDriver.topLevelFrame(named(MainWindow.MAIN_WINDOW_NAME), showingOnScreen()),
				new AWTEventQueueProber(timeoutMillis, 100)
				);
	}
	
	@SuppressWarnings("unchecked")
	public void showsSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
		JTableDriver table = new JTableDriver(this);
		table.hasRow(IterableComponentsMatcher.matching(
				JLabelTextMatcher.withLabelText(itemId),
				JLabelTextMatcher.withLabelText(String.valueOf(lastPrice)),
				JLabelTextMatcher.withLabelText(String.valueOf(lastBid)),
				JLabelTextMatcher.withLabelText(statusText)
				));
	}

	public void hasColumnTitles() {
		JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
		headers.hasHeaders(IterableComponentsMatcher.matching(
				JLabelTextMatcher.withLabelText("item"),
				JLabelTextMatcher.withLabelText("Last Price"),
				JLabelTextMatcher.withLabelText("Last Bid"),
				JLabelTextMatcher.withLabelText("State")
				));
	}

	@SuppressWarnings("unchecked")
	public void startBiddingFor(String itemId) {
		itemField().replaceAllText(itemId);
		bidButton().click();
	}
	
	private JTextFieldDriver itemField() {
		JTextFieldDriver newItemId = new JTextFieldDriver(this, JTextField.class, named(MainWindow.NEW_ITEM_ID_NAME));
		newItemId.focusWithMouse();
		return newItemId;
	}
	
	private JButtonDriver bidButton() {
		return new JButtonDriver(this, JButton.class, named(MainWindow.JOIN_BUTTON_NAME));
	}
}
