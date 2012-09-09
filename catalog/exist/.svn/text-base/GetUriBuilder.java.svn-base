package cs5031.catalogplayer.catalog.exist;

import java.net.URI;

/**
 * Allows ExistGetURI to be mocked
 * @author Dido
 *
 */
public interface GetUriBuilder {

	/**
	 * Applies an XSL stylesheet to the requested resource.
	 * @param xsl XSL Stylesheet
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setXsl(String xsl);

	/**
	 * Executes a query specified by the request. 
	 * @param query XPath/XQuery Expression
	 * @return This, use for method chaining
	 * 
	 * The collection or resource referenced in the request path is added to the set of statically known documents for the query.
	 */
	public abstract GetUriBuilder setQuery(String query);

	/**
	 * Returns indented pretty-print XML
	 * @param indent Yes|no. The default is yes.
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setIndent(boolean indent);

	/**
	 * Sets the character encoding for the resultant XML
	 * @param encoding Character Encoding Type. The default value is UTF-8.
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setEncoding(String encoding);

	/**
	 * Specifies the number of items to return from the resultant sequence
	 * @param howMany Number of Items. The default value is 10.
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setHowMany(int howMany);

	/**
	 * Specifies the index position of the first item in the result sequence to be returned 
	 * @param start Starting Position in Sequence.  The default value is 1.
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setStart(int start);

	/**
	 * Specifies whether the returned query results are to be wrapped into a surrounding <exist:result> element
	 * @param wrap yes | no. The default value is yes.
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setWrap(boolean wrap);

	/**
	 * Specifies whether the query should display its source code instead of being executed. 
	 * @param source The default value is no
	 * @return This, use for method chaining 
	 */
	public abstract GetUriBuilder setSource(boolean source);

	/**
	 * If set to "yes", the query results of the current query are stored into a session on the server. 
	 * A session id will be returned with the response. Subsequent requests can pass this session id via the 
	 * _session parameter. If the server finds a valid session id, it will return the cached results 
	 * instead of re-evaluating the query
	 * 
	 * @param cache yes | no
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setCache(boolean cache);

	/**
	 * Specifies a session id returned by a previous query request. If the session is valid, 
	 * query results will be read from the cached session.
	 * 
	 * @param session session id
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setSession(String session);

	/**
	 * Release the session identified by the session id
	 * @param release session id
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setRelease(String release);

	/**
	 * Setup paging for the request
	 * @param pageSize Maximum number of items per page
	 * @param currentPage Current page that is requested
	 * @return This, use for method chaining
	 */
	public abstract GetUriBuilder setPaging(int pageSize, int currentPage);

	/**
	 * Build HTTP GET URI
	 * @return URI for a GET request to the Exist server, or null on error
	 */
	public abstract URI build();

}