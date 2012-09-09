package cs5031.catalogplayer.ui.events;
/**
* This contains the Events involved in the application 
* Event involves playing of the tracks from the source.  
* **/
import java.util.LinkedList;
import java.util.List;

import cs5031.catalogplayer.data.TrackInfo;

public class PlayTrackSource {
	private Object source;
	private List<PlayTrackListener> listeners;
	/**
	 * Create a play track source which bind the object and source information
	 * @param object source of the track which can be played by the player
	 
	 */
	public PlayTrackSource(Object source) {
		this.source = source;
		this.listeners = new LinkedList<PlayTrackListener>();
	}
	/**
	 * Create an add listener which bind the play track listener and the listener 
	 * **/
	public void addListener(PlayTrackListener listener) {
		this.listeners.add(listener);
	}
	/**
	 * Create an remove listener which bind the play track listener and the listener 
	 * **/
	
	public void removeListener(PlayTrackListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Create a  fire event which bind the play track listener and the listener 
	 * **/

	public void fireEvent(TrackInfo track) {
		PlayTrackEvent e = new PlayTrackEvent(this.source, track);
		this.fireEvent(e);
	}
	public void fireEvent(PlayTrackEvent e) {
		for (PlayTrackListener listener: this.listeners) {
			listener.playTrack(e);
		}
	}
}
