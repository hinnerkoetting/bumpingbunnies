package de.oetting.bumpingbunnies.communication;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.model.game.objects.OpponentFactory;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class StorableSocketTest {

	private MySocket socket;

	@Test
	public void testParcelling() throws IOException {
		int index = storeOriginalSocket();
		StorableSocket storedSocket = new StorableSocket(this.socket, index, OpponentFactory.createLocalPlayer(""));
		checkValues(storedSocket, index);
		StorableSocket after = serializeAndDeserialize(storedSocket);
		checkValues(after, index);
	}

	private StorableSocket serializeAndDeserialize(StorableSocket storedSocket) throws IOException {
		Parcel parcel = Parcel.obtain();
		storedSocket.writeToParcel(parcel, 0);

		parcel.setDataPosition(0);
		return new StorableSocket(parcel);
	}

	private int storeOriginalSocket() {
		this.socket = new TestSocket(mock(OutputStream.class), mock(InputStream.class));
		SocketStorage storage = SocketStorage.getSingleton();
		storage.closeExistingSocket();
		int index = storage.addSocket(this.socket);
		return index;
	}

	private void checkValues(StorableSocket storedSocket, int index) {
		assertThat(storedSocket.getStoredSocket(), is(equalTo(this.socket)));
		assertThat(storedSocket.getOwner().isMyPlayer(), is(true));
		assertThat(storedSocket.getIndex(), is(equalTo(index)));
	}
}
