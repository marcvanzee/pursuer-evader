package pursuerevader.solution;

import java.awt.geom.Point2D;

public class SOM {
	Grid grid;
	private double afstand;
	private Output output ;
	
	
	
	public SOM(int detail){
		grid = new Grid(detail);
	}
	
	public void Set(Point2D.Double evader, Point2D.Double persuer){
		double evaderx = evader.x;
		double evadery = evader.y;
		double pursuerx = persuer.x;
		double pursuery = persuer.y;
		afstand = Math.sqrt(Math.pow(evaderx-pursuerx,2) + Math.pow(evadery-pursuery,2));
		output = grid.geefOutput(0,0);
	}

	
	public double GeefOutput(double maxTurnAngle, double heading, double angle){
		output = grid.geefOutput(heading, angle);
		if (output.getAngle()>maxTurnAngle){
			if(output.getRichting())
				return maxTurnAngle;
			else
				return -maxTurnAngle;
		}
		else{
			if(output.getRichting())
				return output.getAngle();
			else
				return -output.getAngle();
		}
	}
	
	public void Update(Point2D.Double evader, Point2D.Double persuer){
		double evaderx = evader.x;
		double evadery = evader.y;
		double pursuerx = persuer.x;
		double pursuery = persuer.y;
		double nieuweAfstand = Math.sqrt(Math.pow(evaderx-pursuerx,2) + Math.pow(evadery-pursuery,2));
		if (nieuweAfstand<afstand){
			;
		}
		else{
			grid.LeerIets(output);
		}
	}
	
}
