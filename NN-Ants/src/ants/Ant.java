package ants;

import java.util.List;
import java.util.Observable;
import java.util.Random;

import neuralnet.Vector;
import neuralnet.gene.Genome;
import neuralnet.net.BackpropagationNet;

public class Ant extends Observable {
	private BackpropagationNet brain;
	private double rotation;
	private Vector closestPieceOfFood;
	private Vector position;
	private Vector lookAt;
	private double speed;

	private int id;

	private double rotationAngle;

	protected Vector worldSize;
	
	private static int IDs = 0;


	{		
		this.speed = 1;
		this.lookAt = new Vector(0, 1);
	

	}

	public Ant(Vector worldSize, BackpropagationNet net) {
		this.id = IDs++;
		this.rotation = new Random().nextInt(100) / 100.0 * Math.PI * 2;
		this.worldSize = worldSize;
		// leftTrack = 0.16;
		// rightTrack = 0.16;

		// m_dScale(CParams::iSweeperScale),
		// m_iClosestMine(0)
		this.position = Vector.random(worldSize.xi, worldSize.yi);
		this.brain = net;
	}

	public int getId() {
		return id;
	}

	public Vector getClosestParcel() {
		return closestPieceOfFood;
	}

	public Vector getPos() {
		return position;
	}

	public Vector getLookAt() {
		return lookAt;
	}

	public double getRotation() {
		return rotation;
	}

	private Vector calcClosestLandmine(List<Vector> landmines) {
		double closest_so_far = Integer.MAX_VALUE;
		Vector closestMine = null;
		// cycle through mines to find closest
		for (Vector mine : landmines) {
			double distance = mine.distanceTo(getPos());

			if (distance <= closest_so_far) {
				closest_so_far = distance;
				closestMine = mine;
			}
		}
		return closestMine;
	}

	public void move(List<Vector> food) {
		if (food.isEmpty())
			return;
		
		this.closestPieceOfFood = calcClosestLandmine(food);
		
		if (foundFood(closestPieceOfFood)) {
			eat(food, closestPieceOfFood);			
		}

		Vector vectorToMine = getPos().getVectorTo(closestPieceOfFood);

		double[] output = brain.calculate(vectorToMine.x / 50,
				vectorToMine.y / 50);
		// brain.learn();
		/*
		 * double[] output = brain.calculate(vectorToMine.x, vectorToMine.y,
		 * lookAt.x, lookAt.y); leftTrack = output[0]; rightTrack = output[1];
		 */

		// output[1] = 0.5;
		// output[0] = 0.6;
		//

		speed = output[1];
		rotationAngle = ((output[0] * 360) - 180) / 10;

	

		/*
		 * //calculate steering forces double rotationForce = leftTrack -
		 * rightTrack; //clamp rotation rotationForce =
		 * Math.max(-Globals.maxTurnRate, Math.min(Globals.maxTurnRate,
		 * rotationForce)); double oldRotation = rotation; rotation +=
		 * rotationForce; speed = (leftTrack + rightTrack ) * output[2]; double
		 * oldAngle = lookAt.getAngle(vectorToMine)%180; update look at lookAt.x
		 * = - Math.sin(rotation); lookAt.y = Math.cos(rotation);
		 */
		// double newAngle = (lookAt.getAngle(vectorToMine)%180)/100;

		// update position
		Vector velocity = lookAt;
		Vector direction = velocity.rotate(rotationAngle / 10);
		lookAt = direction;

		Vector newPos = position.add(direction.mult(speed)).fitToMap(worldSize);

		// Vector newPos =
		// getPos().getVectorTo(getPos().add(speed)).rotate(rotationAngle).fitToMap();
		// Vector newPos =
		// getPos().rotate(rotationAngle).mult(speed).fitToMap();

		double angleError = (direction.getAngle(vectorToMine) % 360);

		//
		// System.out.println(
		// "result " + output[0] +
		// "\ntarget " + (rotation-angleError+180)/360+
		// "\n result_ " + output[1] +
		// "\n target_" + (Math.min(0, vectorToMine.distanceTo(newPos)-
		// vectorToMine.distanceTo(position))));

		// distanceTillNext += newPos.distanceTo(this.position);

		// lookAt = position.getVectorTo(newPos);

		// double r = Math.max(0,Math.abs(newAngle)-Math.abs(oldAngle));
//		brain.train(
//				angleError,
//				Math.max(
//						0,
//						1 + vectorToMine.distanceTo(newPos)
//								- vectorToMine.distanceTo(position)));
		this.position = newPos;

		setChanged();
		notifyObservers();
	}
	
	private boolean foundFood(Vector closestParcel2) {
		return getPos().distanceTo(closestPieceOfFood) < 5;
	}

	protected void eat(List<Vector> landmines, Vector closestParcel) {
		landmines.remove(closestParcel);
		closestParcel = null;
		getBrain().getGenome().increaseFitness();
	}

	void reset() {
		this.rotation = new Random().nextInt(100) / 100.0 * Math.PI * 2;
		this.position = Vector.random(worldSize.xi, worldSize.yi);
	}
	
	public BackpropagationNet getBrain() {
		return brain;
	}

	public boolean isAlive() {
		return true;
	}

	public void updateBrain(Genome newGenome) {
		getBrain().updateGenes(newGenome);
		reset();
	}
}
