package de.oetting.bumpingbunnies.usecases.game.communication.objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JsonWrapper {

	private final MessageId id;
	private final String message;
	private final byte[] optionalChecksum;

	private JsonWrapper(MessageId id, String message, byte[] hash) {
		this.id = id;
		this.message = message;
		this.optionalChecksum = hash;
	}

	public String getMessage() {
		return this.message;
	}

	public MessageId getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.message == null) ? 0 : this.message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JsonWrapper other = (JsonWrapper) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!this.message.equals(other.message)) {
			return false;
		}
		return true;
	}

	public byte[] getOptionalChecksum() {
		return this.optionalChecksum;
	}

	public static JsonWrapper create(MessageId messageId, String message) {
		return new JsonWrapper(messageId, message, null);
	}

	public static JsonWrapper createWithChecksum(MessageId messageId, String message) {
		try {
			MessageDigest hashAlgorithm = MessageDigest.getInstance("MD5");
			byte[] hash = hashAlgorithm.digest(message.getBytes());
			return new JsonWrapper(messageId, message, hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
