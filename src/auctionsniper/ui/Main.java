package auctionsniper.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
	private MainWindow ui;
	public static final String MAIN_WINDOW_NAME = "Action Sniper Main";
	public static final String SNIPER_STATUS_NAME = "sniper status";
	
	public Main() throws Exception {
		startUserInterface();
	}
	
	public static void main(String... args) throws Exception {
		Main main = new Main();
	}
	
	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {			
			public void run() {
				ui = new MainWindow();
			}
		});
	}
	
	public class MainWindow extends JFrame {
		public MainWindow() {
			super("Auction Sniper");
			setName(MAIN_WINDOW_NAME);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
	}
}