package cs5031.catalogplayer.ui.events;
/**
* This contains listening to the tracks involved in the application 
* Play track extends the Event Listener   
* **/
import java.util.EventListener;

public interface PlayTrackListener extends EventListener {
	void playTrack(PlayTrackEvent event);
}
