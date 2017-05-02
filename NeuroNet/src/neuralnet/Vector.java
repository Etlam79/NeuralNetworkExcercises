package neuralnet;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;

import java.util.Random;

public class Vector {
	
	public double y;
	public double x;
	public int xi;
	public int yi;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
		this.xi = (int)x;
		this.yi = (int)y;
	}

//	public Position move(Position pos) {
//		return move(pos.x,pos.y);
//	}
//	public Position move(double i, double j) {
//		return new Position (Math.abs(x+i%Globals.size), Math.abs(y+i%Globals.size));
//		
//		
//	}
	
	
	@Override
	public String toString() {	
		return String.format("(%s,%s)", x,y);
	}

	public double distanceTo(Vector pos) {
		//double a = 0^2;
		return sqrt(pow(x-pos.x,2) + pow(y-pos.y,2));
		
	}

	public Vector getVectorTo(Vector other) {
		if (other == null) 
			throw new NullPointerException("Vector is null");
		return new Vector(other.x-x,other.y-y);
		
	}

	public Vector mult(double val) {
		return new Vector(x*val, y*val);
	}
	public double mult(Vector o) {
		return x*o.x + y*o.y;
	}

	public Vector add(Vector other) {
		return new Vector(x+other.x, y+other.y);
	}

	public Vector add(double val) {
		return new Vector(x+val, y+val);
	}
	
	public static Vector random(int maxX, int maxY) {
		Random r = new Random();
		return new Vector(r.nextInt(maxY),r.nextInt(maxY)); 
	}
	
	public double abs() {
		return sqrt(x*x+y*y);
	}

	
	public Vector fitToBoundaries(int xMin, int yMin, int xMax, int yMax) {		
		double newX = x;
		double newY = y;
		if (x > xMax) newX = xMax;
		if (x < xMin) newX = xMin;
		
		if (y > yMax) newY = yMax;
		if (y < yMin) newY = yMin;
		return new Vector(newX, newY);
	}
	
	public Vector fitToMap(int maxX, int maxY) {
		double newX = x;
		double newY = y;
		if (x > maxX) newX = 1;
		if (x < 0) newX = maxX-1;
		if (y > maxY) newY = 1;
		if (y < 0) newY = maxY;
		return new Vector(newX, newY);
	}

//	public Vector fitToMap() {
//		double newX = x;
//		double newY = y;
//		if (x > Globals.size) newX = Globals.size-1;
//		if (x < 0) newX = 1;
//		if (y > Globals.size) newY =  Globals.size-1;
//		if (y < 0) newY = 1;
//		return new Vector(newX, newY);
//	}
	
	public double getAngle(Vector o) {
		 double angle1 = atan2(o.y,o.x)- atan2(y,x);
		 return toDegrees(angle1);		
	}

	public Vector rotate(double angle) {
		double angleR = Math.toRadians(angle);
		double x_ = x * cos(angleR) - y *sin(angleR);
		double y_ = x * sin(angleR) + y * cos(angleR);
		return new Vector(x_, y_);
	}

	public Vector fitToMap(Vector worldSize) {
		return fitToMap(worldSize.xi, worldSize.yi);
	}


	

}
