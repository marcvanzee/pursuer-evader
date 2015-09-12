package pursuerevader.solution;

import pursuerevader.debug.Debug;
import pursuerevader.game.GameCommunicator;

/**
  * ExampleAI
  *
  * Example implementation of an AI for the evador, it requires the pursuer evader library.
  * Download it at: http://pursuerevader.googlecode.com/
  *
  * See Javadoc for additional information: http://www.marcvanzee.nl/pursuerev
  *
  * @author M.v.Zee
  *
  */
public class ExampleAI extends GameCommunicator {
	private static final long serialVersionUID = 3962525847855401824L;
	
	int numRounds = 5;
	int countEvaderWon = 0;
	
	public void start() {
		//setRelativity(GridProperties.RELATIVE);	// how to orient and position the axes of the game
		
		setGameSpeed(1);							// speed of the simulation in range [1,9]	
		Debug.DEBUGGING = false;					// whether to print debugging message to the debugging window
		startSimulation(numRounds, true);			// run a simulation for <numRounds> rounds and enable visual feedback in the gui
	}
	
	public void gameStarted() {
		Debug.log("runner> game started");
	}
	
	public void roundStarted() {
	}
	
	public void update() {
		double xpos = evaderListener.getPosition().x;
		double ypos = evaderListener.getPosition().y;
		double xpurs = pursuerListener.getPosition().x;
		double ypurs = pursuerListener.getPosition().y;
		int direction = (int) (8*(evaderListener.getHeading()/(2*Math.PI)));
		evaderListener.setNextTurnAngle(0);
		switch(direction){
		case 0: if(ypurs >= ypos) evaderListener.setNextTurnAngle(evaderListener.getMaxTurnAngle());break;
		case 1: if(xpurs >= xpos) evaderListener.setNextTurnAngle(- evaderListener.getMaxTurnAngle());break;
		case 2: if(xpurs >= xpos) evaderListener.setNextTurnAngle(evaderListener.getMaxTurnAngle());break;
		case 3: if(ypurs<= ypos) evaderListener.setNextTurnAngle(- evaderListener.getMaxTurnAngle());break;
		case 4: if(ypurs<= ypos) evaderListener.setNextTurnAngle(evaderListener.getMaxTurnAngle());break;
		case 5: if(xpurs <= xpos) evaderListener.setNextTurnAngle(- evaderListener.getMaxTurnAngle());break;
		case 6: if(xpurs <= xpos) evaderListener.setNextTurnAngle(evaderListener.getMaxTurnAngle());break;
		case 7: if(ypurs >= ypos) evaderListener.setNextTurnAngle(- evaderListener.getMaxTurnAngle());break;
		default: break;
		}
		
	//System.out.print(evaderListener.getMaxTurnAngle());
	//	evaderListener.setNextTurnAngle(evaderListener.getAngleWith(pursuerListener.getPosition()));
		
		
		
	//	evaderListener.setNextTurnAngle(0);			// current response: always move forward. this call should be in thie methods, else
													// the simulation will not continue!
	
	
		//Point2D.Double p = evaderListener.getPosition();
		//System.out.println("x: "+ p.x + ", y: " + p.y);
		
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
		
	}
}
