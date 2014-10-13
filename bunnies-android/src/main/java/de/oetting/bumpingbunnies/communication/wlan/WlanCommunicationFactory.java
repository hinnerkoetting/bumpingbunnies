package de.oetting.bumpingbunnies.communication.wlan;

import de.oetting.bumpingbunnies.core.network.WlanSocketFactory;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.init.DefaultConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class WlanCommunicationFactory {

	public static ConnectionEstablisher create(RoomActivity origin) {
		SocketFactory socketFactory = new WlanSocketFactory();
		return new DefaultConnectionEstablisher(origin, origin, socketFactory, origin);
	}
}
