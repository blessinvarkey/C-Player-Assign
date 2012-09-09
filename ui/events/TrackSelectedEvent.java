package cs5031.catalogplayer.ui.events;
/**
 * This contains the Events involved in the application 
 * Event involves selection of the tracks 
 * **/
import cs5031.catalogplayer.data.TrackInfo;

public class TrackSelectedEvent {
	private final Object source;
	private final TrackInfo track;
	
	/**
	 * Create a select track event which bind the source and track information
	 * @param  source the source object of the track which can be selected by the player
	 * @param track the track information about the track
	 */
	public TrackSelectedEvent(Object source, TrackInfo track) {
		this.source = source;
		this.track = track;
	}
	/**
	 * Create an Object and return source
	 */

	public Object getSource() {
		return this.source;
	}
		public TrackInfo getTrack() {
		return this.track;
	} 
}
