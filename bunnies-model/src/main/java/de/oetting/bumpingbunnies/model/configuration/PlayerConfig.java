package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.game.objects.Player;

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
