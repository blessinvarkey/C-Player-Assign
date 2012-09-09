package cs5031.catalogplayer.catalog.exist;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Various utility methods
 * @author Dido
 *
 */
public interface UtilityMethods {
	
	/**
	 * Attempt to extract the value of an XML attribute as {@code int}
	 * @param attribs Map of attributes from which to attempt extraction
	 * @param name Name of the attribute
	 * @param defaultVal Value to return on failure
	 * @return Value of attribute {@link name} as {@code int}, or {@link defaultVal} on failure 
	 */
	int getAttrValAsInt(NamedNodeMap attribs, String name, int defaultVal);
	
	/**
	 * Attempt to extract the value of an XML attribute as {@code String}
	 * @param attribs Map of attributes from which to attepmt extraction
	 * @param name Name of the attribute
	 * @param defaultVal Value to return on failure
	 * @return Value of attribute {@link name} as {@code String}, or {@link defaultVal} on failure
	 */
	String getAttrValAsString(NamedNodeMap attribs, String name, String defaultVal);
	
	/**
	 * Combine an absolute URL and a relative URL
	 * @param base Absolute URL to use as a base
	 * @param local Relative URL to append to the base URL
	 * @return Combined URL
	 * @throws MalformedURLException Throw by the URL constructor
	 */
	URL buildURL(String base, String local) throws MalformedURLException;
	
	/**
	 * Attempt to combine an absolute URL and a relative URL, but return null on error instead of throwing an exception
	 * @param base Absolute URL to use as a base
	 * @param local Relative URL to append to the base URL
	 * @return Combined URL or null on error
	 */
	URL tryBuildURL(String base, String local);
	
	/**
	 * Attempt to create an URL, and return null on error, instead of throwing an exception 
	 * @param fromString String to parse
	 * @return URL or null on error
	 */
	URL tryCreateURL(String fromString);
	
	/**
	 * Create a new instance of GetUriBuilder
	 * @return Implementation of GetUriBuilder
	 */
	GetUriBuilder createUriBuilder();
	
	/**
	 * Create a new eXist exception and extract details from an XML node
	 * @param fromNode Exception information will be extracted from this node
	 * @return eXist exception with details extracted from an XML node
	 */
	ExistException createException(Node fromNode);
	
	/**
	 * Create a new ResultElement by parsing an XML node
	 * @param fromNode Node to parse
	 * @return new instance of ResultElement
	 */
	ResultElement createResultElement(Node fromNode);
	
	/**
	 * Create a request to the server and return a XML DOM document
	 * @param uri This method will send HTTP GET request to this URI
	 * @param db Document builder to use for parsing the result
	 * @return XML DOM document
	 * @throws ExistException Whenever there is an error
	 */
	Document executeRequest(URI uri, DocumentBuilderWrapper db);
}
