package cs5031.catalogplayer.catalog.exist;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs5031.catalogplayer.catalog.Album;
import cs5031.catalogplayer.catalog.Resource;
import cs5031.catalogplayer.catalog.Track;

/**
 * Implements {@link cs5031.catalogplayer.catalog.Catalog}. Parses <track> nodes.
 * @author <110017972>
 *
 */
public class ExistTrack implements Track, Comparable<ExistTrack>, Serializable {
	/** Database ID for the track */
	private String id;
	/** Order of the track in the parent album, used for natural sorting */
	private int order;
	/** Title of the track */
	private String title;
	/** Artists performing the track */
	private String[] artists;
	/** URL to the album, null if no album given in the constructor */
	private URL resourceURL;
	/** URL to download the file, null if no album given in the constructor */
	private URL fileURL;
	
	/**
	 * Create a new track by parsing a node
	 * @param trackElement XML DOM node "track" to parse
	 * @param parent Album. If null, getResourceURL and getFileURL will both return null.
	 * @throws IllegalArgumentException When the node passed is null or its name is not "track"
	 */
	public ExistTrack(Node trackElement, Album parent) {
		if (trackElement == null) {
			throw new IllegalArgumentException("trackElement is null");
		}
		if (!"track".equals(trackElement.getNodeName())) {
			throw new IllegalArgumentException("trackElement's nodeName is not track");
		}
		
		NamedNodeMap attributes = trackElement.getAttributes();
		this.id = Utils.getAttrValAsString(attributes, "id", null);
		this.order = Utils.getAttrValAsInt(attributes, "order", 0);
		
		ArrayList<String> authorsList = new ArrayList<String>();
		NodeList children = trackElement.getChildNodes();
		int countOfChildren = children.getLength();
		for (int i = 0; i < countOfChildren; ++i) {
			Node child = children.item(i);
			String childName = child.getNodeName();
			String childValue = child.getTextContent();
			
			if ("title".equals(childName)) {
				this.title = childValue;
			}
			else if ("file".equals(childName)) {
				// OK, this will be null, because the URL class does not allow for relative URLS
				if (parent != null) {
					this.fileURL = Utils.tryBuildURL(parent.getBaseURL().toString(), childValue);
				}
				else {
					this.fileURL = Utils.tryCreateURL(childValue);
				}
				
			}
			else if ("artist".equals(childName)) {
				authorsList.add(childValue);
			}
		}
		if (parent != null) {
			// it is assumed that "meta" information is the eXist XML, which is retrieved by base url + id
			// Of course, it will be null if no album is present, because URL class does not support relative URLs
			this.resourceURL = Utils.tryBuildURL(parent.getBaseURL().toString(), this.id);
		}
		this.artists = new String[authorsList.size()];
		authorsList.toArray(this.artists);
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
		return Resource.TYPE_TRACK;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String[] getArtists() {
		return this.artists;
	}

	@Override
	public URL getURL() {
		return this.fileURL;
	}

	@Override
	public int compareTo(ExistTrack o) {
		//	y.c(z) > 0 => y > z 
		return this.order - o.order;
	}
	
	@Override
	public String toString() {
		return String.format("[Track #%d \"%s\", download: %s]", this.order, this.title, this.fileURL);
	}

	@Override
	public int hashCode() {
		if (this.id != null) {
			return this.id.hashCode();
		}
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExistTrack) {
			ExistTrack other = (ExistTrack) obj;
			if (this.id != null) {
				return id.equals(other.id);
			}
			else {
				return other == null;
			}
		}
		return super.equals(obj);
	}
}
