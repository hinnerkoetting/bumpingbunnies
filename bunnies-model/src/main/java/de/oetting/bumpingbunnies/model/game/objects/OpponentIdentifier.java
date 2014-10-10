package de.oetting.bumpingbunnies.model.game.objects;

public class OpponentIdentifier {

	// Must be unique between all players
	private final String identifier;

	public OpponentIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		OpponentIdentifier other = (OpponentIdentifier) obj;
		return identifier.equals(other.getIdentifier());
	}

	@Override
	public String toString() {
		return "OpponentIdentifier [identifier=" + identifier + "]";
	}

}
