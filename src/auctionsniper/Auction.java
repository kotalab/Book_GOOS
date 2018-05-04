package auctionsniper;

public interface Auction {

	void bid(int price);
	
	void join();

	void addAuctionEventListener(AuctionSniper auctionSniper);

}
