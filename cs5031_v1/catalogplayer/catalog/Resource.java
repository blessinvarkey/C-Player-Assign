package cs5031.catalogplayer.catalog;

import java.net.URL;

/**
 * A resource in an online catalog. A resource has an identifier, a URL where it can be
 * accessed and a type. The latter is an id from the list of ids defined in this interface,
 * so is not specific to the catalog. 
 * 
 * @author alex.voss@st-andrews.ac.uk
 *
 */
public interface Resource {
		
	/**
	 * The TYPE_ constants define types of resources in a way that is independent of the
	 * catalog the resources are drawn from. It is up to the catalog interface implementation
	 * to translate types into the categories defined in this interface.
	 */
	
	/** A resource of unknown type **/
	public static final int TYPE_UNKNOW = 0;
	/** An individual music track **/
	public static final int TYPE_TRACK = 1;
	/** An album consisting of one or more tracks **/
	public static final int TYPE_ALBUM = 2;
	
	/**
	 * Get the catalog-specific identifier for the resource. As different catalogs may used
	 * different kinds of identifiers and as they are meaningful only in the context of the
	 * catalog they relate to, the interface defines a return value of type Object.
	 * 
	 * @return an Object serving as the identifier for this resource in its catalog
	 */
	public Object getIdentifier();
	
	/**
	 * Get a URL under which the metadata about the resource can be accessed. 
	 * 
	 * @return the URL under which the resource metadata can be accessed
	 */
	public URL getResourceURL();
	
	/**
	 * Get the catalogue independent resource type identifier. This will be one of the values
	 * of the constants defined in this interface.
	 * 
	 * @return the type identifier for this resource
	 */
	public int getType();
}
