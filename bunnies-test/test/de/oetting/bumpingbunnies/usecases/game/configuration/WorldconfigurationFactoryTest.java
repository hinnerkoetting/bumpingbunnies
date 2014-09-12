package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.CastleWorldbuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.TestWorldBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.XmlClassicWorldBuilder;

@Category(IntegrationTests.class)
public class WorldconfigurationFactoryTest {

	@Test
	public void create_classis_returnsClassicWorldBuilder() {
		WorldObjectsParser parser = whenCreating(WorldConfiguration.CLASSIC);
		assertThat(parser, instanceOf(XmlClassicWorldBuilder.class));
	}

	@Test
	public void create_castle_returnsCastleWorldBuilder() {
		WorldObjectsParser parser = whenCreating(WorldConfiguration.CASTLE);
		assertThat(parser, instanceOf(CastleWorldbuilder.class));
	}

	@Test
	public void create_rest_returnsTestWorldBuilder() {
		WorldObjectsParser parser = whenCreating(WorldConfiguration.TEST);
		assertThat(parser, instanceOf(TestWorldBuilder.class));
	}

	private WorldObjectsParser whenCreating(WorldConfiguration configuration) {
		return new WorldConfigurationFactory().createWorldParser(configuration);
	}
}
