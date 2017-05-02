package pong.core.bar;

import java.util.Observable;

import pong.core.Ball;

public class Bar extends Observable{
	private int size = 3;
	private double position;
	private int xPos;
	private int score;
	private int height;
	private int facing;

	
	public Bar(int xPos, int height, int facing) {
		this.xPos = xPos;
		this.height = height;
		this.position = height/2;
		this.facing = facing;
	}
	
	//move in percent
	public void move(double direction) {				
		position = Math.max(0, Math.min(position+direction, height-size));			
	}
			
	
	public boolean hits(Ball ball) {			
		boolean sameXPos = ball.x() == xPos;
		boolean hitsBar = ball.y() >= position && ball.y() <= position+size;
		boolean fliesTowards = ball.getFlyingDirection() != facing;
		boolean hits = sameXPos && hitsBar && fliesTowards;
		if (hits)
			score++;
		return hits;
	}
		
	public void loose() {
		score = 0;
		setChanged();
		notifyObservers(new Boolean(false));		
	}	
	
	public int getScore() {
		return score;
	}

	public int getSize() {
		return this.size;
	}
	
	public int getYPosition() {
		return (int) position;
	}
	
	public int getXPosition() {
		return xPos;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		return position + " " + size;
	}
}