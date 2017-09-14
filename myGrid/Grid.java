package myGrid;

public class Grid{

	private static final int MIN_UNSTABLE_PILE = 4;
	private static final int DECAY_VALUE = 1;
	//pseudo-viscosity
	private static final int VISCOSITY = 10;
	private int[][] values;
	private int gridLength;
	
	public Grid(int gridLength) {
		values = new int[gridLength][gridLength];
		this.gridLength = gridLength;
		
	}
	
	public int getgridLength() {
		return gridLength;
	}
	
	public int getValueAt(int x, int y) {
		return values[x][y];
	}
	
	public void setValueAt(int x, int y, int v) {
		
		if(v > 0)
			values[x][y] = v;
		else
			values[x][y] = 0;
	}
	
	public void incrementValueAt(int x, int y, int v) {
			values[x][y] += v;
			if(values[x][y] < 0)
				values[x][y] = 0;
	}
	
	//force piles to scatter to adjacent tiles
	public void spreadOp() {
		int[][] temp = new int[gridLength][gridLength];
		

		//done by collecting values into temporary array
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length; j++) {
				if(values[i][j] < MIN_UNSTABLE_PILE)
					continue;
				if(i > 0) {
					temp[i-1][j]++;
					values[i][j]--;
				}
				if(i < values.length-1) {
					temp[i+1][j]++;
					values[i][j]--;
				}
				if(j > 0) {
					temp[i][j-1]++;
					values[i][j]--;
				}
				if(j < values.length-1) {
					temp[i][j+1]++;
					values[i][j]--;
				}
			}
		}
		
		//collect scatter into original array.
		for(int i = 0; i < values.length; i++)
			for(int j = 0; j < values.length; j++)
				values[i][j] += temp[i][j];
	}
	
	public void fractionalSpreadOp() {
		int[][] temp = new int[gridLength][gridLength];
		
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length; j++) {
				if(values[i][j] < MIN_UNSTABLE_PILE)
					continue;
				int adjacent = 4;
				if(i > 0 || i < values.length-1) {
					adjacent--;
				}
				if(j > 0 || j < values.length-1) {
					adjacent--;
				}
				int transfer = values[i][j] * adjacent / VISCOSITY;
				
				if(i > 0) {
					temp[i-1][j]+=transfer;
					values[i][j]-=transfer;
				}
				if(i < values.length-1) {
					temp[i+1][j]+=transfer;
					values[i][j]-=transfer;
				}
				if(j > 0) {
					temp[i][j-1]+=transfer;
					values[i][j]-=transfer;
				}
				if(j < values.length-1) {
					temp[i][j+1]+=transfer;
					values[i][j]-=transfer;
				}
				
			}
		}
		
		for(int i = 0; i < values.length; i++)
			for(int j = 0; j < values.length; j++)
				values[i][j] += temp[i][j];
		
	}
	
	public void decayOp() {
		for(int i = 0; i < values.length;i++)
			for(int j = 0; j < values.length; j++)
				if(values[i][j] >= DECAY_VALUE)
					values[i][j] -= DECAY_VALUE;
	}
	
	public void gameOfLife() {
		int[][] newValues = new int[values.length][values.length];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length;j++) {
				int numAlive = 0;
				//Count the number of alive neighbors
				if(i >= 1) {
					if(j >= 1)
						numAlive += (values[i-1][j-1] == 0) ? 0 : 1;
					numAlive += values[i-1][j] == 0? 0 : 1;
					if(j <= values.length-2)
						numAlive += (values[i-1][j+1] == 0) ? 0 : 1;
				}
				if(i <= values.length-2) {
					if(j >= 1)
						numAlive += (values[i+1][j-1] == 0) ? 0 : 1;
					numAlive += (values[i+1][j] == 0)? 0 : 1;
					if(j <= values.length-2)
						numAlive += (values[i+1][j+1] == 0)? 0 : 1;
				}
				if(j >= 1)
					numAlive += (values[i][j-1] == 0) ? 0 : 1;
				if(j <= values.length-2)
					numAlive += (values[i][j+1] == 0) ? 0 : 1;
				
				//Check the cell to see if it lives to the next generation and update new grid
				if(values[i][j] != 0) {
					if(numAlive < 2 || numAlive > 3)
						newValues[i][j] = 0;
					else
						newValues[i][j] = 1;
				}
				else {
					if(numAlive == 3)
						newValues[i][j] = 1;
					else
						newValues[i][j] = 0;
				}
			}
		}
		
		//Update old grid with next generation
		values = newValues;
	}
	

	
	public void clear() {
		for(int i = 0; i < values.length;i++)
			for(int j = 0; j < values.length; j++)
				values[i][j] = 0;
	}
	

}
