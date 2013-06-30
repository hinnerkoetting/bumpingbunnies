package de.oetting.bumpingbunnies.usecases.start.communication.wlan;

import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;
import de.oetting.bumpingbunnies.usecases.start.communication.RemoteCommunicationImpl;
import de.oetting.bumpingbunnies.usecases.start.communication.SocketFactory;

public class WlanCommunicationFactory {

	public static WlanCommunication create(RoomActivity origin) {
		SocketFactory socketFactory = new WlanServerSocketFactory();
		RemoteCommunicationImpl communication = new RemoteCommunicationImpl(
				origin, socketFactory);
		return new WlanCommunication(origin, communication);
	}
}
