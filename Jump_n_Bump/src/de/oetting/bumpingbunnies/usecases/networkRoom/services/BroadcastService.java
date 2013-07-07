package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.net.DatagramSocket;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BroadcastService {

	private SendBroadCastsThread sendBroadcastsThread;
	private ListenForBroadcastsThread broadcastThread;

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
		if (this.broadcastThread != null) {
			this.broadcastThread.cancel();
			this.broadcastThread.closeSocket();
		}
	}

	public void listenForBroadCasts(final RoomActivity room) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					DatagramSocket socket = new DatagramSocket(
							NetworkConstants.BROADCAST_PORT);

					BroadcastService.this.broadcastThread = new ListenForBroadcastsThread(
							socket, room);
					BroadcastService.this.broadcastThread.start();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}
}
