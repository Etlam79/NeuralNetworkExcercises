package neuralnet.gene;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static utils.Utils.*;
import neuralnet.net.Gene;
import neuralnet.neuron.Neuron;

public class Genome implements Comparable<Genome> {

	private static double mutationRate;
	private static double crossoverRate;
	private static double maxPerturbation;

	private List<Gene> genes;
	private Hashtable<String, Gene> map;
	private ConnectionMap inputConnections;
	private ConnectionMap outputConnections;
	// List<Gene> weights;
	double fitness;


	
	public static void setGeneticParams(double mutationRate, double crossoverRate, double maxPertubation) {
		Genome.mutationRate = mutationRate;
		Genome.crossoverRate = crossoverRate;
		Genome.maxPerturbation = maxPertubation;
		
	}

	{
		map = new Hashtable<String,Gene>();
		genes = new ArrayList<Gene>();
		inputConnections = new ConnectionMap();
		outputConnections = new ConnectionMap();
	}

	

	public Genome(List<Gene> w) {
		genes = w;
	}

	public Genome() {
		 
	}
	
	public int length() {
		return genes.size();
	}

	// public void addWeight(Gene g) {
	// genes.add(g);
	// }
	//
	// public void addWeights(List<Gene> w) {
	// genes.addAll(w);
	// genes.forEach(g->g.setCurrentGenome(this));
	// }
	//

	public double getFitness() {
		return fitness;
	}

	// public Gene getGeneFor(Neuron from, Neuron to) {
	// return getGeneFor(from.getID(),to.getID());
	// //genes[from.getID()][to.getID()];
	// }

	// public Gene getGeneFor(int fromID, int toID) {
	// return genes.get(fromID*nrOfNeurons + toID);
	// //genes[from.getID()][to.getID()];
	// }
	//

	public List<Gene> getGenes() {
		return genes;

	}
	
	public void updateWeights(double[] genes) {
		if (genes.length != length()) {
			throw new InvalidGenomeException(this, genes);
		}
		List<Gene> list = getGenes();
		for (int i = 0; i < genes.length; i++) {
			list.get(i).setWeight(genes[i]);
		}
	}


	@Override
	public int compareTo(Genome o) {
		if (fitness < o.fitness)
			return 1;
		if (fitness > o.fitness)
			return -1;
		return 0;

	}

	public void setFitness(double fitness) {
		this.fitness = fitness;

	}

	@Override
	public String toString() {
		return "f:" + fitness + " " + genes.toString();
	}

	// public List<Double> getWeights() {
	// List<Double> weights = new ArrayList<Double>();
	// for (int i = 0; i < genes.length; i++) {
	// for (int j = 0; j < genes[i].length; j++)
	// weights.add(genes[i][j].getWeight());
	// }
	// return weights;
	// }

	public void increaseFitness() {
		fitness += 1 ;
	}

	public Genome[] createOffspring(Genome dad) {
		// create some offspring via crossover
		Genome[] children = crossover(dad);
		Genome baby1 = children[0];
		Genome baby2 = children[1];

		// now we mutate
		mutate(baby1);
		mutate(baby2);
		return children;
	}

	// -------------------------------------Crossover()-----------------------
	//
	// given parents and storage for the offspring this method performs
	// crossover according to the GAs crossover rate
	// -----------------------------------------------------------------------
	private Genome[] crossover(Genome dadsGenes) {
		List<Gene> baby1 = new ArrayList<Gene>();
		List<Gene> baby2 = new ArrayList<Gene>();

		
		List<Gene> mum = this.getGenes();
		List<Gene> dad = dadsGenes.getGenes();
		// just return parents as offspring dependent on the rate
		// or if parents are the same
		if ((randomFloat() > crossoverRate) || (mum == dad)) {
			baby1.addAll(mum);
			baby2.addAll(dad);
			return new Genome[] { new Genome(baby1), new Genome(baby2) };
		}

		// determine a crossover point
		int cp = new Random().nextInt(genes.size() - 1);

		// create the offspring
		for (int i = 0; i < cp; ++i) {
			baby1.add(mum.get(i).clone());
			baby2.add(dad.get(i).clone());
		}

		for (int i = cp; i < mum.size(); ++i) {
			baby1.add(dad.get(i).clone());
			baby2.add(mum.get(i).clone());
		}

		return new Genome[] { new Genome(baby1), new Genome(baby2) };
	}

	// mutates a chromosome by perturbing its weights by an amount not
	// greater than CParams::dMaxPerturbation
	// -----------------------------------------------------------------------
	private void mutate(Genome genome) {
		List<Gene> genes = genome.getGenes();
		// traverse the chromosome and mutate each weight dependent
		// on the mutation rate

		for (Gene gene : genes) {
			if (randomFloat() < mutationRate) {
				// add or subtract a small value to the weight
				if (gene.getWeight() > 0) {					
					double d = gene.getWeight();
					gene.setWeight(d + randomDouble() * maxPerturbation);
				}
			}

		}

	}
	
	private String getKey(Neuron from, Neuron to) {
		return from.getID()+":"+to.getID();
	}

	
	@Override
	protected Genome clone() {
		Genome clone = new Genome();
		for (Gene gene : genes) {
			clone.addGene(gene.clone());
			
		}
		return clone;
	}
	
	
	public List<Gene> getInputConnections(Neuron n) {
		return inputConnections.get(n);
	}
	
	public List<Gene> getOutputConnections(Neuron n) {
		return outputConnections.get(n);
	}

	public void addGene(Gene weight) {
		genes.add(weight);
		map.put(getKey(weight.getFrom(), weight.getTo()), weight);
		outputConnections.addConnection(weight.getFrom(), weight);
		inputConnections.addConnection(weight.getTo(), weight);
		
	}



	public boolean containsGene(Neuron from, Neuron to) {
		return map.containsKey(getKey(from, to));
	}

	public Gene getGenome(Neuron from, Neuron to) {
		return map.get(getKey(from, to));
	}
	
	class ConnectionMap extends Hashtable<Neuron, List<Gene>> {
		void addConnection(Neuron neuron, Gene gene) {
			if (!containsKey(neuron)) 
				put(neuron, new ArrayList<Gene>());			
			get(neuron).add(gene);				
		}
		
		
		public synchronized List<Gene> get(Neuron key) {
			if (containsKey(key))
				return super.get(key);
			else 
				return new ArrayList<Gene>();
		}
	}

	public void createGene(Neuron from, Neuron to, double weight) {	
		if (!containsGene(from, to)) {			
			Gene con = new Gene(from,to, weight);		
			addGene(con);
		}
		else
			System.err.println("Genome already exists " + getGenome(from, to));
	}


}
