package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.server.ExistingClientsNofication;
import de.oetting.bumpingbunnies.core.networking.server.ToClientConnector;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class IngameMenu {

	private final GameMain gameMain;
	private final BunnyFactory bunnyFactory;
	private final World world;

	public IngameMenu(GameMain gameMain, BunnyFactory bunnyFactory, World world) {
		this.gameMain = gameMain;
		this.bunnyFactory = bunnyFactory;
		this.world = world;
	}

	public void onAddAiOption() {
		Bunny newAi = createAi();
		PlayerProperties properties = new PlayerProperties(newAi.id(), newAi.getName());
		notifyExistingClientsAboutConnection(properties);
		gameMain.newEvent(newAi);
	}

	private void notifyExistingClientsAboutConnection(PlayerProperties properties) {
		new ExistingClientsNofication(SocketStorage.getSingleton(), gameMain).informAboutNewBunny(properties);
	}

	private Bunny createAi() {
		int newId = world.getNextBunnyId();
		String newName = "AI" + newId;
		return bunnyFactory.createPlayer(newId, newName, ConnectionIdentifierFactory.createAiPlayer(newName));
	}
}
