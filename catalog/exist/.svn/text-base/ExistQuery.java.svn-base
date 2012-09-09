package cs5031.catalogplayer.catalog.exist;

import cs5031.catalogplayer.catalog.Query;

/**
 * Store filter, sort, and paging information to send to the eXist server
 * @author <110017972>
 *
 */
public class ExistQuery implements Query {
	/** Query */
	private String query;
	/** Page size */
	private int numResults;
	/** Page number */
	private int pageNo;

	/**
	 * Construct an empty query with default values
	 */
	public ExistQuery() {
		this(null);
	}
	
	/**
	 * Construct a query for a specific resource, optionally filtered
	 * @param query xQuery or XPATH query (e.g. //album or //track[contains(title,"soldier")]
	 */
	public ExistQuery(String query) {
		this(query, -1);
	}
	
	/**
	 * Construct a query that filters results and sets custom limit of items returned
	 * @param query xQuery or XPATH query (e.g. //album or //track[contains(title,"soldier")]
	 * @param numResults Maximum number of items per page
	 */
	public ExistQuery(String query, int numResults) {
		this(query, numResults, -1);
	}
	
	/**
	 * Construct a custom query
	 * @param query xQuery or XPATH query (e.g. //album or //track[contains(title,"soldier")]
	 * @param numResults Maximum number of items per page
	 * @param pageNo Number of the requested page
	 */
	public ExistQuery(String query, int numResults, int pageNo) {
		this.query = query;
		this.numResults = numResults;
		this.pageNo = pageNo;
	}
	
	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	@Override
	public int getNumResults() {
		return this.numResults;
	}

	@Override
	public void setNumResults(int numResults) {
		this.numResults = numResults;		
	}

	@Override
	public void setPageNo(int page) {
		this.pageNo = page;
	}

	@Override
	public int getPageNo() {
		return this.pageNo;
	}

}
