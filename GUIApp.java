package cs5031.catalogplayer;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cs5031.catalogplayer.catalog.CatalogFactory;
import cs5031.catalogplayer.catalog.exist.ExistCatalog;
import cs5031.catalogplayer.ui.PlayerWindow;


public class GUIApp {
	public static final String CATALOG_NAME = "eXist";
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				CatalogFactory.registerImplementation(CATALOG_NAME, ExistCatalog.class);
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}				
				
				PlayerWindow playerWnd = new PlayerWindow();
				playerWnd.setupLayout();
				playerWnd.setVisible(true);
			}						
		});
	}

}