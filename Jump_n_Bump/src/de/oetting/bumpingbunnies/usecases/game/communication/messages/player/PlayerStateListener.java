package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkListener;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public class PlayerStateListener implements NetworkListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerStateListener.class);
	private final MessageParser parser;
	private final MessageReceiverTemplate<PlayerStateMessage> receiver;

	public PlayerStateListener(MessageReceiverTemplate<PlayerStateMessage> receiver) {
		super();
		this.receiver = receiver;
		this.parser = new MessageParser(new Gson());
	}

	@Override
	public void newMessage(JsonWrapper wrapper) {
		try {
			if (isChecksumValid(wrapper)) {
				String message = wrapper.getMessage();
				PlayerStateMessage playerState = this.parser.parseMessage(message, PlayerStateMessage.class);
				this.receiver.onReceiveMessage(playerState);
			} else {
				LOGGER.warn("Checksum for Player-State is not equal");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isChecksumValid(JsonWrapper wrapper) throws NoSuchAlgorithmException {
		String message = wrapper.getMessage();
		byte[] checksum = wrapper.getOptionalChecksum();
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] shouldBeChecksum = digest.digest(message.getBytes());
		return Arrays.equals(checksum, shouldBeChecksum);
	}
}
