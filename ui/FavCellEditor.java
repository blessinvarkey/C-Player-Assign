package cs5031.catalogplayer.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * Class that accept the actions for the adding and removing from favorite play list 
 *
 */
public class FavCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
	
	private static final long serialVersionUID = -5852948933982091616L;
	JButton favButton;
	JTable talbe;
	public FavCellEditor(JTable table){
		this.talbe = table;
		favButton = new JButton();
		favButton.addActionListener(this);
	}
	
	/**Return a JButton with the action command REMOVE_FAV/ADD_FAV depends on the value in the table 
	 * @param table The JTable in the view 
	 * @param value The value in the cell indicate that either the favorite of this song is true or  
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {				
		favButton.setActionCommand((Boolean)value ? "REMOVE_FAV":"ADD_FAV");		
		return favButton ;
	}
	
	/**Toggle the button to set the action command either be 
	 *  REMOVE_FAV or ADD_FAV
	 */	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand() == "ADD_FAV"){
			favButton.setActionCommand("REMOVE_FAV");
		} else if(arg0.getActionCommand() == "REMOVE_FAV"){
			favButton.setActionCommand("ADD_FAV");
		}
		System.out.println(arg0.getActionCommand());
		stopCellEditing();
	}
	
	
	/**
	 * Translate the action command to the value that need to be stored 
	 */
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		if(favButton.getActionCommand() == "REMOVE_FAV"){
			return true;
		} 
		return false;
	}

}
