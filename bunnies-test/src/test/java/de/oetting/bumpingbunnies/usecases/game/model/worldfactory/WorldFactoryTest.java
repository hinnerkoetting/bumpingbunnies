package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.WallFactory;
import de.oetting.bumpingbunnies.core.worldCreation.WorldFactory;
import de.oetting.bumpingbunnies.model.game.world.XmlWorldBuilderState;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class WorldFactoryTest {

	@Mock
	private XmlWorldBuilderState parserState;

	@Test
	public void create_emptyWorld_returnsEmptyWorld() {
		World world = whenCreating();
		thenWorldIsEmpty(world);
	}

	@Test
	public void create_parserContainsOneWall_returnsWorldWithOneWall() {
		givenParserHasOneWall();
		World world = whenCreating();
		thenWorldContainsOneWall(world);
	}

	@Test
	public void create_parserContainsOneWall_allObjectsHasOneElement() {
		givenParserHasOneWall();
		World world = whenCreating();
		thenAllObjectsOfWorldContainOneElement(world);
	}

	private void givenParserHasOneWall() {
		when(parserState.getAllWalls()).thenReturn(Arrays.asList(WallFactory.createWall(0, 0)));
	}

	private World whenCreating() {
		return new WorldFactory().create(parserState);
	}

	private void thenWorldIsEmpty(World world) {
		assertThat(world.getAllWalls(), is(empty()));
	}

	private void thenWorldContainsOneWall(World world) {
		assertThat(world.getAllWalls(), hasSize(1));
	}

	private void thenAllObjectsOfWorldContainOneElement(World world) {
		assertThat(world.getAllObjects(), hasSize(1));
	}

	@Before
	public void setup() {
		initMocks(this);
	}
}
