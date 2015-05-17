package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.sender.PlayerLeftSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.server.ExistingClientsNofication;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class IngameMenu {

	private final GameMain gameMain;
	private final BunnyFactory bunnyFactory;
	private final World world;
	private final SocketStorage sockets;


	public IngameMenu(GameMain gameMain, BunnyFactory bunnyFactory, World world, SocketStorage sockets) {
		this.gameMain = gameMain;
		this.bunnyFactory = bunnyFactory;
		this.world = world;
		this.sockets = sockets;
	}

	public void onAddAiOption() {
		Bunny newAi = createAi();
		PlayerProperties properties = new PlayerProperties(newAi.id(), newAi.getName());
		notifyExistingClientsAboutConnection(properties);
		gameMain.newEvent(newAi);
	}
	
	public void removePlayer(Bunny bunny) {
		PlayerProperties properties = new PlayerProperties(bunny.id(), bunny.getName());
		synchronized (sockets) {
			for (MySocket socket: sockets.getAllSockets()) {
				new PlayerLeftSender(new SimpleNetworkSender(MessageParserFactory.create(), socket, gameMain)).sendMessage(properties);
			}
		}
		gameMain.removeEvent(bunny);
	}
			
	private void notifyExistingClientsAboutConnection(PlayerProperties properties) {
		new ExistingClientsNofication(sockets, gameMain).informAboutNewBunny(properties);
	}

	private Bunny createAi() {
		int newId = world.getNextBunnyId();
		String newName = "AI" + newId;
		return bunnyFactory.createPlayer(newId, newName, ConnectionIdentifierFactory.createAiPlayer(newName));
	}
}
