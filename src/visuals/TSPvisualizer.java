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

   public TSPvisualizer(TSPproblem p, int stateNum) {
	   this.p = p;
	   int[] pref = p.getPrefDim();
	   this.div = (int)Math.floor(Math.max(pref[0], pref[1])/800);
	   this.RECT_WIDTH = pref[0]/this.div;
	   this.RECT_HEIGHT = pref[1]/this.div;
	   System.out.println(RECT_WIDTH);
	   setOpaque(true);
	   setBackground(Color.WHITE);
	   frame = new JFrame("TSP iteration "+stateNum);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.getContentPane().add(this);
	   frame.pack();
	   frame.setLocationByPlatform(true);
   }
   
   public TSPvisualizer(TSPproblem p, int stateNum, Node[][] nodes) {
	   this(p, stateNum);
	   this.nodes= nodes;
   }
   
   public void setNodes(Node[][] nodes) {
	   this.nodes=nodes;
   }
   
	@Override
	public void display(Node[][]nodes) {
		this.nodes = nodes;
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
	   ArrayList<int[]> coords = p.getCoords();
	   for (int[] c : coords) {
		   g.fillRect((RECT_X+c[0])/this.div, (RECT_Y+c[1])/this.div, RECT_DIM*2, RECT_DIM*2);
	   }
	   
	   //draw nodes
//	   for (int i = 0; i < nodes.length; i++) {
//		   double[] w = nodes[i][0].getWeights();
//		   g.drawOval((int)w[0]-RECT_DIM, (int)w[1]-RECT_DIM, RECT_DIM*2, RECT_DIM*2);
//	   }
	   
   }

	   @Override
	   public Dimension getPreferredSize() {
	      // so that our GUI is big enough
	      return new Dimension(10+RECT_WIDTH + 2 * RECT_X, 10+RECT_HEIGHT + 2 * RECT_Y);
	   }

	   
}
