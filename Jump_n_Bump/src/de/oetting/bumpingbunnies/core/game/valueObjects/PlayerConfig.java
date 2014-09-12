package de.oetting.bumpingbunnies.core.game.valueObjects;

import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerConfig {
	private final OpponentConfiguration configuration;
	private final AiModus aiModus;
	private final Player player;

	public PlayerConfig(AiModus aiModus,
			Player player,
			OpponentConfiguration configuration) {
		super();
		this.aiModus = aiModus;
		this.player = player;
		this.configuration = configuration;
	}

	public Player getPlayer() {
		return this.player;
	}

	public AiModus getAiModus() {
		return this.aiModus;
	}

	public OpponentConfiguration getConfiguration() {
		return this.configuration;
	}

}
