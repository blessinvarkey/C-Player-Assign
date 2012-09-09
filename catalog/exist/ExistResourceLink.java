package cs5031.catalogplayer.catalog.exist;

import java.net.URL;

import org.w3c.dom.Node;

import cs5031.catalogplayer.catalog.Resource;

/**
 * Represents a resource link
 * @author <110017972>
 *
 */
public class ExistResourceLink implements Resource {
	/*
	 * Sometimes <exist:result> contains a single <exist:collection> element, which in turn
	 * holds <exist:resource> elements which have db owner/permissions information and a link to
	 * retrieve the item itself
	 */
	
	/** Resource URL, as parsed by the XML */
	private URL resourceURL;
	/** Name, as parsed by the XML */
	private String name;
	
	/**
	 * Create a new resource link by parsing an exist:resource node
	 * @param existResource XML DOM Node to parse
	 */
	public ExistResourceLink(Node existResource) {
		if (existResource == null) {
			throw new IllegalArgumentException("existResource is null");
		}
		if (!"exist:resource".equals(existResource.getNodeName())) {
			throw new IllegalArgumentException("existResource node name is not 'existResource'");
		}
		
		this.name = Utils.getAttrValAsString(existResource.getAttributes(), "name", null);
		// will BE null since URL type does not support relative URLs
		this.resourceURL = Utils.tryCreateURL(this.name);		
	}
	
	@Override
	public Object getIdentifier() {
		return this.name;
	}

	@Override
	public URL getResourceURL() {
		return this.resourceURL;
	}

	@Override
	public int getType() {
		return Resource.TYPE_UNKNOWN;
	}

}
