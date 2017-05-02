package pong.core.pongs;
import static utils.Utils.random;
import static utils.Utils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import pong.core.Ball;
import pong.core.bar.Bar;


public class Pong extends Observable {	
	private int width = 40;
	private int height = 20;
	private Ball ball;	
	protected List<Bar> bars;
	private int round;

	{
		bars = new ArrayList<Bar>();		
	}
	
	public Pong() {
		bars.add(new Bar(4, height, Ball.LEFT));		
		ball = new Ball(width, height);	
	}	
	
	public void moveBall() {			
		if (hitsSideLines(ball))
			ball.bounceVertical();
		
		if (hitsOppositeWall(ball)) 					
			ball.bounceHorizontal(false);
		
		for (Bar bar : bars) {		
			if (bar.hits(ball))
				ball.bounceHorizontal(true);
		}
		
		if(isOut(ball)) {				
			setChanged();
			notifyObservers();
			determineLooser().loose();
			
			restart();
		}
		ball.moveTowardsDirection();
		setChanged();
		notifyObservers();	
	}
	
	public void start() {
		restart();
	}

	protected Bar determineLooser() {
		return getBar();		
	}

	protected boolean isOut(Ball ball) {		
		return ball.x() <= 0;
	}
	
	protected boolean hitsSideLines(Ball ball) {
		return ball.y() < 0|| ball.y() >=height;
	}
	
	protected boolean hitsOppositeWall(Ball ball) {		
		return ball.x() >= width;
	}
	
	protected void restart() {	
		sleep(10);		
		ball.setPosition(width-1, random(height));
		ball.setDirection(-4-random(2), 1-random(2));			
		round++;
	}	
	
	public List<Bar> getBars() {
		return bars;
	}
	
	public int getRound() {
		return round;
	}
	
	public Bar getBar() {
		return getBars().get(0);
	}	
	
	public Ball getBall() {
		return ball;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

	public byte[][] getBitmap() {
		byte[][] bitmap = new byte[width][height];
		
		int x = limit(ball.x(), 0, width-1);
		int y =  limit(ball.y(), 0, height-1);
		
		bitmap[x][y] = 1;
		bitmap[getBar().getXPosition()][getBar().getYPosition()] = 1;
		return bitmap;
		
	}

	private int limit(int val, int min, int max) {
		return Math.max(min, Math.min(max, val));
	}
	
}
