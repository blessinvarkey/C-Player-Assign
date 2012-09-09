package cs5031.catalogplayer.catalog;

/**
 * A generic interface for a query to be sent to a {@link Catalog}. 
 * 
 * @author avoss
 *
 */
public interface Query {
	
	/**
	 * Set the query to be sent to the catalog. This will be a String containing a specification in
	 * a query language specific to the catalog being accessed. For example, the Internet Archive 
	 * uses the Lucene query engine, so a query for this catalog would contain statements suitable
	 * for Lucene.
	 * 
	 * @see http://www.archive.org/advancedsearch.php 
	 * 
	 * @param query - the query to be sent to the catalog
	 */
	void setQuery(String query);
	
	/**
	 * Retrieve the query set using {@link #setQuery(String)}.
	 * 
	 * @return the query string
	 */
	String getQuery();
	
	/**
	 * Return the number of results set for this query. Note that this is merely a request to 
	 * the catalog, which may return fewer results per page.
	 * 
	 * @return the number of results set for this query
	 */
	int getNumResults();
	
	/**
	 * Set the number of results that the catalog should return in a single page of results.
	 * Note that this is just a request and the catalog is free to return fewer results per
	 * page.
	 * 
	 * @param numResults the number of results requested per page.
	 */
	void setNumResults(int numResults);
	
	/**
	 * Set the page number to be retrieved by the query. 
	 * 
	 * @param page the page number of the page to be retrieved by the query
	 */
	void setPageNo(int page);
	
	/**
	 * Get the page number to be retrieved by the query.
	 * 
	 * @return the page number of the page to be retrieved by the query
	 */
	int getPageNo();
}
