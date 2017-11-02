package visuals;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import caseloader.Problem;
import caseloader.ProblemCreator.TSPproblem;
import som.Node;

 
public class Cards implements ActionListener {
    JPanel cards; //a panel that uses CardLayout
    public final static int TSP = 0;
    public final static int IMG = 1;
    private final int MODE;
    private final String[] titles = new String[] {"TSP ", "Image "};
    private final Problem p;
//    final static String BUTTONPANEL = "Card with JButtons";
//    final static String TEXTPANEL = "Card with JTextField";
     
    public void addComponentToPane(Container pane, ArrayList<Node[][]> nodes) {
        //Put the JComboBox in a JPanel to get a nicer look.
//        JPanel comboBoxPane = new JPanel(); //use FlowLayout
//        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
//        JComboBox cb = new JComboBox(comboBoxItems);
//        cb.setEditable(false);
//        cb.addItemListener(this);
//        comboBoxPane.add(cb);
         
    	//Create the panel that contains the buttons.
    	JPanel buttons = new JPanel();
    	
    	JButton prev = new JButton("Previous");
    	prev.addActionListener(this);
    	JButton next = new JButton("Next");
    	next.addActionListener(this);
        buttons.add(prev, BorderLayout.WEST);
        buttons.add(next, BorderLayout.EAST);
    	//Create the "cards".
        cards = new JPanel(new CardLayout());
        Collections.reverse(nodes);
    	for (Node[][] node : nodes) {
    		if(MODE==TSP) {
    			JPanel card1 = new TSPvisualizer((TSPproblem)this.p, nodes.indexOf(node), node);
    			cards.add(card1, nodes.indexOf(node));
    		}else if(MODE==IMG) {
//    			JPanel card1 = new MNISTVisualizer(this.p, nodes.indexOf(node));
//    			cards.add(card1, nodes.indexOf(node));
    		}
//    		card1.add(prev);
//    		card1.add(next);
		}
         
         
//        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(buttons, BorderLayout.SOUTH);
    }
     
//    public void itemStateChanged(ItemEvent evt) {
//        CardLayout cl = (CardLayout)(cards.getLayout());
//        cl.show(cards, (String)evt.getItem());
//    }
    
	@Override
	public void actionPerformed(ActionEvent evt) {
		CardLayout cl = (CardLayout) cards.getLayout();
//		String e = (String) evt.
		if(evt.getActionCommand()=="Next") {
			cl.next(cards);
		}else {
			cl.previous(cards);
		}
	}
     
	private void createAndShowGUI(ArrayList<Node[][]> nodes) {
        //Create and set up the window.
        JFrame frame = new JFrame(this.titles[this.MODE]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Create and set up the content pane.
        addComponentToPane(frame.getContentPane(), nodes);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public Cards(ArrayList<Node[][]> nodes, int mode, Problem p) {
    	this.MODE = mode;
    	this.p = p;
    	/* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(nodes);
            }
        });    	

    }
    

    public static void main(String[] args) {
    	return;
    }
    }


