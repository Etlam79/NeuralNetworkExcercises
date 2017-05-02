package ants;

import gui.NNSimulationController;
import gui.NNWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import neuralnet.NNProperties;
import neuralnet.Vector;
import neuralnet.net.BackpropagationNet;
import neuralnet.net.LayeredNetwork;
import neuralnet.net.NeuralNet;
import ants.gui.AntPopulationFrame;

public class AntPopulationContoller extends NNSimulationController {
	private List<Vector> food;
	private List<Ant> ants;
	private int amountOfFood;
	private int mapSize;

	public AntPopulationContoller(int mapSize, int nrOfFood, NNProperties props) {	
		super(props);
		this.mapSize = mapSize;
		this.amountOfFood = nrOfFood;	
		this.ants = initAnts(props);	
		this.food = new ArrayList<Vector>();	
	}

	private List<Ant> initAnts(NNProperties props) {	
		List<Ant> trucks = new ArrayList<Ant>();	
		
		for (int i = 0; i < props.getNumberOfNets(); i++) {
			BackpropagationNet net = new BackpropagationNet(props);			
			trucks.add(new Ant(new Vector(mapSize, mapSize), net));
		}
		return trucks;
	}
	
	private void createFood(int amount) {
		for (int i = 0; i < amount; i++) 
			food.add(Vector.random(mapSize, mapSize));
	}
	
	public List<Vector> getParcels() {
		return food;
	}	

	public List<Ant> getAnts() {
		return ants;
	}	

	@Override
	protected List<LayeredNetwork> getNets() {
		List<LayeredNetwork> nets = new ArrayList<LayeredNetwork>();
		ants.forEach(n -> nets.add(n.getBrain()));
		return nets;
	}

	@Override
	protected void doStep(boolean generationChange) {	
		createFood(amountOfFood - food.size());		
		for (Ant tank : ants) 		
			tank.move(food);					
	}
	
	public static void main(String[] args) throws IOException {		
		NNProperties props = new NNProperties("src/ants/ants.properties");			
		AntPopulationContoller m = new AntPopulationContoller(props.asInt("size"), props.asInt("nrOfMines"), props);			
		AntPopulationFrame window = new AntPopulationFrame(m, props);
		NNWindow w = new NNWindow(m.getNets());
		m.runGeneticApproach();		
	}
}