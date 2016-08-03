package model;

public class Table {
	
	private Integer cols;
	private Integer rows;
	private Play[][] gameTable;
	
	public Table() {}
	
	public Table(Integer cols, Integer rows) {
		this.cols = cols;
		this.rows = rows;
		this.gameTable = new Play[cols][rows];
		
		init();
	}
	
	public void init() {
		for ( int x = 0; x < this.cols; x++ ) {
			for ( int y = 0; y < this.rows; y++) {
				this.gameTable[x][y] = new Play();
			}
		}
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Play[][] getGameTable() {
		return gameTable;
	}

	public void setGameTable(Play[][] gameTable) {
		this.gameTable = gameTable;
	}
	
}