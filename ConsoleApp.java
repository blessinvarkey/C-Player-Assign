package cs5031.catalogplayer;

import cs5031.catalogplayer.catalog.Album;
import cs5031.catalogplayer.catalog.Catalog;
import cs5031.catalogplayer.catalog.CatalogFactory;
import cs5031.catalogplayer.catalog.Query;
import cs5031.catalogplayer.catalog.Resource;
import cs5031.catalogplayer.catalog.SearchResult;
import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.catalog.exist.ExistCatalog;
import cs5031.catalogplayer.catalog.exist.ExistQuery;

/**
 * Console interface for the application
 * @author 
 *
 */
public class ConsoleApp {
	/** Name of the implementation */
	private static final String CATALOG_NAME = "eXist";
	
	/**
	 * Application entry point
	 * @param args CLI arguments - Ignored
	 */
	public static void main(String[] args) {		
		test();
	}
	
	/**
	 * Perform a series of tests and mark a test as successful if no exception is thrown; visual inspection
	 * of the console output should further verify if the program behaves as expected
	 */
	private static void test() {
		init();
		
		Object[][] testData = new Object[][] {
		//      query,  page_size,    page_number
				{ "//album", 1, null }	// this should pass
				, { "//album", 10, null } // this should pass
				, { "//album&_howmany=10", null, null } // this SHOULD fail => the code assumes this is xQuery or XPATH, not query string
				, { "//album[contains(title,\"Picture\")]", null, null } // this should pass
				, { "//track[contains(title,\"soldier\")]", null, null } // this should pass
					


		};
		
		for (Object[] row: testData) {
			try {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> Testing row " + rowToString(row));
				testQuery((String) row[0], (Integer) row[1], (Integer) row[2]);
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<< SUCCESS");
			} catch (Exception any) {
				any.printStackTrace();
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<< FAILED");
			}
		}
	}
	
	/**
	 * Create a fancy string for test run parameters. No input verification
	 * @param row Test run parameters (String, Integer, Integer)
	 * @return Pretty-print string
	 */
	private static String rowToString(Object[] row) {
		return String.format("query(%s) numResults(%s) pageNo(%s)", row[0], row[1], row[2]);
	}
	
	/**
	 * Execute a test run with the specified parameters
	 * @param query xQuery or XPATH query for the catalog database
	 * @param numResults Maximum number of results per page
	 * @param pageNo Number of the requested page (counting starts from 0)
	 */
	private static void testQuery(String query, Integer numResults, Integer pageNo) {
		Query q = createQuery(query, numResults, pageNo);
		Catalog cat = CatalogFactory.getCatalog(CATALOG_NAME);
		SearchResult result = cat.search(q);
		System.out.println(result);
		
		while (result.hasNextPage()) {
			result.getNextPage();
			System.out.println(result);
			for (Resource resource: result.getResources()) {
				System.out.println(resource);
				if (resource.getType() == Resource.TYPE_ALBUM && resource instanceof Album) {
					Album album = (Album) resource;
					for (Track track: album.getTracks()) {
						System.out.println("\t" + track);
					}
				}
			}			
		}
	}
	
	/**
	 * Create an instance of {@link cs5031.catalogplayer.catalog.Query} based on the input parameters
	 * @param query xQuery or XPATH query for the catalog database
	 * @param numResults Maximum number of results per page
	 * @param pageNo Number of the requested page (counting starts from 0)
	 * @return instance of {@link cs5031.catalogplayer.catalog.Query}
	 */
	private static Query createQuery(String query, Integer numResults, Integer pageNo) {
		if (numResults == null) {
			return new ExistQuery(query);
		}
		else {
			if (pageNo == null) {
				return new ExistQuery(query, numResults);
			}
			else {
				return new ExistQuery(query, numResults, pageNo);
			}
		}
	}
	
	/**
	 * Register the catalog implementation with the factory
	 */
	private static void init() {
		CatalogFactory.registerImplementation(CATALOG_NAME, ExistCatalog.class);
	}
}