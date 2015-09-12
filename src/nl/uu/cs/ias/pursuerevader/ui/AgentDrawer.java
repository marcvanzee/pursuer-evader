package nl.uu.cs.ias.pursuerevader.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.util.HashSet;
import java.util.Iterator;

import nl.uu.cs.ias.pursuerevader.game.Agent;
import nl.uu.cs.ias.pursuerevader.parameters.GridProperties;

/**
 * <p><b>AgentDrawer class</b></p>
 * 
 * <p>Panel that draws the agents on the Applet</p>
 * 
 * @author M.v.Zee
 *
 */
public class AgentDrawer extends Panel {
	private static final long serialVersionUID = 5721004154735024544L;
	
	private HashSet<Agent> agents = new HashSet<Agent>();
	
	private Image dbImage; 
	private Graphics dbg; 

	private void drawAgent(Graphics g, Agent a) {
		int x = a.getXasInt();
		int y = a.getYasInt();
				
		// center (0,0)
		x += GridProperties.WIDTH/2;
		y = GridProperties.HEIGHT/2 - y;
		
		int circleW = GridProperties.WIDTH / 40;
		int circleH = GridProperties.HEIGHT / 40;
		double lineLength = a.getVelocity() * (GridProperties.WIDTH+GridProperties.HEIGHT)/300;
		
		g.setColor(a.getColor());
				
		int endX = (int) (x + lineLength * Math.sin(a.getHeading()));
		int endY = (int) (y - lineLength * Math.cos(a.getHeading()));

		if (GridProperties.relativity == GridProperties.ABSOLUTE) {
			g.drawOval(modW(x-circleW/2), modH(y-circleH/2), circleW, circleH);
			if (!( ((endX > x) && (modW(endX) <= modW(x))) ||
				 ((endY > y) && (modH(endY) <= modH(y))) ||
				 ((endX < x) && (endX < 0) && (x >= 0))  ||
				 ((endY < y) && (endY < 0) && (y >= 0)) ))
				g.drawLine(modW(x), modH(y), modW(endX), modH(endY));
		}
		else {
			g.drawOval(x-circleW/2, y-circleH/2, circleW, circleH);
			g.drawLine(x, y, endX, endY);
		}
	}
	
	private int modW(int x) {
		if (x<0) {
			return GridProperties.WIDTH + x % GridProperties.WIDTH;
		} else {
			return x % GridProperties.WIDTH;
		}
	}
	
	private int modH(int y) {
		if (y<0) {
			return GridProperties.HEIGHT + y % GridProperties.HEIGHT;
		} else {
			return y % GridProperties.HEIGHT;
		}
	}
	
	private void drawAgents(Graphics g) {
		Iterator<Agent> itr = agents.iterator();
		while(itr.hasNext())
			drawAgent(g, itr.next());
	}
	
	private void clearScreen(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawAxes(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawLine(GridProperties.WIDTH/2, GridProperties.HEIGHT, GridProperties.WIDTH/2, 0);
		g.drawLine(GridProperties.WIDTH, GridProperties.HEIGHT/2, 0, GridProperties.HEIGHT/2);
		g.drawString("(0,0)", GridProperties.WIDTH/2+2,GridProperties.HEIGHT/2+12);
	}
	
	/**
	 * Initialize the applet
	 */
	public void init() {
		setSize(GridProperties.WIDTH, GridProperties.HEIGHT);
	}
	
	/**
	 * Paint a frame of animation.
	 */
	public void paint(Graphics g) {
		clearScreen(g);
		if (agents.size() == 2) {
			drawAxes(g);
			drawAgents(g);
		}
	}

	/**
	 * Update a frame of animation.
	 */
	public void update(Graphics g) {
		// initialize buffer 
		if (dbImage == null)  {
			dbImage = createImage (this.getSize().width, this.getSize().height); 
			dbg = dbImage.getGraphics (); 
		} 
		
		paint (dbg); 

		// draw image on the screen 
		g.drawImage (dbImage, 0, 0, this);
	}

	/**
	 * Add an agents to the drawing grid
	 * @param a		agent to add
	 */
	public void addAgent(Agent a) {
		agents.add(a);
	}

	/**
	 * Remove all agents from the drawing grid
	 */
	public void removeAllAgents() {
		agents.clear();
	}
}
