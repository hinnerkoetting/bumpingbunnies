package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public class OpponentConfiguration {

	private final AiModus aiMode;
	private final PlayerProperties otherPlayerState;
	private final ConnectionIdentifier opponent;

	public OpponentConfiguration(AiModus aiMode, PlayerProperties otherPlayerState, ConnectionIdentifier opponent) {
		this.aiMode = aiMode;
		this.otherPlayerState = otherPlayerState;
		this.opponent = opponent;
	}

	public AiModus getAiMode() {
		return this.aiMode;
	}

	public int getPlayerId() {
		return this.otherPlayerState.getPlayerId();
	}

	public String getName() {
		return this.otherPlayerState.getPlayerName();
	}

	public ConnectionIdentifier getOpponent() {
		return this.opponent;
	}

	public PlayerProperties getOtherPlayerState() {
		return otherPlayerState;
	}

}
