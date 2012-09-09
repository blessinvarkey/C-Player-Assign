package cs5031.catalogplayer.catalog.exist;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Default implementation of DocumentBuilderWrapper
 * @author Dido
 *
 */
public class DefaultDocumentBuilderWrapper implements DocumentBuilderWrapper {
	/**
	 * Adapted builder
	 */
	private DocumentBuilder builder;
	
	/**
	 * Constructs a new adapter
	 * @param builder DocumentBuilder to wrap
	 */
	public DefaultDocumentBuilderWrapper(DocumentBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Document parse(InputStream input) throws IllegalStateException,
			IOException, SAXException {
		return this.builder.parse(input);
	}

}
