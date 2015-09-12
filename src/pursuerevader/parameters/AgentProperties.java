package pursuerevader.parameters;

import java.awt.Color;

/**
 * <p><b>AgentProperties</b></p>
 * 
 * <p>Constants that defines the agents. You are free to use (but <b>not change!</b>) this information when programming an A.I. for the game.</p>
 * <p>Variables with BOUND in the name define the lower/upper bound for the value that will be assigned to that property of the specific agent.</p>
 * <p>For every game (with a fixed number of rounds), the velocity and turn rate of both agents will be set to a new random value within the specified upper and lower bounds.
 * So for example, the velocity of the evader will always have a value between EVADER_MIN_BOUND_VELOCITY and EVADER_MAX_BOUND_VELOCITY, but the exact value is always different.
 * The velocity and angle do not change between different rounds of the same game.</p>
 *
 * @author M.v.Zee
 * 
 */
public class AgentProperties {
	public static final Color PURSUER_COLOR = Color.red;
	public static final Color EVADER_COLOR = Color.blue;

	public static final double PURSUER_MIN_BOUND_TURN_RATE = Math.PI/3;	
	public static final double PURSUER_MAX_BOUND_TURN_RATE = Math.PI/4;
	
	public static final double EVADER_MIN_BOUND_TURN_RATE = Math.PI/5;
	public static final double EVADER_MAX_BOUND_TURN_RATE = Math.PI/6;
	
	public static final double PURSUER_MIN_BOUND_VELOCITY = 4.5;
	public static final double PURSUER_MAX_BOUND_VELOCITY = 5.5;
	
	public static final double EVADER_MIN_BOUND_VELOCITY = 2.5;
	public static final double EVADER_MAX_BOUND_VELOCITY = 2.5;
	
	/**
	 * The maximal turn rate of the pursuer. This value can be retrieved using the AgentListener
	 */
	public static double PURSUER_TURN_RATE = 0;
	
	/**
	 * The maximal turn rate of the evader. This value can be retrieved using the AgentListener
	 */
	public static double EVADER_TURN_RATE = 0;
	
	/**
	 * The velocity of the pursuer. This value can be retrieved using the AgentListener
	 */
	public static double PURSUER_VELOCITY = 10.0;
	
	/**
	 * The velocity of the evader. This value can be retrieved using the AgentListener
	 */
	public static double EVADER_VELOCITY = 10.0;
	
	public static final String PURSUER = "pursuer";
	public static final String EVADER = "evader";
}
