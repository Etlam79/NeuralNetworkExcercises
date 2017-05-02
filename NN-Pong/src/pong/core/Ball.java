package pong.core;

import static utils.Utils.random;
import neuralnet.Vector;

public class Ball {	
	private Vector position;
	private Vector direction;
	private int height;
	private int width;
	private double speed = 0.01;	
	

	public static final int LEFT = 1;
	public static final int RIGHT = -1;

	public Ball(int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector(0,0);
	}
	
	public double[] toList() {
		return new double[]{position.x, position.y, direction.x, direction.y};
	}
	
	public void moveTowardsDirection() {
		position = position.add(direction.mult(speed));		
		
	}

	public void bounceHorizontal(boolean fuzzy) {		
		direction.x = -direction.x;
		if (fuzzy) {
			//direction.y = position.y;
			direction.y =  direction.y + random(2)-1;
		}		
	}

	public void bounceVertical() {
		
		direction.y = -direction.y;	
	}

	public int getFlyingDirection() {
		if (direction.x < 0)
			return RIGHT;
		return LEFT;
	}

	public int x() {
		return position.xi;
	}
	public int y() {
		return position.yi;
	}
	
	public void setPosition(int x, int y) {
		this.position = new Vector(x,y);		
	}
	
	public void setDirection(int x, int y) {
		this.direction = new Vector(x,y);		
	}
}