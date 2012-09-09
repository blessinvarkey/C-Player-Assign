package cs5031.catalogplayer.catalog;

import java.net.URL;

/**
 * A track has a title and artist entry to describe it as well as a URL
 * pointing to the audio file itself. The URL may be absolute or may be
 * relative to a base URL specified for a whole album.
 * 
 * @author avoss
 */
public interface Track extends Resource {
	String getTitle();
	String[] getArtists();
	URL getURL();
}
