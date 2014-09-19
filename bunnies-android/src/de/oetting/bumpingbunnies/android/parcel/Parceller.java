package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;

public interface Parceller<S> {

	void writeToParcel(S input, Parcel parcel);
	
	S createFromParcel(Parcel parcel);
}
