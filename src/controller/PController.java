package controller;

import java.util.ArrayList;
import java.util.Random;

import model.Play;
import model.Table;

public class PController {
	
	private ArrayList<String> playerPlays;
	private ArrayList<String> playerPlaysAux;
	private ArrayList<String> ownPlays;
	private ArrayList<String> ownPlaysAux;
	private ArrayList<String> availablePlays;
	private String playPiece;
	
	public PController(String playPiece) {
		this.playPiece = playPiece;
		this.playerPlays = new ArrayList<String>();
		this.playerPlaysAux = new ArrayList<String>();
		this.ownPlays = new ArrayList<String>();
		this.ownPlaysAux = new ArrayList<String>();
		this.availablePlays = new ArrayList<String>();
		
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++)
				availablePlays.add(x + "|" + y);
		}
	}
	
	public boolean doPlay(Table table, boolean firstToPlay) {
		Integer col = 0;
		Integer row = 0;
		String bestPlay;
		String[] play = new String[2];
		String[] randPlays = new String[9];
		Random generator = new Random();
		
		if ( availablePlays.size() == 1 ) {
			play = this.availablePlays.get(0).split("|");
			col = new Integer(play[0]);
			row = new Integer(play[2]);
			
			table.getGameTable()[col][row] = new Play(playPiece, "Computer", col, row);
			
			return true;
		}
		
		if ( firstToPlay ) {
			if ( this.ownPlays.size() == 0 ) {
				randPlays[0] = "0|0"; randPlays[1] = "0|1"; randPlays[2] = "0|2";
				randPlays[3] = "1|0"; randPlays[4] = "1|1"; randPlays[5] = "1|2";
				randPlays[6] = "2|0"; randPlays[7] = "2|1"; randPlays[8] = "2|2";
				
				bestPlay = randPlays[generator.nextInt(randPlays.length)];
				
				play = bestPlay.split("|");
				
				col = new Integer(play[0]);
				row = new Integer(play[2]);
				
				table.getGameTable()[col][row] = new Play(playPiece, "Computer", col, row);
			}
			
			else {
				bestPlay = checkGonnaWin(table);
				
				if ( bestPlay == null ) {
					bestPlay = checkPlayerAboutToWin(table);
					
					if ( bestPlay == null )
						bestPlay = bestPlay(table);
				}
				
				play = bestPlay.split("|");
				
				col = new Integer(play[0]);
				row = new Integer(play[2]);
				
				table.getGameTable()[col][row] = new Play(playPiece, "Computer", col, row);
			}
		}

		else {
			bestPlay = checkGonnaWin(table);
			
			if ( bestPlay == null ) {
				bestPlay = checkPlayerAboutToWin(table);
				
				if ( bestPlay == null )
					bestPlay = defensivePlay(table);
			}
			
			play = bestPlay.split("|");
			col = new Integer(play[0]);
			row = new Integer(play[2]);
			
			table.getGameTable()[col][row] = new Play(playPiece, "Computer", col, row);
		}
		
		String availablePlayToRemove = col + "|" + row;
		
		this.availablePlays.remove(availablePlayToRemove);
		this.ownPlays.add(col + "|" + row);
		
		return true;
	}
	
	private String defensivePlay(Table table) {
		Table auxT = table.clone();
		Table auxTBackup = null;
		String aux;
		String play = null;
		String playerPlayPiece;
		int[] chances = new int[2];
		int[] biggerChance = new int[2];
		
		chances[0] = 0; chances[0] = 0;
		biggerChance[0] = 0; biggerChance[1] = 0;
		
		if ( this.playPiece.equals("O") ) playerPlayPiece = "X"; else playerPlayPiece = "O";
		
		this.playerPlaysAux = copyOwnPlays(this.playerPlays);
		
		for(int x = 0; x < table.getRows(); x++) {
			for(int y = 0; y < table.getCols(); y++) {
				aux = x + "|" + y;
				
				if ( !checkAllPlays(aux) ) {
					this.playerPlaysAux.add(x + "|" + y);
					auxT.getGameTable()[x][y] = new Play(playerPlayPiece, "Player", x, y);
					auxTBackup = auxT.clone();
			
					for(int r = 0; r < table.getRows(); r++ ) {
						for(int c = 0; c < table.getCols(); c++) {
							aux = r + "|" + c;
							
							if ( !checkAllPlays(aux) ) {
								this.playerPlaysAux.add(r + "|" + c);
								auxT.getGameTable()[r][c] = new Play(playerPlayPiece, "Player", r, c);
								chances[0] = calculatePlayerChancesToWin(auxT);
								
								for(int t = 0; t < table.getRows(); t++ ) {
									for(int l = 0; l < table.getCols(); l++) {
										aux = t + "|" + l;
										
										if ( !checkAllPlays(aux) ) {
											this.playerPlaysAux.add(t + "|" + l);
											auxT.getGameTable()[t][l] = new Play(playerPlayPiece, "Player", t, l);
											chances[1] = calculatePlayerChancesToWin(auxT);
											
											if ( chances[1] < biggerChance[1] ) {
												if ( table.getGameTable()[x][y].getPlay().equals(" ") ) {
													biggerChance[1] = chances[1];
													play = x + "|" + y;
												}
											}
										}
										auxT = auxTBackup.clone();
									}
								}
								
								if ( chances[0] <= biggerChance[0] ) {
									if ( table.getGameTable()[x][y].getPlay().equals(" ") ) {
										biggerChance[0] = chances[0];
										play = x + "|" + y;
									}
								}
					/*			
								else if ( chances[0] == biggerChance[0] ) {
									if ( chances[1] <= biggerChance[1] ) {
										if ( table.getGameTable()[x][y].getPlay().equals(" ") ) {
											biggerChance[1] = chances[1];
											play = x + "|" + y;
										}
									}
								}*/
							}
							auxT = auxTBackup.clone();
						}						
					}
				}
				chances[0] = 0; chances[1] = 0;
			}
			this.ownPlaysAux = copyOwnPlays(this.ownPlays);
			auxT = table.clone();
		}
		return play;
	}
	
	private String checkGonnaWin(Table table) {
		if ( this.ownPlays.size() <= 1 )
			return null;
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[0][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				return "0|2";
		}
		
		if ( table.getGameTable()[0][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[0][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				return "0|0";
		}
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[0][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				return "0|1";
		}
		
		/* --------------- */

		if ( table.getGameTable()[1][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				return "1|2";
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				return "1|0";
		}
		
		if ( table.getGameTable()[1][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		}
		
		/* ------------------ */
		
		if ( table.getGameTable()[2][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				return "2|2";
		}
		
		if ( table.getGameTable()[2][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				return "2|0";
		}
		
		if ( table.getGameTable()[2][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				return "2|1";
		}
		
		/* ------------------- */
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				return "2|0";
		}
		
		if ( table.getGameTable()[1][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				return "0|0";
		}
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				return "1|0";
		}
		
		/* --------------------- */
		
		if ( table.getGameTable()[0][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				return "2|1";
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				return "0|1";
		}
		
		if ( table.getGameTable()[0][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		}
		
		/* ---------------------- */
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				return "2|2";
		}
		
		if ( table.getGameTable()[1][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				return "0|2";
		}
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				return "1|2";
		}
		
		/* ------------------------ */
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				return "2|2";
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				return "0|0";
		}
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		}
		
		/* ------------------------- */
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				return "2|0";
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				return "0|2";
		}
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		}
		
		return null;
	}
	
	private ArrayList<String> copyOwnPlays(ArrayList<String> original) {
		ArrayList<String> copy = new ArrayList<String>();
		String aux;
		
		for(int x = 0; x < copy.size(); x++)
			copy.set(x, null);
		
		for(int x = 0; x < original.size(); x++) {
			aux = new String(original.get(x));
			copy.add(aux);
		}
		return copy;
	}
	
	private String bestPlay(Table table) {
		Table auxT = table.clone();
		Table auxTBackup = null;
		String aux;
		String play = null;
		int[] chances = new int[2];
		int[] biggerChance = new int[2];
		
		chances[0] = 0; chances[1] = 0;
		biggerChance[0] = 0; biggerChance[1] = 0;
		
		this.ownPlaysAux = copyOwnPlays(this.ownPlays);
		
		for(int x = 0; x < table.getRows(); x++) {
			for(int y = 0; y < table.getCols(); y++) {
				aux = x + "|" + y;
				
				if ( !checkAllPlays(aux) ) {
					auxT.getGameTable()[x][y] = new Play(this.playPiece, "Computer", x, y);
					auxTBackup = auxT.clone();
					this.ownPlaysAux.add(x + "|" + y);
					
					for(int r = 0; r < table.getRows(); r++ ) {
						for(int c = 0; c < table.getCols(); c++) {
							aux = r + "|" + c;
							
							if ( !checkAllPlays(aux) ) {
								this.ownPlaysAux.add(r + "|" + c);
								auxT.getGameTable()[r][c] = new Play(this.playPiece, "Computer", r, c);
								
								chances[0] = calculateChancesToWin(auxT);
								
								for(int t = 0; t < table.getRows(); t++ ) {
									for(int l = 0; l < table.getCols(); l++) {
										aux = t + "|" + l;
										
										if ( !checkAllPlays(aux) ) {
											this.ownPlaysAux.add(t + "|" + l);
											auxT.getGameTable()[t][l] = new Play(this.playPiece, "Computer", t, l);
											
											chances[1] = calculatePlayerChancesToWin(auxT);
											
											if ( chances[1] > biggerChance[1] ) {
												if ( table.getGameTable()[x][y].getPlay().equals(" ") ) {
													biggerChance[1] = chances[1];
													play = x + "|" + y;
												}
											}
										}
										auxT = auxTBackup.clone();
									}
								}
								
								if ( chances[0] >= biggerChance[0] ) {
									if ( table.getGameTable()[x][y].getPlay().equals(" ") ) {
										biggerChance[0] = chances[0];
										play = x + "|" + y;
									}
								}
								
								else if ( chances[0] == biggerChance[0] ) {
									if ( chances[1] >= biggerChance[1] ) {
										if ( table.getGameTable()[x][y].getPlay().equals(" ") ) {
											System.out.println("PASSEI AQUI WOW");
											biggerChance[1] = chances[1];
											play = x + "|" + y;
										}
									}
								}
							}
							auxT = auxTBackup.clone();
						}						
					}
				}
				chances[0] = 0; chances[1] = 0;
			}
			this.ownPlaysAux = copyOwnPlays(this.ownPlays);
			auxT = table.clone();
		}
		return play;
	}
	
	private boolean checkAllPlays(String play) {
		for(String aux : this.ownPlaysAux) {
			if ( aux.equals(play) ) return true;
		}
		
		for(String aux : this.ownPlays) {
			if ( aux.equals(play) ) return true;
		}
		
		for(String aux : this.playerPlaysAux) {
			if ( aux.equals(play) ) return true;
		}
		
		for(String aux : this.playerPlays) {
			if ( aux.equals(play) ) return true;
		}

		return false;
	}
	
	private String checkPlayerAboutToWin(Table table) {		
		/* 
		 * [X]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(0, 1) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				return "0|2";
		}
		
		/* 
		 * [X]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(0, 2) )
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				return "0|1";
		
		/* 
		 * [ ]   [X]   [X]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 1) && searchPlay(0, 2) )
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				return "0|0";

		/* ------ */
		
		/* 
		 * [X]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(1, 0) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				return "2|0";
		}
		
		/* 
		 * [X]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(2, 0) )
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				return "1|0";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(1, 0) && searchPlay(2, 0) )
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				return "0|0";
		
		/* ------ */
		
		/* 
		 * [X]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(1, 1) )
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				return "2|2";
		
		/* 
		 * [X]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(0, 0) && searchPlay(2, 2) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(1, 1) && searchPlay(2, 2) )
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				return "0|0";
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [X]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(1, 0) && searchPlay(1, 1) )
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				return "1|2";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(1, 0) && searchPlay(1, 2) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [X]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(1, 1) && searchPlay(1, 2) )
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				return "1|0";
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [X]   [X]   [ ]
		*/
		if ( searchPlay(2, 0) && searchPlay(2, 1) )
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				return "2|2";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [X]
		*/
		if ( searchPlay(2, 0) && searchPlay(2, 2) )
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				return "2|1";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [X]
		*/
		if ( searchPlay(2, 1) && searchPlay(2, 2) )
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				return "2|0";
		
		/* ------ */
		
		/* 
		 * [ ]   [X]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 1) && searchPlay(1, 1) )
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				return "2|1";
		
		/* 
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		*/
		if ( searchPlay(0, 1) && searchPlay(2, 1) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [X]   [ ]
		*/
		if ( searchPlay(1, 1) && searchPlay(2, 1) )
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				return "0|1";
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 2) & searchPlay(1, 2) )
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				return "2|2";
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(0, 2) && searchPlay(2, 2) )
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				return "1|2";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(1, 2) && searchPlay(2, 2) )
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				return "0|2";
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 2) && searchPlay(1, 1) )
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				return "2|0";
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(0, 2) && searchPlay(2, 0) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				return "1|1";
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(1, 1) && searchPlay(2, 0) )
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				return "0|2";
		
		return null;
	}
	
	private Integer calculatePlayerChancesToWin(Table table) {		
		Integer chances = 0;
		
		/* 
		 * [X]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(0, 1) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				chances++;
		}
		
		/* 
		 * [X]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(0, 2) )
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [X]   [X]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 1) && searchPlay(0, 2) )
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				chances++;

		/* ------ */
		
		/* 
		 * [X]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(1, 0) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				chances++;
		}
		
		/* 
		 * [X]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(2, 0) )
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(1, 0) && searchPlay(2, 0) )
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				chances++;
		
		/* ------ */
		
		/* 
		 * [X]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 0) && searchPlay(1, 1) )
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [X]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(0, 0) && searchPlay(2, 2) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(1, 1) && searchPlay(2, 2) )
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				chances++;
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [X]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(1, 0) && searchPlay(1, 1) )
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(1, 0) && searchPlay(1, 2) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [X]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(1, 1) && searchPlay(1, 2) )
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				chances++;
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [X]   [X]   [ ]
		*/
		if ( searchPlay(2, 0) && searchPlay(2, 1) )
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [X]
		*/
		if ( searchPlay(2, 0) && searchPlay(2, 2) )
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [X]
		*/
		if ( searchPlay(2, 1) && searchPlay(2, 2) )
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				chances++;
		
		/* ------ */
		
		/* 
		 * [ ]   [X]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 1) && searchPlay(1, 1) )
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		*/
		if ( searchPlay(0, 1) && searchPlay(2, 1) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [ ]   [X]   [ ]
		*/
		if ( searchPlay(1, 1) && searchPlay(2, 1) )
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				chances++;
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 2) & searchPlay(1, 2) )
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(0, 2) && searchPlay(2, 2) )
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [X]
		*/
		if ( searchPlay(1, 2) && searchPlay(2, 2) )
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				chances++;
		
		/* ------ */
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [X]   [ ]
		 * [ ]   [ ]   [ ]
		*/
		if ( searchPlay(0, 2) && searchPlay(1, 1) )
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [X]
		 * [ ]   [ ]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(0, 2) && searchPlay(2, 0) )
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		
		/* 
		 * [ ]   [ ]   [ ]
		 * [ ]   [X]   [ ]
		 * [X]   [ ]   [ ]
		*/
		if ( searchPlay(1, 1) && searchPlay(2, 0) )
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				chances++;
		
		return chances;
	}
	
	public boolean searchPlay(Integer col, Integer row) {
		String [] playBeingCheck = new String[2];
		
		for (String s : playerPlays) {
			playBeingCheck = s.split("|");
			
			if ( Integer.parseInt(playBeingCheck[0]) == col ) {
				if ( Integer.parseInt(playBeingCheck[2]) == row)
					return true;
			}
		}
		return false;
	}
	
	public Integer calculateChancesToWin(Table table) {
		int chances = 0;
		
		if ( this.ownPlaysAux.size() <= 1 )
			return 0;
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[0][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[0][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[0][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				chances++;
		}
		
		/* --------------- */

		if ( table.getGameTable()[1][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		}
		
		/* ------------------ */
		
		if ( table.getGameTable()[2][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[2][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[2][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				chances++;
		}
		
		/* ------------------- */
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][0].getPlay().equals(" ") )
				chances++;
		}
		
		/* --------------------- */
		
		if ( table.getGameTable()[0][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][1].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][1].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		}
		
		/* ---------------------- */
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][2].getPlay().equals(" ") )
				chances++;
		}
		
		/* ------------------------ */
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][0].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][2].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		}
		
		/* ------------------------- */
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[1][1].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[2][0].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[1][1].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[0][2].getPlay().equals(" ") )
				chances++;
		}
		
		if ( table.getGameTable()[0][2].getPlay().equals(this.playPiece) && 
				table.getGameTable()[2][0].getPlay().equals(this.playPiece) ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") )
				chances++;
		}
		return chances;
	}
	
	public boolean checkPlayerPlays() {
		if ( this.playerPlays.size() == 0 ) 
			return true;
		return false;
	}
	
	public boolean checkOwnPlays() {
		if ( this.ownPlays.size() == 0 )
			return true;
		return false;
	}
	
	public boolean addPlayerPlay(Play play) {
		StringBuilder sb = new StringBuilder();
		
		if ( play.getCol() == null || play.getRow() == null )
			return false;
		
		else {
			sb.append(play.getCol());
			sb.append("|");
			sb.append(play.getRow());
			
			this.playerPlays.add(sb.toString());
			
			return true;
		}
	}
	
	public void remove(String availablePlayToRemove) {
		this.availablePlays.remove(availablePlayToRemove);
	}

	public String getPlayPiece() {
		return this.playPiece;
	}
	
}