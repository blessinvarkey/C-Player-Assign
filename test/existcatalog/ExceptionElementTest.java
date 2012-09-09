package cs5031.catalogplayer.test.existcatalog;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cs5031.catalogplayer.catalog.exist.ExceptionElement;
import cs5031.catalogplayer.test.TestHelper;

/**
 * Unit tests for ExceptionElement
 * 
 */
public class ExceptionElementTest {
	private Mockery context;
	
	@Before
	public void setup()  {
		this.context = new Mockery();		
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_callWithNull_ExceptionIsThrown() {
		new ExceptionElement(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_callWithWrongElementName_ExceptionIsThrown() {
		final Node root = this.context.mock(Node.class);
		
		this.context.checking(new Expectations() {{
			oneOf(root).getNodeName();
				will(returnValue("wrong name"));
		}});
		
		new ExceptionElement(root);
		
		// should never be reached, but here for completeness
		this.context.assertIsSatisfied();
	}
	
	@Test
	public void constructor_rootWithChildren_AllNodesAreExtracted() {
		final Node root = this.context.mock(Node.class, "root");
		final Node onlyChild = this.context.mock(Node.class, "onlyChild");
		final NodeList children = this.context.mock(NodeList.class);
		
		this.context.checking(new Expectations() {{
			oneOf(root).getNodeName();
				will(returnValue("exception"));
			oneOf(root).getChildNodes();
				will(returnValue(children));
			oneOf(children).getLength();
				will(returnValue(1));
			oneOf(children).item(0);
				will(returnValue(onlyChild));
			ignoring(onlyChild);			
		}});
		
		new ExceptionElement(root);
		
		this.context.assertIsSatisfied();
	}
	
	@Test(expected=NullPointerException.class)
	public void parseChild_argumentIsNull_ExceptionIsThrown() {
	    TestableExceptionElement ee = new TestableExceptionElement();
		ee.testParseChild(null);		
	}
	
	@Test
	public void parseChild_childIsPath_getPathReturnsValueOfElement() {
		TestableExceptionElement ee = new TestableExceptionElement();
		final Node child = this.context.mock(Node.class, "child");
		final String childText = "Expected value";
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("path"));
			oneOf(child).getTextContent();
				will(returnValue(childText));			
		}});
		
		ee.testParseChild(child);
		
		this.context.assertIsSatisfied();
		assertEquals(childText, ee.getPath());
	}
	
	@Test
	public void parseChild_childIsMessage_getMessageReturnsValueOfElement() {
		TestableExceptionElement ee = new TestableExceptionElement();
		final Node child = this.context.mock(Node.class, "child");
		final String childText = "Expected value";
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("message"));
			oneOf(child).getTextContent();
				will(returnValue(childText));			
		}});
		
		ee.testParseChild(child);
		
		this.context.assertIsSatisfied();
		assertEquals(childText, ee.getMessage());
	}
	
	@Test
	public void parseChild_childIsQuery_getQueryReturnsValueOfElement() {
		TestableExceptionElement ee = new TestableExceptionElement();
		final Node child = this.context.mock(Node.class, "child");
		final String childText = "Expected value";
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("query"));
			oneOf(child).getTextContent();
				will(returnValue(childText));			
		}});
		
		ee.testParseChild(child);
		
		this.context.assertIsSatisfied();
		assertEquals(childText, ee.getQuery());
	}
	
	@Test
	public void parseChild_childIsUnknown_fieldsAreNotModified() {		
		final Node child = this.context.mock(Node.class, "child");
		final String initialMessage = "Expected message";
		final String initialPath = "expected path";
		final String initialQuery = "expected query";
		TestableExceptionElement ee = new TestableExceptionElement(initialMessage, initialPath, initialQuery);
		
		this.context.checking(new Expectations() {{
			oneOf(child).getNodeName();
				will(returnValue("UnknownElementName"));
			oneOf(child).getTextContent();
				will(returnValue("Something random"));			
		}});
		
		ee.testParseChild(child);
		
		this.context.assertIsSatisfied();
		assertEquals(initialMessage, ee.getMessage());
		assertEquals(initialPath, ee.getPath());
		assertEquals(initialQuery, ee.getQuery());
	}

}

/**
 * Exposes non-public members of ExceptionElement, so that they can be tested
 * @author Dido
 *
 */
class TestableExceptionElement extends ExceptionElement {
	/**
	 * Expose default constructor
	 */
	public TestableExceptionElement() {
		super();
	}
	
	/**
	 * Expose constructor for setting initial values of fields
	 * @param message initial value for the message field
	 * @param path initial value for the path field
	 * @param query initial value for the query field
	 */
	public TestableExceptionElement(String message, String path, String query) {
		super(message,path,query);
	}
	
	/**
	 * Expose parseChild
	 * @param child Node to parse
	 */
	public void testParseChild(Node child) {
		this.parseChild(child);
	}
}
