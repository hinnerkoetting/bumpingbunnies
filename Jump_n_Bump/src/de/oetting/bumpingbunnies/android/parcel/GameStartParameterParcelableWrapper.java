package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;

public class GameStartParameterParcelableWrapper implements Parcelable {

	private final GameStartParameter parameter;

	public static final Parcelable.Creator<GameStartParameterParcelableWrapper> CREATOR = new Parcelable.Creator<GameStartParameterParcelableWrapper>() {
		@Override
		public GameStartParameterParcelableWrapper createFromParcel(Parcel source) {
			return new GameStartParameterParcelableWrapper(source);
		}

		@Override
		public GameStartParameterParcelableWrapper[] newArray(int size) {
			return new GameStartParameterParcelableWrapper[size];
		}
	};

	public GameStartParameterParcelableWrapper(Parcel source) {
		parameter = new GameStartParameterParceller().createFromParcel(source);
	}

	public GameStartParameterParcelableWrapper(GameStartParameter parameter) {
		this.parameter = parameter;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		new GameStartParameterParceller().writeToParcel(parameter, dest);
	}

	public GameStartParameter getParameter() {
		return parameter;
	}

}
