package de.oetting.bumpingbunnies.android.game;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.game.IngameMenu;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class AndroidIngameMenuAdapter {
	private static final int ADD_AI = 0;

	private final IngameMenu ingameMenu;
	private final Context context;
	private final World world;
	private final GameStartParameter parameter;

	public AndroidIngameMenuAdapter(IngameMenu ingameMenu, Context context, World world, GameStartParameter parameter) {
		this.ingameMenu = ingameMenu;
		this.context = context;
		this.world = world;
		this.parameter = parameter;
	}

	public void createMenu(Menu menu) {
		if (parameter.getConfiguration().isHost()) {
			addOptionsForHost(menu);
		}
	}

	private void addOptionsForHost(Menu menu) {
		menu.add(1, ADD_AI, 1, context.getString(R.string.room_add_ai));
		for (Bunny bunny : world.getAllConnectedBunnies()) {
			if (bunny.getOpponent().isAi()) {
				menu.add(2, bunny.id(), bunny.id(), context.getString(R.string.remove_ai) + bunny.getName());
			}
		}
	}

	public boolean menuItemSelected(MenuItem item) {
		if (item.getItemId() == ADD_AI) {
			ingameMenu.onAddAiOption();
		} else {
			Bunny removedBunny = world.findBunny(item.getItemId());
			ingameMenu.removePlayer(removedBunny);
		}
		return true;
	}

}
 