import static org.junit.Assert.*;
import neuralnet.Vector;

import org.junit.Test;


public class VectorTest {

	@Test
	public void testRotation() {
		Vector x = new Vector(1, 0);
		Vector result = x.rotate(90);
		assertEquals(0, result.x,0.001);
		assertEquals(1, result.y, 0.001);
		
		
		Vector position = new Vector(0, 0);
		Vector direction = new Vector(1, 0);
		
	
			
			 direction = direction.rotate(90);
			
			position = position.add(direction);
		
			assertEquals(0, position.x,0.001);
			assertEquals(1, position.y, 0.001);
			
		}
		
		
		
		
	

}
