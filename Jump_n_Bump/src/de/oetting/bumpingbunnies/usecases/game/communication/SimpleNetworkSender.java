package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.Writer;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class SimpleNetworkSender implements NetworkSender {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimpleNetworkSender.class);
	private final Writer writer;
	private final Gson gson;

	public SimpleNetworkSender(Writer writer, Gson gson) {
		super();
		this.writer = writer;
		this.gson = gson;
	}

	@Override
	public void sendMessage(JsonWrapper message) {
		try {
			LOGGER.debug("sending message %s", message.getMessage());
			String json = this.gson.toJson(message);
			this.writer.write(json);
			this.writer.write('\n');
			this.writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
