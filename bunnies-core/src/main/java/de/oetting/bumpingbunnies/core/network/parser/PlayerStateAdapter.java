package de.oetting.bumpingbunnies.core.network.parser;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import de.oetting.bumpingbunnies.model.game.objects.HorizontalMovementState;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

/**
 * Custom adapter for the playerstate to optimize bandwith usage.
 * This reduced the bandwith from ~4,5KB/s to 1,7KB/s (tested with networktester-project).
 */
public class PlayerStateAdapter extends TypeAdapter<PlayerState> {

	@Override
	public PlayerState read(JsonReader reader) throws IOException {
		try {
			String[] splitted = reader.nextString().split(separator());
			PlayerState state = new PlayerState(decodeInt(splitted[0]));
			state.setCenterX(decode(splitted[1]));
			state.setCenterY(decode(splitted[2]));
			state.setMovementX(decodeInt(splitted[3]));
			state.setMovementY(decodeInt(splitted[4]));
			state.setFacingLeft(decodeBoolean(splitted[5]));
			state.setJumpingButtonPressed(decodeBoolean(splitted[6]));
			state.setHorizontalMovementStatus(decodeMovement(splitted[7]));
			return state;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private HorizontalMovementState decodeMovement(String nextString) {
		return HorizontalMovementState.find(nextString);
	}

	private boolean decodeBoolean(String nextString) {
		return nextString.equals("J");
	}

	private long decode(String nextString) {
		return Long.parseLong(nextString, 16);
	}

	private int decodeInt(String nextString) {
		return Integer.parseInt(nextString, radix());
	}

	private int radix() {
		return 16;
	}

	@Override
	public void write(JsonWriter arg0, PlayerState arg1) throws IOException {
		String createString = createString(arg1);
		if(createString.contains("--")){
			throw new IllegalArgumentException();
		}
		arg0.value(createString);
	}

	private String createString(PlayerState arg1) {
		return encode(arg1.getId()) + separator() + //
				encode(arg1.getCenterX()) + separator() + //
				encode(arg1.getCenterY()) + separator() + //
				encode(arg1.getMovementX()) + separator() + //
				encode(arg1.getMovementY()) + separator() + //
				encode(arg1.isFacingLeft()) + separator() + //
				encode(arg1.isJumpingButtonPressed()) + separator() + //
				encode(arg1.getHorizontalMovementStatus());
	}

	private String separator() {
		return ",";
	}

	private String encode(long value) {
		return Long.toString(value, 16);
	}

	private String encode(int value) {
		return Integer.toString(value, 16);
	}

	private String encode(boolean value) {
		return value ? "J" : "N";
	}

	private String encode(HorizontalMovementState value) {
		return value.getEncoding();
	}

}
