package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.core.configuration.ai.AiModus;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

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
