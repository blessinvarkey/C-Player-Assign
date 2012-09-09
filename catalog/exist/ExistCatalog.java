package cs5031.catalogplayer.catalog.exist;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cs5031.catalogplayer.catalog.Catalog;
import cs5031.catalogplayer.catalog.Query;
import cs5031.catalogplayer.catalog.SearchResult;

/**
 * Implements the {@link cs5031.catalogplayer.catalog.Catalog} interface.
 * Connects to catalog database hosted on a remote server that returns results in eXist XML
 * @author <110017972>
 *
 */
public class ExistCatalog implements Catalog {
	/**
	 * Shared document builder across all search results
	 */
	private DocumentBuilderWrapper documentBuilder;
	
	/**
	 * Create a new catalogue
	 * @throws ExistException When the catalogue cannot obtain an instance of {@link javax.xml.parsers.DocumentBuilder}
	 */
	public ExistCatalog() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			this.documentBuilder = new DefaultDocumentBuilderWrapper(builder);
		} catch (ParserConfigurationException e) {
			throw new ExistException(e);
		}
	}
	
	@Override
	public SearchResult search(Query query) {		
		return new ExistSearchResult(query, this.documentBuilder);
	}
	
	@Override
	public Query createQuery() {
		return new ExistQuery();
	}

}
