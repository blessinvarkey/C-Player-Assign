package cs5031.catalogplayer.catalog.exist;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cs5031.catalogplayer.catalog.Query;
import cs5031.catalogplayer.catalog.Resource;
import cs5031.catalogplayer.catalog.SearchResult;

/**
 * Class that implements paging through results
 * @author <110017972>
 *
 */
public class ExistSearchResult implements SearchResult {
	/** Shared document builder with all search results */
	protected DocumentBuilderWrapper documentBuilder;
	/** XML DOM Document representing the current page */
	protected Document document;
	/** xQuery or XPATH to pass to the _query query string parameter */
	protected String query;
	/** Current page. Starts at -1 (which is invalid), the first valid page is 0 */
	protected int currentPage;
	/** Maximum number of results per page */
	protected int pageSize;
	/** XML DOM element that contains the returned XML document */
	protected ResultElement resultElem;
	
	/**
	 * Create a new search result
	 * @param query Query object to use for generating xQuery/XPATH query and paging
	 * @param documentBuilder Shared document builder
	 */
	public ExistSearchResult(Query query, DocumentBuilderWrapper documentBuilder) {
		this.query = query.getQuery();
		this.documentBuilder = documentBuilder;
		this.document = null;
		this.currentPage = Math.max(0, query.getPageNo()) - 1;
		this.pageSize = query.getNumResults();
		if (this.pageSize <= 0) {
			this.pageSize = 10;
		}
	}	
	
	/**
	 * Use for unit testing only
	 */
	protected ExistSearchResult() {
		
	}
	
	/**
	 * Send a request to the server and parse the output
	 * 
	 * @throws ExistException Thrown if there was any error
	 */
	protected void update() {	
		URI uri = 
			Utils.createUriBuilder()
			.setQuery(this.query)
			.setPaging(this.pageSize, this.currentPage)
			.build();
		
		this.document = Utils.executeRequest(uri, this.documentBuilder);
		Node root = this.document.getFirstChild();
		String rootName = root.getNodeName();
		if ("exception".equals(rootName)) {
			throw Utils.createException(root);			
		}
		this.resultElem = Utils.createResultElement(root);
	}
	
	/**
	 * Make certain that the current page's data is retrieved by the server
	 */
	private void ensureUpdated() {
		if (this.document == null || this.resultElem == null) {
			this.update();
		}
	}
	
	@Override
	public Iterable<Resource> getResources() {
		this.ensureUpdated();
		List<Resource> result = new ArrayList<Resource>();
		
		for (Node child: this.resultElem.getChildNodes()) {
			this.parseResultChildNode(child, result);
		}
		
		return result;
	}

	/**
	 * Parse the child nodes of ResultElement
	 * @param child Current Node to parse
	 * @param result Albums and Tracks will be added to this collection
	 */
	protected void parseResultChildNode(Node child, List<Resource> result) {
		String childName = child.getNodeName();
		if ("album".equals(childName)) {				
			ExistAlbum album = new ExistAlbum(child);
			result.add(album);
		}
		else if ("track".equals(childName)) {
			ExistTrack track = new ExistTrack(child, null);
			result.add(track);
		}	
		else if ("exist:collection".equals(childName)) {
			NodeList items = child.getChildNodes();
			int countOfItems = items.getLength();
			for (int itemIdx = 0; itemIdx < countOfItems; ++itemIdx) {
				Node item = items.item(itemIdx);
				String itemName = item.getNodeName();
				if ("exist:resource".equals(itemName)) {
					ExistResourceLink unknown = new ExistResourceLink(item);
					result.add(unknown);
				}
				else {
					throw new ExistException("Unknown element in exist:collection: " + itemName);
				}
			}
		}
		else {
			throw new ExistException("Unknown element in exist:result: " + childName);
		}
	}

	@Override
	public int getResultsPerPage() {
		this.ensureUpdated();
		return Math.min(this.pageSize, this.resultElem.getCount());
	}

	@Override
	public boolean hasNextPage() {
		if (this.currentPage >= 0) {
			this.ensureUpdated();
			int returned = this.resultElem.getCount();
			int requested = this.pageSize;
			int remaining = this.resultElem.getHits() - (requested * (this.currentPage + 1));
			return returned > 0 && returned == requested && remaining > 0;
		}
		else {
			return true;
		}
	}

	@Override
	public Iterable<Resource> getNextPage() {
		return this.incrementPage().getResources();
	}

	@Override
	public boolean hasPrevPage() {
		return this.currentPage > 0;
	}

	@Override
	public Iterable<Resource> getPrevPage() {
		return this.decrementPage().getResources();
	}
	
	/**
	 * Move to the next page
	 * @return This, use for method chaining
	 * @throws NoSuchElementException When there is no next page
	 */
	public ExistSearchResult incrementPage() {
		if (!this.hasNextPage()) {
			throw new NoSuchElementException("There is no next page");
		}
		this.currentPage++;
		this.update();
		return this;
	}
	
	/**
	 * Move to the previous page
	 * @return This, use for method chaining
	 * @throws NoSuchElementException When already at the first page
	 */
	public ExistSearchResult decrementPage() {
		if (!this.hasPrevPage()) {
			throw new NoSuchElementException("There is no previous page");
		}
		this.currentPage--;
		this.update();
		return this;
	}
	
	@Override
	public String toString() {
		if (this.currentPage >= 0) {
			this.ensureUpdated();
			return String.format("[SearchResult: query=%s, page=%d, size=%d, start=%d, received=%d, total=%d]",
					this.query,	this.currentPage, this.pageSize, this.resultElem.getStart(), 
					this.resultElem.getCount(), this.resultElem.getHits());
		}
		else {
			return String.format("[SearchResult: query=%s, page=%d, size=%d]", 
					this.query, this.currentPage, this.pageSize);
		}
	}
}
