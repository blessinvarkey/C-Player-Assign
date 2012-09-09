package cs5031.catalogplayer.test.existcatalog;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cs5031.catalogplayer.catalog.Query;
import cs5031.catalogplayer.catalog.exist.DocumentBuilderWrapper;
import cs5031.catalogplayer.catalog.exist.ExistException;
import cs5031.catalogplayer.catalog.exist.ExistSearchResult;
import cs5031.catalogplayer.catalog.exist.GetUriBuilder;
import cs5031.catalogplayer.catalog.exist.ResultElement;
import cs5031.catalogplayer.catalog.exist.UtilityMethods;
import cs5031.catalogplayer.catalog.exist.Utils;

public class ExistSearchResultTest {
	private Mockery context;

	@Before
	public void setUp() throws Exception {
		this.context = new Mockery();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_NonNullParams_FieldsAreInitialized() {
		final Query query = this.context.mock(Query.class, "query");
		final DocumentBuilderWrapper db = this.context.mock(DocumentBuilderWrapper.class);
		final Sequence seq = this.context.sequence("constructor_NonNullParams_FieldsAreInitialized");		
		final String expectedQuery = "some query";		
		
		this.context.checking(new Expectations() {{
			oneOf(query).getQuery();
				will(returnValue(expectedQuery));
				inSequence(seq);
			oneOf(query).getPageNo();
				will(returnValue(3));
				inSequence(seq);
			oneOf(query).getNumResults();
				will(returnValue(5));
				inSequence(seq);			
		}});
		
		TestableExistSearchResult t = new TestableExistSearchResult(query, db);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedQuery, t.getQuery());
		assertEquals(db, t.getDocumentBuilder());
		assertNull(t.getDocument());
		assertEquals(2, t.getCurrentPage());
		assertEquals(5, t.getPageSize());
	}
	
	@Test
	public void update_whenINvoked_createsRequestAndParsesResult() throws URISyntaxException {
		final UtilityMethods utils = this.context.mock(UtilityMethods.class);
		final GetUriBuilder uriBuilder = this.context.mock(GetUriBuilder.class);
		final Sequence seq = this.context.sequence("update_whenINvoked_createsRequestAndParsesResult");
		final DocumentBuilderWrapper db = this.context.mock(DocumentBuilderWrapper.class); 
		final ResultElement resElem = this.context.mock(ResultElement.class);
		final Document doc = this.context.mock(Document.class);
		final Node root = this.context.mock(Node.class, "root");
		final URI reqURI = new URI("http://example.com");
		Utils.setImplementation(utils);
		final String queryTxt = "some query";
		final int pageSize = 5;
		final int currentPage = 1;
		
		TestableExistSearchResult t = new TestableExistSearchResult();
		t.setQuery(queryTxt);
		t.setPageSize(pageSize);
		t.setCurrentPage(currentPage);
		t.setDocumentBuilder(db);
		
		this.context.checking(new Expectations() {{
			oneOf(utils).createUriBuilder();
				will(returnValue(uriBuilder));
				inSequence(seq);
			oneOf(uriBuilder).setQuery(queryTxt);
				will(returnValue(uriBuilder));
				inSequence(seq);
			oneOf(uriBuilder).setPaging(pageSize, currentPage);
				will(returnValue(uriBuilder));
				inSequence(seq);
			oneOf(uriBuilder).build();
				will(returnValue(reqURI));
				inSequence(seq);
			oneOf(utils).executeRequest(reqURI, db);
				will(returnValue(doc));
				inSequence(seq);
			oneOf(doc).getFirstChild();
				will(returnValue(root));
				inSequence(seq);
			oneOf(root).getNodeName();
				will(returnValue("rootName"));
				inSequence(seq);
			oneOf(utils).createResultElement(root);
				will(returnValue(resElem));
				inSequence(seq);
		}});
		
		
		t.testUpdate();
		
		this.context.assertIsSatisfied();
		assertEquals(doc, t.getDocument());
		assertEquals(resElem, t.getResultElem());
	}
	
	@Test(expected=ExistException.class)
	public void update_serverError_exceptionThrown() throws URISyntaxException {
		final UtilityMethods utils = this.context.mock(UtilityMethods.class);
		final GetUriBuilder uriBuilder = this.context.mock(GetUriBuilder.class);
		final Sequence seq = this.context.sequence("update_whenINvoked_createsRequestAndParsesResult");
		final DocumentBuilderWrapper db = this.context.mock(DocumentBuilderWrapper.class); 
		final ResultElement resElem = this.context.mock(ResultElement.class);
		final Document doc = this.context.mock(Document.class);
		final Node root = this.context.mock(Node.class, "root");
		final URI reqURI = new URI("http://example.com");
		Utils.setImplementation(utils);
		final String queryTxt = "some query";
		final int pageSize = 5;
		final int currentPage = 1;
		
		TestableExistSearchResult t = new TestableExistSearchResult();
		t.setQuery(queryTxt);
		t.setPageSize(pageSize);
		t.setCurrentPage(currentPage);
		t.setDocumentBuilder(db);
		
		this.context.checking(new Expectations() {{
			oneOf(utils).createUriBuilder();
				will(returnValue(uriBuilder));
				inSequence(seq);
			oneOf(uriBuilder).setQuery(queryTxt);
				will(returnValue(uriBuilder));
				inSequence(seq);
			oneOf(uriBuilder).setPaging(pageSize, currentPage);
				will(returnValue(uriBuilder));
				inSequence(seq);
			oneOf(uriBuilder).build();
				will(returnValue(reqURI));
				inSequence(seq);
			oneOf(utils).executeRequest(reqURI, db);
				will(returnValue(doc));
				inSequence(seq);
			oneOf(doc).getFirstChild();
				will(returnValue(root));
				inSequence(seq);
			oneOf(root).getNodeName();
				will(returnValue("exception"));
				inSequence(seq);
			oneOf(utils).createException(root);
				will(returnValue(new ExistException("Error message")));
				inSequence(seq);
		}});
		
		
		t.testUpdate();
	}


}

/**
 * Exposes non-public methods and fields of ExistSearchResult
 * @author Dido
 *
 */
class TestableExistSearchResult extends ExistSearchResult {
	/**
	 * invoke original constructor
	 * @param q query
	 * @param db document builder
	 */
	public TestableExistSearchResult(Query q, DocumentBuilderWrapper db) {
		super(q, db);
	}
	
	/**
	 * Expose default constructor
	 */
	public TestableExistSearchResult() {
		
	}
	
	/**
	 * Expose update method
	 */
	public void testUpdate() {
		this.update();
	}
	
	/**
	 * Expose documentBuilder field
	 * @return value of documentBuilder field
	 */
	public DocumentBuilderWrapper getDocumentBuilder() {
		return this.documentBuilder;
	}
	
	/**
	 * Set new value for field documentBuilder
	 * @param db new value
	 */
	public void setDocumentBuilder(DocumentBuilderWrapper db) {
		this.documentBuilder = db;
	}
	
	/**
	 * Expose field document
	 * @return field value
	 */
	public Document getDocument() {
		return this.document;
	}
	
	/**
	 * Set new value for field document
	 * @param doc new value
	 */
	public void setDocument(Document doc) {
		this.document = doc;
	}
	
	/**
	 * Expose field query
	 * @return field value
	 */
	public String getQuery() {
		return this.query;
	}
	
	/**
	 * Set new value for field query
	 * @param query new value
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	/**
	 * Expose field currentPage
	 * @return field value
	 */
	public int getCurrentPage() {
		return this.currentPage;
	}
	
	/**
	 * Set new value for field currentPage
	 * @param cp new value
	 */
	public void setCurrentPage(int cp) {
		this.currentPage = cp;
	}
	
	/**
	 * Expose field pageSize
	 * @return field value
	 */
	public int getPageSize() {
		return this.pageSize;
	}
	
	/**
	 * Set new value for field pageSize
	 * @param ps new value
	 */
	public void setPageSize(int ps) {
		this.pageSize = ps;
	}
	
	/**
	 * Expose field resultElem
	 * @return field value
	 */
	public ResultElement getResultElem() {
		return this.resultElem;
	}
	
	/**
	 * Set new value for field resultElem
	 * @param re new value
	 */
	public void setResultElem(ResultElement re) {
		this.resultElem = re;
	}
}