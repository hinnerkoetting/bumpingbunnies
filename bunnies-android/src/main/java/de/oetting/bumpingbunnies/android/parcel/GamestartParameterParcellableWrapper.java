package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;

public class GamestartParameterParcellableWrapper implements Parcelable {

	public static final Parcelable.Creator<GamestartParameterParcellableWrapper> CREATOR = new Parcelable.Creator<GamestartParameterParcellableWrapper>() {
		@Override
		public GamestartParameterParcellableWrapper createFromParcel(Parcel source) {
			return new GamestartParameterParcellableWrapper(source);
		}

		@Override
		public GamestartParameterParcellableWrapper[] newArray(int size) {
			return new GamestartParameterParcellableWrapper[size];
		}
	};

	private final GameStartParameter parameter;


	public GamestartParameterParcellableWrapper(GameStartParameter parameter) {
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

	public GamestartParameterParcellableWrapper(Parcel source) {
		parameter = new GameStartParameterParceller().createFromParcel(source);
	}

	public GameStartParameter getParameter() {
		return parameter;
	}

}
