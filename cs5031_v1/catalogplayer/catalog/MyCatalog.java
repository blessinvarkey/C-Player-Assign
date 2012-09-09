package cs5031.catalogplayer.catalog;

import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;

public class MyCatalog implements Catalog{
	private HttpClient client = null;
	String baseUrl = "http://138.251.198.119:8080/exist/rest/db/catalog";
	String query = "?_query=//album&_howmany=10";
	Document result = null;
		
	/**
	 * @param client
	 */
	MyCatalog() {
		this.client = new DefaultHttpClient();
		connect ();
	}
			
	public void connect(){
		HttpGet get = new HttpGet("http://138.251.198.119:8080/exist/rest/db/catalog");
		System.out.println("assel is here");
		try{
		HttpResponse response = client.execute(get);
		System.out.println(response);
		response.getEntity();
		}
		catch(ClientProtocolException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public Document search(Query query) {  //public SearchResult
		
		// the response handler that will parse the JSON code
				ResponseHandler<Document> resHandler = new MyResponseHandler();					
								
				// try to execute th request
				try {
					HttpGet get = new HttpGet(new URI(baseUrl+query+"&count=20"));
					result = client.execute(get, resHandler);
				} catch (Exception e) {
					// Ok, so there are all sorts of things that can go wrong here. In production
					// code you would probably want to catch and handle each possible Exception separately
					// but here we assume the only thing that can be done is to print the stack
					// trace and end the program (another thing you would not do in production code).
					e.printStackTrace();
					System.exit(1);
				}	
				return result;
		}
		
	//access server
	//get request
}
