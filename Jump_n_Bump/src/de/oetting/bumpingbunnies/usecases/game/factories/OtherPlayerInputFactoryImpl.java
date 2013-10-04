package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class OtherPlayerInputFactoryImpl implements NewOtherPlayerInputServiceFactory {

	private final GameMain main;
	private final World world;

	public OtherPlayerInputFactoryImpl(GameMain main, World world) {
		this.main = main;
		this.world = world;
	}

	@Override
	public OtherPlayerInputService create(Player p) {
		if (this.main.existsRemoteConnection(p.getOpponent())) {
			return new PlayerFromNetworkInput(p);
		} else {
			return new AiInputService(new PlayerMovement(p), this.world);
		}
	}
}
