package cs5031.catalogplayer.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.JavaLayerException;

import cs5031.catalogplayer.catalog.Track;
import cs5031.catalogplayer.data.TrackInfo;
import cs5031.catalogplayer.ui.events.PlayTrackEvent;
import cs5031.catalogplayer.ui.events.PlayTrackListener;
import cs5031.catalogplayer.ui.events.PlayTrackSource;
import cs5031.catalogplayer.ui.events.TrackSelectedEvent;
import cs5031.catalogplayer.ui.events.TrackSelectedListener;
import cs5031.fancyplayer.player.FancyPlayer;
import cs5031.fancyplayer.player.PlaybackEvent;
import cs5031.fancyplayer.player.PlaybackListener;

/**
 * This is the main UI window for the application, 
 * it contains 2 sub windows, one is the player control window, 
 * the other one is the playlist window. 
 * @author <110016203>,<110017972>,<110021201>
 *
 */
public class PlayerWindow 
	extends JFrame 
	implements ActionListener
	, PlayTrackListener
	, PlaybackListener
	, ChangeListener
	, TrackSelectedListener
{
	private static final long serialVersionUID = -3521212929554047528L;
	public static final String COMMAND_PREVIOUS = "previous";
	public static final String COMMAND_PLAY_PAUSE = "play/pause";
	public static final String COMMAND_STOP = "stop";
	public static final String COMMAND_NEXT = "next";
	public final PlayTrackSource trackPlayed;
	
	protected enum State { Idle, Playing, Paused };
	private FancyPlayer fancyPlayer;
	private JLabel lblText;
	private JLabel lblTime;
	private IconButton btnPlayPause;
	private TrackInfo currentTrack;
	private State state;
	private PlaylistView playlistView;
	private PlaylistView tracksView;
	private PlaylistView favouritesView;
	private JTabbedPane contentPane;
	private final ImageIcon playListIcon = new  ImageIcon("data/format_list_unordered.png","playListIcon");
	private final ImageIcon favouriteIcon = new  ImageIcon("data/bookmarks_list_add.png","playListIcon");
	
	
	/**
	 * Constructor of the main UI window, create the sub panels and register listeners 
	 */
	public PlayerWindow() {
		this.trackPlayed = new PlayTrackSource(this);
		this.setTitle("Player");
		this.state = State.Idle;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.contentPane = new JTabbedPane();
		this.tracksView = new TracksView();		
		this.favouritesView = new FavouritesView();	
		this.playlistView = this.tracksView;
		this.setResizable(false);
		
		this.tracksView.playTrack.addListener(this);
		this.favouritesView.playTrack.addListener(this);
		this.tracksView.trackSelected.addListener(this);
		this.favouritesView.trackSelected.addListener(this);
		
		this.trackPlayed.addListener(this.favouritesView);
		this.trackPlayed.addListener(this.tracksView);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				PlaylistView.saveDefaultStatistics();
			}
		});
	}
	
	/**
	 * set up the content in sub panels and layout them
	 */
	public void setupLayout() {
		JPanel playbackPanel = this.createPlaybackPanel();
		JPanel infoPanel = this.createInfoPanel();		
		JPanel playlistPanel = this.createPlaylistPanel();
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.getContentPane().add(infoPanel);
		this.getContentPane().add(playbackPanel);
		this.getContentPane().add(playlistPanel);
		
		this.pack();
	}
	
	/**
	 * Create the play list panel
	 * @return the JPanel which contains the play list view 
	 */
	protected JPanel createPlaylistPanel() {
		JPanel playlistPanel = new JPanel();		
		
		playlistPanel.setLayout(new BorderLayout());
		this.tracksView.setupLayout();
		this.favouritesView.setupLayout();
		this.contentPane.addTab("", playListIcon, tracksView);
		this.contentPane.addTab("", favouriteIcon, favouritesView);
		this.contentPane.setTabPlacement(JTabbedPane.LEFT);
		this.contentPane.addChangeListener(this);
		
		playlistPanel.add(this.contentPane);
		return playlistPanel;
	}
	
	/**
	 * Create the panel which show the information of the current playing track
	 * @return the panel which show the track playing information
	 */
	protected JPanel createInfoPanel() {
		Font font = new Font("SansSerif", Font.PLAIN, 30);
		
		this.lblText = new JLabel("No track selected");
		this.lblText.setFont(font);		
		
		this.lblTime = new JLabel("--:--:--");
		this.lblTime.setFont(font);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		
		infoPanel.add(this.lblText, BorderLayout.CENTER);
		infoPanel.add(this.lblTime, BorderLayout.PAGE_END);
		
		return infoPanel;
	}
	
	/**
	 * Create the track playing control panel which contains the play control buttons 
	 * @return
	 */
	protected JPanel createPlaybackPanel() {
		JButton btnPrev = new IconButton("data/player_rew.png", "Previous Track");
		btnPrev.setActionCommand(COMMAND_PREVIOUS);
		btnPrev.addActionListener(this);
		
		this.btnPlayPause = new IconButton("data/player_play.png", "Play");
		this.btnPlayPause.setActionCommand(COMMAND_PLAY_PAUSE);
		this.btnPlayPause.addActionListener(this);
		
		JButton btnStop = new IconButton("data/player_stop.png", "Stop");
		btnStop.setActionCommand(COMMAND_STOP);
		btnStop.addActionListener(this);
		
		JButton btnNext = new IconButton("data/player_fwd.png", "Next Track");
		btnNext.setActionCommand(COMMAND_NEXT);
		btnNext.addActionListener(this);
		
		JPanel playbackPane = new JPanel();
		BoxLayout playbackLayout = new BoxLayout(playbackPane, BoxLayout.LINE_AXIS);
		playbackPane.setLayout(playbackLayout);
		playbackPane.add(btnPrev);
		playbackPane.add(this.btnPlayPause);
		playbackPane.add(btnStop);
		playbackPane.add(btnNext);
		
		return playbackPane;
	}
	
	/**
	 * Receive the action event from the play control button and react 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (COMMAND_STOP.equals(cmd)) {
			this.stop();
		}
		else if (COMMAND_PLAY_PAUSE.equals(cmd)) {
			if (this.state == State.Playing) {
				this.pause();
			}
			else if (this.state == State.Paused) {
				this.resume();
			}
			else if (this.state == State.Idle && this.currentTrack != null) {
				this.play(this.currentTrack);
			}
		}
		else if (COMMAND_NEXT.equals(cmd)) {
			this.playNextTrack();
		}
		else if (COMMAND_PREVIOUS.equals(cmd)) {
			this.playPreviousTrack();
		}
		
		System.out.printf("PlayerWindow.actionPerformed(%s)\n", cmd);
	}
	
	
	/**
	 * Play the current track which get from the event 
	 * @param event the event created by other process 
	 */
	@Override
	public void playTrack(PlayTrackEvent event) {
		this.play(event.getTrack());
	}
	
	/**
	 * Play the track with given a track information 
	 * @param forTrack TrackInfo which hold the track
	 */
	protected void play(TrackInfo forTrack) {
		if (this.fancyPlayer != null) {
			this.fancyPlayer.setPlaybackListener(null);
			try {
				this.fancyPlayer.stop();
			} catch (BitstreamException e) {
				// ignore
			}			
		}
		
		Track track = forTrack.getTrack();
		final URL url = track.getURL();
		if (url == null) {
			this.handlePlaybackError("No URL for " + track);
			this.fancyPlayer = null;
			return;
		}
		this.setPlayingTrackTitle(forTrack);
		this.btnPlayPause.setIcon("data/player_pause.png", "Pause");
		this.currentTrack = forTrack;
		this.currentTrack.setTimesPlayed(this.currentTrack.getTimesPlayed() + 1);
		this.trackPlayed.fireEvent(this.currentTrack);
		this.state = State.Playing;
		this.pack();
		final PlayerWindow self = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {				
					fancyPlayer = new FancyPlayer(url);
					fancyPlayer.setPlaybackListener(self);
					fancyPlayer.play();
				} catch (JavaLayerException e) {
					handlePlaybackError(e.getMessage());
				} catch (IOException e) {
					handlePlaybackError(e.getMessage());
				}
			}			
		});
	}
	
	/**
	 * Set the playing track tile which shown in the track information panel
	 * @param info given a trackinfo which is playing 
	 */
	protected void setPlayingTrackTitle(TrackInfo info) {
		StringBuilder txt = new StringBuilder();
		String[] authors = info.getTrack().getArtists();
		if (authors.length > 0) {
			for (int i = 0; i < authors.length; i++) {
				txt.append(authors[i]);
				if (i != authors.length - 1) {
					txt.append(" ft. ");
				}
			}
		} else {
			txt.append("Unknown Artist");
		}
		txt.append(" - ");
		txt.append(info.getTrack().getTitle());
		this.lblText.setText(txt.toString());
	}
	
	/**
	 * Stop playing the music 
	 */
	protected void stop() {
		if (this.fancyPlayer == null) {
			return;
		}
		try {
			this.fancyPlayer.stop();
		} catch (BitstreamException e) {
			// ignore; we want it to stop anyway
		}
		this.fancyPlayer.setPlaybackListener(null);
		this.state = State.Idle;
		this.btnPlayPause.setIcon("data/player_play.png", "Play");
		this.fancyPlayer = null;
	}
	
	/**
	 * Pause the playing music
	 */
	protected void pause() {
		if (this.fancyPlayer != null) {
			this.btnPlayPause.setIcon("data/player_play.png", "Play");
			this.state = State.Paused;
			SwingUtilities.invokeLater(new Runnable() {				
				public void run() { fancyPlayer.pausePlayback(); };
			});		
		}
	}
	
	/**
	 * Resume playing the music 
	 */
	protected void resume() {
		if (this.fancyPlayer != null) {
			this.btnPlayPause.setIcon("data/player_pause.png", "Pause");
			this.state = State.Playing;
			SwingUtilities.invokeLater(new Runnable() {				
				public void run() { fancyPlayer.resumePlayback(); };
			});			
		}		
	}
	
	/**
	 * Play next track which next to the current track in the list 
	 */
	protected void playNextTrack() {
		TrackInfo next = this.getCurrentView().getNextTrack();
		if (next != null) {
			this.play(next);
		}
	}
	
	/**
	 * Play previous track previous to the current track in the list 
	 */
	protected void playPreviousTrack() {
		TrackInfo previous = this.getCurrentView().getPreviousTrack();
		if (previous != null) {
			this.play(previous);
		}
	}
	
	/**
	 * Get current view of the window, would be either play list view or favourite view
	 * @return current view 
	 */
	public PlaylistView getCurrentView() {
		return this.playlistView;
	}
	
	/**
	 * Set current view to the window
	 * @param view the view need to be shown
	 */
	public void setCurrentView(PlaylistView view) {
		this.playlistView = view;
	}
	
	/**
	 * Get the play list view 
	 * @return the play list view 
	 */
	public PlaylistView getTracksView() {
		return this.tracksView;
	}
	
	/**
	 * Get the favourite list view 
	 * @return the favourite list view 
	 */
	public PlaylistView getFavouritesView() {
		return this.favouritesView;
	}
	
	/**
	 * handle the play back event 
	 * update the time
	 */
	@Override
	public void handlePlaybackEvent(PlaybackEvent e) {
		this.lblTime.setText(e.getTimeStr());
	}

	/**
	 * handle the play back error 
	 * @param errorMsg the error message 
	 */
	@Override
	public void handlePlaybackError(String errorMsg) {
		JOptionPane.showMessageDialog(this, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);	
		this.state = State.Idle;
	}
	
	/**
	 * changes the current state
	 *  
	 * **/
	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(contentPane.getSelectedIndex() == 1){
			this.setCurrentView(this.favouritesView);
			((FavouritesView)this.favouritesView).updateFavourite();			
			System.out.println("PlaylistWindow: updata favourite");
		}		
		else {
			this.setCurrentView(this.favouritesView);
		}
	}
/**
 *  Selects the required track
 *  @param: Track needs to be shown
 *  **/
	@Override
	public void trackSelected(TrackSelectedEvent event) {
		this.currentTrack = event.getTrack();
	}
}
