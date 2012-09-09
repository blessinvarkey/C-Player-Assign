package cs5031.catalogplayer.catalog;

import org.w3c.dom.Document;

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
	public Document search(Query query);//was SearchResult
}
