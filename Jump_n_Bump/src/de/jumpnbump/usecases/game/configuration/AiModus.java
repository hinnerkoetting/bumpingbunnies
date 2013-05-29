package de.jumpnbump.usecases.game.configuration;

import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.factories.ai.NoAiInputFactory;
import de.jumpnbump.usecases.game.factories.ai.NormalAiInputFactory;
import de.jumpnbump.usecases.game.factories.ai.RunnerAiInputFactory;

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
