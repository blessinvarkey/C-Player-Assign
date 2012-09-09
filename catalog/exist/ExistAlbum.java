package cs5031.catalogplayer.catalog.exist;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs5031.catalogplayer.catalog.Album;
import cs5031.catalogplayer.catalog.Resource;
import cs5031.catalogplayer.catalog.Track;

/**
 * Class that parses album XML DOM nodes as returned by the eXist server
 * @author <110017972>
 *
 */
public class ExistAlbum implements Album {
	/** ID of the sever - can be used to directly access the item without query */
	private String id;
	/** Full resource URL */
	private URL resourceURL;
	/** URL to use to build URLs for tracks and other relative URLs */
	protected URL baseURL;
	/** URL to the album cover image */
	private URL imageURL;
	/** URL to the album license */
	private URL licenseURL;
	/** Set of tags applied to the album */
	private Set<String> tags;
	/** Title of the album */
	private String title;
	/** List of artists performing the album */
	private String[] artists;
	/** List of tracks in the album */
	private SortedSet<Track> tracks;
	
	/** used to construct imageURL - before baseURL is available */
	protected String imageLocalURL;
	/** used to construct licenseURL - before baseURL is available */
	protected String licenseLocalURL;
	
	/**
	 * Use for unit testing only
	 */
	protected ExistAlbum() {
		this.tags = new HashSet<String>();
		this.tracks = new TreeSet<Track>();
	}
		
	/**
	 * Create an album by parsing an XML DOM node
	 * @param album XML DOM node
	 * @throws IllegalArgumentException When the node is null or its name is not "album"
	 */
	public ExistAlbum(Node album) {
		if (album == null) {
			throw new IllegalArgumentException("album is null");
		}
		if (!"album".equals(album.getNodeName())) {
			throw new IllegalArgumentException("album's node name is not album");
		}
		
		this.id = Utils.getAttrValAsString(album.getAttributes(), "id", null);		
		
		ArrayList<String> artistsList = new ArrayList<String>();
		this.tags = new HashSet<String>();
		this.tracks = new TreeSet<Track>();
		
		this.imageLocalURL = null;
		this.licenseLocalURL = null;
		
		NodeList children = album.getChildNodes();
		int countOfChildren = children.getLength();
		for (int childIdx = 0; childIdx < countOfChildren; ++childIdx) {
			Node child = children.item(childIdx);
			this.processChild(child, artistsList);
		} // end for each child of <album>
		
		if (this.baseURL != null) {
			String basseURLStr = this.baseURL.toString();
			this.imageURL = Utils.tryBuildURL(basseURLStr, this.imageLocalURL);
			this.licenseURL = Utils.tryBuildURL(basseURLStr, this.licenseLocalURL);
		}
		
		this.artists = new String[artistsList.size()];
		artistsList.toArray(this.artists);
	}

	/**
	 * Process a child element of album
	 * @param child Child element of album
	 * @param artistsList List of artists to add to if child is artist
	 */
	protected void processChild(Node child, List<String> artistsList) {
		String childName = child.getNodeName();
		
		if ("title".equals(childName)) {
			this.title = child.getTextContent();
		}
		else if ("artist".equals(childName)) {
			artistsList.add(child.getTextContent());				 
		}
		else if ("resourceUrl".equals(childName)) {
			this.resourceURL = Utils.tryCreateURL(child.getTextContent());
		}
		else if ("baseUrl".equals(childName)) {
			this.baseURL = Utils.tryCreateURL(child.getTextContent());
		}
		else if ("image".equals(childName)) {
			this.imageLocalURL = child.getTextContent();
		}
		else if ("licenseurl".equals(childName)) {
			this.licenseLocalURL = child.getTextContent();
		}
		else if ("tags".equals(childName)) {
			NodeList tagNodes = child.getChildNodes();
			int countOfTagNodes = tagNodes.getLength();
			for (int tagIdx = 0; tagIdx < countOfTagNodes; ++tagIdx) {
				Node tag = tagNodes.item(tagIdx);
				if ("tag".equals(tag.getNodeName())) {
					this.tags.add(tag.getTextContent());
				}
			}
		}
		else if ("tracks".equals(childName)) {
			NodeList trackNodes = child.getChildNodes();
			int countOfTrackNodes = trackNodes.getLength();
			for (int trackIdx = 0; trackIdx < countOfTrackNodes; ++trackIdx) {
				Node track = trackNodes.item(trackIdx);
				if ("track".equals(track.getNodeName())) {
					this.tracks.add(this.parseTrack(track));
				}
			}
		}
	}	
	
	/**
	 * Parse track node and return track
	 * @param trackNode Node to parse
	 * @return Parsed track
	 */
	protected Track parseTrack(Node trackNode) {
		return new ExistTrack(trackNode, this);
	}
	
	@Override
	public Object getIdentifier() {
		return this.id;
	}

	@Override
	public URL getResourceURL() {
		return this.resourceURL;
	}

	@Override
	public int getType() {
		return Resource.TYPE_ALBUM;
	}

	/**
	 * Get the album's title
	 * @return Title of the album
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * Get the artists performing in this album
	 * @return List of artists
	 */
	@Override
	public String[] getArtists() {
		return this.artists;
	}

	/**
	 * Get the URL to use for forming URLs for files
	 * @return Base URL for files related to the album
	 */
	@Override
	public URL getBaseURL() {
		return this.baseURL;
	}

	/**
	 * Get an URL for the cover image of the album
	 * @return URL for the cover image of the album
	 */
	@Override
	public URL getImageURL() {
		return this.imageURL;
	}

	/**
	 * Get an URL for the file containing the license for this album
	 * @return URL for the file containing the license for this album
	 */
	@Override
	public URL getLicenseURL() {
		return this.licenseURL;
	}

	/**
	 * Get the tags applied to this album
	 * @return Tags applied to the album
	 */
	@Override
	public Set<String> getTags() {
		return this.tags;
	}

	/**
	 * Get an ordered collection of tracks in the album
	 * @return Ordered collection of the tracks in the album
	 */
	@Override
	public SortedSet<Track> getTracks() {
		return this.tracks;
	}

	@Override
	public String toString() {
		return String.format("[Album \"%s\"]", this.title);
	}
}
