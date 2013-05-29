package de.jumpnbump.usecases.game.configuration;

import de.jumpnbump.usecases.game.android.input.ai.NoAiInputFactory;
import de.jumpnbump.usecases.game.android.input.ai.NormalAiInputFactory;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;

public enum AiModus {

	OFF(NoAiInputFactory.class), NORMAL(NormalAiInputFactory.class);

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
