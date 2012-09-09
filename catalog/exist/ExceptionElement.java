package cs5031.catalogplayer.catalog.exist;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class that parses exception elements
 * @author <110017972>
 *
 */
public class ExceptionElement {
	/** value of the message attribute */
	private String message;
	/** value of the path attribute */
	private String path;
	/** value of the query attribute */
	private String query;
	
	/**
	 * Parse an exception element
	 * @param root Node with name equal to exception
	 * @throws IllegalArgumentException When {@code root} is {@code null} or when the node name is not equal to "exception"
	 */
	public ExceptionElement(Node root) {
		if (root == null) {
			throw new IllegalArgumentException("root is null");
		}
		if (!"exception".equals(root.getNodeName())) {
			throw new IllegalArgumentException("root is not an exception element");
		}
 		NodeList children = root.getChildNodes();
		int countOfChildren = children.getLength();
		for (int i = 0; i < countOfChildren; ++i) {
			Node child = children.item(i);
			this.parseChild(child);
		}
	}
	
	/**
	 * Parse a child node of exception element and fill the proper field
	 * @param child DOM element
	 */
	protected void parseChild(Node child) {
		String name = child.getNodeName();
		String nodeText = child.getTextContent();
		if (nodeText != null) {
			nodeText = nodeText.trim();
		}
		
		if ("path".equals(name)) {
			this.path = nodeText;
		}
		else if ("message".equals(name)) {
			this.message = nodeText;
		}
		else if ("query".equals(name)) {
			this.query = nodeText;
		}
	}
	
	/**
	 * Use for unit testing only
	 */
	protected ExceptionElement() {
		
	}
	
	/**
	 * Use for unit testing only
	 * @param message initial value for the message field
	 * @param path initial value for the path field
	 * @param query initial value for the query field
	 */
	protected ExceptionElement(String message, String path, String query) {
		this.message = message;
		this.path = path;
		this.query = query;
	}
	
	/**
	 * Get the exception message returned by the server
	 * @return Exception message returned by the server
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Get the request path whose processing resulted in a server exception
	 * @return Request path whose processing resulted in a server exception
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Get the query which caused the exception
	 * @return Query that caused the exception
	 */
	public String getQuery() {
		return this.query;
	}
	
	@Override
	public String toString() {
		return String.format("[exception %s, path=%s, query=%s]", this.message, this.path, this.query);
	}
}
