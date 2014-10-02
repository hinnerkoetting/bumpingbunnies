package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;

public class GameStartParameterParceller implements Parceller<GameStartParameter> {

	@Override
	public void writeToParcel(GameStartParameter input, Parcel parcel) {
		new ConfigurationParceller().writeToParcel(input.getConfiguration(), parcel);
		parcel.writeInt(input.getPlayerId());
	}

	@Override
	public GameStartParameter createFromParcel(Parcel source) {
		Configuration configuration = new ConfigurationParceller().createFromParcel(source);
		int playerId = source.readInt();
		return new GameStartParameter(configuration, playerId);
	}

}
