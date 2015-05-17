package de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.clientReceivedDeadBunny.BunnyIsDeadMessageReceivedSender;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PlayerIsDeadReceiver extends MessageReceiverTemplate<PlayerIsDeadMessage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIsDeadReceiver.class);

	private final World world;
	private final MusicPlayer deadPlayerMusic;
	private final boolean istHostSystem;
	private final SocketStorage sockets;

	private final PlayerDisconnectedCallback disconnectCallback;


	public PlayerIsDeadReceiver(NetworkToGameDispatcher dispatcher, World world, MusicPlayer deadPlayerMusic, SocketStorage sockets, PlayerDisconnectedCallback disconnectCallback, boolean istHostSystem) {
		super(dispatcher, new PlayerIsDeadMetaData());
		this.world = world;
		this.deadPlayerMusic = deadPlayerMusic;
		this.sockets = sockets;
		this.disconnectCallback = disconnectCallback;
		this.istHostSystem = istHostSystem;
	}

	@Override
	public void onReceiveMessage(PlayerIsDeadMessage object) {
		if (world.existsBunny(object.getPlayerId())) {
			Bunny bunny = findPlayer(object);
			bunny.setDead(true);
			deadPlayerMusic.start();
			if (bunny.getOpponent().isLocalPlayer() && !istHostSystem)
				notifyHostThatMessageWasReceived(bunny);
		} else {
			LOGGER.warn("Received player is dead message but player does not exist %d ", object.getPlayerId());
		}
	}

	private void notifyHostThatMessageWasReceived(Bunny bunny) {
		for (MySocket socket: sockets.getAllSockets()) {
			SimpleNetworkSender sender = new SimpleNetworkSender(MessageParserFactory.create(), socket, disconnectCallback);
			new BunnyIsDeadMessageReceivedSender(sender).sendMessage(bunny.id());
		}
	}

	private Bunny findPlayer(PlayerIsDeadMessage message) {
		return this.world.findBunny(message.getPlayerId());
	}
}
