package cs5031.catalogplayer.test.existcatalog;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xalan.internal.xsltc.dom.AnyNodeCounter;



import cs5031.catalogplayer.catalog.exist.ExistResultElement;
import cs5031.catalogplayer.catalog.exist.ResultElement;
import cs5031.catalogplayer.catalog.exist.UtilityMethods;
import cs5031.catalogplayer.catalog.exist.Utils;

public class ExistResultElementTest {
	private Mockery context;
	private UtilityMethods utils;

	@Before
	public void setUp() throws Exception {
		this.context = new Mockery();
		this.utils = this.context.mock(UtilityMethods.class);
		Utils.setImplementation(this.utils);
	}

	@After
	public void cleanUp() {
		Utils.setDefaultImplementation();
	}
	
	@Test
	public void constructor_validNode_ParsedCorrectly() {
		final Node node = this.context.mock(Node.class, "node");
		final Node child = this.context.mock(Node.class, "child");
		final NamedNodeMap attribs = this.context.mock(NamedNodeMap.class, "attribs");
		final NodeList children = this.context.mock(NodeList.class, "children");
		final Sequence seq = this.context.sequence("seq");
		final int expectedCount = 4;
		final int expectedHits = 3;
		final int expectedStart = 2;
		final String expectedChildName = "some name";
		
		this.context.checking(new Expectations() {{
			one(node).getNodeName();
				will(returnValue("exist:result"));
				inSequence(seq);
			one(node).getAttributes();
				will(returnValue(attribs));
				inSequence(seq);
			one(utils).getAttrValAsInt(attribs, "exist:count", 0);
				will(returnValue(expectedCount));
				inSequence(seq);
			one(utils).getAttrValAsInt(attribs, "exist:hits", 0);
				will(returnValue(expectedHits));
				inSequence(seq);
			one(utils).getAttrValAsInt(attribs, "exist:start", 1);
				will(returnValue(expectedStart));
				inSequence(seq);
			one(node).getChildNodes();
				will(returnValue(children));
				inSequence(seq);
			one(children).getLength();
				will(returnValue(1));
				inSequence(seq);
			one(children).item(0);
				will(returnValue(child));
				inSequence(seq);
			one(child).getNodeName();
				will(returnValue(expectedChildName));
				inSequence(seq);
		}});
		
		ResultElement ere = new ExistResultElement(node);
		
		this.context.assertIsSatisfied();
		assertEquals(expectedCount, ere.getCount());
		assertEquals(expectedHits, ere.getHits());
		assertEquals(expectedStart, ere.getStart());
		assertArrayEquals(new Node[] { child }, ere.getChildNodes());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_nullparam_exceptionThrown() {
		new ExistResultElement(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_nodeNotExistResult_exceptionThrown() {
		final Node node = this.context.mock(Node.class, "node");
		this.context.checking(new Expectations() {{
			one(node).getNodeName();
				will(returnValue("some random value"));
		}});
		new ExistResultElement(node);
	}

}
