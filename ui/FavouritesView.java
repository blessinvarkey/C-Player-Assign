package cs5031.catalogplayer.ui;
/**
 * SubClass of PlaylistView showing the favourite play list 
 * @author <110016203>,<110017972>,<110021201>
 *
 */
public class FavouritesView extends PlaylistView {	
	private static final long serialVersionUID = 9218492848663928317L;

	/**
	 * Constructor for the favourite view
	 */
	public FavouritesView() {
		super();
		tableModel = new FavListTableModel(columnNames);
	}

	/**
	 * Update the favourite table model
	 */
	public void updateFavourite(){
		((ItemTableModel) table.getModel()).setData(this.getFavourites());		
	}	
}