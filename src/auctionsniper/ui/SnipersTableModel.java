package auctionsniper.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.objogate.exception.Defect;

import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
	private List<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();
	private static String[] STATUS_TEXT = {
			"Joining", "Bidding", "Winning", "Lost", "Won"
			};

	@Override
	public int getRowCount() {
		return snapshots.size();
	}

	@Override
	public int getColumnCount() {
		return Column.values().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshots.get(rowIndex));
	}
	
	@Override
	public String getColumnName(int column) {
		return Column.at(column).name;
	}

	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		int row = rowMatching(newSnapshot);
		snapshots.set(row, newSnapshot);
		fireTableRowsUpdated(row, row);		
	}
	
	private int rowMatching(SniperSnapshot snapshot) {
		for (int i = 0; i < snapshots.size(); i++) {
			if (snapshot.isForSameItemAs(snapshots.get(i))) {
				return i;
			}
		}
		throw new Defect("Cannot find match for " + snapshot);
	}

	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}

	public void addSniper(SniperSnapshot snapshot) {
		int row = getRowCount();
		snapshots.add(snapshot);
		fireTableRowsInserted(row, row);
	}
}
