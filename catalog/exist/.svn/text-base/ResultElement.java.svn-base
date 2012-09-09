package cs5031.catalogplayer.catalog.exist;

import org.w3c.dom.Node;

/**
 * Allows ExistResultElement to be mocked
 * @author Dido
 *
 */
public interface ResultElement {

	/**
	 * Get the number of items returned by the query
	 * @return Number of items returned by the query
	 */
	public abstract int getCount();

	/**
	 * Get total number of items
	 * @return Total number of items
	 */
	public abstract int getHits();

	/**
	 * Get the index position of the first item in the result sequence that was returned
	 * @return Index position of the first item in the result sequence that was returned
	 */
	public abstract int getStart();

	/**
	 * Get the first child element that is not #text
	 * @return First non-#text element
	 */
	public abstract Node getRoot();

	/**
	 * Get a list of all child nodes whose name is not #text
	 * @return List of child nodes whose name is not #text
	 */
	public abstract Node[] getChildNodes();

}