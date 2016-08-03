package model;

public class Scan {
	
	private Integer col;
	private Integer row;
	private String keyBoardIn;
	
	public Scan(String keyBoardIn) {
		init(keyBoardIn);
	}
	
	private void init(String keyBoardIn) {
		if ( keyBoardIn.equals("1") || keyBoardIn.equals("2") || keyBoardIn.equals("3") ) {
			if ( keyBoardIn.equals("1") ) this.col = 0;
			if ( keyBoardIn.equals("2") ) this.col = 1;
			if ( keyBoardIn.equals("3") ) this.col = 2;
			
			this.row = 2;
		}
		
		if ( keyBoardIn.equals("4") || keyBoardIn.equals("5") || keyBoardIn.equals("6") ) {
			if ( keyBoardIn.equals("4") ) this.col = 0;
			if ( keyBoardIn.equals("5") ) this.col = 1;
			if ( keyBoardIn.equals("6") ) this.col = 2;
			
			this.row = 1;
		}
		
		if ( keyBoardIn.equals("7") || keyBoardIn.equals("8") || keyBoardIn.equals("9") ) {
			if ( keyBoardIn.equals("7") ) this.col = 0;
			if ( keyBoardIn.equals("8") ) this.col = 1;
			if ( keyBoardIn.equals("9") ) this.col = 2;
			
			this.row = 0;
		}
	}

	public Integer getCol() {
		return col;
	}

	public Integer getRow() {
		return row;
	}

	public String getKeyBoardIn() {
		return keyBoardIn;
	}

}
