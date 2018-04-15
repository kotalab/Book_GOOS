package auctionsniper;

import javax.swing.SwingUtilities;

import auctionsniper.ui.SnipersTableModel;

public class SwingThreadSniperListener implements SniperListener {
	private final SniperListener sniperListener;
	
	public SwingThreadSniperListener(SnipersTableModel snipers) {
		this.sniperListener = snipers;
	}

	public void sniperStateChanged(final SniperSnapshot state) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				sniperListener.sniperStateChanged(state);
			}
		});
	}
}
