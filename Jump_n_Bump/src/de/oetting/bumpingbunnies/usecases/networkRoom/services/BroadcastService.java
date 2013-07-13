package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.net.BindException;
import java.net.DatagramSocket;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class BroadcastService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastService.class);
	private final Activity origin;
	private SendBroadCastsThread sendBroadcastsThread;
	private ListenForBroadcastsThread broadcastThread;

	public BroadcastService(Activity origin) {
		this.origin = origin;
	}

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
					LOGGER.info("Searching for host...");
					DatagramSocket socket = new DatagramSocket(
							NetworkConstants.BROADCAST_PORT);

					BroadcastService.this.broadcastThread = new ListenForBroadcastsThread(
							socket, room);
					BroadcastService.this.broadcastThread.start();
				} catch (BindException e) {
					displayErrorAddressInUse();
					LOGGER.warn("Error when trying to search for host", e);
					throw new RuntimeException(e);
				} catch (Exception e) {
					displayListenError();
					LOGGER.warn("Error when trying to search for host", e);
				}
			}
		}).start();
	}

	public void displayErrorAddressInUse() {
		String addressInUse = this.origin.getResources().getString(R.string.address_in_use);
		displayMessage(addressInUse);
	}

	public void displayListenError() {
		String unknownError = this.origin.getResources().getString(R.string.unknown_error);
		displayMessage(unknownError);
	}

	private void displayMessage(final String message) {
		this.origin.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(BroadcastService.this.origin, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
