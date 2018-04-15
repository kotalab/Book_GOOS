package auctionsniper;

public class SniperSnapshot {
	public final String itemId;
	public final int lastPrice;
	public final int lastBid;
	public final SniperState state;
	
	public SniperSnapshot(String itemId, int lastPrice, int lastBid, SniperState state) {
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
		this.state = state;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) { return false; }
		
		return this.itemId.equals(((SniperSnapshot) obj).itemId) &&
				this.lastPrice == ((SniperSnapshot) obj).lastPrice &&
				this.lastBid == ((SniperSnapshot) obj).lastBid; 
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public SniperSnapshot biding(int newLastPrice, int newLatBid) {
		return new SniperSnapshot(itemId, newLastPrice, newLatBid, SniperState.BIDDING);
	}

	public SniperSnapshot winning(int newLastPrice) {
		return new SniperSnapshot(itemId, newLastPrice, lastBid, SniperState.WINNING);
	}

	public static SniperSnapshot joining(String itemId) {
		return new SniperSnapshot(itemId, 0, 0, SniperState.JOINNING);
	}
}
