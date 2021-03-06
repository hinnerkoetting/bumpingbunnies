package de.oetting.bumpingbunnies.model.game.objects;

public class ConnectionIdentifier {

	private final OpponentIdentifier identifier;
	private final OpponentType type;

	public ConnectionIdentifier(OpponentIdentifier identifier, OpponentType type) {
		this.identifier = identifier;
		this.type = type;
	}

	public OpponentIdentifier getIdentifier() {
		return this.identifier;
	}

	public boolean isLocalPlayer() {
		return OpponentType.LOCAL_PLAYER == this.type || OpponentType.AI == this.type;
	}

	public boolean isLocalHumanPlayer() {
		return OpponentType.LOCAL_PLAYER == this.type;
	}

	public OpponentType getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.identifier == null) ? 0 : this.identifier.hashCode());
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
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
		ConnectionIdentifier other = (ConnectionIdentifier) obj;
		if (this.identifier == null) {
			if (other.identifier != null) {
				return false;
			}
		} else if (!this.identifier.equals(other.identifier)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public ConnectionIdentifier clone() {
		return new ConnectionIdentifier(identifier, type);
	}

	@Override
	public String toString() {
		return "Opponent [identifier=" + identifier + ", type=" + type + "]";
	}

	public boolean isDirectlyConnected() {
		return type.equals(OpponentType.WLAN) || type.equals(OpponentType.BLUETOOTH);
	}

	public boolean isAi() {
		return type.equals(OpponentType.AI);
	}

	public boolean isRemotePlayer() {
		return !isLocalPlayer();
	}

}
