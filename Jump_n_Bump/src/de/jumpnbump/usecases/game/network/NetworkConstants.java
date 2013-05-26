package de.jumpnbump.usecases.game.network;

import java.util.UUID;

import android.os.Build;

public class NetworkConstants {

	static int sdk = Integer.parseInt(Build.VERSION.SDK);

	public static final UUID MY_UUID = UUID
			.fromString((sdk <= 8 || sdk >= 11) ? "04c6093b-0000-1000-8000-00805f9b34fb"
					: "00001101-0000-1000-8000-00805F9B34FB");
	public static final String NAME = "JUMPNBUMP";
}
