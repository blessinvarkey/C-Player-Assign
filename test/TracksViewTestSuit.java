package cs5031.catalogplayer.test;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jmock.integration.junit4.*;

import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;
import cs5031.catalogplayer.ui.PlayerWindow;
import cs5031.catalogplayer.ui.TracksView;


@RunWith(JMock.class)
public class TracksViewTestSuit {
	
	private TracksView tv;
	private Mockery context = new JUnit4Mockery();
	private static int number = 0;
	
    @Before
    public void Setup(){
    	ArrayList<Track> tracks = new  ArrayList<Track>();
    	for(int i = 0; i < 3; i++){
    		tracks.add(createTrack());
    	}	    	
    	
    	Track[] trackList = new Track[3]; 
    	tracks.toArray(trackList);
//		tv = new TracksView();
//		tv.setList(trackList); 
    }	
	
	@Test
	public void TracksViewAddTimePlayed() {
		//tv.addPlayTimes(0);
		TrackInfo trackInfo = tv.getRowInfo(0);
		assertTrue(trackInfo.getTimesPlayed() == 1);
	}	
	
	@Test
	public void TracksViewAddAndRemove() {
		//tv.addPlayTimes(0);
		TrackInfo trackInfo = tv.getRowInfo(0);
		Track removedTrack = trackInfo.getTrack();
		
		tv.remove(0);
		//tv.addTack(removedTrack);
	}	
	
	
	
	private Track createTrack(){
		number++;
		
		Track track = new Track(){
			
			String title = "Track " + number;
			@Override
			public Object getIdentifier() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public URL getResourceURL() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getType() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getTitle() {
				// TODO Auto-generated method stub				
				return (title);
			}

			@Override
			public String[] getArtists() {
				// TODO Auto-generated method stub
				return new String[]{"Art1", "Art2"};
			}

			@Override
			public URL getURL() {
				// TODO Auto-generated method stub
				return null;
			}};		
		 return track;
	}
	
	
	
}
