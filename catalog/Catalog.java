package cs5031.catalogplayer.catalog;

/**
 * A generic interface for a catalog of resources. All that is required is that the 
 * catalog provide a search interface that can handle queries in some appropriate 
 * language and returns paged result sets.
 *  
 * @author avoss
 *
 */
public interface Catalog {
	
	/**
	 * Do a search on the catalog and return a {@link SearchResult}.
	 * 
	 * @param a {@link Query}
	 * @return a pageable {@link SearchResult}
	 */
	public SearchResult search(Query query);
	
	/**
	 * Creates an empty query to be passed to the {@link cs5031.catalogplayer.catalog.Catalog.search(Query) method}
	 * @return Empty query
	 */
	public Query createQuery();
}
