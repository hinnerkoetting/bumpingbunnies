package de.oetting.bumpingbunnies.communication;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.communication.TestSocket;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

@RunWith(RobolectricTestRunner.class)
public class StorableSocketTest {

	private MySocket socket;

	@Test
	public void testParcelling() throws IOException {
		int index = storeOriginalSocket();
		StorableSocket storedSocket = new StorableSocket(this.socket, index, Opponent.createMyPlayer("player"));
		checkValues(storedSocket, index);
		StorableSocket after = serializeAndDeserialize(storedSocket);
		checkValues(after, index);
	}

	private StorableSocket serializeAndDeserialize(StorableSocket storedSocket) throws IOException {
		Parcel parcel = Parcel.obtain();
		storedSocket.writeToParcel(parcel, 0);
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
		assertThat(storedSocket.getOwner().getIdentifier(), is(equalTo("player")));
		assertThat(storedSocket.getOwner().isMyPlayer(), is(true));
		assertThat(storedSocket.getIndex(), is(equalTo(index)));
	}
}
