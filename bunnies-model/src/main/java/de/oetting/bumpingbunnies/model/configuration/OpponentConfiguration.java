package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class OpponentConfiguration {

	private final AiModus aiMode;
	private final PlayerProperties otherPlayerState;
	private final Opponent opponent;

	public OpponentConfiguration(AiModus aiMode, PlayerProperties otherPlayerState, Opponent opponent) {
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

	public Opponent getOpponent() {
		return this.opponent;
	}

	public PlayerProperties getOtherPlayerState() {
		return otherPlayerState;
	}

}
