package de.oetting.bumpingbunnies.model.configuration;

import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PlayerConfig {

	private final OpponentConfiguration configuration;
	private final AiModus aiModus;
	private final Bunny player;
	private final InputConfiguration inputConfiguration;

	public PlayerConfig(OpponentConfiguration configuration, AiModus aiModus, Bunny player, InputConfiguration inputConfiguration) {
		this.configuration = configuration;
		this.aiModus = aiModus;
		this.player = player;
		this.inputConfiguration = inputConfiguration;
	}

	public Bunny getPlayer() {
		return this.player;
	}

	public AiModus getAiModus() {
		return this.aiModus;
	}

	public OpponentConfiguration getConfiguration() {
		return this.configuration;
	}

	public InputConfiguration getInputConfiguration() {
		return inputConfiguration;
	}

}
