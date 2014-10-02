package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class OpponentParceller implements Parceller<Opponent> {

	@Override
	public void writeToParcel(Opponent opponent, Parcel parcel) {
		parcel.writeString(opponent.getIdentifier());
		parcel.writeString(opponent.getType().toString());
	}

	@Override
	public Opponent createFromParcel(Parcel parcel) {
		String identifier = parcel.readString();
		OpponentType type = OpponentType.valueOf(parcel.readString());
		return new Opponent(identifier, type);
	}

}
