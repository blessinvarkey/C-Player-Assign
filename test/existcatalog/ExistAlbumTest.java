package cs5031.catalogplayer.test.existcatalog;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.catalog.exist.ExistAlbum;
import cs5031.catalogplayer.catalog.exist.UtilityMethods;
import cs5031.catalogplayer.catalog.exist.Utils;

public class ExistAlbumTest {
	private Mockery context;
	private UtilityMethods utils;
	
	@Before
	public void setup() {
		this.context = new Mockery();
		this.utils = this.context.mock(UtilityMethods.class);
		Utils.setImplementation(this.utils);
	}
	
	@After 
	public void cleanUp() {
		Utils.setDefaultImplementation();
	}
	
	@Test
	public void constructor_passedValidAlbum_ExtractsAttributesAndParsesChildren() throws MalformedURLException {
		final Node album = this.context.mock(Node.class, "album");
		final Node child1 = this.context.mock(Node.class, "child1");
		final Node child2 = this.context.mock(Node.class, "child2");
		final NodeList children = this.context.mock(NodeList.class, "album children");
		final NamedNodeMap albumAttribs = this.context.mock(NamedNodeMap.class, "album attribs");
		final Sequence seq = this.context.sequence("constructor_passedValidAlbum_ExtractsAttributesAndParsesChildren");
		
		final String expectedID = "some id";
		final URL baseURL = new URL("http://example.com");
		final URL expectedImageURL = new URL("http://example.com/image");
		final URL expectedLicenseURL = new URL("http://example.com/image");
		
		
		this.context.checking(new Expectations() {{
			oneOf(album).getNodeName(); 
				will(returnValue("album")); 
				inSequence(seq);
			oneOf(album).getAttributes(); 
				will(returnValue(albumAttribs)); 
				inSequence(seq);
			oneOf(utils).getAttrValAsString(albumAttribs, "id", null); 
				will(returnValue(expectedID)); 
				inSequence(seq);
			oneOf(album).getChildNodes(); 
				will(returnValue(children)); 
				inSequence(seq);
			oneOf(children).getLength(); 
				will(returnValue(2)); 
				inSequence(seq);
			oneOf(children).item(0);
				will(returnValue(child1));
				inSequence(seq);
			ignoring(child1);
			oneOf(children).item(1);
				will(returnValue(child2));
				inSequence(seq);
			ignoring(child2);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum(album);
		tea.setBaseURL(baseURL);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedID, tea.getIdentifier());
	}

	
	@Test
	public void processChild_titleIsPassed_getTitleReturnsNodeText() {
		final Node child = this.context.mock(Node.class, "child");
		final Sequence seq = this.context.sequence("processChild_titleIsPassed_getTitleReturnsNodeText");
		final String expectedValue = "expected value";
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("title"));
				inSequence(seq);
			oneOf(child).getTextContent();
				will(returnValue(expectedValue));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedValue, tea.getTitle());
	}
	
	@Test
	public void processChild_artistIsPassed_nodeTextAddedToArtistsList() {
		final Node child = this.context.mock(Node.class, "child");
		final Sequence seq = this.context.sequence("processChild_artistIsPassed_nodeTextAddedToArtistsList");
		final String expectedValue = "expected value";
		final List<String> artistsList = new ArrayList<String>();
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("artist"));
				inSequence(seq);
			oneOf(child).getTextContent();
				will(returnValue(expectedValue));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedValue, artistsList.get(0));
	}
	
	@Test
	public void processChild_resourceUrlIsPassed_getResourceURLReturnsNodeText() throws MalformedURLException {
		final Node child = this.context.mock(Node.class, "child");
		final Sequence seq = this.context.sequence("processChild_resourceUrlIsPassed_getResourceURLReturnsNodeText");
		final String expectedValue = "http://example.com/resourceURL";
		final URL expectedURL = new URL(expectedValue);
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("resourceUrl"));
				inSequence(seq);
			oneOf(child).getTextContent();
				will(returnValue(expectedValue));
				inSequence(seq);
			oneOf(utils).tryCreateURL(expectedValue);
				will(returnValue(expectedURL));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedURL, tea.getResourceURL());
	}
	
	@Test
	public void processChild_baseUrlIsPassed_getBaseURLReturnsNodeText() throws MalformedURLException {
		final Node child = this.context.mock(Node.class, "child");
		final Sequence seq = this.context.sequence("processChild_baseUrlIsPassed_getBaseURLReturnsNodeText");
		final String expectedValue = "http://example.com/baseURL";
		final URL expectedURL = new URL(expectedValue);
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("baseUrl"));
				inSequence(seq);
			oneOf(child).getTextContent();
				will(returnValue(expectedValue));
				inSequence(seq);
			oneOf(utils).tryCreateURL(expectedValue);
				will(returnValue(expectedURL));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedURL, tea.getBaseURL());
	}
	
	@Test
	public void processChild_imageIsPassed_getImageLocalURLReturnsNodeText() throws MalformedURLException {
		final Node child = this.context.mock(Node.class, "child");
		final Sequence seq = this.context.sequence("processChild_imageIsPassed_getImageLocalURLReturnsNodeText");
		final String expectedValue = "http://example.com/image";
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("image"));
				inSequence(seq);
			oneOf(child).getTextContent();
				will(returnValue(expectedValue));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedValue, tea.getImageLocalURL());
	}
	
	@Test
	public void processChild_licenseurlIsPassed_getLicenseLocalURLReturnsNodeText() throws MalformedURLException {
		final Node child = this.context.mock(Node.class, "child");
		final Sequence seq = this.context.sequence("processChild_licenseurlIsPassed_getLicenseLocalURLReturnsNodeText");
		final String expectedValue = "http://example.com/licenseurl";
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("licenseurl"));
				inSequence(seq);
			oneOf(child).getTextContent();
				will(returnValue(expectedValue));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedValue, tea.getLicenseLocalURL());
	}
	
	@Test
	public void processChild_tagsIsPassed_getTagsReturnsTagChildrenValues() throws MalformedURLException {
		final Node child = this.context.mock(Node.class, "child");
		final Node tag1 = this.context.mock(Node.class, "tag1");
		final Node tag2 = this.context.mock(Node.class, "tag2");
		final NodeList children = this.context.mock(NodeList.class, "children");
		final Sequence seq = this.context.sequence("processChild_tagsIsPassed_getTagsReturnsTagChildrenValues");
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		final String[] expectedTagValues = { "tag1", "tag2" };
		Set<String> expected = new HashSet<String>();
		expected.add(expectedTagValues[0]);
		expected.add(expectedTagValues[1]);
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("tags"));
				inSequence(seq);
			oneOf(child).getChildNodes();
				will(returnValue(children));
				inSequence(seq);
			oneOf(children).getLength();
				will(returnValue(2));
				inSequence(seq);
			oneOf(children).item(0);
				will(returnValue(tag1));
				inSequence(seq);
			oneOf(tag1).getNodeName();
				will(returnValue("tag"));
				inSequence(seq);
			oneOf(tag1).getTextContent();
				will(returnValue(expectedTagValues[0]));
				inSequence(seq);
			oneOf(children).item(1);
				will(returnValue(tag2));
				inSequence(seq);
			oneOf(tag2).getNodeName();
				will(returnValue("tag"));
				inSequence(seq);
			oneOf(tag2).getTextContent();
				will(returnValue(expectedTagValues[1]));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.testProcessChild(child, artistsList);
		
		Set<String> actualTags = tea.getTags();
		
		
		this.context.assertIsSatisfied();
		assertEquals(expected, actualTags);		
	}
	
	@Test
	public void processChild_tracksIsPassed_getTracksReturnsTagChildrenValues() throws MalformedURLException {
		final Node child = this.context.mock(Node.class, "child");
		final Node trackNode = this.context.mock(Node.class, "track1");
		final Track trackMock = this.context.mock(Track.class, "trackMock");
		final NodeList children = this.context.mock(NodeList.class, "children");
		final Sequence seq = this.context.sequence("processChild_tracksIsPassed_getTracksReturnsTagChildrenValues");
		@SuppressWarnings("unchecked")
		final List<String> artistsList = this.context.mock(List.class, "artistsList");
		SortedSet<Track> expected = new TreeSet<Track>();
		expected.add(trackMock);
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("tracks"));
				inSequence(seq);
			oneOf(child).getChildNodes();
				will(returnValue(children));
				inSequence(seq);
			oneOf(children).getLength();
				will(returnValue(1));
				inSequence(seq);
			oneOf(children).item(0);
				will(returnValue(trackNode));
				inSequence(seq);
			oneOf(trackNode).getNodeName();
				will(returnValue("track"));
				inSequence(seq);
		}});
		
		TestableExistAlbum tea = new TestableExistAlbum();
		tea.setTrackMock(trackMock);
		tea.testProcessChild(child, artistsList);
		
		SortedSet<Track> actualTracks = tea.getTracks();		
		
		this.context.assertIsSatisfied();	
		assertArrayEquals(expected.toArray(), actualTracks.toArray());
	}
}

/**
 * Exposes non-public membet
 * @author Dido
 *
 */
class TestableExistAlbum extends ExistAlbum {
	private Track trackMock;
	
	/**
	 * Parse an element
	 * @param album Element to parse
	 */
	public TestableExistAlbum(Node album) {
		super(album);
	}
	
	/**
	 * Expose the default constructor
	 */
	public TestableExistAlbum() {
		super();
	}
	
	/**
	 * Expose the processChild method
	 * @param child Element to parse
	 * @param artistsList List to fill if element is artist
	 */
	public void testProcessChild(Node child, List<String> artistsList) {
		this.processChild(child, artistsList);
	}
	
	/**
	 * Expose imageLocalURL field
	 * @return value of imageLocalURL field
	 */
	public String getImageLocalURL() {
		return this.imageLocalURL;
	}
	
	/**
	 * Expose licenseLocalURL field
	 * @return value of licenseLocalURL field
	 */
	public String getLicenseLocalURL() {
		return this.licenseLocalURL;
	}
	
	/**
	 * Expose baseURL field
	 * @param baseURL new value
	 */
	public void setBaseURL(URL baseURL) {
		this.baseURL = baseURL;
	}
	
	public void setTrackMock(Track trackMock) {
		this.trackMock = trackMock;
	}
	
	protected Track parseTrack(Node trackNode) {
		if (this.trackMock != null) {
			return this.trackMock;
		} else {
			return super.parseTrack(trackNode);
		}
	}
}
