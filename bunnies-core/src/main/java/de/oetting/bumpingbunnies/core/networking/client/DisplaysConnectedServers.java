package de.oetting.bumpingbunnies.core.networking.client;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public interface DisplaysConnectedServers {

	LocalPlayerSettings createLocalPlayerSettings();

	void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex);

	void addMyPlayerRoomEntry(int myPlayerId);

	void launchGame(GeneralSettings generalSettingsFromNetwork, boolean b);

}
