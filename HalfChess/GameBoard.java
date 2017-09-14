package HalfChess;

import java.util.Arrays;
import java.util.Collections;

public class GameBoard {

	//The game consists of half a XiangQi board.
	//That is, the grid is a 4 by 8 and filled with face down pieces.
	private GamePiece[][] board;
	public static final int BOARD_LENGTH = 4;
	public static final int BOARD_WIDTH = 8;

	
	//There are 5 rank 1 troops, 2 rank 2 troops, and so forth as can be seen in the rank set.
	public static final int[] RANK_SET = {1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7};
	public GameBoard() {
		board = new GamePiece[BOARD_LENGTH][BOARD_WIDTH];
		resetBoard();
		

	}
	
	public void resetBoard() {
		
		GamePiece[] gamePieceSet = new GamePiece[BOARD_LENGTH * BOARD_WIDTH];
		
		for(int i = 0;i < gamePieceSet.length; i++) {
			if(i < BOARD_LENGTH* BOARD_WIDTH /2)
				gamePieceSet[i] = new GamePiece(PlayerType.WHITE, RANK_SET[i],false);
			else
				gamePieceSet[i] = new GamePiece(PlayerType.BLACK,
						RANK_SET[i-(BOARD_LENGTH * BOARD_WIDTH/2)],false);
		}
		
		Collections.shuffle(Arrays.asList(gamePieceSet));
		
		int setIndex = 0;
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[0].length; j++)
				board[i][j] = gamePieceSet[setIndex++];
	}
	
	public void flipUp(int r, int c) {
		board[r][c].flipUp();
	}
	
	//Function attempts to move piece in (r1,c1) to the space (r2,c2) and returns a string depending on the success.
	public String attemptCapture(int r1, int c1, int r2, int c2) {
		if(board[r1][c1] == null)
			return "First selected piece must be yours.";
		if(board[r2][c2] != null) {
			if(board[r1][c1].getRank() == 0 || board[r2][c2].getRank() == 0)
				return "Both selected spots must be face up.";
			if(board[r1][c1].getOwner() == board[r2][c2].getOwner())
				return "Members of the same side cannot capture each other.";
			if(!board[r1][c1].greaterOrEqualRank(board[r2][c2]))
				return "Only higher ranks can capture lower ranks";
		}
		board[r2][c2] = board[r1][c1];
		board[r1][c1] = null;
		return "Capture Successful.";
	}
	
	public int getRank(int r, int c) {
		if(board[r][c] == null)
			return -1;
		return board[r][c].getRank();
	}
	
	public PlayerType getOwner(int r, int c) {
		if(board[r][c] == null)
			return null;
		return board[r][c].getOwner();
	
	}
	
	public void printBoard() {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++)
				System.out.print(board[i][j].getRank() + " " + board[i][j].getOwner() + " ");
			System.out.println();
		}
	}
	

}
