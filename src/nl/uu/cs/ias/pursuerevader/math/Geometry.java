package nl.uu.cs.ias.pursuerevader.math;

import java.awt.geom.Point2D;

/**
 * <p><b>Geometry class</b><p>
 * 
 * <p>This class contains several useful methods for geometric operations</p>
 *
 * @author M.v.Zee
 * 
 */
public class Geometry {

	/**
	 * Find the angle between twree points. P0 is center point
	 * 
	 * @param p0, p1, p2  Three points finding angle between [x,y].
	 * @return            Angle (in radians) between given points.
	 */
	public static double computeAngle (Point2D.Double p0, Point2D.Double p1, Point2D.Double p2) {
		double[] v0 = createVector (p0, p1);
	    double[] v1 = createVector (p0, p2);

	    double dotProduct = computeDotProduct (v0, v1);

	    double length1 = length (v0);
	    double length2 = length (v1);

	    double denominator = length1 * length2;
	    
	    double product = denominator != 0.0 ? dotProduct / denominator : 0.0;
	  
	    double angle = Math.acos (product);

	    return angle;
	  }
	
	/**
	 * Construct the vector specified by two points.
	 * 
	 * @param  p0, p1  Points the construct vector between [x,y].
	 * @return v       Vector from p0 to p1 [x,y].
	 */
	public static double[] createVector (Point2D.Double p0, Point2D.Double p1) {
	    double v[] = {p1.x - p0.x, p1.y - p0.y};
	    return v;
	}
	
	/**
	 * Compute the dot product (a scalar) between two vectors.
	 * 
	 * @param v0, v1  Vectors to compute dot product between [x,y].
	 * @return        Dot product of given vectors.
	 */
	public static double computeDotProduct (double[] v0, double[] v1) {
		return v0[0] * v1[0] + v0[1] * v1[1];
	}
	  
	/**
	 * Return the length of a vector.
	 * 
	 * @param v  Vector to compute length of [x,y].
	 * @return   Length of vector.
	 */
	public static double length (double[] v) {
		return Math.sqrt (v[0]*v[0] + v[1]*v[1]);
	}
	  
	/**
	 * Return whether a point is below a line.
	 * 
	 * @param p0,p1	Line points
	 * @param p2		Point to check against
	 */
	public static boolean isBelow(Point2D.Double p0, Point2D.Double p1, Point2D.Double p2) {
		if ((p1.x - p0.x)*(p2.y - p0.y) - (p1.y - p0.y)*(p2.x - p0.x) > 0) {
			return false;
		} else {
			return true;
		}
	}
	  
	/**
	 * Rotate a point with a certain degree c.c.w. over the origin point (0,0)
	 * 
	 * @param p0		Point to be rotated
	 * @param angle		Angle to rotate with (in radians)
	 * @return			The point after rotation
	 * */
	public static Point2D.Double rotate(Point2D.Double p0, double angle) {
		double x = (p0.x * Math.cos(angle)) - (p0.y * Math.sin(angle));
		double y = (p0.x * Math.sin(angle)) + (p0.y * Math.cos(angle));
		return new Point2D.Double(x, y);
	}
	
	/**
	 * Rotate a point with a certain degree c.c.w. over a given point
	 * 
	 * @param p0		Point to be rotated
	 * @param angle		Angle to rotate with (in radians)
	 * @param p1		Point to rotate over
	 * @return		The point after rotation
	 * */
	public static Point2D.Double rotateOver(Point2D.Double p0, double angle, Point2D.Double p1) {
		// translate point p0 with p1, so that p1 becomes the origin (0,0)
		Point2D.Double p0prime = new Point2D.Double(p0.x - p1.x, p0.y - p1.y);
		
		// now simply rotate over (0,0)
		return rotate(p0prime, angle);
	}
	
	/**
	 * Calculate a number in radians to the equivalent number in degrees
	 * 
	 * @param angle		Angle in radians
	 * @return			Angle in degrees
	 */
	public static double radToDegrees(double angle) {
		return angle/(2*Math.PI)*360; 
	}
	
	/**
	 * Calculate a number in degrees to the equivalent number in radians
	 * 
	 * @param angle		Angle in degrees
	 * @return			Angle in radians
	 */
	public static double degreesToRad(double degree) {
		return degree/360*Math.PI*2; 
	}
}