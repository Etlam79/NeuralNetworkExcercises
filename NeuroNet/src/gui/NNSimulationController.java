package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import neuralnet.NNProperties;
import neuralnet.gene.Genome;
import neuralnet.gene.GenomePopulation;
import neuralnet.gene.GenomePopulation.EpochParams;
import neuralnet.net.LayeredNetwork;
import neuralnet.net.NeuralNet;

public abstract class NNSimulationController extends Observable implements KeyListener {
	private Timer timer;
	private int speed = 1;
	private int time;
	private boolean pause;
	private NNProperties props;
	private GenomePopulation genAlgo;
	private boolean geneticMode;
	
	public NNSimulationController(NNProperties props) {
		this.props = props;		
	}
	
	public NNProperties getProps() {
		return props;
	}
	
	protected abstract List<LayeredNetwork> getNets();
	protected abstract void doStep(boolean generationChange);
	
	@Override
	public void keyPressed(KeyEvent e) {	
		switch (e.getKeyChar()) {	
			case 'p':
				pause = !pause;
				getNets().forEach(p->System.out.println(p.getGenome()));
				break;
			case '+':
				speed = Math.max(1, --speed);
				updateTimer(speed);
				break;	
			case '-':			
				updateTimer(++speed);
				break;					
		}	
	}
	
	public void run() {	
		geneticMode = false;
		updateTimer(speed);		
	}
	


	public void runGeneticApproach() {	
		geneticMode = true;
		initGenomeGenetics();
		updateTimer(speed);			
	}	

//	
//	public long getSpeed() {
//		return speed;
//	}

//	public long getAgeOfGeneration() {
//		return time;
//	}

	public List<EpochParams> getStats() {
		return genAlgo.getEpochHistory();
	}
	
	private void storeCurrentGeneration() {			
		 EpochParams d = genAlgo.getCurrentEpoch();	
		 System.out.printf((String.format("best:%s,avg:%s,worst:%s\n", 
				 d.get(EpochParams.BEST), 
				 d.get(EpochParams.AVERAGE), 
				 d.get(EpochParams.WORST))));			
	}
	
	public int getGeneration() {
		if (genAlgo == null)
			return 0;
		return genAlgo.getCurrentEpoch().getEpoch();
	}
	
	private void updateTimer(int speed) {
		if (timer != null)
			timer.cancel();
			 
		timer = new Timer();
	
		timer.schedule(new TimerTask() {			
			@Override
			public void run() {
				if (!pause) {
					time++;
					boolean generationChange = geneticMode && time > props.getGenerationLength();
				
					if (generationChange) 						
						createNextGen();					
					doStep(generationChange);		
					setChanged();
					notifyObservers(new int[] {speed, getGeneration(), time});
				}				
			}
		}, 0,speed);		
	}

	private void createNextGen() {		
		System.out.println("New Generation");	
		List<Genome> newGenomes = genAlgo.epoch();
		List<LayeredNetwork> nets = getNets();
		for (int i = 0; i < nets.size(); i++) {		
			nets.get(i).updateGenes(newGenomes.get(i));			
		}
		time = 0;
	}
	


	
	
	private void initGenomeGenetics() {					
		NNProperties props = getProps();
		Genome.setGeneticParams(props.getMutationRate(), props.getCrossoverRate(), props.getMaxPerturbation());		
		Genome stem = getNets().get(0).getGenome();			
		genAlgo = new GenomePopulation(props.getNumberOfNets(), stem, props.getNrElite(), props.getNrCopyElite());		
	
	}
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	
	
}
