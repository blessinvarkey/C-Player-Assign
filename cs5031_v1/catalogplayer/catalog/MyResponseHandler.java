package cs5031.catalogplayer.catalog;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class MyResponseHandler implements ResponseHandler<Document>{
	
	public Document handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		// first check the status of the request and proceed only if it is OK
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			System.err.println(response.getStatusLine().getReasonPhrase());
			return null;
		}

		// get the entity returned and invoke the parsing mechanism
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {
				return parseResponseEntity(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	private Document parseResponseEntity(HttpEntity entity)
			throws IllegalStateException, IOException, ParserConfigurationException, SAXException {
		
		InputStream is = null;
		Document doc = null;
		//ObjectMapper m = new ObjectMapper();
		try
	    {
	      // Create the XML Document
		  is = entity.getContent();
	      DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	      doc = docBuilder.parse(is);
	      }
		 finally {
			if (is != null)
				is.close();
		}
		return doc;
		
	}


}
