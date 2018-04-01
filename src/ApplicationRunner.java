import auctionsniper.ui.Main;

public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	public static final String XMPP_HOSTNAME = "localhost:9090";
	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "lost";
	private AuctionSniperDriver driver;
	
	public void startBiddingIn(final FakeAuctionServer auction) {
		Thread thread = new Thread("Test Application") {
			@Override public void run() {
				try {
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.showsSniperStatus(STATUS_JOINING);
	}
	
	public void showsSniperHasLostAuction() {
		driver.showsSniperStatus(STATUS_LOST);
	}
	
	public void stop() {
		if (driver != null) {
			driver.dispose();
		}
	}
}
