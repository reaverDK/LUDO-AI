package LUDOSimulator;
/**
 * Example of automatic LUDO player
 * @author Adam Czerwinski
 * 
 * @version 0.9
 *
 */
public class FifoLUDOPlayer implements LUDOPlayer {
	LUDOBoard board;
	public FifoLUDOPlayer(LUDOBoard board)
	{
		this.board = board;
	}
	public void play() {
		board.print("Fifo player playing");
		board.rollDice();
		for(int i=0;i<4;i++)
		{
			if(board.moveable(i)) {
				board.moveBrick(i);
				return;
			}
		}
	}
	public synchronized void delay() {
		try {
			wait(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
