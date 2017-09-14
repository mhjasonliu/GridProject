package HalfChess;

public class GamePiece {

	private PlayerType owner;
	private int rank;
	private boolean faceUp;
	
	public GamePiece(PlayerType p, int r, boolean faceUp) {
		owner = p;
		rank = r;
		this.faceUp = faceUp;
	}
	
	public boolean greaterOrEqualRank(GamePiece other) {
		if(other == null)
			return true;
		if(this.rank == 1 && other.rank == 9)
			return true;
		if(this.rank == 9 && other.rank == 1)
			return false;
		return this.rank >= other.rank;
	}
	
	public void flipUp() {
		faceUp = true;
	}
	
	public PlayerType getOwner() {
		if(faceUp)
			return owner;
		return PlayerType.UNKNOWN;
	}
	
	//if the gamepiece is face up, the proper piece rank is returned. Otherwise, 0 is returned.
	public int getRank() {
		if(faceUp)
			return rank;
		return 0;
	}
}
