package pursuerevader.solution;

public class Node {
	
	private int xcor;			//heading evader 0-15
	private int ycor;			//angle persuer 0-15
	
	private Output output;
	
	
	
	public Output provideAngle(){
		return output;
	}
	
	public void leerAngle(Output nieuwe){
		output = nieuwe;
	}
	
	public int getX(){
		return xcor;
	}
	
	public int getY(){
		return ycor;
	}
	
	public Node(int xcor, int ycor){
		this.xcor=xcor;
		this.ycor=ycor;
		output = new Output(0,true);
	}

	 
}
