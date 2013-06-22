package de.jumpnbump.usecases.game.communication;

import java.util.UUID;

import android.os.Build;

public class NetworkConstants {

	static int sdk = Build.VERSION.SDK_INT;
	public static final UUID MY_UUID_2 = UUID
			.fromString("B2EF7440-D2BF-11E2-8B8B-0800200C9A66");
	public static final UUID MY_UUID = UUID
			.fromString("417561e0-d9c6-11e2-a28f-0800200c9a66");
	// public static final UUID MY_UUID = UUID
	// .fromString((sdk <= 8 || sdk >= 11) ?
	// "04c6093b-0000-1000-8000-00805f9b34fb"
	// : "00001101-0000-1000-8000-00805F9B34FB");
	public static final String NAME = "JUMPNBUMP";

	public static final String ENCODING = "UTF-8";

	public static final int WLAN_PORT = 10765;
}
