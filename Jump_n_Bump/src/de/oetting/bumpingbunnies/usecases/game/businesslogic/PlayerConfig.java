package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;

public class PlayerConfig {
	private final OpponentConfiguration configuration;
	private final AiModus aiModus;
	private final PlayerMovement movementController;

	public PlayerConfig(AiModus aiModus,
			PlayerMovement movementController,
			OpponentConfiguration configuration) {
		super();
		this.aiModus = aiModus;
		this.movementController = movementController;
		this.configuration = configuration;
	}

	public PlayerMovement getMovementController() {
		return this.movementController;
	}

	public AiModus getAiModus() {
		return this.aiModus;
	}

	public OpponentConfiguration getConfiguration() {
		return this.configuration;
	}

}
