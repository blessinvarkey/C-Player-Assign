package cs5031.catalogplayer.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import cs5031.catalogplayer.GUIApp;
import cs5031.catalogplayer.catalog.Album;
import cs5031.catalogplayer.catalog.Catalog;
import cs5031.catalogplayer.catalog.CatalogFactory;
import cs5031.catalogplayer.catalog.Query;
import cs5031.catalogplayer.catalog.Resource;
import cs5031.catalogplayer.catalog.SearchResult;
import cs5031.catalogplayer.catalog.Track;
/**
 * The JDialog window show the tracks which get from the web service, 
 * can search the tracks by tile and album 
 * and return the selected tracks 
 * @author  <110017972>
 *
 */
public class AddTracksWindow extends JDialog implements ActionListener, KeyListener {
	private static final long serialVersionUID = -5639039088937830717L;
	public static final int EVENT_CLOSE_OK = 0;
	public static final int EVENT_CLOSE_CANCEL = 1;
	public static final String COMMAND_SELECTION_CHANGED = "Selection chaned";
	public static final String COMMAND_DIALOG_CLOSED = "Dialog closed";
	
	protected enum SearchOptions {
		ByTrackTitle,
		ByAlbumName
	}
	
	protected static final String COMMAND_OK = "OK";
	protected static final String COMMAND_CANCEL = "Cancel";
	protected static final String COMMAND_TEXT_CHANGED = "text changed";
	protected static final String COMMAND_SEARCH_OPTION_CHANGED = "search option changed";
	
	private DefaultListModel listItems;
	private JTextField txtSearchTerm;
	private MyJList lstTracks;
	private SearchOptions searchOption;
	private boolean dataBinding;
	private List<ActionListener> actionListeners;	 
	
	/**
	 * Constructor show new dialog
	 */
	public AddTracksWindow() {
		this.searchOption = SearchOptions.ByTrackTitle;
		this.listItems = new DefaultListModel();
		this.actionListeners = new LinkedList<ActionListener>();
		this.setTitle("Add tracks...");		
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);		
	}
	
	/**
	 * process the window event, if it is close event, then call notifyDialogDlosed method 
	 * @param e window event 
	 */
	@Override
	protected void processWindowEvent(WindowEvent e) 
	{
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.notifyDialogClosed();
		}		
		super.processWindowEvent(e);
	}
		
	/**
	 * Binding the data
	 */
	protected void deferredDataBind() {
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				dataBind();				
			}
		});
	}
	
	//bind the searched data with the list table
	private void dataBind() {
		this.dataBinding = true;
		this.listItems.clear();
		
		// construct query
		Catalog cat = CatalogFactory.getCatalog(GUIApp.CATALOG_NAME);
		Query query = cat.createQuery();
		String queryTxtTemplate = null;
		String queryTxtAllTemplate = null;
		if (this.searchOption == SearchOptions.ByTrackTitle) {
			queryTxtTemplate = "//track[contains(title,\"%s\")]";
			queryTxtAllTemplate = "//track";
		}
		else if (this.searchOption == SearchOptions.ByAlbumName) {
			queryTxtTemplate = "//album[contains(title,\"%s\")]";
			queryTxtAllTemplate = "//album";
		}
		else {
			throw new RuntimeException(this.searchOption + " not implemented yet.");
		}
		String term = this.txtSearchTerm.getText();
		if (term.length() > 0) {
			query.setQuery(String.format(queryTxtTemplate, term));
		}
		else {
			query.setQuery(queryTxtAllTemplate);
		}			
		
		// populate list
		SearchResult result = cat.search(query);
		while (result.hasNextPage()) {
			for (Resource res: result.getNextPage()) {
				if (res.getType() == Resource.TYPE_TRACK) {
					this.listItems.addElement((Track) res);
				}
				else if (res.getType() == Resource.TYPE_ALBUM) {
					Album album = (Album) res;
					for (Track track: album.getTracks()) {
						this.listItems.addElement(track);
					}
				}
			}
		}	
		
		//this.lstTracks.set
		System.out.printf("AddTracksWindow: data bound, %d tracks total\n", this.listItems.size());
		this.dataBinding = false;
	}
	
	/**
	 * Setup the layout 
	 */
	public void setupLayout() {		
		// search
		JLabel lblSearchTxt = new JLabel("Search by ");
		this.txtSearchTerm = new JTextField();
		this.txtSearchTerm.setColumns(30);
		this.txtSearchTerm.addKeyListener(this);
		this.txtSearchTerm.addActionListener(this);
		this.txtSearchTerm.setActionCommand(COMMAND_TEXT_CHANGED);
		
		// order is important
		JComboBox cbType = new JComboBox(new String[] {
				"by track title", "by album name"
		});
		cbType.setActionCommand(COMMAND_SEARCH_OPTION_CHANGED);
		cbType.addActionListener(this);
		
		JPanel searchPanel = new JPanel();
		BoxLayout searchLayout = new BoxLayout(searchPanel, BoxLayout.LINE_AXIS);
		searchPanel.setLayout(searchLayout);
		
		searchPanel.add(this.txtSearchTerm);
		searchPanel.add(lblSearchTxt);
		searchPanel.add(cbType);
		
		// list
		this.lstTracks = new MyJList(this.listItems);
		this.lstTracks.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.lstTracks.setVisibleRowCount(-1);
		this.lstTracks.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(this.lstTracks);
		listScroller.setPreferredSize(new Dimension(200, 300));
		listScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		
		// buttons
		JButton btnOK = new JButton("OK");
		btnOK.setActionCommand(COMMAND_OK);
		btnOK.addActionListener(this);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand(COMMAND_CANCEL);
		btnCancel.addActionListener(this);
		
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(btnOK, BorderLayout.LINE_START);
		buttonsPanel.add(btnCancel, BorderLayout.LINE_END);
		
		// window layout
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(searchPanel, BorderLayout.PAGE_START);
		this.getContentPane().add(listScroller, BorderLayout.CENTER);
		this.getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
		
		
		this.setResizable(false);
		this.pack();		
		this.deferredDataBind();
	}
	
	/**
	 * Get the selected tracks from the dialog
	 * @return array of tracks
	 */
	public Track[] getSelectedTracks() {
		Object[] sel = this.lstTracks.getSelectedValues();
		Track[] res = new Track[sel.length];
		for (int i = 0; i < sel.length; i++) {
			res[i] = (Track) sel[i];
		}
		return res;
	}

	/**
	 * Add action listener to the list of action listener list
	 * @param l Actionlistener
	 */
	public void addActionListener(ActionListener l) {
		this.actionListeners.add(l);
	}
	
	/**
	 * Remove the action listener from the action listener list
	 * @param l Actionlistener
	 */
	public void removeActionListener(ActionListener l) {
		this.actionListeners.remove(l);
	}	
	
	/**
	 * Notify the other observers when the selection changed
	 */
	protected void notifySelectionChanged() {
		ActionEvent event = new ActionEvent(this, EVENT_CLOSE_OK, COMMAND_SELECTION_CHANGED);
		this.notifyActionPerformed(event);
	}
	
	/**
	 * Notify the other observers when the dialog closed
	 */
	protected void notifyDialogClosed() {
		ActionEvent event = new ActionEvent(this, EVENT_CLOSE_CANCEL, COMMAND_DIALOG_CLOSED);
		this.notifyActionPerformed(event);
	}
	
	/**
	 * React the action event, notify all the other listeners 
	 * @param event
	 */
	protected void notifyActionPerformed(ActionEvent event) {
		for (ActionListener listener: this.actionListeners) {
			listener.actionPerformed(event);
		}
	}
	
	/**
	 * Performe the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		final String cmd = e.getActionCommand();
		
		if (COMMAND_TEXT_CHANGED.equals(cmd)) {
			if (!this.dataBinding) {
				this.deferredDataBind();
			}
		}
		if (COMMAND_SEARCH_OPTION_CHANGED.equals(cmd)) {
			JComboBox cb = (JComboBox) e.getSource();
			int idx = cb.getSelectedIndex();
			this.searchOption = SearchOptions.values()[idx];
			this.deferredDataBind();
		}
		if (COMMAND_OK.equals(cmd)) {
			this.notifySelectionChanged();
			this.setVisible(false);			
		}
		if (COMMAND_CANCEL.equals(cmd)) {
			this.notifyDialogClosed();
			this.setVisible(false);		
		}
		System.out.printf("AddTracksWindow: %s\n", e.getActionCommand());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (!this.dataBinding)
			this.deferredDataBind();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// ignore
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// ignore
	}	
}
