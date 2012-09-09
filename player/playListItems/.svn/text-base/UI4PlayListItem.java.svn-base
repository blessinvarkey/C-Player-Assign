package cs5031.catalogplayer.player.playListItems;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class UI4PlayListItem {
	JFrame mainFrame;
	JScrollPane trackPanel;
	
	public UI4PlayListItem(){
		mainFrame = new JFrame();
		
		mainFrame.setSize(800, 600);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createJPanel();
		mainFrame.setVisible(true);
	}	
	
	private void createJPanel(){		
		String[] data = {"one", "two", "three", "four"};
		JPanel topPanel = new JPanel();
		JList dataList = new JList(data);
		trackPanel = new JScrollPane(dataList);	
		trackPanel.setName("trackList");
		trackPanel.getViewport().setView(dataList);
		topPanel.add(trackPanel, BorderLayout.CENTER );
		mainFrame.add(topPanel);
	}
	
	
}
