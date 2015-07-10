package de.oetting.bumpingbunnies.android.game;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class AndroidIngameMenuAdapter {
	private static final int ADD_AI = 0;
	private static final int PAUSE = 1;
	private static final int PLAY = 2;

	private final IngameMenu ingameMenu;
	private final Context context;
	private final World world;
	private final GameStartParameter parameter;
	private final GameMain main;

	public AndroidIngameMenuAdapter(IngameMenu ingameMenu, Context context, World world, GameStartParameter parameter,
			GameMain main) {
		this.ingameMenu = ingameMenu;
		this.context = context;
		this.world = world;
		this.parameter = parameter;
		this.main = main;
	}

	public void createMenu(Menu menu) {
		addPlayPauseButton(menu);
		if (parameter.getConfiguration().isHost()) {
			addOptionsForHost(menu);
		}
	}

	private void addPlayPauseButton(Menu menu) {
		if (main.isPaused())
			menu.add(1, PLAY, 1, context.getString(R.string.play));
		else
			menu.add(1, PAUSE, 1, context.getString(R.string.pause));
	}

	private void addOptionsForHost(Menu menu) {
		menu.add(1, PLAY, 1, context.getString(R.string.room_add_ai));
		for (Bunny bunny : world.getAllConnectedBunnies()) {
			if (bunny.getOpponent().isAi()) {
				menu.add(2, bunny.id(), bunny.id(), context.getString(R.string.remove_ai) + " " + bunny.getName());
			}
		}
	}

	public boolean menuItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ADD_AI:
			ingameMenu.onAddAiOption();
			break;
		case PAUSE:
			ingameMenu.pause();
			break;
		case PLAY:
			ingameMenu.play();
			break;
		default:
			Bunny removedBunny = world.findBunny(item.getItemId());
			ingameMenu.removePlayer(removedBunny);
		}
		return true;
	}
}
