package ants;

import java.util.List;
import java.util.Random;

import neuralnet.Vector;
import neuralnet.gene.Genome;
import neuralnet.net.BackpropagationNet;

public class MatingAnt extends Ant {
	private static int energyForMating;
	private static int matingPause;
	private int timeToLastMating;
	private static double matingLikelyhood;
	private int lifetime;
	private boolean alive;
	
	static {
		energyForMating = 1000;
		matingLikelyhood = 0.05;
		matingPause = 400;		
	}
	
	{		
		this.lifetime = 1500;	
		this.alive = true;
	}
	
	public MatingAnt(Vector worldSize, BackpropagationNet net) {
		super(worldSize, net);
	}

	public MatingAnt[] tryToMate(List<MatingAnt> tanks) {
		if (lifetime < energyForMating
				|| new Random().nextDouble() < matingLikelyhood
				|| timeToLastMating > 0)
			return new MatingAnt[0];

		for (MatingAnt tank : tanks) {
			if (tank != this && getPos().distanceTo(tank.getPos()) < 5)
				return mate(tank);
		}
		return new MatingAnt[0];
	}

	private MatingAnt[] mate(MatingAnt partner) {
		Genome myGenome = getBrain().getGenome();
		Genome myPartnersGenome = partner.getBrain().getGenome();
		Genome[] children = myGenome.createOffspring(myPartnersGenome);
		
		MatingAnt child1 = createChild(children[0]);
	
		
		MatingAnt child2 = createChild(children[1]);
		

		this.lifetime -= energyForMating;
		partner.lifetime -= energyForMating;
		this.timeToLastMating = matingPause;
		partner.timeToLastMating = matingPause;
		
		
		return new MatingAnt[] { child1, child2 };
	}
	
	private MatingAnt createChild(Genome genome) {
		BackpropagationNet net = new BackpropagationNet(getBrain());
		net.updateGenes(genome);
		MatingAnt child = new MatingAnt(worldSize, net);
		child.timeToLastMating = matingPause;
		return child;
	}
	
	private void die() {
		this.alive = false;

	}
	
	public int getEnergy() {
		return lifetime;
	}

	public boolean isAlive() {
		return this.alive;
	}
	
	@Override
	public void move(List<Vector> food) {	
		 lifetime--;
		 timeToLastMating = Math.max(0, timeToLastMating-1);
		 if (lifetime <= 0)
			 die();
		 if (isAlive())			
			 super.move(food);
	}
	
	@Override
	protected void eat(List<Vector> landmines, Vector closestParcel) {	
		super.eat(landmines, closestParcel);
		lifetime += 2000;
	}
}
