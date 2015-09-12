package pursuerevader.game;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Random;

import pursuerevader.math.Geometry;
import pursuerevader.parameters.AgentProperties;
import pursuerevader.parameters.GridProperties;

/**
 * <p><b>Agent</b></p>
 * 
 * <p>In this class an agent is defined, which can be both an evader or pursuer.<br />
 * This class is not accessible, but it might be useful to look through to gain understanding.<br />
 * For a list of methods that are accessible when playing the game, please see the <b>AgentListener</b> class</p>
 * 
 * @see pursuerevader.game.AgentListener
 * 
 * @author M.v.Zee
 */
public class Agent {
	private double maxVelocity, maxTurnRate;
	private String type;
	private Receiver recv;
	
	// position of the agent
	private Point2D.Double pos;
	
	// heading of the agent [0,2*PI]
	private double heading;
	
	// speed of the agent
	private double velocity;
	
	/**
	 * Add an agent with a speed, max turn rate and type
	 * <b>Not to be used by the student!</b>
	 * 
	 * @param v		maximal velocity
	 * @param t		maximal turning rate
	 * @param type	agent type (evader or pursuer)
	 */
	public Agent(double v, double t, String type) {
		maxVelocity = v;
		maxTurnRate = t;
		this.type = type;
		recv = new Receiver();
		
		randomInit();
	}
	
	private void randomInit() {
		// randomize start postion
		double w = GridProperties.WIDTH;
		double h = GridProperties.HEIGHT;
		pos = new Point2D.Double(Math.random()*w-w/2, Math.random()*h-h/2);
		
		// randomize direction
		heading = Math.random() * 2 * Math.PI;
		
		// start with maximum speed
		velocity = maxVelocity;
	}
	
	private void turnLeft() {
		heading -= maxTurnRate*perturbation();
		setHeading(heading);
	}
	
	private void turnRight() {
		setHeading(heading + maxTurnRate*perturbation());
	}
	
	private double perturbation() {
		// random term with range [0.5,1]
		return ( new Random().nextDouble()/5 );
	}	
	
	private double limitTurn(double turn) {
		if (turn > 0) {
			return (turn > maxTurnRate) ? maxTurnRate : turn;
		} else {
			return (turn < -maxTurnRate) ? -maxTurnRate : turn;
		}
	}
	
	/**
	 * Position the agent one step forward, depending on its speed;
	 * a speed of X will move the agent X steps forward every tick.
	 */
	public void moveForward() {
		double x = pos.x;
		double y = pos.y;
		
		pos.x = x + velocity * Math.sin(heading);
		pos.y = y + velocity * Math.cos(heading);
		
	}
	
	/*
	public void moveAwayFrom(Agent a) {
		// first determine whether we should move left or right
		
		// compute the angle between p0,p1,p1 where
		// p0 = our position (the center point)
		// p1 = the direction of the other agent
		// p2 = our heading
		
		// notice that we need to create a virtual point to find out p2
		double p2X = pos.x + velocity * Math.sin(heading);
		double p2Y = pos.y + velocity * Math.cos(heading);
		
		Point2D.Double p2 = new Point2D.Double(p2X, p2Y);
		
		//double relAngle = Geometry.computeAngle(pos, a.getPosition(), p2);
		boolean isBelow = Geometry.isBelow(pos, p2, a.getPosition());
		
		// now find out in what squadron the arrow lies:
		if (isBelow) {
			turnLeft();
		} else {
			turnRight();
		}
		moveForward();
	}
	*/
	
	/**
	 * Basic behavior of the pursuer, always try to turn towards the evader
	 */
	public void moveTowards(Agent a) {		
		// compute the angle between p0,p1,p1 where
		// p0 = our position (the center point)
		// p1 = the direction of the other agent
		// p2 = our heading
		
		// notice that we need to create a virtual point to find out p2
		double p2X = pos.x + velocity * Math.sin(heading);
		double p2Y = pos.y + velocity * Math.cos(heading);
		
		Point2D.Double p2 = new Point2D.Double(p2X, p2Y);
		
		//double relAngle = Geometry.computeAngle(pos, a.getPosition(), p2);
		boolean isBelow = Geometry.isBelow(pos, p2, a.getPosition());
		
		// random pertubation
		if (Math.random() > 0.1) {
			// now find out in what squadron the arrow lies:
			if (isBelow) {
				turnRight();
			} else {
				turnLeft();
			}
		}
		moveForward();
	}
	
	/**
	 * Returns the position of the agent
	 * 
	 * @return		Position as a Point2D.Double value
	 */
	public Point2D.Double getPosition() {
		return pos;
	}
	
	/**
	 * Returns the heading of the agent in radians
	 * @return		Heading
	 */
	public double getHeading() {
			return heading;
	}
	
	/**
	 * Returns the velocity of the agent in steps/tick
	 * @return		Velocity
	 */
	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * Returns the type as defined in parameters.AgentProperties
	 * @return		Type as a String value
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns the color
	 * @return		Color.RED for pursuer, Color.BLUE for evader
	 */
	public Color getColor() {
		return (type.equals(AgentProperties.PURSUER)) ? AgentProperties.PURSUER_COLOR : AgentProperties.EVADER_COLOR;
	}
	
	/**
	 * @return		X-coordinate of the agent
	 */
	public int getXasInt() {
		return (int) pos.x;
	}
	
	/**
	 * @return		Y-coordinate of the agent
	 */
	public int getYasInt() {
		return (int) pos.y;
	}
	
	/**
	 * Returns the heading of the agents in radians. A heading of
	 * 0 implies that the agents faces downwards.
	 * 
	 * @return		Heading of the agent
	 */
	public void setHeading(double heading) {
		if (heading < 0) {
			heading += 2 * Math.PI;
		}
		this.heading = heading % (2 * Math.PI);
	}
	
	/**
	 * Internal method.
	 * <b>Not to be used by the student!</b>
	 * 
	 * @param x		x-coordinate
	 * @param y		y-coordinate
	 */
	public void setPosition(double x, double y) {
		pos = new Point2D.Double(x, y);
	}
	
	/**
	 * Internal method.
	 * <b>Not to be used by the student!</b>
	 */
	public void processMove() {
		double recvHeading = recv.getTurnHeading();
		double turnHeading = limitTurn(recvHeading);
		setHeading(heading + turnHeading);
		moveForward();
		recv.reset();
	}
	
	/**
	 * Internal method.
	 * <b>Not to be used by the student!</b>
	 * 
	 * @param turnHeading
	 */
	public void nextMove(double turnHeading) {
		recv.setTurnHeading(turnHeading);
	}
	
	/**
	 * Internal method.
	 * <b>Not to be used by the student!</b>
	 * 
	 * @return
	 */
	public boolean receivedMove() {
		return !recv.isEmpty();
	}
}
