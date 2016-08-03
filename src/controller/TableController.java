package controller;

import model.Play;
import model.Table;

public class TableController {

	private Table table;
	private PController pCtrl;
	private Boolean winner;
	private boolean pcFirst;
	
	public TableController(Table table, String playPiece, boolean pcFirst) {
		this.table = table;
		this.winner = false;
		this.pcFirst = pcFirst;
		
		if ( playPiece.equals("O") )
			pCtrl = new PController("X");
		else
			pCtrl = new PController("O");
	}
	
	public boolean doPlay(Play play) {
		try {
			if ( checkPlay(play) ) {
				this.table.getGameTable()[ play.getCol()][play.getRow() ] = play;
				pCtrl.addPlayerPlay(play);
				return true;
			}
			return false;
			
		} catch(Exception error) {
			error.printStackTrace();
			return false;
		}
	}
	
	public boolean doPCPlay() {
		return this.pCtrl.doPlay(this.table, pcFirst);
	}
	
	public boolean checkPlay(Play play) {
		if ( !(this.table.getGameTable()[ play.getCol()][play.getRow() ].getPlay().equals(" ")) )
			return false;
		
		return true;
	}
	
	public boolean checkGame() {		
		if ( checkVelha() ) {
			winner = null;
			return true;
		}
		
		if ( this.table.getGameTable()[0][0].equals(this.table.getGameTable()[0][1]) &&
				this.table.getGameTable()[0][0].equals(this.table.getGameTable()[0][2]) ) {
			if ( !(this.table.getGameTable()[0][0].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		if ( this.table.getGameTable()[1][0].equals(this.table.getGameTable()[1][1]) &&
				 this.table.getGameTable()[1][0].equals(this.table.getGameTable()[1][2]) ) {
			if ( !(this.table.getGameTable()[1][0].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		if ( this.table.getGameTable()[2][0].equals(this.table.getGameTable()[2][1]) &&
				 this.table.getGameTable()[2][0].equals(this.table.getGameTable()[2][2]) ) {
			if ( !(this.table.getGameTable()[2][0].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		/* ----- */
		
		if ( this.table.getGameTable()[0][0].equals(this.table.getGameTable()[1][0]) &&
				 this.table.getGameTable()[0][0].equals(this.table.getGameTable()[2][0]) ) {
			if ( !(this.table.getGameTable()[0][0].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		if ( this.table.getGameTable()[0][1].equals(this.table.getGameTable()[1][1]) &&
				 this.table.getGameTable()[0][1].equals(this.table.getGameTable()[2][1]) ) {
			if ( !(this.table.getGameTable()[0][1].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		if ( this.table.getGameTable()[0][2].equals(this.table.getGameTable()[1][2]) &&
				 this.table.getGameTable()[0][2].equals(this.table.getGameTable()[2][2]) ) {
			if ( !(this.table.getGameTable()[0][2].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		/* ----- */
		
		if ( this.table.getGameTable()[0][0].equals(this.table.getGameTable()[1][1]) &&
				 this.table.getGameTable()[0][0].equals(this.table.getGameTable()[2][2]) ) {
			if ( !(this.table.getGameTable()[0][0].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		if ( this.table.getGameTable()[0][2].equals(this.table.getGameTable()[1][1]) &&
				 this.table.getGameTable()[0][2].equals(this.table.getGameTable()[2][0]) ) {
			if ( !(this.table.getGameTable()[0][2].getPlay().equals(pCtrl.getPlayPiece())) )
				winner = true;
			return true;
		}
		
		/* ----- */
		
		return false;
	}
	
	public boolean checkVelha() {
		for(int row = 0; row < table.getRows(); row++) {
			for(int col = 0; col < table.getCols(); col++) {
				if ( table.getGameTable()[row][col].getPlay().equals(" ") )
					return false;
			}
		}
		return true;
	}
	
	public String getGameTable() {
		StringBuilder sb = new StringBuilder();
		
		for ( int x = 0; x < this.table.getCols(); x++ ) {
			for ( int y = 0; y < this.table.getRows(); y++) {
				sb.append("[ " + this.table.getGameTable()[x][y].getPlay() + " ]   ");
			}
			sb.append("\n\n");
		}
		
		return sb.toString();
	}
	
	public void resetTable() {
		this.table.init();
		this.winner = false;
	}
	
	public void resetPC(String playPiece, boolean pcFirst) {
		this.pcFirst = pcFirst;
		
		if ( playPiece.equals("O") )
			pCtrl = new PController("X");
		else
			pCtrl = new PController("O");
	}

	public Boolean isWinner() {
		return winner;
	}
	
}