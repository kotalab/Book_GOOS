package auctionsniper;

public class SniperState {
	public final String itemId;
	public final int lastPrice;
	public final int lastBid;
	
	public SniperState(String itemId, int lastPrice, int lastBid) {
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) { return false; }
		
		return this.itemId.equals(((SniperState) obj).itemId) &&
				this.lastPrice == ((SniperState) obj).lastPrice &&
				this.lastBid == ((SniperState) obj).lastBid; 
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
