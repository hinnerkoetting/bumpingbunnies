package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.usecases.game.factories.AbstractInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.ai.NoAiInputFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.ai.NormalAiInputFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.ai.RunnerAiInputFactory;

public enum AiModus {

	OFF(NoAiInputFactory.class), NORMAL(NormalAiInputFactory.class), RUNNER(
			RunnerAiInputFactory.class);

	private Class<? extends AbstractInputServiceFactory> factoryClass;

	private AiModus(Class<? extends AbstractInputServiceFactory> clazz) {
		this.factoryClass = clazz;
	}

	public AbstractInputServiceFactory createAiModeFactoryClass() {
		try {
			return this.factoryClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
