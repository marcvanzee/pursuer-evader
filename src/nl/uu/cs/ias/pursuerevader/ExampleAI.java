package nl.uu.cs.ias.pursuerevader;

import nl.uu.cs.ias.pursuerevader.debug.Debug;
import nl.uu.cs.ias.pursuerevader.game.GameCommunicator;
import nl.uu.cs.ias.pursuerevader.parameters.GridProperties;

/**
 * <p><b>ExampleAI</b></p>
 * 
 * <p><b>Introduction</b><br />
 * This is an example implementation of the Pursuer/Evader game.
 * The class shows the functionalities of the PursuerEvader package. It does not actually represent an AI for the evader in any way; this is the task of the student.<br /></p>
 * 
 * <p><b>Game Communication</b><br />
 * As can be seen, this class extends the GameCommunicator class, which is a requirement
 * to communicate with the game. Five important methods are defined below, which can be
 * used to receive information from the game: <i>gameStarted(), roundStarted(), update(), 
 * roundEnded(boolean winner)</i> and <i>gameEnded()</i>. The explanation of these methods
 * can be found in the Javadoc of the GameCommunicator class.</p>
 * 
 * <p><b>The Applet</b><br />
 * When you run this class, an Applet will load. On default, this class will play the game
 * for five rounds with visual feedback using the "absolute" view. It is useful to look
 * through the code to find out what the possibilities of the simulation are. Once the Applet 
 * is loaded, it can be used to experiment with different views. Try out different modes of 
 * relativity and see if you can understand them! For more detailed information on why you will
 * need the relativity settings, please see the Javadoc of the GameCommunicator class, the
 * setRelativity() method.</p>
 * 
 * <p><b>Available methods in this class</b><br />
 * See the Javadoc of the GameCommunicator class for a list of methods that can be used in this class.</p>
 * 
 * <p><b>More Information</b><br />
 * To create the Self-Organizing Map, you will need to extract important information such as the speed and
 * location of the agents. Below is a list of classes where useful information can be found. We refer to 
 * the Javadoc of these classes for more detailed information:<br />
 * @see nl.uu.cs.ias.pursuerevader.game.AgentListener
 * @see nl.uu.cs.ias.pursuerevader.game.Game
 * @see nl.uu.cs.ias.pursuerevader.game.GameCommunicator
 * @see nl.uu.cs.ias.pursuerevader.parameters.GridProperties
 * @see nl.uu.cs.ias.pursuerevader.parameters.AgentProperties
 */
public class ExampleAI extends GameCommunicator {
	private static final long serialVersionUID = 3962525847855401824L;
	
	int numRounds = 5000;
	int countEvaderWon = 0;
	
	public void start() {
		//setRelativity(GridProperties.RELATIVE);	// how to orient and position the axes of the game
		
		setGameSpeed(5);							// speed of the simulation in range [1,9]
				
		Debug.DEBUGGING = false;					// whether to print debugging message to the debugging window

		startSimulation(numRounds, false);			// run a simulation for <numRounds> rounds and enable visual feedback in the gui
	}
	
	public void gameStarted() {
		Debug.log("runner> game started");
	}
	
	public void roundStarted() {
	}
	
	public void update() {
		evaderListener.setNextTurnAngle(0.01);			// current response: always move forward. this call should be in thie methods, else
													// the simulation will hang!
		
	}
	
	public void roundEnded(boolean evaderWon) {
		Debug.log("runner> round ended, winner: " + (evaderWon?"evader":"pursuer"));
		
		if (evaderWon) 
			countEvaderWon++;
	}
	
	public void gameEnded() {
		super.gameEnded(); //------------------|	// do not remove this line!
		
		System.out.println("evader won " + countEvaderWon + " times, which is " + ((countEvaderWon*100)/numRounds) + "% of the time.");
		
		countEvaderWon=0;
		System.out.println(		GridProperties.WIDTH);
	}
}
