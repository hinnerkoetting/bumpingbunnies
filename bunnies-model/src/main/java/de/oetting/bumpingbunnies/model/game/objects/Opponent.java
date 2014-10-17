package de.oetting.bumpingbunnies.model.game.objects;


public class Opponent {

	private final OpponentIdentifier identifier;
	private final OpponentType type;

	public Opponent(OpponentIdentifier identifier, OpponentType type) {
		this.identifier = identifier;
		this.type = type;
	}

	public OpponentIdentifier getIdentifier() {
		return this.identifier;
	}

	public boolean isMyPlayer() {
		return OpponentType.LOCAL_PLAYER == this.type;
	}

	public boolean isLocalPlayer() {
		return OpponentType.LOCAL_PLAYER == this.type || OpponentType.AI == this.type;
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
		Opponent other = (Opponent) obj;
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
	public Opponent clone() {
		return new Opponent(identifier, type);
	}

	@Override
	public String toString() {
		return "Opponent [identifier=" + identifier + ", type=" + type + "]";
	}

	public boolean isDirectlyConnected() {
		return type.equals(OpponentType.WLAN) || type.equals(OpponentType.BLUETOOTH);
	}

}
