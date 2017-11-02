package som;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

import caseloader.ProblemCreator.TSPproblem;
import rushhour.RushHour;

public class TSPvisualizer extends JPanel implements SOMvisualizer{

   private static final int gridSize = 6;
   private final TSPproblem p;
   private int RECT_WIDTH;
   private int RECT_HEIGHT;
   private JFrame frame;

   public TSPvisualizer(TSPproblem p, int stateNum) {
	   this.p = p;
	   setOpaque(true);
	   setBackground(Color.WHITE);
	   frame = new JFrame("TSP iteration "+stateNum);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.getContentPane().add(this);
	   frame.pack();
	   frame.setLocationByPlatform(true);
   }
   
	@Override
	public void display(Node[] nodes, Node[][]... nodeArray) {
		
		frame.setVisible(true);
		
	}
	
	@Override
	   protected void paintComponent(Graphics g) {
		   super.paintComponent(g);
		   // draw board borders
		   g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
		   
		   //draw coordinate dots
		   g.setColor(Color.BLACK);
		   for (int i = 0; i < gridSize; i++) {
			   for (int j = 0; j < gridSize; j++) {
				   g.fillOval(30+60*i, 30+60*j, 20, 20);
			   }
		   }
		   
		   //draw exit
		   g.clearRect(10+60*gridSize, 10+2*60, 10, 60);
		   
		   //goal car
		   g.setColor(car0col);
		   RushHour.Piece p = gs.getCar0();
		   int[] c = p.getCoords();
		   g.fillRect(15+60*c[0], 15+60*c[1], (1-p.getOrientation())*60*p.getLength()+p.getOrientation()*60-10, (p.getOrientation())*60*p.getLength()+(1-p.getOrientation())*60-10);
		   
		   //other cars
		   for (int i = 0; i < gs.getPieces().size(); i++) {
			   p = gs.getPieces().get(i);
			   c = p.getCoords();
			   g.setColor(cols[i]);
			   g.fillRect(15+60*c[0], 15+60*c[1], ((1-p.getOrientation())*60*p.getLength()+p.getOrientation()*60)-10, ((p.getOrientation())*60*p.getLength()+(1-p.getOrientation())*60)-10);
	      }
	   }

	   @Override
	   public Dimension getPreferredSize() {
	      // so that our GUI is big enough
	      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
	   }

}
