package pursuerevader.game;

import java.awt.geom.Point2D;

import pursuerevader.parameters.AgentProperties;

/**
 * <p><b>AgentListener</b></p>
 * 
 * <p>The <b>AgentListener</b> class serves as an interface between the AI and the agents (the pursuer and the evader),
 * because Agent class holds methods that are not allowed to be used when implementing an AI for this game.
 * 
 * When starting the game, both the evader and pursuer will have an AgentListener attached to them, which can
 * be used to retrieve all necessary information to implement a sophisticated AI.
 * 
 * @see pursuerevader.game.Agent
 * 
 * @author M.v.Zee
 *
 */
public class AgentListener {
	private Agent agent;
	
	/**
	 * Attach a listener to an agent
	 * 
	 * @param agent			Agent to create a listener for
	 */
	public AgentListener(Agent agent) {
		this.agent = agent;
	}
	
	/**
	 * Returns the position of the agent
	 * 
	 * @return		Position as a Point2D.Double value
	 */
	public Point2D.Double getPosition() {
    	return agent.getPosition();
    }
    
	/**
	 * Returns the velocity of the agent in steps/tick
	 * @return		Velocity
	 */
    public double getVelocity() {
    	return agent.getVelocity();
    }
    
    /**
	 * Returns the heading of the agent in radians. When the agent faces north, the heading
	 * will be zero, and it increases clockwise, so that the angle of the direction east will
	 * be PI/2
	 * @return		Heading
	 */
    public double getHeading() {
    	return agent.getHeading();
    }
    
    /**
     * Returns the maximal turn rate of the agent.
     * A turn rate is defined as the change in heading per tick.
     * 
     * @return	The maximal heading of the agent
     */
    public double getMaxTurnAngle() {
    	return (agent.getType().equals(AgentProperties.EVADER)) ? 
    			AgentProperties.EVADER_TURN_RATE : AgentProperties.PURSUER_TURN_RATE;
    }
  
    /**
     * Returns the angle in radians that the agent makes with a given point, 
     * where direction north in the GUI represents an angle of 0. The angle 
     * is measured clockwise, so direction east will return an angle of PI/2
     * 
     * @param p0		The point to calculate the angle with
     * @return
     */
    public double getAngleWith(Point2D.Double p0) {
    	Point2D.Double pos = getPosition();

    	// tranlate point so that p0 will be positioned to origin (0,0)
    	Point2D.Double posPrime = new Point2D.Double(pos.x-p0.x, pos.y-p0.y);
    	
    	// now calculate angle with origin
    	double a = Math.atan2(posPrime.y,posPrime.x);
    	
    	if (a < 0) a = Math.PI*2 + a;
    	
    	return (Math.PI*2.5 - a) % (2*Math.PI);
    }
    
    /**
     * Define the next turn for the agent to take. This only works for the evader agent, since
     * this is the agent that requires an AI. The pursuer agent comes with a build-in AI that
     * needs to be defeated.
     * 
     * @param angle		The angle that the agent needs to turn with in the next tick. This can be a value between 0 and 2*PI. 
     * A positive angle will make the agent move in a clockwise direction. Negative values are also accepted, and translated
     * into their corresponding direction. A negative turning angle of x will result in a turning angle of 2*PI-x.
     */
    public void setNextTurnAngle(double angle) {
    	agent.nextMove(angle);
    }
    
}
