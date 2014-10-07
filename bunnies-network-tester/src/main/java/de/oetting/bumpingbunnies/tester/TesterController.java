package de.oetting.bumpingbunnies.tester;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;

public class TesterController implements Initializable, OnBroadcastReceived {

	@FXML
	private TableView<Host> broadcastTable;

	private ListenForBroadcastsThread listenForBroadcasts;

	public void initialize(URL location, ResourceBundle resources) {
		listenForBroadcasts = ListenforBroadCastsThreadFactory.create(this);
	}

	public void broadcastReceived(InetAddress senderAddress) {
		// TODO Auto-generated method stub

	}

	public void errorOnBroadcastListening() {
		// TODO Auto-generated method stub

	}

}
