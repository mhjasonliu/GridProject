package HalfChess;

public class GamePlayException extends Exception {
	
	private String msg;
	GamePlayException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
}
