package pong.core.bar;

import pong.core.Ball;


public class FootballBar extends Bar {
	private Bar shooter;

	public FootballBar(int xPos, int xPos2, int height, int facing) {
		super(xPos, height, facing);
		shooter = new Bar(xPos2,height, facing);
	}
	
	@Override
	public void move(double direction) {
		super.move(direction);
		shooter.move(direction);
	}
	
	public boolean hits(Ball ball) {		
		return super.hits(ball) || shooter.hits(ball);
	}	

	public Bar getShooter() {
		return shooter;
	}
}
