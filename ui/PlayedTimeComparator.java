package cs5031.catalogplayer.ui;

import java.util.Comparator;

import cs5031.catalogplayer.data.TrackInfo;

/**
 * Class that accept the actions for the adding and removing from favorite play list 
 * @author <110016203>
 *
 */

public class PlayedTimeComparator implements Comparator<TrackInfo>{
	
	/**
	 * compare the played times of the two tracks
	 * @param o1 the first trackinfo
	 * @param o2 the second trackinfo
	 */
	@Override
	public int compare(TrackInfo o1, TrackInfo o2) {
		int playtime1 = o1.getTimesPlayed();
		int playtime2 = o2.getTimesPlayed();
		return playtime2 - playtime1;
	}

}
