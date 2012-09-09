package cs5031.catalogplayer.ui.events;
/**
 * This contains the Events involved in the application 
 * Event involves listening of the selected tracks 
 * **/
import cs5031.catalogplayer.data.TrackInfo;

public interface TrackSelectedListener {
	void trackSelected(TrackSelectedEvent event);
}
