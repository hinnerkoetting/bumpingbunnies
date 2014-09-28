package de.oetting.bumpingbunnies.communication.wlan;

import de.oetting.bumpingbunnies.communication.ConnectionEstablisher;
import de.oetting.bumpingbunnies.communication.SocketFactory;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class WlanCommunicationFactory {

	public static WlanCommunication create(RoomActivity origin) {
		SocketFactory socketFactory = new WlanSocketFactory();
		ConnectionEstablisher communication = new ConnectionEstablisher(origin, origin, socketFactory);
		return new WlanCommunication(origin, communication);
	}
}
