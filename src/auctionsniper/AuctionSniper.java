package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	private final SniperListener sniperListener;
	private final Auction auction;
	private boolean isWinning = false;
	private SniperSnapshot snapshot;

	public AuctionSniper(Auction auction, SniperListener sniperListener, String itemId) {
		this.sniperListener = sniperListener;
		this.auction = auction;
		this.snapshot = SniperSnapshot.joining(itemId);
	}

	public String getItemId() {
		return snapshot.itemId;
	}
	public void auctionClosed() {
		if (isWinning) {
			sniperListener.sniperWon();
		} else {
			sniperListener.sniperLost();	
		}
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = priceSource == PriceSource.FromSniper;
		if (isWinning) {
			snapshot = snapshot.winning(price);
		} else {
			int bid = price + increment;
			auction.bid(bid);
			snapshot = snapshot.biding(price, bid);
		}
		sniperListener.sniperStateChanged(snapshot);
	}
}
