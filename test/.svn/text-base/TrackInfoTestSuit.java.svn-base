package cs5031.catalogplayer.test;



import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jmock.integration.junit4.*;
import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;

@RunWith(JMock.class)
public class TrackInfoTestSuit {
	private TrackInfo trackInfo; 
	Mockery context = new JUnit4Mockery();	
	
	@Test
	public void TrackInfoGetTrackInfo() {
		final Track track = context.mock(Track.class);
		
		trackInfo = new TrackInfo(track);
		context.checking(new Expectations(){{
			trackInfo.getTrack().equals(track);
		}});
	}	
	
	
	
	
	
}
