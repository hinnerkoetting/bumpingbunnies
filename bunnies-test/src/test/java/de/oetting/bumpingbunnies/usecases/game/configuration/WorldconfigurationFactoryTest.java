package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlWorldParserTemplate;
import de.oetting.bumpingbunnies.android.xml.parsing.XmlClassicWorldBuilder;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
public class WorldconfigurationFactoryTest {

	@Test
	public void create_classis_returnsClassicWorldBuilder() {
		AndroidXmlWorldParserTemplate parser = whenCreating(WorldConfiguration.CLASSIC);
		assertThat(parser, instanceOf(XmlClassicWorldBuilder.class));
	}

	private AndroidXmlWorldParserTemplate whenCreating(WorldConfiguration configuration) {
		return new WorldConfigurationFactory().createWorldParser(configuration);
	}
}
