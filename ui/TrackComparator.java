package cs5031.catalogplayer.ui;

import java.util.Comparator;

import cs5031.catalogplayer.data.TrackInfo;
/**
 * Comparator for the tracks, will be ordered by the title of the track 
 * @author <110016203>
 *
 */
public class TrackComparator implements Comparator<TrackInfo>{

	/**Compare two tracks by given their track information
	 * @param left the first track information 
	 * @param right the second track information
	 */
	@Override
	public int compare(TrackInfo left, TrackInfo right) {
		
		String leftTitle = left.getTrack().getTitle();
		String rightTitle = right.getTrack().getTitle();	
		
		if (leftTitle != null) {
			return leftTitle.compareToIgnoreCase(rightTitle);
		}
		else {
			return rightTitle == null ? 0 : -1;
		}		
	}
	
}
