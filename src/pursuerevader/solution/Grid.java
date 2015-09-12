package pursuerevader.solution;

import java.util.ArrayList;


public class Grid {
	double step;
	int aantal;
	Node[][] grid;
	Node laatste;
	
	public Output geefOutput(double headingEvader, double anglePersuer){
		int xwaarde =  (int)(headingEvader/step);
		int ywaarde = (int) (anglePersuer/step);
		laatste = grid[xwaarde][ywaarde];
		Output centrum = laatste.provideAngle();
		int r=0;
		double rechts=0;
		int l=0;
		double links=0;
		if (centrum.getRichting()){
			r+=3;
			rechts += 3*centrum.getAngle();
		}
		else{
			l+=3;
			links += 3*centrum.getAngle();
		}
		ArrayList<Output> buren = neighbourhood(xwaarde,ywaarde);
		for (Output i : buren){
			if (i.getRichting()){
				r++;
				rechts += i.getAngle();
			}
			else{
				l++;
				links+= i.getAngle();
			}
		}
		
				
		
		double rechtsom = rechts/r;
		double linksom = links/l;
		if (rechtsom>linksom){
			Output output = new Output(Math.abs(rechtsom-linksom),true);
			output.Ruis();
			return output;
		}
		else{
			Output output = new Output (Math.abs(rechtsom-linksom),false);
			output.Ruis();
			return output;
		}		
	}
	
	public void LeerIets(Output succesvol){
		laatste.leerAngle(succesvol);
	}
	
	public Grid(int aantal){
		this.aantal=aantal;
		step = 2*Math.PI/(aantal-1);
		grid = new Node[aantal][aantal];
		for (int i = 0; i<aantal ; i++){
			for (int j = 0 ; j<aantal ; j++){
				grid[i][j]=new Node(i,j);
			}
		}
		laatste = grid[0][0];
	}
	
	public void addNode(int xcor, int ycor){
		grid[xcor][ycor]=new Node(xcor,ycor);
	}	
	
	public ArrayList<Output> neighbourhood(int x, int y){
		ArrayList<Output> buren = new ArrayList<Output>();
		
		if(y<aantal&x>0)	 	buren.add(grid[x-1][y+1].provideAngle());
		if(y<aantal)		 	buren.add(grid[x][y+1].provideAngle());
		if(y<aantal&x<aantal)	buren.add(grid[x+1][y+1].provideAngle());
		if(x<aantal)			buren.add(grid[x+1][y].provideAngle());
		if(x<aantal&y>0)		buren.add(grid[x+1][y-1].provideAngle());
		if(y>0)					buren.add(grid[x][y-1].provideAngle());
		if(y>0&x>0)				buren.add(grid[x-1][y-1].provideAngle());
		if(x>0)					buren.add(grid[x-1][y].provideAngle());
		
		return buren;
	}
}
