package pursuerevader.game;

/**
 * <p><b>GameInterface class</b></p>
 * 
 * <p>An interface that defines the events that are dropped by the game.<br />
 * It is being used by the <b>GameCommunicator</b> class</p>
 * 
 * @see pursuerevader.game.GameCommunicator
 * 
 * @author M.v.Zee
 *
 */
public interface GameInterface {
	public void gameStarted();
	public void roundStarted();
	public void roundEnded(boolean evaderWon);
	public void gameEnded();
	public void update();
}
