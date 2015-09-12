package nl.uu.cs.ias.pursuerevader.debug;

/**
 * <p><b>Debug</b></p>
 * 
 * <p>This class may be used to print optional debugging messages.<br />
 * Use the <i>DEBUGGING</i>-flag to enable debugging.<br />
 * Debugging can be done using the <i>log()</i> method.<br />
 * All these methods are <i>static</i>, which means that they can be called in the following way: <b>Debug.DEBUGGER = true</b></p>
 * 
 * @author M.v.Zee
 */
public class Debug {
	/**
	 * Debugging flag. If set, log() will print the message to the debugging window
	 */
	public static boolean DEBUGGING = false;
	
	/**
	 * Logging message
	 * @param s		message to log
	 */
	public static void log(String s) {
		if (DEBUGGING) {
			System.out.println(s);
		}
	}
}
