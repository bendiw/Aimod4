package visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

import caseloader.ProblemCreator.MNISTproblem;
import som.Node;

public class IMGvisualizer extends JPanel implements SOMvisualizer{
	private static final int tempSol = 0;
	private final MNISTproblem p;
	private final int frameSIZE = 400;
	private final int RECT_WIDTH;
	private final int RECT_HEIGHT;
	private final int[] NEURON_DIM;
	private JFrame frame;
	private static final int RECT_X = 10;
	private static final int RECT_Y = RECT_X;
	private Node[][] nodes;
	private final double div;
	private final int mode;
	private final double[] dim;
	private final Color[] cols = new Color[] {
			Color.blue, 	//0
			Color.cyan, 	//1
			Color.yellow, 	//2
			Color.red, 		//3
			Color.green,	//4
			Color.orange, 	//5
			Color.pink, 	//6
			Color.magenta, 	//7
			new Color(170, 66, 244), //8 purple
			new Color(209, 244, 66)}; //9 light green
	public IMGvisualizer(MNISTproblem p, int mode, Node[][] nodes) {
		this.nodes = nodes;
		this.p = p;
		this.mode = mode;
		int[] pref = p.getPrefDim();
		double size = Math.max(pref[0], pref[1]);
		if(size > 800) {
			this.div = (size/frameSIZE);
		}else if (size < frameSIZE){
			this.div = (1/(frameSIZE/size));
		}else {
			this.div = 1;
		}
		this.RECT_WIDTH =(int) ((pref[0])/this.div)+12;
		this.RECT_HEIGHT = (int)((pref[1])/this.div)+12;
		this.dim = new double[] {RECT_WIDTH, RECT_HEIGHT};
		this.NEURON_DIM = new int[] {(int)(dim[0]/nodes.length), (int)(dim[1]/nodes[0].length)};
		setOpaque(true);
		setBackground(Color.WHITE);
		frame = new JFrame("IMG");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLocationByPlatform(true);
	}
   
   
   public void setNodes(Node[][] nodes) {
	   this.nodes=nodes;
   }
   
	@Override
	public void display(int stateNum) {
//		this.nodes = nodes;
		frame.setTitle("IMG iteration "+stateNum);
		frame.setVisible(true);
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   drawTemp(g);
   }
	
	public double[] fixCoords(double[] coords) {
		double[] newCoords = new double[] {coords[0], coords[1]};
		//x coord
		newCoords[0] = (coords[0])/div; //offset and scale
		
		//y coord
		newCoords[1] = dim[1]-((coords[1])/div); //flip vertical, offset and scale
		
		return newCoords;
	}
	
	private Color getCol(double label) {
		float r = (float)((label/10));
//		float g = (float)((120.0+label*10)/255);
//		float b = (float) ((10.0+label*10)/255);
//		System.out.println(r+" "+g+" "+b+"\n\n");
//		return new Color(r,(float)0.0,(float)0.0);
		int rounded = (int)Math.round(label);
		//if transparency for nodes that 
		return new Color(cols[rounded].getColorSpace(), cols[rounded].getColorComponents(null), (float)(1-1.5*Math.abs((label-rounded))));
//		return cols[rounded];
	}
	
	private void drawTemp(Graphics g) {
		// draw board borders
	   g.drawRect(0,0, RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
	   //TODO: draw grid
	   
	   //draw neurons
	   for (int i = 0; i < nodes.length; i++) {
		   for (int j = 0; j < nodes[0].length; j++) {
			   g.setColor(getCol((double)nodes[i][j].getLabel()));
			   g.fillRect((int)(i*NEURON_DIM[0]), (int)(j*NEURON_DIM[1]), NEURON_DIM[0], NEURON_DIM[1]);
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
