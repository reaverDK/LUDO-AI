package LUDOSimulator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * Main class the LUDO simulator - "controlles" the game.
 * This is where you decise how many games to play and if 
 * the graphical interface should be visible.
 * 
 * @author Adam Czerwinski
 * 
 * @version 0.9
 */
public class LUDO extends Frame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	static LUDOBoard board;
	public LUDO() 
	{  
		super("LUDO Simulator");
	    setBackground(Color.white);
		board = new LUDOBoard();
		add(board, BorderLayout.CENTER);
    
		Menu optionsMenu = new Menu("Options", true);  
		optionsMenu.add("Reset Game");
		optionsMenu.addActionListener(this);

		MenuBar mbar = new MenuBar();  
	    mbar.add(optionsMenu);
    
		setMenuBar(mbar); // Add the menu bar to the frame.
		setBounds(30,50,1000,800);  // Set size and position of window.
    
		setResizable(false);
		addWindowListener(
	       new WindowAdapter() {
	            public void windowClosing(WindowEvent evt) {
	               LUDO.this.dispose();
				   System.exit(0);
				   
	            }
	         }
         );
		setVisible(visual);
		play();
	}
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getActionCommand()=="Reset Game") {
			board.kill();
		}
	}
	public static boolean visual = true;
	/**
	 * Plays a number of games, which are usefull when all players are automatic.
	 * Remember to set the "visual" field to speed up the simulation time.
	 *
	 */
	public void play() {
		System.out.println("Playing Ludo");
		long time = System.currentTimeMillis();
		int[] result = new int[4];
		board.setPlayer(new ManualLUDOPlayer(board),LUDOBoard.YELLOW);
		//board.setPlayer(new RandomLUDOPlayer(board),LUDOBoard.YELLOW);
		board.setPlayer(new SemiSmartLUDOPlayer(board),LUDOBoard.RED);
		board.setPlayer(new SemiSmartLUDOPlayer(board),LUDOBoard.BLUE);
		board.setPlayer(new SemiSmartLUDOPlayer(board),LUDOBoard.GREEN);
		try {
			for(int i=0;i<1000;i++) {
				board.play();
				board.kill();
				
				result[0]+=board.getPoints()[0];
				result[1]+=board.getPoints()[1];
				result[2]+=board.getPoints()[2];
				result[3]+=board.getPoints()[3];
				
				board.reset();
				board.setPlayer(new ManualLUDOPlayer(board),LUDOBoard.YELLOW);
				//board.setPlayer(new RandomLUDOPlayer(board),LUDOBoard.YELLOW);
				board.setPlayer(new SemiSmartLUDOPlayer(board),LUDOBoard.RED);
				board.setPlayer(new SemiSmartLUDOPlayer(board),LUDOBoard.BLUE);
				board.setPlayer(new SemiSmartLUDOPlayer(board),LUDOBoard.GREEN);
				if((i%500)==0) System.out.print(".");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("Simulation took "+(System.currentTimeMillis()-time)+" miliseconds");
		System.out.println("RESULT:");
		System.out.println("YELLOW Player: "+result[0]);
		System.out.println("RED    Player: "+result[1]);
		System.out.println("BLUE   Player: "+result[2]);
		System.out.println("GREEN  Player: "+result[3]);
		
	}
	
	public static void main(String[] args)
	{
		new LUDO();
	}
}