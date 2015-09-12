package pursuerevader.game;

import pursuerevader.parameters.GridProperties;
import pursuerevader.ui.BaseGUI;

/**
 * <p><b>GameCommunicator class</b></p>
 * 
 * <p>This class allows communication with the game, and makes sure an applet is started.<br />
 * It defines the skeleton for a AI and can be extended by implementing the required methods.
 * 
 * @see nl.uu.cs.ias.pursuerevader.gui.BaseGUI nl.uu.cs.ias.pursuerevader.game.GameInterface nl.uu.cs.ias.pursuerevader.game.Game
 * 
 * @author M.v.Zee
 *
 */
public abstract class GameCommunicator extends BaseGUI implements GameInterface {
	private static final long serialVersionUID = 5721004154735024544L;
	private boolean playDefaultAI = false;
	private boolean playingGame = false;
	
    private Thread game;
	
    /**
     * This is a listeners that you can use to communicate with the agent.
     */
	protected AgentListener evaderListener, pursuerListener;
	
	/**
	 * Called by the game when a game has started..
	 */
	public abstract void gameStarted();
	
	/**
	 * Called by the game when a round has started. A game can consists of any number of rounds,
	 * which is determined by the user when starting the simulation.
	 */
	public abstract void roundStarted();
	
	/**
	 * Called by the game when the current round has ended. This happens when
	 * the evader has been caught, or when the time is finished. If the evader
	 * has not been caught when the time is finished, the evader has won. If the
	 * evader has been won, logically, the pursuer wins.
	 * 
	 * @param evaderWon		Whether the evader was the winner of this round
	 */
	public abstract void roundEnded(boolean evaderWon);
	
	/**
	 * Called by the game when the game has ended
	 */
	public void gameEnded() {
		playingGame = false;
	}
	
	/**
	 * Called by the game when the pursuer and evader have been updated
	 * Use this method to do things accordingly
	 * Available methods:
	 * 
	 */
	public abstract void update();
	
	/**
	 * Internal method.
	 * Called when all agents have been placed into the game. This methods
	 * attached agent listeners for both agents.
	 * 
	 * @param pursuer		Pursuer agent
	 * @param evader		Evader agent
	 */
	public final void agentsPlaced(Agent pursuer, Agent evader) {		
		evaderListener = new AgentListener(evader);
		pursuerListener = new AgentListener(pursuer);
	}

	/**
	 * A game is started using this method<br /><br />
	 * 
	 * When implementing the SOM, it will be necessary to train it by playing this
	 * game very often. Similar to the TOTO implementation that you programmed using Netlogo,
	 * you do not want to have visual feedback when testing. This can be done by setting
	 * the second argument of the startSimulation call to false. When it is set to true,
	 * visual feedback will be given.
	 * 
	 * @param numRounds		The number of rounds to play
	 * @param paint			Whether to output the game in the Applet, or to play in the background. Usually the game is played in the background when it is played for a large amount of times (when training a neural network for example).
	 * 
	 */
    protected void startSimulation(int numRounds, boolean paint) {
    	while (playingGame) {
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	playingGame = true;
    	Game g = new Game(this, numRounds, paint);
    	if (playDefaultAI) g.playDefaultAI();
    	game = new Thread(g);
		game.start();
    }
    
    /**
     * Play the default AI for the evader, which simply moves forward all the time.
     */
    protected void playDefaultAI() {
    	playDefaultAI = true;
    }
    
    /**
     * There are three different way in which the game can be visualized using the Applet:<br />
     * - <b>GridProperties.ABSOLUTE</b>:			Use absolute axes, the agents will both move and turn<br />
     * - <b>GridProperties.POSITION_RELATIVE</b>:	Set (0,0) on the position of the pursuer<br />
     * - <b>GridProperties.RELATIVE</b>:			Position relative, and also rotate axes so that the angle of the pursuer is always 0.<br /><br />
     * The first choice is the most clear one, because it will show the movement and direction of both agents.
     * It is advised that you use the RELATIVE mode when training the SOM. This will decrease the numbers of parameters that matter drastically.
     * The direction and movement of the pursuer are both set to zero when playing in this mode, so the only values that matter are those of the evader.
     * This allows you to train the SOM with less parameters, which makes the problem easier.
     * 
     * @param relativity		Relativity choice
     */
    protected void setRelativity(int relativity) {
    	GridProperties.relativity = relativity;
    }
    
    /**
     * Define the speed to play the game on the Applet with.
     * 
     * @param speed		Speed of game [1-10]
     */
    protected void setGameSpeed(int speed) {
    	Game.DELAY = 100 - speed*10;;
    }
}
