package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import neuralnet.net.Gene;
import neuralnet.net.LayeredNetwork;
import neuralnet.neuron.InputNeuron;
import neuralnet.neuron.Neuron;
import neuralnet.neuron.group.NeuronGroup;
import neuralnet.neuron.layer.NeuronLayer;

public class NNCanvas  extends JPanel {
	private int diameter = 40;
	int paddingRight = 0;
	int paddingLeft = 20;
	int paddingTop = 0;
    double scale = .8;
	private int x, y;
	private LayeredNetwork net;
	private Hashtable<Neuron, JTable> models;
	private Color[] neuronGroupColors = {Color.GRAY, Color.BLUE, Color.magenta, Color.yellow, Color.GREEN};
	
	
	
	public NNCanvas(LayeredNetwork net) {	
		this.net = net;
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(500,600));	

		setLayout(null);
	 }
	
	public NNCanvas(LayeredNetwork net, int x, int y) {
		this(net);
		this.y = y;
		this.x = x;		
		setPreferredSize(new Dimension(x,y));	 
	 }
	
	public void setNet(LayeredNetwork net) {
		this.net = net;
	}
		
	private void drawLines(Graphics g, Neuron a, Point to, Hashtable<Neuron, ColoredPoint> renderer) {
		g.setColor(Color.lightGray);
		
		for (Gene c :  a.getInputConnections()) {			
			Point from = renderer.get(c.getFrom());		
			g.drawLine(from.x+diameter/2, from.y+diameter/2, to.x+diameter/2, to.y+diameter/2);
		}				
	}
	
	private void fillShape(Graphics g, ColoredPoint position, Neuron n) {	
		g.setColor(position.color);
		if (n instanceof InputNeuron) 
			g.fillRect(position.x,position.y, diameter, diameter);
		else
			g.fillOval(position.x,position.y, diameter, diameter);
		
		double out = n.getOutput();
		
		g.setColor(Color.gray.brighter());
		g.fillRect(position.x-5, position.y+18, 75, 15);
		
		g.setColor(Color.black);
		g.drawString(n.getName(), position.x+5, position.y+15);
		
		g.drawString(String.format("%.6f",out), position.x-5, position.y+30);
		g.drawString(String.format("%.6f",n.getError()), position.x-5, position.y+50);
	}
	
		
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1.create();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		//g.scale(.8, .8);		
		Hashtable<Neuron, ColoredPoint> renderer = calcPositions();		
		drawNet(g, renderer);			
		drawWeights(g, renderer);				
		g.dispose();		
	}
	
	private Hashtable<Neuron, ColoredPoint> calcPositions() {		
		Hashtable<Neuron, ColoredPoint> renderer = new Hashtable<Neuron, ColoredPoint>();			
		NeuronLayer inputs = net.getInputLayer();
		List<NeuronLayer> all = net.getAllLayer();

		int distance_y = (int) ((y-160) / (all.size())*(2-scale));	
		int width = (int)((x-diameter-paddingRight-paddingLeft)*(2-scale));				
		int offset = width / (inputs.size()-1);			
		int index = 0;
		
		for (int x = 0; x < inputs.getNeuronGroups().size(); x++) {
			NeuronGroup group = inputs.getNeuronGroups().get(x);
			Color c = neuronGroupColors[x];
			for (Neuron neuron : group) {
				renderer.put(neuron, new ColoredPoint(index*offset+paddingLeft, 50, c));	
				index++;
			}
		}
				
		for (int i = 0; i < all.size(); i++) {
			NeuronLayer layer = all.get(i);			
			index = 0;
			for (int gIndex = 0; gIndex < layer.getNeuronGroups().size(); gIndex++ ) {					
				Color c = neuronGroupColors[gIndex];
				NeuronGroup group = layer.getNeuronGroups().get(gIndex);
				
				for (Neuron neuron : group) {				
					offset = width / (layer.size());
					renderer.put(neuron, new ColoredPoint(offset/2+index*offset+paddingLeft, 50+distance_y*(i+1),c));
					index++;
				}
			}
		}		
		return renderer;
	}

	private void drawNet(Graphics2D g, Hashtable<Neuron, ColoredPoint> renderer) {
		for (Neuron a : renderer.keySet()) {
			Point to = renderer.get(a);			
			drawLines(g, a, to, renderer);				
		}	
		for (Neuron a : renderer.keySet()) {
			ColoredPoint to = renderer.get(a);				
			fillShape(g, to, a);		
		}
	}
	
		
	int steps;
	
	
	private void drawWeights(Graphics g, Hashtable<Neuron, ColoredPoint> renderer) {
		
		if(models == null) 
			models = createWeights(renderer);		
		if (steps++ > 100)
			steps = 0;
		else
			return;
			
		
		for (Neuron a : renderer.keySet()) {
			//Point to = renderer.get(a);
			//int toX = to.x-10;
			//Color old = g.getColor();
			
			if (models.containsKey(a) ){					
				List<Double> weights = a.getInputWeights();
				Object[][] o = new Object[weights.size()][1];
				for (int i =0, j = 0 ; i < weights.size(); i++,j+=2) {
					o[i][0] = weights.get(i);
				}					
				models.get(a).setModel(new DefaultTableModel(o, new String[]{a.getName()}));
			}		
		}		
	}		

		private Hashtable<Neuron, JTable> createWeights(Hashtable<Neuron, ColoredPoint> renderer) {
			Hashtable<Neuron, JTable> models = new Hashtable<Neuron, JTable>();
			
			for (Neuron a : renderer.keySet()) {
				Point to = renderer.get(a);
												
				if (! (a instanceof InputNeuron)) {
									
					JTable t = new JTable();
					models.put(a,t);
//						
//	//				t.setPreferredSize(new Dimension(76, 15* o.length));
//	//				t.setSize(new Dimension(76, 15* o.length));
//	//				t.setMaximumSize(new Dimension(76, 15* o.length));
//	//				t.setBounds(to.x-50, to.y-200, 76, 200);
					int nr = a.getInputWeights().size();
//					
					JScrollPane sp = new JScrollPane(t);
//				
//					t.setBounds(to.x, to.y, 100, Math.min(200, 23+nr*16));
					
				
						
					sp.setBounds(to.x, to.y+100, 100, Math.min(200, 23+nr*16));
					
					add(sp);
				}
			}	
			return models;
		}

//		private void drawWeight(Graphics g, Color c, String text, int toX, int toY) {
//			
//			g.setColor(c);
//			g.fillRect(toX, toY-24, 140,20);
//			g.setColor(Color.lightGray.brighter());
//			g.drawString(text, toX, toY-10);
//		}
		
//		private void drawWeight(Graphics g, Color c, Neuron a, Neuron b, double weight, int toX, int toY, double gene) {
//			String text = String.format("%.15f ", //,g:%s/f:%s",
////					b.getName(), a.getName(), 
//					weight
//					//, gene.getEpoch(), 
//					//(int)gene.getParentGenome().getFitness()
//					);
//			drawWeight(g, c, text, toX, toY);	
//		}	
//		
		class ColoredPoint extends Point {
			private Color color;

			public ColoredPoint(int x, int y, Color c) {
				super(x,y);
				this.color = c;
			}
		}
	}

