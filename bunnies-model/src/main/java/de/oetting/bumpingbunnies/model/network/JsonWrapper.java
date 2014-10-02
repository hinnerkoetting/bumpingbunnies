package de.oetting.bumpingbunnies.model.network;

public class JsonWrapper {

	private final MessageId id;
	private final String message;

	private JsonWrapper(MessageId id, String message) {
		this.id = id;
		this.message = message;
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

	public static JsonWrapper create(MessageId messageId, String message) {
		return new JsonWrapper(messageId, message);
	}

	@Override
	public String toString() {
		return "JsonWrapper [id=" + this.id + ", message=" + this.message + "]";
	}

}
