package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import android.content.Context;

public class BroadcastService {

	private SendBroadCastsThread sendBroadcastsThread;

	public void startRegularServerBroadcast(final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				BroadcastService.this.sendBroadcastsThread = SendBroadcastFactory
						.create(context);
				BroadcastService.this.sendBroadcastsThread.start();

			}
		}).start();
	}

	public void cancel() {
		if (this.sendBroadcastsThread != null) {
			this.sendBroadcastsThread.cancel();
			this.sendBroadcastsThread.closeAllSockets();
		}
	}
}
