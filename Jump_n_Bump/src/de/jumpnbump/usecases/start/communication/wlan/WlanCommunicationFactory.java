package de.jumpnbump.usecases.start.communication.wlan;

import de.jumpnbump.usecases.networkRoom.RoomActivity;
import de.jumpnbump.usecases.start.communication.RemoteCommunicationImpl;
import de.jumpnbump.usecases.start.communication.SocketFactory;

public class WlanCommunicationFactory {

	public static WlanCommunication create(RoomActivity origin) {
		SocketFactory socketFactory = new WlanServerSocketFactory();
		RemoteCommunicationImpl communication = new RemoteCommunicationImpl(
				origin, socketFactory);
		return new WlanCommunication(communication);
	}
}
