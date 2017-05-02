package neuralnet.gene;
import static utils.Utils.randomDouble;
import static utils.Utils.randomFloat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import neuralnet.net.Gene;

public class GenomePopulation {	
	private int popSize;
	private List<Genome> population;	
	private List<EpochParams> epochHistory;
	private EpochParams currentEpoch;
	private int numElite;
	private int numCopiesElite;

	public GenomePopulation(int popsize, Genome stem , int numElite, int numCopiesElite) {
		this.popSize = popsize;	
		this.numElite = numElite;
		this.numCopiesElite = numCopiesElite;
		this.population = initPopulation(popSize, stem);
		currentEpoch = new EpochParams(0);
		epochHistory = new ArrayList<GenomePopulation.EpochParams>();
	}
	
	

	//-----------------------------------Epoch()-----------------------------
		//
		//		takes a population of chromosones and runs the algorithm through one
		//		 cycle.
		//		Returns a new population of chromosones.
		//
		//-----------------------------------------------------------------------
	public List<Genome> epoch() {
		return epoch(this.numCopiesElite, this.numElite);
	}
		
	
	
	private List<Genome> epoch(int numCopiesElite, int numElite) {
		//assign the given population to the classes population		 
		//sort the population (for scaling and elitism)
		Collections.sort(population);
		 
		//calculate best, worst, average and total fitness
	  
		currentEpoch = calculateBestWorstAvTot(currentEpoch.epoch, population);
		epochHistory.add(currentEpoch);
	  
		//create a temporary vector to store new chromosones
		List<Genome> newPopulation = new ArrayList<Genome>();

		// Now to add a little elitism we shall add in some copies of the
		// fittest genomes. Make sure we add an EVEN number or the roulette
		// wheel sampling will crash
		if ((numCopiesElite * numElite)%2 == 0) {
			List<Genome> nBest = grabNBest(numElite, numCopiesElite, population);		
			newPopulation.addAll(nBest);	
		}	
	  
//		  newPopulation.forEach(p->System.out.println("Cloning the Best" + newPopulation));
		
		//now we enter the GA loop			
		//repeat until a new population is generated
		while (newPopulation.size() < this.popSize) {
			//grab two chromosones
			Genome mum = getChromoRoulette(population, currentEpoch.totalFitness);
			Genome dad = getChromoRoulette(population, currentEpoch.totalFitness);
			Genome[] children = mum.createOffspring(dad);
			//now copy into vecNewPop population
			newPopulation.add(children[0]);
			newPopulation.add(children[1]);
		}	
		newPopulation.forEach(p->System.out.println(p));
//			newPopulation.forEach(p->System.out.println(newPopulation));
		this.population =  newPopulation;
		return this.population;
	}
	
	/**
	 * initialise population with chromosomes consisting of random weights and all fitnesses set to zero		
	 * @param popSize
	 * @param nrOfGenes
	 * @return
	 */	
	private List<Genome> initPopulation(int popSize, Genome stem) {
		List<Genome> population = new ArrayList<Genome>();	
		//int length = chromoLength.getGenes().size();	
		
		for (int i=0; i<popSize; ++i) {			
			Genome g = stem.clone();
			
			for (Gene gene : g.getGenes()) {									
				gene.setWeight(randomDouble());		
			}
			population.add(g);		
		}
		return population;
	}

	//----------------------------------GetChromoRoulette()------------------
	//
    //		returns a chromo based on roulette wheel sampling
	//
	//-----------------------------------------------------------------------
	private Genome getChromoRoulette(List<Genome> population, double totalFitness) {
		//generate a random number between 0 & total fitness count		
		double slice = (double)(randomFloat() * totalFitness);

		//go through the chromosones adding up the fitness so far
		double fitnessSoFar = 0;
		
		for (int i = 0; i < population.size(); ++i) {
			fitnessSoFar += population.get(i).getFitness();
			//if the fitness so far > random number return the chromo at 
			//this point
			if (fitnessSoFar >= slice) 
				return population.get(i);			
		}
		return null;
	}
		
	//-------------------------GrabNBest----------------------------------
	//
    //	This works like an advanced form of elitism by inserting NumCopies
	//  copies of the NBest most fittest genomes into a population vector
	//--------------------------------------------------------------------
	private List<Genome> grabNBest(int n, int numCopies, List<Genome> population) {		
		//add the required amount of copies of the n most fittest 
		//to the supplied vector
		List<Genome> nBest = new ArrayList<Genome>();
		for (int i = 0; i < n; i++) {
			for (int j=0; j<numCopies; ++j) {
				if (population.get(i).getFitness() > 0)
					nBest.add(new Genome(population.get(i).getGenes()));
		    }
		}
		return nBest;
	}

	//-----------------------CalculateBestWorstAvTot-----------------------	
	//
	//		calculates the fittest and weakest genome and the average/total 
	//		fitness scores
	//---------------------------------------------------------------------
	private EpochParams calculateBestWorstAvTot(int epoch, List<Genome> population) {			
		double highestSoFar = 0;
		double lowestSoFar  = 9999999;
		EpochParams stat = new EpochParams(epoch+1);
		
		for (int i=0; i<popSize; ++i) {
			double popFitness = population.get(i).getFitness();
			//update fittest if necessary
			if (popFitness > highestSoFar) {
				highestSoFar = popFitness;				
				stat.fittestGenome = i;
				stat.bestFitness = highestSoFar;
			}			
			//update worst if necessary
			if (popFitness < lowestSoFar) {
				lowestSoFar = popFitness;				
				stat.worstFitness = lowestSoFar;
			}			
			stat.totalFitness += popFitness;			
		}//next chromo		
		stat.averageFitness = stat.totalFitness / popSize;
		return stat;
	}





	public class EpochParams {
		public static final int BEST = 0;
		public static final int AVERAGE = 1;
		public static final int TOTAL = 2;
		public static final int WORST = 3;		
//		public List<Genome> population;
		private int epoch;

		public EpochParams(int epoch) {
//			this.population = population;
			this.epoch = epoch;
		}

		public int fittestGenome;
		 private  double bestFitness;
		 private double worstFitness;
		 private double averageFitness;
		 private double totalFitness;
		 
		 {
			bestFitness		= 0;
			worstFitness	= 9999999;
			averageFitness	= 0;
		 }	
		 
		public double get(int param) {
			if (param == BEST) return bestFitness;
			if (param == AVERAGE) return averageFitness;
			if (param == TOTAL) return totalFitness;
			if (param == WORST) return worstFitness;
			return -1;
		}

		public int getEpoch() {
			return epoch;
		}
	}

	public List<EpochParams> getEpochHistory() {
		return epochHistory;
	}
	
	public EpochParams getCurrentEpoch() {
		return currentEpoch;
	}	
}
