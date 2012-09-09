package cs5031.catalogplayer.ui;

import java.util.Collections;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import cs5031.catalogplayer.data.TrackInfo;

/**
 * This is the base class for the TableModel showing in the JTable, it handle the data in the JTable 
 * Each data element in the ItemTableModel is an instance of TrackInfo 
 * @author <110016203>,<110017972>,<110021201>
 *
 */

public abstract class ItemTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final int FAV_INDEX = 0;
    public static final int TRACKINF_INDEX = 1;
    public static final int TIMESPLAYED_INDEX = 2;   
	protected String[] columnNames;
    protected Vector<TrackInfo> dataVector;   
    
    
    /**
     * Constructor for the TableModel, it set the column names which shows in JTable header
     * @param columnNames
     */
    public ItemTableModel(String[] columnNames) {    
        this.columnNames = columnNames;
        dataVector = new Vector<TrackInfo>();
    }

    /**
     * Get the columnNames of the JTable 
     */
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    
    /**
     * Add a new row to the table
     * @param data A new instance of TrackInfo
     */
    public void addRow(TrackInfo data){
    	if(!dataVector.contains(data)){
    		dataVector.add(data);
    	}    	    
    	this.fireTableDataChanged();
    }
    
    /**
     * Replace the new data vector with the old one in TableModel 
     * @param newData new Vector which hold the list of TrackInfo
     */
    public void setData(Vector<TrackInfo> newData){
    	dataVector = newData;
    	this.fireTableDataChanged();
    }
    
    
    /**
     * Get read-only data in the TableModel as read-only format
     * @return Array of TrackInfo
     */
    public TrackInfo[] getConstData() {
    	TrackInfo[] constData = new TrackInfo[this.dataVector.size()];
    	this.dataVector.toArray(constData);
    	return constData;
    }
    
    /**
     * Empty the data vector
     */
    public void clear(){
    	dataVector.clear();
    }
    
    /**
     * Remove the selected rows in the table
     * @param selecRows Array of indexes of selected rows
     */
    public void removeRows(int[] selecRows){
    	Object[] itemsToRemove = new Object[selecRows.length];
    	for (int i = 0; i < selecRows.length; i++) {
    		itemsToRemove[i] = this.dataVector.get(selecRows[i]);
    	}
    	for (int i = 0; i < itemsToRemove.length; i++) {
    		this.dataVector.remove(itemsToRemove[i]);
    	}    	
    	this.fireTableDataChanged();
    }
    
    /**
     * Return if the cell can be edited, only the favourite column can be edited
     */
    public boolean isCellEditable(int row, int column) {
        if (column == FAV_INDEX) return true;
        else return false;
    }
    
    /**
     * Get the value of the specific row and column
     * @return Return Boolean if it is first column, TrackInfo if it is second column, Integer if it is third column
     */
    public Object getValueAt(int row, int column) {
        TrackInfo record = (TrackInfo)dataVector.get(row);
        switch (column) {
            case FAV_INDEX:
               return record.isFavourite();               
            case TRACKINF_INDEX:
               return record;             
            case TIMESPLAYED_INDEX:
               return record.getTimesPlayed();               
            default:
               return record;
        }
    }   
    
    /**
     * Set the value to the specific row and column, only accept the first column value
     * @param value The value need to set 
     * @param row  The specific row
     * @param column  The specific column 
     */
	public void setValueAt(Object value, int row, int column){
		TrackInfo record = (TrackInfo) dataVector.get(row);
		switch (column) {
		case FAV_INDEX:
			record.setIsFavourite((Boolean) value);
			break;
		default:
			System.out.println("invalid index");
		}
		this.fireTableCellUpdated(row, column);
	}
	
	/**
	 * Get the length of current list
	 * @return length as an integer
	 */
	public int getRowCount() {
        return dataVector.size();
    }
	
	/**
	 * Get the length of the columns 
	 * @return the length of the columns
	 */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Sort the data in the TableModel by one of the column
     * @param colum The column need to be sorted, only the track column and timesplayed column can be sorted  
     */
    public void sortBy(int colum){
    	if(colum == TRACKINF_INDEX){
    		Collections.sort(dataVector, new TrackComparator());
    	} else if (colum == TIMESPLAYED_INDEX){
    		Collections.sort(dataVector, new PlayedTimeComparator());
    	}
    }

    
}