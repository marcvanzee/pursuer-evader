package nl.uu.cs.ias.pursuerevader.game;

/**
 * <p><b>Receiver class</b></p>
 * 
 * <p>This is an internal class to receive messages to let the AI steer the evader.</p>
 *
 * @author M.v.Zee
 */
public class Receiver {
	private boolean received;
	private double turnHeading;
	
	public Receiver() {
		received = false;
	}
	
	public void reset() {
		received = false;
	}
	
	public void setTurnHeading(double turnHeading) {
		received = true;
		this.turnHeading = turnHeading;
	}
	
	public boolean isEmpty() {
		return !received;
	}
	
	public double getTurnHeading() {
		return turnHeading;
	}
}
