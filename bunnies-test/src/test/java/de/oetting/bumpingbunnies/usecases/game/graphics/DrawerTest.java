package de.oetting.bumpingbunnies.usecases.game.graphics;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.core.game.TestPlayerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.ScoreDrawer;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 18)
public class DrawerTest {

	private ObjectsDrawer fixture;
	@Mock
	private DrawablesFactory factory;
	@Mock
	private Drawable playerDrawable;
	@Mock
	private CanvasAdapter canvas;
	private Bunny player;

	@Test
	public void playerJoins_thenPlayerDrawableShouldBeDrawn() {
		whenPlayerJoins();
		this.fixture.draw(mock(CanvasWrapper.class));
		thenNewDrawableIsDrawn();
	}

	@Test
	public void playerLeaves_thenPlayerDrawableShouldNotBeDrawn() {
		this.player = createOpponentPlayer();
		givenPlayerDrawableDoesExist(this.player);
		whenPlayerLeaves(this.player);
		this.fixture.draw(mock(CanvasWrapper.class));
		thenDrawableIsNotDrawn();
	}

	private void thenDrawableIsNotDrawn() {
		verify(this.playerDrawable, never()).draw(this.canvas);
	}

	private void whenPlayerLeaves(Bunny p) {
		this.fixture.removeEvent(p);
	}

	private void givenPlayerDrawableDoesExist(Bunny p) {
		this.fixture.newEvent(p);
	}

	private void thenNewDrawableIsDrawn() {
		verify(this.playerDrawable).draw(this.canvas);
	}

	private void whenPlayerJoins() {
		this.player = createOpponentPlayer();
		this.fixture.newEvent(this.player);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new ObjectsDrawer(this.factory, this.canvas);
		when(this.factory.createPlayerDrawable(any(Bunny.class), eq(canvas))).thenReturn(this.playerDrawable);
		when(this.playerDrawable.drawsPlayer(any(Bunny.class))).thenReturn(true);
		when(factory.createScoreDrawer(any(Bunny.class))).thenReturn(new ScoreDrawer(TestPlayerFactory.createMyPlayer(), mock(World.class)));
	}
}
