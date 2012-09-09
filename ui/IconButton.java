package cs5031.catalogplayer.ui;

import java.awt.MediaTracker;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * The JButton with Icon
 * @author <110017972>
 *
 */
public class IconButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	private String iconPath;
	
	/**
	 * Set the path of the icon and the text of the button  
	 * @param path the path of the icon
	 * @param text the text of the button
	 */
	public IconButton(String path, String text) {
		this.setIcon(path, text);		
	}

	/**
	 * Get the icon path 
	 * @return the path of the icon
	 */
	public String getIconPath() {
		return this.iconPath;
	}
	
	/**
	 * Set the icon and tool tip text to the button
	 * @param path the path of the icon
	 * @param text the text of the tool tip
	 */
	public void setIcon(String path, String text) {
		ImageIcon icon = this.loadIcon(path, text);
		if (icon != null && icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			this.setIcon(icon);
			this.iconPath = path;
			this.setToolTipText(text);
		}
		else {
			this.setText(text);
			this.iconPath = null;
		}
		this.buildLayout();
	}
	
	/**
	 * setup the layout
	 */
	protected void buildLayout() {
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	/**
	 * load the Icon 
	 * @param path the path of the icon
	 * @param description the description of the button
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
