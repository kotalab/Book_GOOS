package auctionsniper.ui;

import java.lang.Thread.State;

import javax.swing.table.AbstractTableModel;

import auctionsniper.Main;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
	private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINNING);
	private SniperSnapshot snapshot = STARTING_UP; 
	private static String[] STATUS_TEXT = {
			"Joining", "Bidding", "Winning", "Lost", "Won"
			};

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
		return Column.at(columnIndex).valueIn(snapshot);
	}

	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		snapshot = newSnapshot;
		fireTableRowsUpdated(0, 0);		
	}
	
	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}

}
