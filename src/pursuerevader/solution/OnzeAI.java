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
	
public class OnzeAI extends GameCommunicator {
	
		private static final long serialVersionUID = 3962525847855401824L;
		
		int numRounds = 5;
		int countEvaderWon = 0;
		SOM som = new SOM(15);
		
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
			som.Set(evaderListener.getPosition(),pursuerListener.getPosition());
		}
		
		public void update() {
			som.Update(evaderListener.getPosition(), pursuerListener.getPosition());
			double maxTurnAngle = evaderListener.getMaxTurnAngle();
			double heading = evaderListener.getHeading();
			double angle = evaderListener.getAngleWith(pursuerListener.getPosition());
			evaderListener.setNextTurnAngle(som.GeefOutput(maxTurnAngle, heading, angle));			
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


