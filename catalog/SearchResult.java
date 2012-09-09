package cs5031.catalogplayer.catalog;

/**
 * The result of a search in a catalog. As search interfaces often return only a limited
 * number of results at a time, the SearchResult may only contain part of the overall
 * results of the search and the user needs to page through successive partial sets. An
 * implementation of SearchResult will need to maintain enough state to be able to retrieve
 * the other pages of results.
 * 
 * To reduce the number of interactions with the remote search service, any pages of 
 * results already retrieved should be cached.
 * 
 * @author avoss
 *
 */
public interface SearchResult {
	
	/**
	 * Get an Iterable set of resources in the current result page.
	 * 
	 * Note the use of Generics in this...
	 * 
	 * @return an {@link java.lang.Iterable} set of {@link Resource}s.
	 */
	public Iterable<Resource> getResources();
	
	/**
	 * Return the number of results per page that was returned by the catalog.
	 * 
	 * @return the number of results per page that was returned by the catalog.
	 */
	public int getResultsPerPage();
	
	/**
	 * Check if there is another page of results after the current page.
	 * @return true if there is another page, false otherwise
	 */
	public boolean hasNextPage();
	
	/**
	 * Get the next page of results, making it the new current page and returning
	 * the Resources it contains.
	 */
	public Iterable<Resource> getNextPage();
	
	/**
	 * Check if there is another page of results before the current page.
	 * @return true if there is another page, false otherwise
	 */
	public boolean hasPrevPage();
	
	/**
	 * Get the previous page of results, making it the new current page and returning
	 * the Resources it contains.
	 * @return an {@link java.lang.Iterable} set of {@link Resource}s.
	 */
	public Iterable<Resource> getPrevPage();
}
