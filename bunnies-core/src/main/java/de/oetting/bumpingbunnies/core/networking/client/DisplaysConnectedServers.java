package de.oetting.bumpingbunnies.core.networking.client;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public interface DisplaysConnectedServers {

	LocalPlayerSettings createLocalPlayerSettings();

	void addPlayerEntry(MySocket serverSocket, PlayerProperties properties, int socketIndex);

	void addMyPlayerRoomEntry(int myPlayerId);

	void launchGame(GeneralSettings generalSettingsFromNetwork, boolean asHost);

}
