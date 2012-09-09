package cs5031.catalogplayer.test;

import static org.junit.Assert.assertTrue;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;
import cs5031.catalogplayer.ui.ItemTableModel;
import cs5031.catalogplayer.ui.PlayListTableModel;

@RunWith(JMock.class)
public class ItemTableModelTestSuit {
	private Mockery context = new JUnit4Mockery();
	private ItemTableModel test; 
	private final String[] columNames = new String[]{"Co1","2"}; 
	@Before
	public void setup(){
		test = new PlayListTableModel(columNames);
	}
	
	
	@Test 
	public void addRowTest(){
		Track track = context.mock(Track.class);
		TrackInfo data = new TrackInfo(track);
		test.addRow(data);
		assertTrue(test.getRowCount() == 1);
	}
	
	@Test
	public void sortByPlayTimesTest(){
		Track track = context.mock(Track.class);	
		//two new tracks
		TrackInfo data1 = new TrackInfo(track);
		TrackInfo data2 = new TrackInfo(track);
		
		//add it to the model
		test.clear();
		test.addRow(data1);
		test.addRow(data2);		
		
		test.sortBy(2);
		assertTrue(test.getValueAt(0, 3).equals(data1));
		
		//set the second one higher playtimes
		data2.setTimesPlayed(2);
		
		//sort again and check
		test.sortBy(2);
		assertTrue(test.getValueAt(0, 3).equals(data2));
	}
}
