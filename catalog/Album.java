package cs5031.catalogplayer.catalog;

import java.net.URL;
import java.util.Set;
import java.util.SortedSet;

/**
 * Describes an album in a {@link Catalog}. An album has a title
 * and artist entries to describe the album as a whole. A set of
 * URLs are associated with it such as the resource URL that points
 * to further information about the album (defined in {@link Resource}), 
 * a base URL that provides the basis for finding the artefacts that make 
 * up the album, a URL for an image representing the album and a URL for 
 * the licence under which the music has been published. An album can be 
 * 'tagged' and, finally, has a list of tracks that it consists of. 
 *  
 * @author avoss
 */
public interface Album extends Resource {
		
	public String getTitle();	
	public String[] getArtists();
	public URL getBaseURL();
	public URL getImageURL();
	public URL getLicenseURL();
	public Set<String> getTags();
	public SortedSet<Track> getTracks();
	
}
