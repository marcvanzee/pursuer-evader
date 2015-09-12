package nl.uu.cs.ias.pursuerevader.parameters;

/**
 * <p><b>GridProperties class</b></p>
 * 
 * <p>Constants that define the field we are playing on. You are free to use this information when programming an A.I. for the game.</p>
 * 
 * @author M.v.Zee
 * 
 */

public class GridProperties {
	/**
	 * Width of the field that will be shown on the GUI
	 */
	public static final int WIDTH = 600;
	
	/**
	 * Height of the field that will be shown on the GUI
	 */
	public static final int HEIGHT = 600;
	
	/**
	 * relativity mode of simulation. See GameCommunicator class, setRelativity method for more information.
	 */
	public static int relativity = 0;
	
	/**
	 * The number of ticks that a round lasts
	 */
	public static final int NUM_TICKS = 100;
	
	/**
	 * The minimal distance between the evader and pursuer for the pursuer to win the game
	 */
	public static final int CATCH_DISTANCE = GridProperties.HEIGHT/100;
	
	/**
	 * The different values for relativity
	 */
	public static final int ABSOLUTE = 0;
	public static final int POSITION_RELATIVE = 1;
	public static final int RELATIVE = 2;
}
