/**
 * 
 */
package cs5031.catalogplayer.catalog.exist;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Default implementation of @see cs5031.catalogplayer.catalog.exist.UtilityMethods
 * @author Dido
 *
 */
public class DefaultUtilityMethods implements UtilityMethods {

	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.UtilityMethods#getAttrValAsInt(org.w3c.dom.NamedNodeMap, java.lang.String, int)
	 */
	@Override
	public int getAttrValAsInt(NamedNodeMap attribs, String name, int defaultVal) {
		Node attr = attribs.getNamedItem(name);
		int result = defaultVal;
		if (attr != null) {
			String countTxt = attr.getNodeValue();
			try {
				result = Integer.parseInt(countTxt);
			}
			catch (NumberFormatException e) {
				// ignore
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.UtilityMethods#getAttrValAsString(org.w3c.dom.NamedNodeMap, java.lang.String, java.lang.String)
	 */
	@Override
	public String getAttrValAsString(NamedNodeMap attribs, String name,
			String defaultVal) {
		Node attr = attribs.getNamedItem(name);
		String result = defaultVal;
		if (attr != null) {
			result = attr.getNodeValue();			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.UtilityMethods#buildURL(java.lang.String, java.lang.String)
	 */
	@Override
	public URL buildURL(String base, String local) throws MalformedURLException {
		base = base.trim();
		local = local.trim();
		StringBuilder url = new StringBuilder(base);
		if (!base.endsWith("/")) {
			url.append('/');
		}
		url.append(local);
		return new URL(url.toString());		
	}

	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.UtilityMethods#tryBuildURL(java.lang.String, java.lang.String)
	 */
	@Override
	public URL tryBuildURL(String base, String local) {
		try {
			return buildURL(base, local);
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see cs5031.catalogplayer.catalog.exist.UtilityMethods#tryCreateURL(java.lang.String)
	 */
	@Override
	public URL tryCreateURL(String fromString) {
		try {
			return new URL(fromString);
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public GetUriBuilder createUriBuilder() {
		return new ExistGetURI();
	}

	@Override
	public ExistException createException(Node fromNode) {
		ExceptionElement exData = new ExceptionElement(fromNode);
		throw new ExistException(exData.getMessage());
	}

	@Override
	public ResultElement createResultElement(Node fromNode) {
		return new ExistResultElement(fromNode);
	}

	@Override
	public Document executeRequest(URI uri, DocumentBuilderWrapper db) {
		HttpGet request = new HttpGet(uri);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			throw new ExistException(e);
		} catch (IOException e) {
			throw new ExistException(e);
		}
		int status = response.getStatusLine().getStatusCode();		
		if (status != 200) {
			String queryString = request.getURI().getQuery();			
			String reason = response.getStatusLine().getReasonPhrase();
			if (reason != null) {
				reason = reason.replace('+', ' ');
			}
			String message = null;
			if (reason != null) {
				message = String.format("%s (%s returned HTTP status code %d)", reason, queryString, status);
			}
			else {
				message = String.format("%s returned HTTP status code %d", queryString, status);
			}
			throw new ExistException(message);
		}
		InputStream data = null;
		try {
			data = response.getEntity().getContent();
			return db.parse(data);											
		} catch (IllegalStateException e) {
			throw new ExistException(e);
		} catch (IOException e) {
			throw new ExistException(e);
		} catch (SAXException e) {
			throw new ExistException(e);
		}
		finally {
			if (data != null) {
				try {
					data.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

}
