package cs5031.catalogplayer.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePane extends JPanel implements ActionListener{
	private JButton playList;
	private JButton favourite;	
	public static final String SW_PLAYLIST = "SW_PLAYLIST"; 
	public static final String SW_FAVOURITE = "SW_FAVOURITE";	
	public static final int PLAYLIST = 0; 
	public static final int FAVOURITE = 1;	
	
	private List<ActionListener> actionListeners;	
	
	public SidePane(){
		actionListeners = new ArrayList<ActionListener>();
		playList = new IconButton("data/list_add.png","add");
		favourite = new IconButton("data/list_remove.png","remove");
		
		playList.setActionCommand(SW_PLAYLIST);
		favourite.setActionCommand(SW_FAVOURITE);
		
		this.playList.addActionListener(this);
		this.favourite.addActionListener(this);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(playList);
		this.add(favourite);
		
	}
	
	public void addActionListener(ActionListener l){
		this.actionListeners.add(l);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if(SW_PLAYLIST.equals(command) ){
			this.notifySwitchToPlayList();
		} else if(SW_FAVOURITE.equals(command)){
			this.notifySwitchToFavourite();
		}
	}

	private void notifySwitchToFavourite() {
		// TODO Auto-generated method stub
		ActionEvent event = new ActionEvent(this, FAVOURITE, SW_FAVOURITE);
		this.notifyActionPerformed(event);
	}

	private void notifySwitchToPlayList() {
		// TODO Auto-generated method stub
		ActionEvent event = new ActionEvent(this, PLAYLIST, SW_PLAYLIST);
		this.notifyActionPerformed(event);
	}

	private void notifyActionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		for (ActionListener listener: this.actionListeners) {
			listener.actionPerformed(event);
		}
	}
}
