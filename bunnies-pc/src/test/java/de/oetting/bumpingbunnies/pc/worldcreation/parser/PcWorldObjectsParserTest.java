package de.oetting.bumpingbunnies.pc.worldcreation.parser;

import static org.junit.Assert.assertThat;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.worldcreator.load.ClasspathXmlreader;
import de.oetting.bumpingbunnies.worldcreator.load.DefaultResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.ImageCache;
import de.oetting.bumpingbunnies.worldcreator.load.ResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;
import de.oetting.bumpingbunnies.worldcreatorPc.load.PcWorldObjectsParser;

public class PcWorldObjectsParserTest {

	@Test
	public void build_classicWorld_hasAllValuesFilled() {
		ResourceProvider provider = new DefaultResourceProvider(new ImageCache());
		XmlReader reader = new ClasspathXmlreader(getClass().getResourceAsStream("/test.xml"));
		World world = new PcWorldObjectsParser().build(provider, reader);
		assertThat(world, hasNoEmptyList());
	}

	private Matcher<World> hasNoEmptyList() {
		return new BaseMatcher<World>() {

			@Override
			public boolean matches(Object item) {
				World worldItem = (World) item;
				boolean hasEmptyWalls = worldItem.getAllWalls().isEmpty();
				boolean hasEmptyIceWalls = worldItem.getAllIcyWalls().isEmpty();
				boolean hasEmptyJumper = worldItem.getAllJumper().isEmpty();
				boolean hasEmptyWaters = worldItem.getAllWaters().isEmpty();
				boolean hasEmptySpawns = worldItem.getSpawnPoints().isEmpty();
				return !hasEmptyWalls && !hasEmptyIceWalls && !hasEmptyJumper && !hasEmptyWaters && !hasEmptySpawns;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("No list is empty");
			}

			@Override
			public void describeMismatch(Object item, Description description) {
				World worldItem = (World) item;
				description.appendText("getAllWalls " + worldItem.getAllWalls().size() + " - ");
				description.appendText("getAllIcyWalls " + worldItem.getAllIcyWalls().size() + " - ");
				description.appendText("getAllJumper " + worldItem.getAllJumper().size() + " - ");
				description.appendText("getAllWaters " + worldItem.getAllWaters().size() + " - ");
				description.appendText("getSpawnPoints " + worldItem.getSpawnPoints().size() + " - ");
			}
		};
	}
}
