package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	private final SniperListener sniperListener;
	private final Auction auction;

	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.sniperListener = sniperListener;
		this.auction = auction;
	}

	public void auctionClosed() {
		sniperListener.sniperLost();
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		switch (priceSource) {
		case FromSniper:
			sniperListener.sniperWinning();
			break;
		case FromOtherBidder:
			auction.bid(price + increment);
			sniperListener.sniperBidding();
		}
	}
}
