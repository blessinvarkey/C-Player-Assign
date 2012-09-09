package cs5031.catalogplayer.ui.events;
/**
* This contains the Events involved in the application 
* Event involves selecting of the tracks from the source.  
* **/
import java.util.LinkedList;
import java.util.List;

import cs5031.catalogplayer.data.TrackInfo;

public class TrackSelectedSource {
	private Object source;
	private List<TrackSelectedListener> listeners;
	/**
	 * Create a track selected source which bind the object and source information
	 * @param object source of the track which can be selected by the player
	 
	 */
	public TrackSelectedSource(Object source) {
		this.source = source;
		this.listeners = new LinkedList<TrackSelectedListener>();
	}
	/**
	 * Create an add listener which bind the Track Selected Listener and the listener 
	 * **/
	public void addListener(TrackSelectedListener listener) {
		this.listeners.add(listener);
	}
	/**
	 * Create an remove listener which bind the Track Selected Listener and the listener 
	 * **/
	public void removeListener(TrackSelectedListener listener) {
		this.listeners.remove(listener);
	}
	/**
	 * Create a fire event which bind the Track info and the track 
	 * **/
	public void fireEvent(TrackInfo track) {
		TrackSelectedEvent e = new TrackSelectedEvent(this.source, track);
		this.fireEvent(e);
	}

	public void fireEvent(TrackSelectedEvent e) {
		for (TrackSelectedListener listener: this.listeners) {
			listener.trackSelected(e);
		}
	}
}
