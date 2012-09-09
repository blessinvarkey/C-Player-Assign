package cs5031.catalogplayer.ui;

import java.awt.MediaTracker;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
/**
 * Create a IconToggleButton with ImageIcon

 *
 */
public class IconToggleButton extends JToggleButton {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the button with path and text
	 * @param path the path of the icon
	 * @param text the text of the tooltips
	 */
	public IconToggleButton(String path, String text) {
		ImageIcon icon = this.loadIcon(path, text);
		if (icon != null && icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			this.setIcon(icon);
			this.setToolTipText(text);
		}
		else {
			this.setText(text);
		}
		this.buildLayout();
		
	}
	
	/**
	 * Setup and layout
	 */
	protected void buildLayout() {
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	/**
	 * set the icon to the button
	 * @param path the path of the IconImage
	 * @param description the description of the image
	 * @return
	 */
	protected ImageIcon loadIcon(String path, String description) {
		File basePath = new File(".");
		File localPath = new File(basePath, path);
		try {
			return new ImageIcon(localPath.toURI().toURL(), description);
		} catch (MalformedURLException e) {
			return null;
		}
	}

}
