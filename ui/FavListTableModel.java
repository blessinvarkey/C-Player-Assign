package cs5031.catalogplayer.ui;

import cs5031.catalogplayer.data.TrackInfo;

/**
 * SubClass of ItemTableModel for JTable in favorite view 
 * @author <110016203>,<110017972>,<110021201>
 *
 */

public class FavListTableModel extends ItemTableModel{
	private static final long serialVersionUID = -3484611801464404910L;
	
	/**
	 * Call the super constructor 
	 * @param columnNames column names that shows in the JTalbe
	 */
	public FavListTableModel(String[] columnNames) {
		super(columnNames);
	}
	
	/**
	 * Update the data when the favorite attribute 
	 */
	@Override
	public void fireTableCellUpdated(int row, int column){
		TrackInfo record = (TrackInfo) dataVector.get(row);
		if(!record.isFavourite()){
			dataVector.remove(record);
		}
		super.fireTableDataChanged();
	}

}
