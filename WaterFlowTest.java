import static org.junit.Assert.*;


import org.junit.Test;


public class WaterFlowTest {

	@Test
	public void test() throws Exception {
		String filename = "waterFlowSample.txt";
		WaterFlow wf = new WaterFlow(filename);
		int x = 4;
		int y = 2;
		assertFalse(wf.canFlowOffMap(new Coordinate(x, y)));
	}

}
