package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.ai.NoAiInputFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.ai.NormalAiInputFactory;

public enum AiModus {

	OFF(NoAiInputFactory.class), NORMAL(NormalAiInputFactory.class)
	// , RUNNER(RunnerAiInputFactory.class)
	;

	private Class<? extends OtherPlayerInputServiceFactory> factoryClass;

	private AiModus(Class<? extends OtherPlayerInputServiceFactory> clazz) {
		this.factoryClass = clazz;
	}

	public OtherPlayerInputServiceFactory createAiModeFactoryClass() {
		try {
			return this.factoryClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
