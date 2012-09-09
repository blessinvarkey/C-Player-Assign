package cs5031.catalogplayer.catalog;

import java.util.HashMap;
import java.util.Set;

/**
 * A factory for Catalog implementations. Implementations are registered with the
 * factory and instances are constructed using {@link java.lang.Class#newInstance()},
 * so the implementation needs to have a simple constructor.
 * 
 * @author avoss
 *
 */
public class CatalogFactory {
	
	/**
	 * A map of implementations registered with the factory. This can be used to 
	 * add new kinds of catalogs at runtime, provided suitable implementation
	 * classes are available.
	 */
	private static HashMap<String,Class<? extends Catalog>> implementations = new HashMap<String,Class<? extends Catalog>>();

	/**
	 * Register an implementation with the factory under the given id. The {@link java.lang.Class} provided
	 * needs to have a simple constructor, i.e., one without parameters.
	 *   
	 * @param id
	 * @param clazz
	 */
	public static void registerImplementation(String id, Class<? extends Catalog> implementation) {
		implementations.put(id, implementation);
	}		
	
	/**
	 * Return the ids under which the existing {@link Catalog} implementations are registered.
	 * 
	 * @return a {@link java.util.Set} of ids
	 */
	public static Set<String> listImplementations() {
		return implementations.keySet();
	}
	
	/**
	 * Return an instance of the {@link Catalog} implementation registered under the given id.
	 *  
	 * @param id
	 * @return a new instance of the implementation 
	 */
	public static Catalog getCatalog(String id) {
		Class<?> clazz = implementations.get(id);
		if(clazz == null) return null;
		Catalog catalog = null;
		try {
			catalog = (Catalog)clazz.newInstance();
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		} 
		return catalog;
	}
}
