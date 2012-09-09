package cs5031.catalogplayer.player.playListItems;

import cs5031.catalogplayer.catalog.Track;

public abstract class PlayListItem {
	protected String trackInfo;
	protected boolean favourite;
	protected int playedTimes;
	protected String timeStr;
	protected Track track;
	
	public abstract void setTrack(Track track);
	public abstract Track getTrack();
	
	public abstract void setFav(boolean b);
	public abstract boolean getFav();
}
