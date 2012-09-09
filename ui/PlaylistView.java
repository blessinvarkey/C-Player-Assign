package cs5031.catalogplayer.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.io.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;
import cs5031.catalogplayer.ui.events.PlayTrackEvent;
import cs5031.catalogplayer.ui.events.PlayTrackListener;
import cs5031.catalogplayer.ui.events.PlayTrackSource;
import cs5031.catalogplayer.ui.events.TrackSelectedSource;
/**
 *  The base class for the play list view, it has some common methods which can be used both in 
 *  favourite view and tracks view  
 * @author <110016203>,<110017972>,<110021201>
 *
 */

public abstract class PlaylistView extends JPanel implements PlayTrackListener {
	private static final long serialVersionUID = 8678715036338646447L;
	public static final String[] columnNames = {
        "Fav", "Track", "PlayedTimes"
    };	
	public static final File STATS_FILE; 
	
	public final PlayTrackSource playTrack;
	public final TrackSelectedSource trackSelected;
	
	protected JScrollPane content;
	protected JTable table;
	protected ItemTableModel tableModel;
	private static HashMap<Track, TrackInfo> trackInfoMap;
	private static String lastSavedPlaylistName = "";
	
	private List<ActionListener> listeners;
	
	//Default home directory 
	static {		
		File homeDir = new File(System.getProperty("user.home"));
		STATS_FILE = new File(homeDir, "fooplayer.stats");
		loadDefaultStatistics();
	}
	
	/**
	 * Basic constructor
	 */
	public PlaylistView() {
		this.playTrack = new PlayTrackSource(this);
		this.trackSelected = new TrackSelectedSource(this);
		this.listeners = new LinkedList<ActionListener>();		
	}
	
	/**
	 * Set up and layout the content
	 */
	public void setupLayout(){
		content = new JScrollPane();
		table = new JTable();		
		table.setModel(tableModel);
		table.setRowHeight(30);
		table.setRowSelectionAllowed(true);
		table.setShowVerticalLines(false);		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				handleTalbeRowSelected(e);				
			}
		});
		
		JTableHeader header = table.getTableHeader();
	    header.setUpdateTableInRealTime(true);
	    header.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		handleMouseClicked(e);
	    	}
		});
	    header.setReorderingAllowed(true);
	    
	    TableColumnModel colModel = table.getColumnModel();	    
		colModel.getColumn(0).setCellEditor(new FavCellEditor(table));
		colModel.getColumn(0).setCellRenderer(new TrackInfCellRenderer());
		colModel.getColumn(0).setMaxWidth(30);

		colModel.getColumn(1).setCellRenderer(new TrackInfCellRenderer());
		table.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		handleMouseClicked(e);
	    	}
		});	
		
		content = new JScrollPane(table);			
		this.setLayout(new BorderLayout());
		this.add(content,BorderLayout.CENTER);			
	}
	
	/**
	 * Fire the event when any row is selected from the table
	 * @param e ListSelection event
	 */
	protected void handleTalbeRowSelected(ListSelectionEvent e) {
		int[] rows = this.table.getSelectedRows();
		if (rows.length > 0) {
			this.trackSelected.fireEvent(this.getRowInfo(rows[0]));
		}
	}
	
	/**
	 * Get trackinfo which stored in the hashmap given by the track,
	 * search the hashmap which store the track information, if there is no such trackinfo, then just create a new one  
	 * @param forTrack The given track
	 * @return Trackinfo which hold the information about the track
	 */
	protected TrackInfo getTrackInfo(Track forTrack) {
		TrackInfo info = trackInfoMap.get(forTrack);
		if(info == null){
			info = new TrackInfo(forTrack);
			trackInfoMap.put(forTrack, info);
		} 
		return info;
	}
	
	/**
	 * To ensure any given track information is in the hashmap 
	 * @param info given track information
	 */
	protected void ensureHasStats(TrackInfo info) {
		if (!trackInfoMap.containsKey(info.getTrack())) {
			trackInfoMap.put(info.getTrack(), info);
		}
	}
	
	/**
	 * To ensure any track has a track information in the hashmap, otherwise just create a new one  
	 * @param track given track
	 */
	protected void ensureHasStats(Track track) {
		if (!trackInfoMap.containsKey(track)) {
			trackInfoMap.put(track, new TrackInfo(track));
		}
	}
	
	/**
	 * Get the list of track information which the track is favorite to the user 
	 * @return Vector of track information
	 */
	protected Vector<TrackInfo> getFavourites() {
		Vector<TrackInfo> favourites = new Vector<TrackInfo>();
		for (TrackInfo info: trackInfoMap.values()) {
			if (info.isFavourite()) {
				favourites.add(info);
			}
		}
		return favourites;
	}
	
	/**
	 * Add action listener to the list of listeners 
	 * @param listener new Action listener
	 */
	public void addActionListener(ActionListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Remove action listener from the list 
	 * @param listener given listener
	 * @return if the operation is successful
	 */
	public boolean removeActionListener(ActionListener listener) {
		return this.listeners.remove(listener);
		
	}	
	
	/**
	 * notify all the listener the action is occurred 
	 * @param event the action event 
	 */
	protected void notifyActionPeformed(ActionEvent event) {
		for (ActionListener listener: this.listeners) {
			listener.actionPerformed(event);
		}
	}
	
	/**
	 * get the track information with the given row which is the index number of the list 
	 * @param row given row index
	 * @return track information
	 */
	public TrackInfo getRowInfo(int row) {
		ItemTableModel tableModel = (ItemTableModel) table.getModel();
		TrackInfo track = (TrackInfo) tableModel.getValueAt(row, 1);		
		return track;
	}
	
	/**
	 * Get which column the mouse just clicked 
	 * @param event the mouse click event
	 * @return the index of the column which the mouse just click on 
	 */
	protected int getColumNum(MouseEvent event) {
		TableColumnModel columModel = table.getColumnModel();
		int position = event.getX();
		int colum = columModel.getColumnIndexAtX(position);
		return colum;
	}
	
	/**
	 * Handle the mouse click event which will sort the list of tracks or fire the play track event  
	 * @param arg0 mouse click event 
	 */
	public void handleMouseClicked(MouseEvent arg0) {		
		//Single click action
		if (arg0.getClickCount() == 1){
			if(arg0.getSource() instanceof JTableHeader){
				int colum = getColumNum(arg0);			
				((ItemTableModel) table.getModel()).sortBy(colum);								
			}
			arg0.consume();
		}
		//Double click action
		else if (arg0.getClickCount() == 2) {
			if (arg0.getSource() instanceof JTable) {
				JTable sltTable = (JTable) arg0.getSource();
				int row = sltTable.getSelectedRow();		
				this.playTrack.fireEvent(getRowInfo(row));
			}
			arg0.consume();
		}
	}
	
	/**
	 * Load the default state of the player window from default file, update the track information hashmap
	 */
	public static void loadDefaultStatistics() {
		try {
			loadStatistics(STATS_FILE);
		}
		catch (Exception e) {
			trackInfoMap = new HashMap<Track, TrackInfo>();
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the default state of player window to the default file 
	 */
	public static void saveDefaultStatistics() {
		try {
			saveStatistics(STATS_FILE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save the state to the specific file  
	 * @param fileName the name of the file 
	 * @throws FileNotFoundException the Exception thrown when the file not found 
	 * @throws IOException input and output exception during the process
	 */
	public static void saveStatistics(File fileName) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
		Collection<TrackInfo> stats = trackInfoMap.values();
		TrackInfo[] arr = new TrackInfo[stats.size()];
		stats.toArray(arr);
		oos.writeObject(arr);
		oos.writeObject(lastSavedPlaylistName == null ? "" : lastSavedPlaylistName);
		oos.close();
	}
	
	/**
	 * Save the the last saved play list name to the local static variable  
	 * @param val name of the play list 
	 */
	public static void setLastSavedPlaylistName(String val) {
		lastSavedPlaylistName = val;
	}
	
	/**
	 * Return the last saved play list name
	 * @return the name of the last saved play list name
	 */
	public static String getLastSavedPlaylistName() {
		return lastSavedPlaylistName;
	}
	
	/**
	 * Load the state from a given file 
	 * @param fileName The specific file name
	 * @throws FileNotFoundException No file found exception
	 * @throws IOException input and output exception
	 * @throws ClassNotFoundException Class not found exception
	 */
	public static void loadStatistics(File fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		if (fileName.exists()) {
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(fileName));
			TrackInfo[] stats = (TrackInfo[]) oos.readObject();
			lastSavedPlaylistName = (String) oos.readObject();
			oos.close();
			
			if (trackInfoMap == null) {
				trackInfoMap = new HashMap<Track, TrackInfo>();
			} else {
				trackInfoMap.clear();
			}
			for (TrackInfo info: stats) {
				trackInfoMap.put(info.getTrack(), info);
			}
		} else {
			trackInfoMap = new HashMap<Track, TrackInfo>();
		}
	}
	
	/**
	 * Play a track and add the played times to that track information
	 */
	@Override
	public void playTrack(PlayTrackEvent event) {		
		int row = -1;
		for (int i = 0; i < this.table.getModel().getRowCount(); i++) {
			TrackInfo current = this.getRowInfo(i);
			if (current.equals(event.getTrack())) {
				row = i;
				int timesPlayed = event.getTrack().getTimesPlayed();
				current.setTimesPlayed(timesPlayed);	
				this.getTrackInfo(current.getTrack()).setTimesPlayed(timesPlayed);
				break;
			}
		}
		if (row != -1) {
			this.tableModel.fireTableRowsUpdated(row, row);
			this.table.setRowSelectionInterval(row, row);			
		} else {
			System.out.println("PlaylistView:playtrack - cannot find track in playlist");
		}
	}
	
	/**
	 *  Get previous track
	 * @return the previous track information 
	 */
	public TrackInfo getPreviousTrack() {
		int[] rows = this.table.getSelectedRows();
		if (rows.length > 0) {
			int row = rows[0];
			if (row - 1 >=0 ) {
				return this.getRowInfo(row - 1);
			}
		}
		return null;
	}
	
	/**
	 * Get the next track 
	 * @return the nex track information
	 */
	public TrackInfo getNextTrack() {
		int[] rows = this.table.getSelectedRows();
		if (rows.length > 0) {
			int row = rows[0];
			if (row + 1 < this.table.getModel().getRowCount()) {
				return this.getRowInfo(row + 1);
			}
		}
		return null;
	}
}