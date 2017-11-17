package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import org.math.plot.*;

import javax.swing.*;

import caseloader.ProblemCreator.TSPproblem;
import som.Node;
import som.Tools;

public class TSPvisualizer extends JPanel implements SOMvisualizer{

	private static final int tempSol = 0;
	private static final int finalSol = 1;
	private final TSPproblem p;
	private final int RECT_DIM = 6;
	private final int RECT_WIDTH;
	private final int RECT_HEIGHT;
	private JFrame frame;
	private static final int RECT_X = 10;
	private static final int RECT_Y = RECT_X;
	private static final int maxSize = 650;
	private Node[][] nodes;
	private final double div;
	private final double[] offset;
	private final int mode;
	private final double[] dim;
	private final int gOffset = 6;
	public TSPvisualizer(TSPproblem p, int mode) {
		this.p = p;
		this.mode = mode;
		this.offset = p.getOffset();
		int[] pref = p.getPrefDim();
		double size = Math.max(pref[0], pref[1])+Math.max(offset[0], offset[1]);
		if(size > maxSize) {
			this.div = (size/maxSize);
		}else if (size < maxSize){
			this.div = (1/(maxSize/size));
		}else {
			this.div = 1;
		}
		this.RECT_WIDTH =(int) ((pref[0]+offset[0])/this.div)+12;
		this.RECT_HEIGHT = (int)((pref[1]+offset[1])/this.div)+12;
		this.dim = new double[] {RECT_WIDTH, RECT_HEIGHT};
		setOpaque(true);
		setBackground(Color.WHITE);
		frame = new JFrame("TSP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLocationByPlatform(true);
	}
   
   public TSPvisualizer(TSPproblem p, Node[][] nodes, int mode) {
	   this(p, mode);
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
	
	public void drawFinal(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		ArrayList<double[]> coords = p.getCoords();
		
		
		//draw all cities
		for (double[] c : coords) {
			double[] cf = fixCoords(c);
			g.fillRect((int)(cf[0])-RECT_DIM+gOffset, (int)(cf[1])-RECT_DIM+gOffset, RECT_DIM*2, RECT_DIM*2);
		}
		double[] currCity = null;
		double[] firstCity = null;
		for (int i = 0; i < nodes.length; i++) {
			ArrayList<double[]>lab = (ArrayList<double[]>) nodes[i][0].getLabel();
			if(lab!=null) {
				for (double[] ds : lab) {
					if(firstCity==null) {
						currCity = fixCoords(ds);
						firstCity = fixCoords(ds);
					}else {
						g.setColor(Color.BLACK);
//						double[] cFix = fixCoords(currCity);
						double[] dFix = fixCoords(ds);
						g.drawLine((int)(currCity[0])+gOffset, (int)(currCity[1])+gOffset, (int)(dFix[0])+gOffset, (int)(dFix[1])+gOffset);
						currCity = dFix;
					}
				}
			}
		}
		g.drawLine((int)(currCity[0]), (int)(currCity[1]), (int)(firstCity[0]), (int)(firstCity[1]));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   if(this.mode == tempSol) {
		   drawTemp(g);
	   }else {
		   drawFinal(g);
	   }
   }
	
	public double[] fixCoords(double[] coords) {
		double[] newCoords = new double[] {coords[0], coords[1]};
		//x coord
		newCoords[0] = (coords[0]+offset[0])/div; //offset and scale
		
		//y coord
		newCoords[1] = dim[1]-((coords[1]+offset[1])/div); //flip vertical, offset and scale
		
		return newCoords;
	}
	
	private void drawTemp(Graphics g) {
		// draw board borders
		   g.drawRect(0,0, RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
		   //
		   //draw cities
		   g.setColor(Color.GREEN);
		   ArrayList<double[]> coords = p.getCoords();
		   for (double[] c : coords) {
			   double[] cf = fixCoords(c);
				g.fillRect((int)(cf[0])-RECT_DIM+gOffset, (int)(cf[1])-RECT_DIM+gOffset, RECT_DIM*2, RECT_DIM*2);
		   }
		   
		   //draw nodes, lines
		   for (int i = 0; i < nodes.length; i++) {
			   for (int j = 0; j < nodes[0].length; j++) {
				   double[] w = fixCoords(nodes[i][j].getWeights());
				   double[] w1;
				   if(i==0) {
					   w1 = fixCoords(nodes[nodes.length-1][j].getWeights());
				   }else {
					   w1 = fixCoords(nodes[i-1][j].getWeights());
				   }
				   g.setColor(Color.yellow);
				   g.fillOval((int)((w[0]-RECT_DIM))+gOffset, (int)((w[1]-RECT_DIM))+gOffset, RECT_DIM*2, RECT_DIM*2);

				   g.setColor(Color.black);
				   g.drawLine((int)(w[0])+gOffset, (int)(w[1])+gOffset, (int)(w1[0])+gOffset, (int)(w1[1])+gOffset);
			   }
		   }
	}

	   @Override
	   public Dimension getPreferredSize() {
	      // so that our GUI is big enough
	      return new Dimension(10+RECT_WIDTH + 2 * RECT_X, 10+RECT_HEIGHT + 2 * RECT_Y);
	   }
	   
	   public static void main(String[] args) {
		// create your PlotPanel (you can use it as a JPanel)
		   	double[] x = new double[] {-20,30};
			double[] y = new double[] {3000, 100};
			Plot2DPanel plot = new Plot2DPanel();
		  
			// add a line plot to the PlotPanel
			plot.addLinePlot("my plot", x, y);
		  
			// put the PlotPanel in a JFrame, as a JPanel
			JFrame frame = new JFrame("a plot panel");
			frame.setContentPane(plot);
			frame.setVisible(true);
	}

	   
}
