package pursuerevader.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import pursuerevader.parameters.GridProperties;

/**
 * <p><b>BaseGUI</b></p>
 *
 * <p>This is the basic Graphical User Interface (GUI) for the simulation.<br />
 * It contains merely the visual elements. When extended, it requires the method startSimulation.</p>
 * 
 * @author M.v.Zee
 * 
 */
public abstract class BaseGUI extends JApplet {
	private static final long serialVersionUID = 5721004154735024544L;
    private AgentDrawer drawPanel;

    protected abstract void startSimulation(int numRounds, boolean paint);
    protected abstract void playDefaultAI();
    
    /** 
     * Initialize the applet and compute the delay between frames.
     */
    public void init() {
    	initMenuBar();
    	initDrawingGrid();
    	setSize(GridProperties.WIDTH, GridProperties.HEIGHT+30);
    }

    private void initMenuBar() {
    	JMenuBar menubar = new JMenuBar();

    	JMenu menuSim = new JMenu("Simulation");

        JMenuItem startItem = new JMenuItem("Play one round");
        JMenuItem popupItem = new JMenuItem("Play rounds...");
        menuSim.add(startItem);
        menuSim.add(popupItem);
        
        menubar.add(menuSim);
        
        JMenu menuPersp = new JMenu("Perspective");

        JMenuItem absItem = new JMenuItem("Absolute");
        menuPersp.add(absItem);

        JMenuItem relPosItem = new JMenuItem("Relative (position)");
        menuPersp.add(relPosItem);

        JMenuItem relItem = new JMenuItem("Relative (all)");
        menuPersp.add(relItem);
        
        menubar.add(menuPersp);

        setJMenuBar(menubar);
        
        startItem.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent arg0) {
        				//playDefaultAI();
        				startSimulation(1, true);
        			}
        		}
        );
        
        popupItem.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent arg0) {
        				JApplet frame = new JApplet();
        			    Object result = JOptionPane.showInputDialog(frame, "Enter number of rounds to play:");

        			    int rounds = Integer.parseInt(result.toString());
        			    
        			    //playDefaultAI();
        				startSimulation(rounds, true);
        			}
        		}
        );
        
       absItem.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent arg0) {
        				GridProperties.relativity = GridProperties.ABSOLUTE;
        			}
        		}
         );
        relPosItem.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent arg0) {
        				GridProperties.relativity = GridProperties.POSITION_RELATIVE;
        			}
        		}
         );
        relItem.addActionListener(
        		new ActionListener() {
        			public void actionPerformed(ActionEvent arg0) {
        				GridProperties.relativity = GridProperties.RELATIVE;
        			}
        		}
         );
    }
    
    private void initDrawingGrid() {
    	setLayout(new BorderLayout());
    	drawPanel = new AgentDrawer();
    	add(drawPanel, BorderLayout.CENTER);
    }
    
    public AgentDrawer getDrawPanel() {
    	return drawPanel;
    }
}
