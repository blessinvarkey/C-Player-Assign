package cs5031.catalogplayer.catalog;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyCatalog mycatalog = new MyCatalog();
		Main main = new Main();
		//main.run();
	}
	/*
	public void run() {
		
		MyCatalog mycatalog = new MyCatalog();
		Document result = mycatalog.search(query);
		if (result.has("results")) {
			for (NodeList tweets : result.findValues("results")) {
				for (NodelList tweet : tweets) {
					System.out.println("From: "
							+ tweet.findValue("from_user").asText());
					System.out.println("  " + tweet.findValue("text").asText());
				}
			}
			System.out.flush();
		}
	}*/
}
