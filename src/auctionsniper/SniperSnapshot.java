package auctionsniper;

public class SniperSnapshot {
	public final String itemId;
	public final int lastPrice;
	public final int lastBid;
	
	public SniperSnapshot(String itemId, int lastPrice, int lastBid) {
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
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
}
