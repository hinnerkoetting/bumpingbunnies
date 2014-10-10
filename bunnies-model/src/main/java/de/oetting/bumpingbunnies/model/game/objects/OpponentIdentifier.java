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

}
