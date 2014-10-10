package de.oetting.bumpingbunnies.usecases.game.graphics;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
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

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.ScoreDrawer;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class DrawerTest {

	private ObjectsDrawer fixture;
	@Mock
	private DrawablesFactory factory;
	@Mock
	private Drawable playerDrawable;
	@Mock
	private CanvasDelegate canvas;
	private Player player;

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

	private void whenPlayerLeaves(Player p) {
		this.fixture.playerLeftTheGame(p);
	}

	private void givenPlayerDrawableDoesExist(Player p) {
		this.fixture.newPlayerJoined(p);
	}

	private void thenNewDrawableIsDrawn() {
		verify(this.playerDrawable).draw(this.canvas);
	}

	private void whenPlayerJoins() {
		this.player = createOpponentPlayer();
		this.fixture.newPlayerJoined(this.player);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new ObjectsDrawer(this.factory, this.canvas);
		when(this.factory.createPlayerDrawable(any(Player.class), eq(canvas))).thenReturn(this.playerDrawable);
		when(this.playerDrawable.drawsPlayer(any(Player.class))).thenReturn(true);
		when(factory.createScoreDrawer(any(Player.class))).thenReturn(new ScoreDrawer(TestPlayerFactory.createMyPlayer(), 0, 0));
	}
}
