package nl.uu.cs.ias.pursuerevader.game;

import java.awt.geom.Point2D;
import java.util.Random;

import nl.uu.cs.ias.pursuerevader.math.Geometry;
import nl.uu.cs.ias.pursuerevader.parameters.AgentProperties;
import nl.uu.cs.ias.pursuerevader.parameters.StringMessage;
import nl.uu.cs.ias.pursuerevader.parameters.GridProperties;
import nl.uu.cs.ias.pursuerevader.ui.AgentDrawer;

/**
 * <p><b>Game class</b></p>
 * 
 * <p>This class represents an actual game that is being played.<br />
 * A game consists of one or more rounds, which all have a winner.<br />
 * The game runs in the background as a thread and notifies the <b>GameCommunicator</b> class when an event occurs.
 * This event can then be picked up by the AI to be implemented.<br /></p>
 * <p><i>Game characteristics:</i><br />
 * A game is played for a fixed amount of ticks, that can be found in the GridProperties class under NUM_TICKS.<br />
 * Default it is set to 300, you are <b>NOT</b> allowed to alter this value in any way!<br />
 * The update() event is sent to the GameCommunicator class at every tick.<br /><br />
 * 
 * It is the goal of the pursuer to catch the evader. The evader has been caught if it's distance is smaller than
 * a certain value. This value can be found in the GridProperties class as well under CATCH_DISTANCE.<br /><br />
 * 
 * At the beginning of every game, the pursuer and evader will both be assigned a random minimal and maximal
 * turn rate, which defines the amount of degrees (in radians) that they are able to turn at every tick. Moreover,
 * they are also assigned a random velocity. These range of these values is also defined in the GridProperties
 * class. The most important thing to notice is that the pursuer will always have a higher velocity, but a lower
 * turning rate.<br /><br />
 * 
 * When the evader has not been caught within the time, the evader has won. Else, the pursuer has won. The winner
 * of a game is passed as an argument of the method roundEnded() that is sent to the GameCommunicator class.<br /><br />
 * 
 * @see nl.uu.cs.ias.pursuerevader.game.GameCommunicator
 * 
 * @author M.v.Zee
 *
 */
public class Game implements Runnable {
	public static int DELAY = 50;

	private boolean evaderWon;
	private boolean paint = false;
	private boolean defaultAI = false;
	
	private Agent evader, pursuer;
	private GameCommunicator runner;
	private AgentDrawer drawPanel;
	
	private int numRounds;
	
	/**
	 * Constructor, defines the parameters of the game.
	 * Called by the interface.
	 * 
	 * @param runner		Parent class to notify when events occur
	 * @param numRounds		Number of rounds to play
	 * @param paint			Whether to draw the game to the GUi or to play in the background
	 */
	public Game(GameCommunicator runner, int numRounds, boolean paint) {
		this.runner = runner;
		this.paint = paint;
		this.numRounds = numRounds;
		
		if (paint)
			drawPanel = runner.getDrawPanel();
	}
	
	/**
	 * Called by the interface when the thread is being started.
	 * It will start the game
	 */
	public void run() {
		// tell parent that the game has started
		send(StringMessage.GAME_STARTED);

		// play the game for the number of rounds selected by the player
		// if numRounds is smaller than 1, we play indefinitely
		int round = 0;
		while (round < numRounds) {
			initAgents();
			playRound();
			round++;
		}
		
		send(StringMessage.GAME_ENDED);
	}
	
	private void playRound() {
		int tick = 0;
		
		send(StringMessage.ROUND_STARTED);
		
		while (tick < GridProperties.NUM_TICKS) {
			// if we are using a gui: wait a while
			halt();
			
			// update agents
			if (!evaderIsCaught()) {
				// update the class that started this thread
				send(StringMessage.AGENTS_UPDATED);
				
				if (defaultAI) {
					evader.moveForward();
				} else {
					// wait until the evader has chosen a move
					
					while(!evader.receivedMove()){}
					
					// submit moves
					// submitted turn from the evader
					evader.processMove();
				}
				
				// move for our simple pursuer AI
	    		pursuer.moveTowards(evader);
	    		
	    		// reposition the agents according to the relativity set
	    		repositionAgents();
	    		
	    	} else {
	    		evaderWon = false;
	    		send(StringMessage.ROUND_ENDED);
	    		return;
	    	}
			tick++;
		}

		// we played for <NUM_TICKS> ticks and the game is still not finished, this means that the evader wins
		evaderWon = true;
		send(StringMessage.ROUND_ENDED);
	}
	
	private void initAgents() {
		// determine the turn rate and velocity of the agents
		double Vmax = AgentProperties.EVADER_MAX_BOUND_VELOCITY;
		double Vmin = AgentProperties.EVADER_MIN_BOUND_VELOCITY;
		
		AgentProperties.EVADER_VELOCITY = new Random().nextDouble()*(Vmax-Vmin)+Vmin;
		
		Vmax = AgentProperties.PURSUER_MAX_BOUND_VELOCITY;
		Vmin = AgentProperties.PURSUER_MIN_BOUND_VELOCITY;
		
		AgentProperties.PURSUER_VELOCITY = new Random().nextDouble()*(Vmax-Vmin)+Vmin;
		
		double Tmax = AgentProperties.EVADER_MAX_BOUND_TURN_RATE;
		double Tmin = AgentProperties.EVADER_MIN_BOUND_TURN_RATE;
		
		AgentProperties.EVADER_TURN_RATE = new Random().nextDouble()*(Tmax-Tmin)+Tmin; 
		
		Tmax = AgentProperties.PURSUER_MAX_BOUND_TURN_RATE;
		Tmin = AgentProperties.PURSUER_MIN_BOUND_TURN_RATE;
		
		AgentProperties.PURSUER_TURN_RATE = new Random().nextDouble()*(Tmax-Tmin)+Tmin; 
			
		pursuer = new Agent(AgentProperties.PURSUER_VELOCITY, AgentProperties.PURSUER_TURN_RATE, AgentProperties.PURSUER);
		
		// place the evader in a range of +/- half the world size relative to the pursuer
		evader = new Agent(AgentProperties.EVADER_VELOCITY, AgentProperties.EVADER_TURN_RATE, AgentProperties.EVADER);
		
		positionEvader(pursuer.getPosition());
		
		repositionAgents();
		
		send(StringMessage.AGENTS_PLACED);
    }
	
	private void halt() {
		if (paint) {
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void positionEvader(Point2D.Double p0) {
		// generate a random x coordinate
		double diam = GridProperties.WIDTH/4;
		double randX = p0.x - diam + Math.random()*diam;
		
		// now a random y coordinate according to this x-coord so that it will be on a circle
		// x^2 + y^2 = WIDTH/2
		// y^2 = WIDTH/2 - x^2
		// |y| = sqrt(|W/2-x^2|)
		double randY = p0.y + ((new Random().nextInt(2)==0)?-1:1)*Math.sqrt(Math.abs(diam-randX*randX));
		
		evader.setPosition(randX, randY);
	}
	
	private boolean evaderIsCaught() {
		return (evader.getPosition().distance(pursuer.getPosition()) <= GridProperties.CATCH_DISTANCE);
	}

	private void send(String msg) {
		if (msg.equals(StringMessage.GAME_STARTED)) {
				runner.gameStarted();
		} else if (msg.equals(StringMessage.ROUND_STARTED)) {
				runner.roundStarted();
		} else if (msg.equals(StringMessage.AGENTS_PLACED)) {
			if (paint) {
				drawPanel.removeAllAgents();
				drawPanel.addAgent(evader);
				drawPanel.addAgent(pursuer);
			}
			runner.agentsPlaced(pursuer, evader);
		} else if (msg.equals(StringMessage.AGENTS_UPDATED)) {
			if (paint) {
				drawPanel.repaint();
			}
			runner.update();
		} else if (msg.equals(StringMessage.ROUND_ENDED)) {
			runner.roundEnded(evaderWon);
		} else if (msg.equals(StringMessage.GAME_ENDED)) {
			runner.gameEnded();
		}
	}
	
	private void repositionAgents() {
    	switch (GridProperties.relativity) {
    	case 0:
    		// no relative display, just show the agents as they are
    		break;	
    	case 1:
    		// relative postioning
			evader.setPosition(evader.getXasInt()-pursuer.getXasInt(), evader.getYasInt()-pursuer.getYasInt());
			pursuer.setPosition(0,0);
			break;
    	case 2:
    		// it's all relative

			// translate with coordinates of pursuer
	        evader.setPosition(evader.getXasInt()-pursuer.getXasInt(), evader.getYasInt()-pursuer.getYasInt());
	        pursuer.setPosition(0,0);
	        
	        // now we can rotate over the origin
			Point2D.Double p0 = Geometry.rotate(evader.getPosition(), pursuer.getHeading());
			evader.setPosition(p0.x, p0.y);
			
			// change direction of evader with direction of pursuer
			evader.setHeading(evader.getHeading()-pursuer.getHeading());
			
			// set direction of pursuer to PI/2
			pursuer.setHeading(0);
			
			break;
    	}
	}
	
	public void playDefaultAI() {
		defaultAI = true;
	}
}
