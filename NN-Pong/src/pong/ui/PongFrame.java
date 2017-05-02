package pong.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import pong.PongController;
import pong.core.pongs.Pong;
import neuralnet.net.LayeredNetwork;
import neuralnet.net.NeuralNet;

public class PongFrame extends JFrame {
	private List<Pong> pongs ;
	
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400,250);	
		setAlwaysOnTop( true );
	}
	
	public PongFrame(List<Pong> pongs) {	
		int grid = (int)Math.sqrt(pongs.size());		
		setLayout(new GridLayout(grid, grid));
		
		for (int i = 0; i < pongs.size(); i++) {
			PongPanel p = new PongPanel(pongs.get(i));			
			JPanel pa = new JPanel();
			pa.setLayout(new GridLayout());
			pa.add(p);
			//pa.add(new StatsPanel(control.getPongPlayers().get(i).getBrain()));		
			add(pa);
		}
	}
	
	
	class StatsPanel extends JPanel implements Observer {
		private JTextArea t ;
		private LayeredNetwork brain; 
		
		public StatsPanel(NeuralNet brain) {
			brain.addObserver(this);
			this.brain = brain;
			t = new JTextArea(10,10);
			add(t);
			
			setPreferredSize(new Dimension(50, 50));
			setMaximumSize(new Dimension(50, 50));
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			
//			byte[][] bitmap = pongs.get(0).getBitmap();
//			
//				StringBuffer b = new StringBuffer();
//			for (byte[] bs : bitmap) {
//				b.append(Arrays.toString(bs) + "\n");
//			}
			
		
			t.setText(brain.getFitness()+""  );
			
		}
	}
}
