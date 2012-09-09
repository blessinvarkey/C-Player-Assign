package cs5031.catalogplayer.catalog.exist;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Various utility methods
 * @author <110017972>
 *
 */
public class Utils {
	/** Implementation of UtilityMethods to use */
	private static UtilityMethods impl;
	/** Default implementation of utility methods */
	private static UtilityMethods defImpl;
	
	static {
		impl = new DefaultUtilityMethods();
		defImpl = impl;
	}
	
	/**
	 * Prevent creating instances of this class
	 */
	private Utils() {}
	
	/**
	 * Change the implementation used in other static methods - useful for unit testing
	 * @param impl Implementation to use
	 */
	public static void setImplementation(UtilityMethods impl) {
		if (impl == null) throw new IllegalArgumentException("impl is null");
		Utils.impl = impl;
	}
	
	/**
	 * Revert to using the default implementation of the static methods - useful for unit testing
	 */
	public static void setDefaultImplementation() {
		impl = defImpl;
	}
	
	/**
	 * Attempt to extract the value of an XML attribute as {@code int}
	 * @param attribs Map of attributes from which to attempt extraction
	 * @param name Name of the attribute
	 * @param defaultVal Value to return on failure
	 * @return Value of attribute {@link name} as {@code int}, or {@link defaultVal} on failure 
	 */
	public static int getAttrValAsInt(NamedNodeMap attribs, String name, int defaultVal) {
		return impl.getAttrValAsInt(attribs, name, defaultVal);
	}
	
	/**
	 * Attempt to extract the value of an XML attribute as {@code String}
	 * @param attribs Map of attributes from which to attepmt extraction
	 * @param name Name of the attribute
	 * @param defaultVal Value to return on failure
	 * @return Value of attribute {@link name} as {@code String}, or {@link defaultVal} on failure
	 */
	public static String getAttrValAsString(NamedNodeMap attribs, String name, String defaultVal) {
		return impl.getAttrValAsString(attribs, name, defaultVal);
	}
	
	/**
	 * Combine an absolute URL and a relative URL
	 * @param base Absolute URL to use as a base
	 * @param local Relative URL to append to the base URL
	 * @return Combined URL
	 * @throws MalformedURLException Throw by the URL constructor
	 */
	public static URL buildURL(String base, String local) throws MalformedURLException {
		return impl.buildURL(base, local);	
	}
	
	/**
	 * Attempt to combine an absolute URL and a relative URL, but return null on error instead of throwing an exception
	 * @param base Absolute URL to use as a base
	 * @param local Relative URL to append to the base URL
	 * @return Combined URL or null on error
	 */
	public static URL tryBuildURL(String base, String local) {
		return impl.tryBuildURL(base, local);
	}
	
	/**
	 * Attempt to create an URL, and return null on error, instead of throwing an exception 
	 * @param fromString String to parse
	 * @return URL or null on error
	 */
	public static URL tryCreateURL(String fromString) {
		return impl.tryCreateURL(fromString);
	}
	
	/**
	 * Create a new instance of GetUriBuilder
	 * @return Implementation of GetUriBuilder
	 */
	public static GetUriBuilder createUriBuilder() {
		return impl.createUriBuilder();
	}
	
	/**
	 * Create a new eXist exception and extract details from an XML node
	 * @param fromNode Exception information will be extracted from this node
	 * @return eXist exception with details extracted from an XML node
	 */
	public static ExistException createException(Node fromNode) {
		return impl.createException(fromNode);
	}
	
	/**
	 * Create a new ResultElement by parsing an XML node
	 * @param fromNode Node to parse
	 * @return new instance of ResultElement
	 */
	public static ResultElement createResultElement(Node fromNode) {
		return impl.createResultElement(fromNode);
	}
	
	/**
	 * Create a request to the server and return a XML DOM document
	 * @param uri This method will send HTTP GET request to this URI
	 * @param db Document builder to use for parsing the result
	 * @return XML DOM document
	 * @throws ExistException Whenever there is an error
	 */
	public static Document executeRequest(URI uri, DocumentBuilderWrapper db) {	
		return impl.executeRequest(uri, db);
	}	
}
