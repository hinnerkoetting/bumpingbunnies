package de.oetting.bumpingbunnies.pc.music;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PlayerFactory {

	private final String classpath;
	private byte[] cachedMp3;

	public PlayerFactory(String classpath) {
		this.classpath = classpath;
	}

	public Mp3Player createPlayer() {
		try {
			if (cachedMp3 == null) {
				cachedMp3 = cacheMp3();
			}
			return new Mp3Player(new ByteArrayInputStream(cachedMp3));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] cacheMp3() {
		try {
			ByteArrayOutputStream bais = new ByteArrayOutputStream(1024);
			InputStream inputStream = getClass().getResourceAsStream(classpath);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer)) > 0) {
				bais.write(buffer, 0, bytesRead);
			}
			return bais.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
