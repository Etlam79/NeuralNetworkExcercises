package utils;

import java.util.Random;

public  class Utils {
	private static Random random;
	
	static {
		random = new Random();
	}
	
	public static void sleep(long milis) {
		try { 
			Thread.sleep(milis);
		}	
		catch (InterruptedException e) {		
			e.printStackTrace();
		}
	}
	
	public static int random(int range) {
		return random.nextInt(range);
	}
	
	public static double random(double range) {
		
		return random.nextInt((int)(range*100))/100.0;
	}
	
	public static float randomFloat() {
		return random.nextFloat();
	}

	public  static double randomDouble() {
		return random.nextDouble();
	}

}
