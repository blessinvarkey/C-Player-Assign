package cs5031.catalogplayer.catalog.exist;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class that parses exist:result elements
 * @author <110017972>
 *
 */
public class ExistResultElement implements ResultElement {	
	/** first non-#text child node of exist:result */
	private Node root;
	/** value of exist:count attribute */
	private int count;
	/** value of exist:hits attribute */
	private int hits;
	/** value of exist:start attribute */
	private int start;
	/** all child elements that are not #text */
	private Node[] childNodes;
	
	/**
	 * Parse an exist:result node
	 * @param node Node whose name is exist:result
	 * @throws IllegalArgumentException IIf node is null or its node name is not equal to "exist:result"
	 */
	public ExistResultElement(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("node is null");
		}
		if (!"exist:result".equals(node.getNodeName())) {
			throw new IllegalArgumentException("node's node name is not \"exist:result\"");
		}
		
		NamedNodeMap attribs = node.getAttributes();
		
		this.count = Utils.getAttrValAsInt(attribs, "exist:count", 0);
		this.hits = Utils.getAttrValAsInt(attribs, "exist:hits", 0);
		this.start = Utils.getAttrValAsInt(attribs, "exist:start", 1);
		
		// "root" is the first non-#text node
		NodeList children = node.getChildNodes();
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		int countOfChildren = children.getLength();
		for (int i = 0; i < countOfChildren; ++i) {
			Node child = children.item(i);
			String childName = child.getNodeName();
			if (!"#text".equals(childName)) {
				if (this.root == null) {
					this.root = child;
				}
				listOfNodes.add(child);
			}
		}
		
		this.childNodes = new Node[listOfNodes.size()];
		listOfNodes.toArray(childNodes);
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.ResultElement#getCount()
	 */
	@Override
	public int getCount() {
		return this.count;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.ResultElement#getHits()
	 */
	@Override
	public int getHits() {
		return this.hits;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.ResultElement#getStart()
	 */
	@Override
	public int getStart() {
		return this.start;
	}	
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.ResultElement#getRoot()
	 */
	@Override
	public Node getRoot() {
		return this.root;
	}
	
	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.ResultElement#getChildNodes()
	 */
	@Override
	public Node[] getChildNodes() {
		return this.childNodes;
	}
	
	@Override
	public String toString() {
		String rootName = this.root != null ? this.root.getNodeName() : "::nothing::";
		return String.format("[exist:result wrapping %s, start=%d, count=%d, hits=%d]"
				, rootName, this.start, this.count, this.hits);
	}
}
