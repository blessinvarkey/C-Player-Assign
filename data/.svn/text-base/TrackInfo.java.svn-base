package cs5031.catalogplayer.data;

import java.io.Serializable;

import cs5031.catalogplayer.catalog.Track;

public class TrackInfo implements Serializable {
	/**
	 * Serialization version ID
	 */
	private static final long serialVersionUID = -798299963298263019L;
	
	private Track track;
	private int timesPlayed;
	private boolean favourite;
	
	
	public TrackInfo() {
		this.timesPlayed = 0;
		this.favourite = false;
	}
	
	public TrackInfo(Track track) {
		this();
		this.track = track;
	}	
	
	public int getTimesPlayed() {
		return this.timesPlayed;
	}
	
	public void setTimesPlayed(int timesPlayed) {
		if (timesPlayed < 0) {
			throw new IllegalArgumentException("timesPlayed cannot be negative");
		}
		if (this.timesPlayed != timesPlayed) {
			this.timesPlayed = timesPlayed;
		}
	}
	
	public boolean isFavourite() {
		return this.favourite;
	}
	
	public void setIsFavourite(boolean favourite) {
		if (this.favourite != favourite) {
			this.favourite = favourite;
		}
	}
	
	public Track getTrack() {
		return this.track;
	}	
	
	public String toString(){
		String str = track.getTitle() + "   ";
		for(String art : track.getArtists()){
			str +=  art + ",";
		}
		str = str.substring(0,str.length()-1) + "   ";
		str += this.timesPlayed;
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrackInfo) {
			TrackInfo other = (TrackInfo) obj;
			if (other != null && other.track != null) {
				return this.track.equals(other.track);
			}
			else {
				return false;
			}
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if (this.track != null) {
			return this.track.hashCode();
		}
		return super.hashCode();
	}
}


