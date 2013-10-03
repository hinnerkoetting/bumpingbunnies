package de.oetting.bumpingbunnies.usecases.game.graphics;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import android.graphics.Canvas;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class DrawerTest {

	private Drawer fixture;
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
		this.fixture.draw(mock(Canvas.class));
		thenNewDrawableIsDrawn();
	}

	@Test
	public void playerLeaves_thenPlayerDrawableShouldNotBeDrawn() {
		this.player = new Player(0, "", 1);
		givenPlayerDrawableDoesExist(this.player);
		whenPlayerLeaves(this.player);
		this.fixture.draw(mock(Canvas.class));
		thenDrawableIsNotDrawn();
	}

	@Test(expected = Drawer.PlayerDoesNotExist.class)
	public void playerLeaves_givenPlayerDoesNotExist_shouldThrowException() {
		this.player = new Player(0, "", 1);
		whenPlayerLeaves(this.player);
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
		this.player = new Player(0, "", 1);
		this.fixture.newPlayerJoined(this.player);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new Drawer(this.factory, this.canvas);
		when(this.factory.createPlayerDrawable(any(Player.class))).thenReturn(this.playerDrawable);
		when(this.playerDrawable.drawsPlayer(any(Player.class))).thenReturn(true);
	}
}
