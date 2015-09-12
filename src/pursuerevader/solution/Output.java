package pursuerevader.solution;

public class Output {
	double angle;		//max pi
	boolean richting;	//true is rechtsom, false is linksom
	
	public Output(double angle, boolean richting){
		this.angle=angle;
		this.richting=richting;
	}
	public double getAngle(){
		return angle;
	}
	public void setAngle(double angle){
		this.angle=angle;
	}
	
	public boolean getRichting(){
		return richting;
	}
	public void setRichting(boolean richting){
		this.richting=richting;
	}
	
	public void Ruis(){
		if ((int)Math.random()*10==0){
			if (richting)
				richting = false;
			else
				richting = true;
		}
		double ruis = angle/10;
		if ((int)Math.random()*2==0){
			if (angle<ruis)
				angle = 0;
			else
				angle -= ruis;}
		else{
			if (angle > Math.PI-ruis)
				angle = Math.PI;
			else
				angle += ruis;
		}		
	}
	
}
