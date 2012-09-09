package cs5031.catalogplayer.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;
/**
 * Tracks view show current play list, subclass of PlayListView   
 * @author <110016203>,<110017972>,<110021201>
 *
 */
public class TracksView extends PlaylistView implements ActionListener{
	private static final long serialVersionUID = 5021640233402642082L;
	private JPanel tools;
	private final String ADD_TRACK = "ADD_TRACK";
	private final String REMOVE_TRACK = "REMOVE_TRACK";
	private final String SAVE_LIST = "SAVE_LIST";
	private final String LOAD_LIST = "LOAD_LIST";
	private AddTracksWindow addTracksWnd;
			
	/**
	 * Constructor of the track view, call super constructor,
	 * create tools panel which at the bottom,
	 * create new add track dialog  
	 */
	public TracksView() {
		super();
		tableModel = new PlayListTableModel(columnNames);
		
		tools = createToolsPane();
		addTracksWnd = new AddTracksWindow();
		addTracksWnd.addActionListener(this);
		addTracksWnd.setupLayout();
		addTracksWnd.setVisible(false);		
	}
	
	/**
	 * Setup and layout the content 
	 */
	public void setupLayout(){
		super.setupLayout();
		this.add(tools,BorderLayout.SOUTH);
		File lastPlaylist = new File(PlaylistView.getLastSavedPlaylistName());
		if (lastPlaylist.exists()) {
			this.loadPlaylistInternal(lastPlaylist);
		}		
	}

	/**
	 * Create the tolls panel which can add new tracks, 
	 * remove tracks from the list,
	 * save the current playlist,
	 * load saved playlist
	 * @return JPanel which hold these buttons
	 */
	private JPanel createToolsPane() {
		JButton add;
		JButton remove;
		JButton save;
		JButton load;
		

		JPanel toolsPanel = new JPanel();
		add = new IconButton("data/list_add.png","add");
		remove = new IconButton("data/list_remove.png","remove");
		save = new IconButton("data/document_save.png","save");
		load = new IconButton("data/document_open.png","load");
		
		add.setActionCommand(ADD_TRACK);
		remove.setActionCommand(REMOVE_TRACK);
		save.setActionCommand(SAVE_LIST);
		load.setActionCommand(LOAD_LIST);	
		
		add.addActionListener(this);
		remove.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);
		
		toolsPanel.add(add);
		toolsPanel.add(remove);
		toolsPanel.add(save);
		toolsPanel.add(load);
		return toolsPanel;
	}	
	
	/**
	 * Set the given list of the tracks to the table 
	 * @param tracks given list of tracks 
	 */
	public void setList(TrackInfo[] tracks) {
		ItemTableModel tableModel = (ItemTableModel)table.getModel();
		tableModel.clear();
		for(TrackInfo track : tracks) {
			TrackInfo updated = this.getTrackInfo(track.getTrack());
			System.out.println("Add " + updated.getTrack().getTitle());		
			tableModel.addRow(updated);
		}	
	}
	
	/**
	 * Clear the play list
	 */
	public void clearList(){
		ItemTableModel tableModel = (ItemTableModel)table.getModel();
		tableModel.clear();
	}
	
	/**
	 * Add tracks to the table with given list of tracks 
	 * @param tracks  array of given tracks 
	 */
	public void addTracks(Track[] tracks){
		ItemTableModel tableModel = (ItemTableModel)table.getModel();
		for(Track track : tracks) {
			System.out.println("Add " + track.getTitle());
			TrackInfo trackInf = this.getTrackInfo(track);			
			tableModel.addRow(trackInf);
		}	
	}
	
	/**
	 * Perform the actions which fired by the tool buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals(ADD_TRACK)){	
			addTracksWnd.setVisible(true);
			System.out.println(e.getActionCommand());
		} else if (command.equals(REMOVE_TRACK)){
			this.removeTracks();
		}
		else if (command.equals(AddTracksWindow.COMMAND_SELECTION_CHANGED)) {
			AddTracksWindow wnd = (AddTracksWindow) e.getSource();
			Track[] selected = wnd.getSelectedTracks();
			this.addTracks(selected);
		}
		else if (command.equals(SAVE_LIST)) {
			this.savePlaylist();
		}
		else if (command.equals(LOAD_LIST)) {
			this.loadPlaylist();
		}
	}
	
	//create a file chooser to save and load the play list
	private JFileChooser constructFileChooser() {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
		
		FileFilter fooFilter = new FileNameExtensionFilter("Foo Player Playlist (*.fooplaylist)", "fooplaylist");
		
		chooser.addChoosableFileFilter(fooFilter);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		return chooser;
	}
	
	/**
	 * Save the current play list
	 */
	protected void savePlaylist() {
		JFileChooser chooser = this.constructFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle("Save playlist as...");
		chooser.setApproveButtonText("Save");
		chooser.setApproveButtonToolTipText("Save");
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			ItemTableModel model = (ItemTableModel) this.table.getModel();
			TrackInfo[] data = model.getConstData();
			this.savePlaylistInternal(file, data);
		}
	}
	
	//Save the play list by given the file and the data 
	private void savePlaylistInternal(File file, TrackInfo[] data) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(data);
			oos.close();
			PlaylistView.setLastSavedPlaylistName(file.getAbsolutePath());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	/**
	 * Create a file chooser to selection play list to open
	 */
	protected void loadPlaylist() {
		JFileChooser chooser = this.constructFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("Load playlist...");
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();			
			this.loadPlaylistInternal(file);
		}
	}
	
	//load the play list from a given file
	private void loadPlaylistInternal(File file) {
		try {
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file));
			TrackInfo[] data = (TrackInfo[])oos.readObject();
			oos.close();
			
			this.setList(data);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Corrupt playlist file");
		}
	}
	
	/**
	 * Remove the selected rows from the play list
	 */
	public void removeTracks() {
		ItemTableModel tableModel = (ItemTableModel)table.getModel();
		int[] selecRows = table.getSelectedRows();
		tableModel.removeRows(selecRows);		
	}
	
}