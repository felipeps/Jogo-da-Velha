package controller;

import java.util.ArrayList;
import java.util.Random;

import model.Play;
import model.Table;

public class PController {
	
	private ArrayList<String> playerPlays;
	private ArrayList<String> ownPlays;
	private String playPiece;
	
	public PController(String playPiece) {
		this.playPiece = playPiece;
		playerPlays = new ArrayList<String>();
		ownPlays = new ArrayList<String>();
	}
	
	public boolean doPlay(Table table, boolean firstToPlay) {
		Integer col = 0;
		Integer row = 0;
		String bestPlay;
		String[] play = new String[2];
		String[] randPlays = new String[3];
		Random generator = new Random();
		
		if ( firstToPlay ) {
			if ( this.ownPlays.size() == 0 ) {
				randPlays[0] = "0|0"; randPlays[1] = "1|1"; randPlays[2] = "0|2";
				
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
					
					if ( bestPlay == null ) {
						bestPlay = bestPlay(table);
					}
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
				
				if ( bestPlay == null ) {
					bestPlay = defensivePlay(table);
				}
			}
			
			play = bestPlay.split("|");
			
			col = new Integer(play[0]);
			row = new Integer(play[2]);
			
			table.getGameTable()[col][row] = new Play(playPiece, "Computer", col, row);
		}
		
		this.ownPlays.add(col + "|" + row);
		return true;
	}
	
	public String defensivePlay(Table table) {
		String lastPlayerPlay = this.playerPlays.get(this.playerPlays.size() - 1);
		ArrayList<String> randPlays = new ArrayList<String>();
		Random generator = new Random();
		
		if ( this.ownPlays.size() == 0 ) {
			if ( table.getGameTable()[1][1].getPlay().equals(" ") ) return "1|1";
		}
		
		if ( lastPlayerPlay.equals("0|0") ) {
			randPlays.add("0|1");
			randPlays.add("1|0");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("0|1") ) {
			randPlays.add("0|0");
			randPlays.add("0|2");
			randPlays.add("1|1");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("0|2") ) {
			randPlays.add("0|1");
			randPlays.add("1|2");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("1|0") ) {
			randPlays.add("1|1");
			randPlays.add("2|0");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("1|1") ) {
			randPlays.add("1|0");
			randPlays.add("1|2");
			randPlays.add("0|1");
			randPlays.add("2|1");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("1|2") ) {
			randPlays.add("1|1");
			randPlays.add("2|2");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("2|0") ) {
			randPlays.add("2|1");
			randPlays.add("1|0");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("2|1") ) {
			randPlays.add("2|0");
			randPlays.add("2|2");
			randPlays.add("1|1");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		if ( lastPlayerPlay.equals("2|2") ) {
			randPlays.add("2|1");
			randPlays.add("1|2");
			
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		return null;
	}
	
	public String checkGonnaWin(Table table) {
		if ( this.ownPlays.size() <= 1 ) {
			return null;
		}
		
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
	
	public String bestPlay(Table table) {
		String lastPlay = "";
		String beforeLastPlay = "";
		boolean moreThanOnePlay = false;
		
		if ( this.ownPlays.size() != 0 ) {	
			Random generator = new Random();
			String[] randPlays = new String[2];
			ArrayList<Integer> randOptions;
			
			lastPlay = this.ownPlays.get(this.ownPlays.size() - 1);
			
			if ( this.ownPlays.size() > 1 ) {
				beforeLastPlay = this.ownPlays.get(this.ownPlays.size() - 2);
				moreThanOnePlay = true;
			}
			
			if ( lastPlay.equals("0|0") ) {
				if ( moreThanOnePlay ) {
					if ( beforeLastPlay.equals("2|2") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[0][1].getPlay().equals(" "))
										return "0|2";
									randOptions.remove((Integer) 1);
								
								case 2:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[2][1].getPlay().equals(" "))
										return "2|0";
									randOptions.remove((Integer) 2);
							}
						} while ( randOptions.size() > 0 );
					}
					
					if ( beforeLastPlay.equals("2|0") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2); randOptions.add(3);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[2][2].getPlay().equals(" ") )
										return "1|1";
									randOptions.remove((Integer) 1);
								
								case 2:
									if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[0][1].getPlay().equals(" ") )
										return "0|2";
									randOptions.remove((Integer) 2);
								
								case 3:
									if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[2][1].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" ") )
										return "2|2";	
									randOptions.remove(3);
							}
						} while ( randOptions.size() > 0 );
					}

					if ( beforeLastPlay.equals("0|2") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" ") )
										return "2|0";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" "))
										return "2|2";
									randOptions.remove((Integer) 2);
							}
						
						} while ( randOptions.size() > 0 );
					}
				}
				
				/* */
		
				randOptions = new ArrayList<Integer>();
				
				randOptions.add(1); randOptions.add(2); randOptions.add(3);
				
				do {
					switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
						case 1:
							if ( table.getGameTable()[2][0].getPlay().equals(" ") ) {
								//	1 1 / 0 2 / 2 2
								if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[2][2].getPlay().equals(" ") )
									return "2|0";	
								
								// 0 2 / 1 1 / 0 1
								if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[0][1].getPlay().equals(" ") )
									return "2|0";
								
								// 2 2 / 2 1 / 1 1
								if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[2][1].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") )
									return "2|0";
							}
							randOptions.remove((Integer) 1);
							
						case 2:
							if ( table.getGameTable()[2][2].getPlay().equals(" ") ) {
								// 0 2 / 1 2 / 0 1
								if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[0][1].getPlay().equals(" ") )
									return "2|2";
								
								// 2 0 / 1 0 / 2 1
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[2][1].getPlay().equals(" ") )
									return "2|2";
							}
							randOptions.remove((Integer) 2);
							
						case 3:
							if ( table.getGameTable()[0][2].getPlay().equals(" ") ) {
								// 2 0 / 1 0 / 1 1
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") )
									return "0|2";
								
								// 2 2 / 1 2 / 1 1
								if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") )
									return "0|2";
							}
							randOptions.remove((Integer) 3);
					}
				
				} while ( randOptions.size() > 0 );
				
				/* */
				
				if ( table.getGameTable()[0][1].getPlay().equals(" ") &&
						table.getGameTable()[0][2].getPlay().equals(" ") ) {
					
					randPlays[0] = "0|1"; randPlays[1] = "0|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
						table.getGameTable()[2][0].getPlay().equals(" ") ) {
					
					randPlays[0] = "1|0"; randPlays[1] = "2|0";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
						table.getGameTable()[2][2].getPlay().equals(" ") ) {

					randPlays[0] = "1|1"; randPlays[1] = "2|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 0|0 */
			
			if ( lastPlay.equals("0|1") ) {
				if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
						table.getGameTable()[0][2].getPlay().equals(" ") ) {

					randPlays[0] = "0|0"; randPlays[1] = "0|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				
				}
				if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
						table.getGameTable()[2][1].getPlay().equals(" ") ) {
					
					randPlays[0] = "1|1"; randPlays[1] = "2|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 0|1 */
			
			if ( lastPlay.equals("0|2") ) {
				if ( moreThanOnePlay ) {
					if ( beforeLastPlay.equals("0|0") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" "))
										return "2|0";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" "))
										return "2|2";
									randOptions.remove((Integer) 2);
							}
						
						} while( randOptions.size() > 0 );
					}
					
					if ( beforeLastPlay.equals("2|0") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2); randOptions.add(3);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[2][2].getPlay().equals(" "))
										return "1|1";
									randOptions.remove(1);
									
								case 2:
									if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[2][2].getPlay().equals(" ") )
										return "0|2";
									randOptions.remove(2);
									
								case 3:
									if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[2][1].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") )
										return "2|2";		
									randOptions.remove(3);
							}
						
						} while( randOptions.size() > 0 );
					}

					if ( beforeLastPlay.equals("2|2") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2); randOptions.add(3);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[0][1].getPlay().equals(" ") )
										return "0|0";
									randOptions.remove(1);
									
								case 2:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[2][1].getPlay().equals(" "))
										return "2|0";
									randOptions.remove(2);
									
								case 3:
									if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
											table.getGameTable()[0][0].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" "))
										return "1|1";
									randOptions.remove(3);
							}
						
						} while ( randOptions.size() > 0 );
					}
				}
				
				/* =============== */
				
				randOptions = new ArrayList<Integer>();
				
				randOptions.add(1); randOptions.add(2); randOptions.add(3);
				
				do {
					switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
						case 1:
							if ( table.getGameTable()[0][0].getPlay().equals(" ") ) {
								// 2 0 / 1 0 / 1 1
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") )
									return "0|0";
								
								// 2 2 / 1 2/ 1 1
								if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") )
									return "0|0";
							}
							randOptions.remove((Integer) 1);
						
						case 2:
							if ( table.getGameTable()[2][0].equals(" ") ) {
								// 1 1 / 0 2 / 2 2
								if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[2][2].getPlay().equals(" ") )
									return "2|0";
								
								// 0 2 / 1 1 / 2 2 
								if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[2][2].getPlay().equals(" ") )
									return "2|0";
								
								// 2 2 / 2 1 / 0 2
								if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[2][1].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") )
									return "2|0";
							}
							randOptions.remove((Integer) 2);
							
						case 3:
							if ( table.getGameTable()[2][2].getPlay().equals(" ") ) {
								// 0 0 / 1 1 / 0 1
								if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[0][1].getPlay().equals(" ") )
									return "2|2";
								
								// 2 0 / 1 1 / 2 1
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[2][1].getPlay().equals(" ") )
									return "2|2";
								
								// 1 1 / 0 0 / 2 0
								if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
										table.getGameTable()[0][0].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") )
									return "2|2";
								randOptions.remove((Integer) 3);
							}
					}
				
				} while ( randOptions.size() > 0 );
			}
			
			/* FIM LASTPLAY 0|2 */
			
			if ( lastPlay.equals("1|0") ) {
				if ( moreThanOnePlay ) {
					if ( beforeLastPlay.equals("0|0") ) {
						if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
								table.getGameTable()[2][2].getPlay().equals(" ") &&
								table.getGameTable()[1][2].getPlay().equals(" ") )
							return "1|1";
					}
					
					if ( beforeLastPlay.equals("2|0") ) {
						if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
								table.getGameTable()[1][2].getPlay().equals(" ") &&
								table.getGameTable()[0][2].getPlay().equals(" ") )
							return "1|1";
					}
				}
				
				if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
						table.getGameTable()[2][0].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|0"; randPlays[1] = "2|0";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 1|0 */
			
			if ( lastPlay.equals("1|1") ) {
				if ( moreThanOnePlay ) {
					if ( beforeLastPlay.equals("1|0") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1: 
									if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
											table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" ") )
										return "0|0";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[0][0].getPlay().equals(" ") )
										return "2|0";
									randOptions.remove((Integer) 2);
							}
						
						} while ( randOptions.size() > 0 );
					}
					
					if ( beforeLastPlay.equals("1|2") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[2][2].getPlay().equals(" ") )
										return "0|2";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[0][0].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") )
										return "2|2";
									randOptions.remove((Integer) 2);
							}
						
						} while ( randOptions.size() > 0 );
					}
					
					if ( beforeLastPlay.equals("0|0") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2); randOptions.add(3);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" ") )
										return "1|0";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") )
										return "2|0";
									randOptions.remove((Integer) 2);
									
								case 3:
									if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[0][1].getPlay().equals(" ") )
										return "0|2";
									randOptions.remove((Integer) 3);
							}
						
						} while ( randOptions.size() > 0 );
					}
					
					if ( beforeLastPlay.equals("0|2") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" ") )
										return "1|2";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[0][0].getPlay().equals(" ") )
										return "2|2";
									randOptions.remove((Integer) 2);
							}
						} while (randOptions.size() > 0);
					}
					
					if ( beforeLastPlay.equals("2|0") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") )
										return "1|0";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
											table.getGameTable()[1][0].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") )
										return "2|0";
									randOptions.remove((Integer) 2);
							}
						} while (randOptions.size() > 0);
					}
					
					if ( beforeLastPlay.equals("2|2") ) {
						randOptions = new ArrayList<Integer>();
						
						randOptions.add(1); randOptions.add(2);
						
						do {
							switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
								case 1:
									if ( table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[1][0].getPlay().equals(" ") )
										return "1|2";
									randOptions.remove((Integer) 1);
									
								case 2:
									if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
											table.getGameTable()[1][2].getPlay().equals(" ") &&
											table.getGameTable()[2][0].getPlay().equals(" ") )
										return "2|0";
									randOptions.remove((Integer) 2);
							}
						} while (randOptions.size() > 0);
					}
				}

				/* ============= */
		
				randOptions = new ArrayList<Integer>();
				
				randOptions.add(1); randOptions.add(2); randOptions.add(3);
				randOptions.add(4); randOptions.add(5); randOptions.add(5);
				
				do {
					switch ( randOptions.get(generator.nextInt(randOptions.size())) ) {
						case 1:
							if ( table.getGameTable()[1][0].getPlay().equals(" ") ) {
								// 0 0 / 2 2 / 2 0
								if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
										table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") )
									return "1|0";
								
								// 2 0 / 0 2 / 0 0
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[0][0].getPlay().equals(" ") )
									return "1|0";
							}
							randOptions.remove((Integer) 1);
							
						case 2:
							if ( table.getGameTable()[1][2].getPlay().equals(" ") ) {
								// 0 2 / 2 0 / 2 2
								if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[2][2].getPlay().equals(" ") )
									return "1|2";
								
								// 2 2 / 0 0 / 0 2
								if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[0][0].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") )
									return "1|2";
							}
							randOptions.remove((Integer) 2);
							
						case 3:
							if ( table.getGameTable()[0][0].getPlay().equals(" ") ) {
								// 1 0 / 1 2 / 2 0
								if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") )
									return "0|0";
								
								// 2 0 / 1 0 / 0 2
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") )
									return "0|0";
								
								// 0 2 / 2 0 / 0 1
								if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[0][1].getPlay().equals(" "))
									return "0|0";
							}
							randOptions.remove((Integer) 3);
							
						case 4:
							if ( table.getGameTable()[0][2].getPlay().equals(" ") ) {
								// 1 2 / 2 2 / 2 0
								if ( table.getGameTable()[1][2].getPlay().equals(" ") && 
										table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") )
									return "0|2";
								
								// 2 2 / 1 2 / 0 0
								if ( table.getGameTable()[2][2].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[0][0].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") )
									return "0|2";
							}
							randOptions.remove((Integer) 4);
							
						case 5:
							if ( table.getGameTable()[2][0].getPlay().equals(" ") ) {
								// 1 0 / 1 2 / 0 2
								if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") )
									return "2|0";
								
								// 2 0 / 1 0 / 0 2 
								if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
										table.getGameTable()[1][0].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") )
									return "2|0";
							}
							randOptions.remove((Integer) 5);
							
						case 6:
							if ( table.getGameTable()[2][2].getPlay().equals(" ") ) {
								// 1 2 / 0 2 / 1 0
								if ( table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[1][0].getPlay().equals(" ") )
									return "2|2";
								
								// 0 2 / 1 2 / 2 0
								if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
										table.getGameTable()[1][2].getPlay().equals(" ") &&
										table.getGameTable()[2][0].getPlay().equals(" ") )
									return "2|2";
							}
							randOptions.remove((Integer) 6);
					}
				
				} while ( randOptions.size() > 0 );
				
				/* */
				
				if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
						table.getGameTable()[1][2].getPlay().equals(" ") ) {
				
					randPlays[0] = "1|0"; randPlays[1] = "1|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[0][1].getPlay().equals(" ") &&
						table.getGameTable()[2][1].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|1"; randPlays[1] = "2|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
						table.getGameTable()[2][2].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|0"; randPlays[1] = "2|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[0][1].getPlay().equals(" ") &&
						table.getGameTable()[2][1].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|1"; randPlays[1] = "2|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 1|1 */
			
			if ( lastPlay.equals("1|2") ) {
				if ( table.getGameTable()[1][0].getPlay().equals(" ") &&
						table.getGameTable()[1][1].getPlay().equals(" ") ) {
				
					randPlays[0] = "1|0"; randPlays[1] = "1|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
						table.getGameTable()[2][2].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|2"; randPlays[1] = "2|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 1|2 */
			
			if ( lastPlay.equals("2|0") ) {
				if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
						table.getGameTable()[1][0].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|0"; randPlays[1] = "1|0";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[2][1].getPlay().equals(" ") &&
						table.getGameTable()[2][2].getPlay().equals(" ") ) {
					
					randPlays[0] = "2|1"; randPlays[1] = "2|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
						table.getGameTable()[0][2].getPlay().equals(" ") ) {
				
					randPlays[0] = "1|1"; randPlays[1] = "0|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LATPLAY 2|0 */
			
			if ( lastPlay.equals("2|1") ) {
				if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
						table.getGameTable()[2][2].getPlay().equals(" ") ) {
			
					randPlays[0] = "2|0"; randPlays[1] = "2|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[1][1].getPlay().equals(" ") &&
						table.getGameTable()[0][1].getPlay().equals(" ") ) {
				
					randPlays[0] = "1|1"; randPlays[1] = "0|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 2|1 */
			
			if ( lastPlay.equals("2|2") ) {
				if ( table.getGameTable()[2][0].getPlay().equals(" ") &&
						table.getGameTable()[2][1].getPlay().equals(" ") ) {
				
					randPlays[0] = "2|0"; randPlays[1] = "2|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[0][2].getPlay().equals(" ") &&
						table.getGameTable()[1][2].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|2"; randPlays[1] = "1|2";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
				
				if ( table.getGameTable()[0][0].getPlay().equals(" ") &&
						table.getGameTable()[1][1].getPlay().equals(" ") ) {
				
					randPlays[0] = "0|0"; randPlays[1] = "1|1";
					
					return randPlays[generator.nextInt(randPlays.length)];
				}
			}
			
			/* FIM LASTPLAY 2|2 */
		}
		
		return getFirstEmptySpot(table);
	}
	
	public String getFirstEmptySpot(Table table) {
		ArrayList<String> randPlays = new ArrayList<String>();
		String lastPlayerPlay = this.playerPlays.get(this.playerPlays.size() - 1);
		Random generator = new Random();
		
		if ( lastPlayerPlay.equals("0|1") ||
				lastPlayerPlay.equals("1|0") ||
				lastPlayerPlay.equals("1|2") ||
				lastPlayerPlay.equals("2|1") )
			return "1|1";
			
		if ( table.getGameTable()[0][0].getPlay().equals(" ") ) randPlays.add("2|0");
		if ( table.getGameTable()[2][2].getPlay().equals(" ") ) randPlays.add("0|2");
		if ( table.getGameTable()[0][2].getPlay().equals(" ") ) randPlays.add("2|2");
		if ( table.getGameTable()[2][0].getPlay().equals(" ") ) randPlays.add("0|0");
		
		if ( randPlays.size() > 0 ) {
			return randPlays.get(generator.nextInt(randPlays.size()));
		}
		
		else {
			for(int row = 0; row < table.getRows(); row++) {
				for(int col = 0; col < table.getCols(); col++) {
					if ( table.getGameTable()[row][col].getPlay().equals(" ") ) 
						return row + "|" + col;
				}
			}
		}
		return null;
	}
	
	public String checkPlayerAboutToWin(Table table) {		
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
	
	public boolean searchPlay(Integer col, Integer row) {
		//00
		//01
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
	
	public boolean checkPlayerPlays() {
		if ( playerPlays.size() == 0 ) 
			return true;
		
		return false;
	}
	
	public boolean checkOwnPlays() {
		if ( ownPlays.size() == 0 )
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
			
			playerPlays.add(sb.toString());
			
			return true;
		}
	}

	public String getPlayPiece() {
		return playPiece;
	}
	
}