package cs5031.catalogplayer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolsPane extends JPanel {
	
	private JButton add;
	private JButton remove;
	private JButton save;
	private JButton load;
	private final String ADD_TRACK = "ADD_TRACK"; 
	private final String REMOVE_TRACK = "REMOVE_TRACK";
	private final String SAVE_LIST = "SAVE_LIST";
	private final String LOAD_LIST = "LOAD_LIST";
	
	
	public ToolsPane(){
		add = new IconButton("data/list_add.png","add");
		remove = new IconButton("data/list_remove.png","remove");
		save = new IconButton("data/document_save.png","save");
		load = new IconButton("data/document_open.png","load");
		
		add.setActionCommand(ADD_TRACK);
		remove.setActionCommand(REMOVE_TRACK);
		save.setActionCommand(SAVE_LIST);
		load.setActionCommand(LOAD_LIST);	
		 
		this.add(add);
		this.add(remove);
		this.add(save);
		this.add(load);
	}
	
}
