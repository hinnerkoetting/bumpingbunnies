package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.core.game.OpponentFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class OpponentParceller implements Parceller<ConnectionIdentifier> {

	@Override
	public void writeToParcel(ConnectionIdentifier opponent, Parcel parcel) {
		parcel.writeString(opponent.getIdentifier().getIdentifier());
		parcel.writeString(opponent.getType().toString());
	}

	@Override
	public ConnectionIdentifier createFromParcel(Parcel parcel) {
		String identifier = parcel.readString();
		OpponentType type = OpponentType.valueOf(parcel.readString());
		return OpponentFactory.createRemoteOpponent(identifier, type);
	}

}
