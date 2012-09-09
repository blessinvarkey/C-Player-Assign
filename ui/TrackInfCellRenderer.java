package cs5031.catalogplayer.ui;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableCellRenderer;

import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;
/**
 * The custom renderer for the track information, 
 * render the boolean favorite as a JToggleButton,
 * render the track information as a String,
 *    
 * @author <110016203>
 *
 */

public class TrackInfCellRenderer extends DefaultTableCellRenderer   {
	private static final long serialVersionUID = 493159827354744461L;
	
	/**
	 * Return the rendered component with given value 
	 */
	public Component getTableCellRendererComponent(JTable table,
	        Object value, boolean isSelected, boolean hasFocus, int row,
	        int column){		
		
		/**
		 * return a toogleButton if the value is about Boolean value of favourite 
		 */
		if (value instanceof Boolean){				
			JToggleButton button = new IconToggleButton("data/bookmark.png","fav");
			button.setPreferredSize(new Dimension(22,22));
			button.setSelected((Boolean) value);
			return button;
		}
		
		/**
		 * return a label if the value is about Track
		 */
		if (value instanceof Track) {			
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, ((Track)value).getTitle(),
					isSelected,hasFocus,row, column);	
			return label;
		}
		
		/**
		 * return a lable if the value is about TrackInfor
		 */
		if (value instanceof TrackInfo) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, ((TrackInfo)value).getTrack().getTitle(),
					isSelected,hasFocus,row, column);	
			return label;
		}
		return null;		
	}
	
	

}
