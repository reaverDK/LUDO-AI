package LUDOSimulator;
import java.util.Random;
/**
 * Example of automatic LUDO player
 * @author Adam Czerwinski
 * 
 * @version 0.9
 *
 */
public class SemiSmartLUDOPlayer implements LUDOPlayer {
	
	LUDOBoard board;
	Random rand;
	public SemiSmartLUDOPlayer(LUDOBoard board)
	{
		this.board = board;
		rand = new Random();
	}
	
	public void play() {
		board.print("Semi Smart player playing");

		int[] myBricksValue = new int[4];  
		board.rollDice();
		float max =-1;
		int bestIndex = -1;
		for(int i=0;i<4;i++)
		{
			float value = analyzeBrickSituation(i); 
			if(value>max&&value>0) {
				bestIndex = i;
				max = value;
			}
		}
		if(bestIndex!=-1) board.moveBrick(bestIndex);
	}
	public float analyzeBrickSituation(int i) {
		if(board.moveable(i)) {
			int[][] current_board = board.getBoardState();
			int[][] new_board = board.getNewBoardState(i, board.getMyColor(), board.getDice());
			
			if(hitOpponentHome(current_board,new_board)) {
				return 5+rand.nextFloat();
			}
			
			//*****Add more code here *******//////
			

			else {
				return 1+rand.nextFloat();
			}
		}
		else {
			return 0;
		}
	}


	private boolean moveOut(int[][] current_board, int[][] new_board) {
		for(int i=0;i<4;i++) {
			if(board.inStartArea(current_board[board.getMyColor()][i],board.getMyColor())&&!board.inStartArea(new_board[board.getMyColor()][i],board.getMyColor())) {
				return true;
			}
		}
		return false;
	}

	private boolean hitOpponentHome(int[][] current_board, int[][] new_board) {
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(board.getMyColor()!=i) {
					if(board.atField(current_board[i][j])&&!board.atField(new_board[i][j])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean hitMySelfHome(int[][] current_board, int[][] new_board) {
		for(int i=0;i<4;i++) {
			if(!board.inStartArea(current_board[board.getMyColor()][i],board.getMyColor())&&board.inStartArea(new_board[board.getMyColor()][i],board.getMyColor())) {
				return true;
			}
		}
		return false;
	}
}
