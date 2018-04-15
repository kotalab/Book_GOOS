package auctionsniper.ui;

import javax.swing.table.AbstractTableModel;

import auctionsniper.SniperSnapshot;

public class SnipersTableModel extends AbstractTableModel {
	private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0);
	private String statusText = MainWindow.STATUS_JOINING;
	private SniperSnapshot sniperState = STARTING_UP; 

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
			return sniperState.itemId;
		case LAST_PRICE:
			return sniperState.lastPrice;
		case LAST_BID:
			return sniperState.lastBid;
		case SNIPER_STATE:
			return statusText;
		default:
			throw new IllegalArgumentException("No columnt at " + columnIndex);
		}
	}

	public void setStatusText(String statusText) {
		// TODO Auto-generated method stub
		
	}

	public void sniperStatusChanged(SniperSnapshot newSniperState, String newStatusText) {
		sniperState = newSniperState;
		statusText = newStatusText;
		fireTableRowsUpdated(0, 0);		
	}

}
