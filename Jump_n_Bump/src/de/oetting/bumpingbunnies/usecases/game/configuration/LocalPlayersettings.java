package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

/**
 * Properties which define the local player and should be transferred to other
 * players.
 * 
 */
public class LocalPlayersettings implements Parcelable {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LocalPlayersettings.class);
	public static final Parcelable.Creator<LocalPlayersettings> CREATOR = new Parcelable.Creator<LocalPlayersettings>() {
		@Override
		public LocalPlayersettings createFromParcel(Parcel source) {
			try {
				return new LocalPlayersettings(source);
			} catch (RuntimeException e) {
				LOGGER.error("Exception during reading local player settings",
						e);
				throw e;
			}
		}

		@Override
		public LocalPlayersettings[] newArray(int size) {
			return new LocalPlayersettings[size];
		}
	};

	private final String playerName;

	@Override
	public int describeContents() {
		return 0;
	}

	public LocalPlayersettings(String playerName) {
		super();
		this.playerName = playerName;
	}

	public LocalPlayersettings(Parcel parcel) {
		super();
		this.playerName = parcel.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.playerName);
	}

	public String getPlayerName() {
		return this.playerName;
	}

}
