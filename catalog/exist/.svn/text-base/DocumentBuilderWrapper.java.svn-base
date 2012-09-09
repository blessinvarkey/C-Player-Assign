package cs5031.catalogplayer.catalog.exist;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Wraps methods used in DocumentBuilder so that classes that need
 * DocumentBuilder will be able to mock invocations to DocumentBuilder's methods
 * @author Dido
 *
 */
public interface DocumentBuilderWrapper {
	/**
     * Parse the content of the given <code>InputStream</code> as an XML
     * document and return a new DOM {@link Document} object.
     * An <code>IllegalArgumentException</code> is thrown if the
     * <code>InputStream</code> is null.
     *
     * @param is InputStream containing the content to be parsed.
     *
     * @return <code>Document</code> result of parsing the
     *  <code>InputStream</code>
     *
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
     * @throws IllegalArgumentException When <code>is</code> is <code>null</code>
     *
     * @see org.xml.sax.DocumentHandler
     */
	Document parse(InputStream input) 
		throws IllegalStateException, IOException, SAXException;
}
