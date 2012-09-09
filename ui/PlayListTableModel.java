package cs5031.catalogplayer.ui;
/**
 * SubClass of ItemTableModel that handle the data of PlayListTable 
 * @author <110016203>,<110017972>,<110021201>
 *
 */
public class PlayListTableModel extends ItemTableModel {
	private static final long serialVersionUID = 5804357066951763068L;

	/**
	 * Call super class constructor 
	 * @param columnNames The column names shown at the header of the table 
	 */
	public PlayListTableModel(String[] columnNames) {
		super(columnNames);
	}

}
