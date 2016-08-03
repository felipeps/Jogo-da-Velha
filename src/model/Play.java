package model;

public class Play {

	private String play;
	private String playerName;
	private Integer col;
	private Integer row;
	
	public Play() {
		this.play = " ";
	}
	
	public Play(String play, String playerName, Integer col, Integer row) {
		this.play = play;
		this.playerName = playerName;
		this.col = col;
		this.row = row;
	}
	
	public String getPlay() {
		return play;
	}
	
	public void setPlay(String play) {
		this.play = play;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null )
			return false;
		
		if ( !(obj instanceof Play) )
			return false;
		
		if ( ((Play) obj).getPlay() == null )
			return false;
		
		if ( ((Play) obj).getPlay().equals(" ") )
			return false;
		
		if (this.getPlay().equals(((Play) obj).getPlay()))
			return true;
		
		return false;
	}
	
}