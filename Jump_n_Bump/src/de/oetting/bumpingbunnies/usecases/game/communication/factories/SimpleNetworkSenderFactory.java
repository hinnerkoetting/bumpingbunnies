package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import java.io.OutputStreamWriter;
import java.io.Writer;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class SimpleNetworkSenderFactory {

	public static SimpleNetworkSender createNetworkSender(MySocket socket) {
		try {
			Writer writer = new OutputStreamWriter(socket.getOutputStream(),
					NetworkConstants.ENCODING);
			return new SimpleNetworkSender(writer, new Gson());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
