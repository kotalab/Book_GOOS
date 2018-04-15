package auctionsniper.ui;

import java.lang.Thread.State;

import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

public class SnipersTableModel extends AbstractTableModel {
	private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINNING);
	private String state = MainWindow.STATUS_JOINING;
	private SniperSnapshot snapshot = STARTING_UP; 
	private static String[] STATUS_TEXT = { MainWindow.STATUS_JOINING, MainWindow.STATUS_BIDDING };

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (Column.at(columnIndex)) {
		case ITEM_IDENTIFIER:
			return snapshot.itemId;
		case LAST_PRICE:
			return snapshot.lastPrice;
		case LAST_BID:
			return snapshot.lastBid;
		case SNIPER_STATE:
			return state;
		default:
			throw new IllegalArgumentException("No columnt at " + columnIndex);
		}
	}

	public void setStatusText(String statusText) {
		// TODO Auto-generated method stub
		
	}

	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		snapshot = newSnapshot;
		state = STATUS_TEXT[newSnapshot.state.ordinal()];
		
		fireTableRowsUpdated(0, 0);		
	}

}
