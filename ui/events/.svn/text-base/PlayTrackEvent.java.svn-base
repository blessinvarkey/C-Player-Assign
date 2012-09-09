package cs5031.catalogplayer.ui.events;

/**
 * This contains the Events involved in the application 
 * Event involves playing of the tracks  
 * **/

import cs5031.catalogplayer.data.TrackInfo;

public class PlayTrackEvent {
	private final Object source;
	private final TrackInfo track;
	
	/**
	 * Create a play track event which bind the source and track information
	 * @param source the source object of the track which can be palyed by the player
	 * @param track the track information about the track
	 */
	public PlayTrackEvent(Object source, TrackInfo track) {
		this.source = source;
		this.track = track;
	}
	
	/**
	 * Get the source	
	 * @return the source of the track
	 */
	public Object getSource() {
		return this.source;
	}
	
	/**
	 * Get the track information
	 * @return the track information
	 */
	public TrackInfo getTrack() {
		return this.track;
	}
}
