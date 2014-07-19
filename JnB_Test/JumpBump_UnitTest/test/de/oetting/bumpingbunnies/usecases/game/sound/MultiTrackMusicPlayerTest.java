package de.oetting.bumpingbunnies.usecases.game.sound;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class MultiTrackMusicPlayerTest {

	private MultiTrackMusicPlayer fixture;
	private List<MusicPlayer> tracks;

	@Test
	public void start_givenThereIsOneTrack_shouldStartFirstTrack() {
		givenThereExistsOneTrack();
		this.fixture.start();
		verifyThatPlayerIsStarted(this.tracks.get(0));
	}

	@Test
	public void firstTrackFinishes_givenThereIsOneTrack_shouldRestartTrack() {
		givenThereExistsOneTrack();
		this.fixture.start();
		whenTrackIsFinished();
		verifyThatPlayerIsStarted(this.tracks.get(0), 2);
	}

	@Test
	public void firstTrackFinished_givenThereAreTwoTracks_shouldStartSecondTrack() {
		givenThereExistTwoTracks();
		this.fixture.start();
		verifyThatPlayerIsStarted(this.tracks.get(0));
		whenTrackIsFinished();
		verifyThatPlayerIsStarted(this.tracks.get(1));
	}

	@Test
	public void startAndTwoTracksAreFinished_givenThereAreTwoTracks_shouldStartFirstTrackTwoTimes() {
		givenThereExistTwoTracks();
		this.fixture.start();
		whenTrackIsFinished();
		whenTrackIsFinished();
		verifyThatPlayerIsStarted(this.tracks.get(0), 2);
	}

	@Test
	public void pauseBeforeFirstTrackFinished_givenThereAreTwoTracks_shouldPauseFirstTrack() {
		givenThereExistsOneTrack();
		this.fixture.start();
		this.fixture.pauseBackground();
		verifyThatPlayerIsPaused(this.tracks.get(0));
	}

	@Test
	public void pause_afterFirstTrackFinished_givenThereAreTwoTracks_shouldPauseSecondTrack() {
		givenThereExistTwoTracks();
		this.fixture.start();
		whenTrackIsFinished();
		this.fixture.pauseBackground();
		verifyThatPlayerIsPaused(this.tracks.get(1));
	}

	@Test
	public void stop_shouldStopCurrentSong() {
		givenThereExistsOneTrack();
		this.fixture.start();
		this.fixture.stopBackground();
		verifyThatPlayerIsStopped(this.tracks.get(0));
	}

	@Test
	public void create_shouldAddOnCompletionListener() {
		MusicPlayer player = mock(MusicPlayer.class);
		this.fixture = new MultiTrackMusicPlayer(Arrays.asList(player));
		verify(player).setOnCompletionListener(this.fixture);
	}

	private void verifyThatPlayerIsStopped(MusicPlayer musicPlayer) {
		verify(musicPlayer).stopBackground();
	}

	private void verifyThatPlayerIsPaused(MusicPlayer musicPlayer) {
		verify(musicPlayer).pauseBackground();
	}

	private void whenTrackIsFinished() {
		this.fixture.onCompletion(mock((MediaPlayer.class)));
	}

	private void givenThereExistsOneTrack() {
		MusicPlayer player = createMusicPlayer();
		this.tracks.add(player);
	}

	private void givenThereExistTwoTracks() {
		this.tracks.add(createMusicPlayer());
		this.tracks.add(createMusicPlayer());
	}

	private void verifyThatPlayerIsStarted(MusicPlayer player) {
		verify(player).start();
	}

	private void verifyThatPlayerIsStarted(MusicPlayer player, int times) {
		verify(player, times(times)).start();
	}

	private MusicPlayer createMusicPlayer() {
		return mock(MusicPlayer.class);
	}

	@Test(expected = MultiTrackMusicPlayer.NoTrackExists.class)
	public void start_givenThereAreNoTracks_shouldThrowException() {
		this.fixture.start();
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.tracks = new ArrayList<>();
		this.fixture = new MultiTrackMusicPlayer(this.tracks);
	}
}
