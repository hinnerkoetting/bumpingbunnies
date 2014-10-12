package de.oetting.bumpingbunnies.core.game.main;

import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerPropertiesReceiveListener;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class OtherPlayerJoinsGameListener implements PlayerPropertiesReceiveListener {

	private final PlayerJoinListener main;
	private final Configuration configuration;

	public OtherPlayerJoinsGameListener(PlayerJoinListener main, Configuration configuration) {
		this.main = main;
		this.configuration = configuration;
	}

	@Override
	public void addOtherPlayer(PlayerProperties object) {
		Player player = createPlayer(object);
		main.newEvent(player);
	}

	private Player createPlayer(PlayerProperties object) {
		int speedSetting = configuration.getGeneralSettings().getSpeedSetting();
		int playerId = object.getPlayerId();
		String playerName = object.getPlayerName();
		Opponent opponent = OpponentFactory.createJoinedPlayer(playerName, playerId);
		return new PlayerFactory(speedSetting).createPlayer(playerId, playerName, opponent);
	}
}
