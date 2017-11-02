package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import caseloader.ProblemCreator.TSPproblem;
import som.Node;

public class TSPvisualizer extends JPanel implements SOMvisualizer{

   private final TSPproblem p;
   private final int RECT_DIM = 6;
   private final int RECT_WIDTH;
   private final int RECT_HEIGHT;
   private JFrame frame;
   private static final int RECT_X = 10;
   private static final int RECT_Y = RECT_X;
   private Node[][] nodes;
   private final int div;

   public TSPvisualizer(TSPproblem p) {
	   this.p = p;
	   int[] pref = p.getPrefDim();
	   this.div = (int)Math.floor(Math.max(pref[0], pref[1])/800);
	   this.RECT_WIDTH = pref[0]/this.div;
	   this.RECT_HEIGHT = pref[1]/this.div;
//	   System.out.println(RECT_WIDTH);
	   setOpaque(true);
	   setBackground(Color.WHITE);
	   frame = new JFrame("TSP");
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.getContentPane().add(this);
	   frame.pack();
	   frame.setLocationByPlatform(true);
   }
   
   public TSPvisualizer(TSPproblem p, Node[][] nodes) {
	   this(p);
	   this.nodes= nodes;
   }
   
   public void setNodes(Node[][] nodes) {
	   this.nodes=nodes;
   }
   
	@Override
	public void display(int stateNum) {
//		this.nodes = nodes;
		frame.setTitle("TSP iteration "+stateNum);
		frame.setVisible(true);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   // draw board borders
	   g.drawRect(0,0, RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
	   //
	   //draw cities
	   g.setColor(Color.GREEN);
	   ArrayList<double[]> coords = p.getCoords();
	   for (double[] c : coords) {
		   g.fillRect((int)(c[0]-RECT_DIM)/this.div, (int)(c[1]-RECT_DIM)/this.div, RECT_DIM*2, RECT_DIM*2);
	   }
	   
	   //draw nodes
	   g.setColor(Color.yellow);
	   for (int i = 0; i < nodes.length; i++) {
		   for (int j = 0; j < nodes[0].length; j++) {
			   double[] w = nodes[i][j].getWeights();
			   System.out.println(w[0]+" "+w[1]);
			   g.fillOval(((int)w[0]-RECT_DIM)/this.div, (int)(w[1]-RECT_DIM)/this.div, RECT_DIM*2, RECT_DIM*2);
		   }
	   }
	   
   }

	   @Override
	   public Dimension getPreferredSize() {
	      // so that our GUI is big enough
	      return new Dimension(10+RECT_WIDTH + 2 * RECT_X, 10+RECT_HEIGHT + 2 * RECT_Y);
	   }

	   
}
