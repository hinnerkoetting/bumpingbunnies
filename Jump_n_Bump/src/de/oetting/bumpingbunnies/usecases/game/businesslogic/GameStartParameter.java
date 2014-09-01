package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.android.parcel.ConfigurationParceller;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;

public class GameStartParameter implements Parcelable {

	public static final Parcelable.Creator<GameStartParameter> CREATOR = new Parcelable.Creator<GameStartParameter>() {
		@Override
		public GameStartParameter createFromParcel(Parcel source) {
			return new GameStartParameter(source);
		}

		@Override
		public GameStartParameter[] newArray(int size) {
			return new GameStartParameter[size];
		}
	};

	private final Configuration configuration;
	private final int playerId;

	public GameStartParameter(Configuration configuration, int playerId) {
		this.configuration = configuration;
		this.playerId = playerId;
	}

	public GameStartParameter(Parcel source) {
		this.configuration = new ConfigurationParceller().createFromParcel(source);
		this.playerId = source.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		new ConfigurationParceller().writeToParcel(configuration, dest);
		dest.writeInt(this.playerId);
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public int getPlayerId() {
		return this.playerId;
	}

}
