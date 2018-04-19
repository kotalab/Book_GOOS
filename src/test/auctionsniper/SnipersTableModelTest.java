package test.auctionsniper;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.jws.Oneway;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasProperty;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.syntax.WithClause;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import auctionsniper.Main;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.ui.Column;
import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;

@RunWith(JMock.class)
public class SnipersTableModelTest {
	private final Mockery context = new Mockery();
	private TableModelListener listener = context.mock(TableModelListener.class);
	private final SnipersTableModel model = new SnipersTableModel();
	
	@Before
	public void attachModelListener() {
		model.addTableModelListener(listener);
	}
	
	@Test
	public void hasEnoughColumns() {
		assertThat(model.getColumnCount(), Matchers.equalTo(Column.values().length));
	}
	
	@Test
	public void setsSniperValuesInColumns() {
		SniperSnapshot joining = SniperSnapshot.joining("item id");
		SniperSnapshot bidding = joining.biding(555, 666);
		context.checking(new Expectations() {{
			allowing(listener).tableChanged(with(anyInsertionEvent()));
			one(listener).tableChanged(with(aChangeInRow(0)));
		}});
		
		model.addSniper(joining);
		model.sniperStateChanged(bidding);
		
		assertRowMatchesSnapshot(0, bidding);
	}
	
	@Test
	public void setupColumnHeadings() {
		for (Column column: Column.values()) {
			assertEquals(column.name, model.getColumnName(column.ordinal()));
		}
	}
	
	@Test
	public void notifiersListenersWhenAddingASniper() {
		SniperSnapshot joining = SniperSnapshot.joining("item123");
		context.checking(new Expectations() {{
			one(listener).tableChanged(with(anInsertionAtRow(0)));
		}});
		
		assertEquals(0, model.getRowCount());
		
		model.addSniper(joining);
		
		assertEquals(1, model.getRowCount());
		assertRowMatchesSnapshot(0, joining);
	}
	
	@Test
	public void holdsSnipersInAddtionOrder() {
		context.checking(new Expectations() {{
			ignoring(listener);
		}});
		
		model.addSniper(SniperSnapshot.joining("item 0"));
		model.addSniper(SniperSnapshot.joining("item 1"));
		
		assertEquals("item 0", cellValue(0, Column.ITEM_IDENTIFIER));
		assertEquals("item 1", cellValue(1, Column.ITEM_IDENTIFIER));
	}
	
	@Test
	public void updatesCorrectRowForSniper() {
		// TODO
	}

	@Test
	public void throwsDefectIfNoExistingSniperForAnUpdate() {
		// TODO
	}

	private Object cellValue(int rowIndex, Column column) {
		return model.getValueAt(rowIndex, column.ordinal());
	}

	private void assertRowMatchesSnapshot(int rowIndex, SniperSnapshot snapshot) {
		assertEquals(snapshot.itemId, cellValue(rowIndex, Column.ITEM_IDENTIFIER));
		assertEquals(snapshot.lastPrice, cellValue(rowIndex, Column.LAST_PRICE));
		assertEquals(snapshot.lastBid, cellValue(rowIndex, Column.LAST_BID));
		assertEquals(SnipersTableModel.textFor(snapshot.state), cellValue(rowIndex, Column.SNIPER_STATE));
	}

	protected Matcher<TableModelEvent> anInsertionAtRow(int row) {
		return samePropertyValuesAs(
				new TableModelEvent(
						model,
						row,
						row,
						TableModelEvent.ALL_COLUMNS,
						TableModelEvent.INSERT
						));
	}

	private Matcher<TableModelEvent> anyInsertionEvent() {
		return hasProperty("type", equalTo(TableModelEvent.INSERT));
	}

	private Matcher<TableModelEvent> aChangeInRow(int row) {
		return samePropertyValuesAs(new TableModelEvent(model, row));
	}
}