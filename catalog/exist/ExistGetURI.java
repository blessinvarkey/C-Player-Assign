package cs5031.catalogplayer.catalog.exist;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import cs5031.catalogplayer.catalog.Query;

/**
 * Class that helps generation of URLs for GET HTTP requests to the eXist server
 * @author <110017972>
 *
 */
public class ExistGetURI implements GetUriBuilder {
	/**
	 * Hard-coded protocol
	 */
	private static final String PROTOCOL = "http";
	/**
	 * Hard-coded host
	 */
	private static final String HOST = "138.251.198.119:8080";
	/**
	 * Hard-coded resource path to the catalog database
	 */
	private static final String CATALOG_RESOURCE = "/exist/rest/db/catalog";
	
	/** Container */
	private List<NameValuePair> queryStringParams;
	/** */
	private String resourceUrl;
	
	/**
	 * Create a new URI builder with specific resource
	 * @param resource Specific resource (e.g. /catalog-id)
	 */
	public ExistGetURI(String resource) {
		StringBuilder sb = new StringBuilder(CATALOG_RESOURCE);
		
		if (resource != null) {
			if (!resource.startsWith("/")) {
				sb.append("/");
			}
			sb.append(resource);
		}
		
		this.resourceUrl = sb.toString();
		this.queryStringParams = new ArrayList<NameValuePair>();
	}
	
	/**
	 * Create a new URI pointing to the root of the catalog database
	 */
	public ExistGetURI() {
		this((String)null);
	}
	
	/**
	 * Create a new URI pointing to the root of the catalog database and a query/paging settings extracted from a Query object
	 * @param queryObj Query object to parse
	 */
	public ExistGetURI(Query queryObj) {
		this((String)null);
		
		String query = queryObj.getQuery();
		if (query != null && query.length() > 0) {
			this.setQuery(query);
		}
		int numResults = queryObj.getNumResults();
		if (numResults > 0) {
			this.setHowMany(numResults);
		}
		int page = queryObj.getPageNo();
		if (page >= 0) {
			int itemsPerPage = numResults;
			if (itemsPerPage <= 0)
				itemsPerPage = 10;
			int start = page * itemsPerPage;
			this.setStart(start);
		}
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setXsl(java.lang.String)
	 */
	@Override
	public GetUriBuilder setXsl(String xsl) {
		this.queryStringParams.add(new BasicNameValuePair("_xsl", xsl));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setQuery(java.lang.String)
	 */
	@Override
	public GetUriBuilder setQuery(String query) {
		this.queryStringParams.add(new BasicNameValuePair("_query", query));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setIndent(boolean)
	 */
	@Override
	public GetUriBuilder setIndent(boolean indent) {
		this.queryStringParams.add(new BasicNameValuePair("_indent", indent ? "yes" : "no"));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setEncoding(java.lang.String)
	 */
	@Override
	public GetUriBuilder setEncoding(String encoding) {
		this.queryStringParams.add(new BasicNameValuePair("_encoding", encoding));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setHowMany(int)
	 */
	@Override
	public GetUriBuilder setHowMany(int howMany) {
		this.queryStringParams.add(new BasicNameValuePair("_howmany", Integer.toString(howMany)));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setStart(int)
	 */
	@Override
	public GetUriBuilder setStart(int start) {
		this.queryStringParams.add(new BasicNameValuePair("_start", Integer.toString(start)));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setWrap(boolean)
	 */
	@Override
	public GetUriBuilder setWrap(boolean wrap) {
		this.queryStringParams.add(new BasicNameValuePair("_wrap", wrap ? "yes" : "no"));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setSource(boolean)
	 */
	@Override
	public GetUriBuilder setSource(boolean source) {
		this.queryStringParams.add(new BasicNameValuePair("_source", source ? "yes" : "no"));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setCache(boolean)
	 */
	@Override
	public GetUriBuilder setCache(boolean cache) {
		this.queryStringParams.add(new BasicNameValuePair("_cache", cache ? "yes" : "no"));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setSession(java.lang.String)
	 */
	@Override
	public GetUriBuilder setSession(String session) {
		this.queryStringParams.add(new BasicNameValuePair("_session", session));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setRelease(java.lang.String)
	 */
	@Override
	public GetUriBuilder setRelease(String release) {
		this.queryStringParams.add(new BasicNameValuePair("_release", release));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#setPaging(int, int)
	 */
	@Override
	public GetUriBuilder setPaging(int pageSize, int currentPage) {
		int start = 1 + (pageSize * currentPage);
		this.setStart(start);
		this.setHowMany(pageSize);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.GetUriBuilder#build()
	 */
	@Override
	public URI build() {
		String queryString = URLEncodedUtils.format(this.queryStringParams, "UTF-8");
		try {
			URI uri = URIUtils.createURI(PROTOCOL, HOST, -1, this.resourceUrl, queryString, null);
			return uri;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
