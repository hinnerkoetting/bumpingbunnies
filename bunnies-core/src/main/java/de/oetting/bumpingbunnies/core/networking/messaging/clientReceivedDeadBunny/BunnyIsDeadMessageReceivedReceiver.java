package de.oetting.bumpingbunnies.core.networking.messaging.clientReceivedDeadBunny;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BunnyIsDeadMessageReceivedReceiver extends MessageReceiverTemplate<Integer> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BunnyIsDeadMessageReceivedSender.class);
	
	private final World world;

	public BunnyIsDeadMessageReceivedReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new BunnyIsDeadReceivedMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(Integer object) {
		Bunny bunny = world.findBunny(object);
		if (bunny != null)
			bunny.setClientUpToDate(true);
		else 
			LOGGER.warn("Received message, that client received message about dead bunny. But the bunny could not be found %d", object);
	}

}
