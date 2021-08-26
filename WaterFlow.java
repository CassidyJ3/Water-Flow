import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class WaterFlow implements ADelegate {
	
	private float[][] map;
	public final int WIDTH;
	public final int HEIGHT;
	private JFrame window;
	// SIZE defines the size of a single JPanel
	public final Dimension SIZE = new Dimension(4, 4);
	private EPanel[][] graphicalGrid;
	private float min;
	private float max;
	private Coordinate current;
	private JPanel primary;
	
	
	/**
	 * creates an object using information in a given file
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public WaterFlow(String filename) throws FileNotFoundException {
		File f = new File(filename);
		Scanner s = new Scanner(f);
		WIDTH = s.nextInt();
		HEIGHT = s.nextInt();
		map = new float[WIDTH][HEIGHT];
		
		min = Float.MAX_VALUE;
		max = Float.MIN_VALUE;
		while(s.hasNextFloat()){
			float value = s.nextFloat();
			int x = s.nextInt();
			int y = s.nextInt();
			map[x][y] = value;
			if(map[x][y] < min){
				min = map[x][y];
			}
			if(map[x][y] > max){
				max = map[x][y];
			}
		}
		fillGaps();
		
		s.close();
		window = new JFrame();
		GridLayout gl = new GridLayout(WIDTH, HEIGHT, 0, 0);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graphicalGrid = new EPanel[WIDTH][HEIGHT];
		primary = new JPanel();
		primary.setLayout(gl);
		for(int row = 0; row < HEIGHT; row++){
			for(int col = 0; col < WIDTH; col++){
				EPanel panel = new EPanel(this, map[col][row], col, row);
				panel.setPreferredSize(SIZE);
				graphicalGrid[col][row] = panel;
				Coordinate c = new Coordinate(col, row);
				colorBlock(c);
				primary.add(panel);
				panel.addMouseListener(panel);
			}
		}
		window.add(primary);
		window.pack();
		window.setVisible(true);
	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception  {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the filename: ");
		String filename = keyboard.next();
		WaterFlow wf = new WaterFlow(filename);
		System.out.println("Enter anything to stop.");
		keyboard.nextLine();
		keyboard.close();
	}
	
	/**return true if a drop of water starting at the
	* location specified by row, column can reach the edge of
	* the map, false otherwise.
	 * @throws Exception 
	*/
	public boolean canFlowOffMap(Coordinate drop) throws Exception{
		Queue<Coordinate> queue = new Queue<Coordinate>(WIDTH * HEIGHT);
		queue.enqueue(drop);
		floodBlock(drop);
		while(queue.count() > 0){
			current = queue.dequeue();
			Coordinate north = new Coordinate(current.x, current.y - 1);
			if((canFlowTo(north))){
				queue.enqueue(north);
				floodBlock(north);
			}
			Coordinate south = new Coordinate(current.x, current.y + 1);
			if((canFlowTo(south))){
				queue.enqueue(south);
				floodBlock(south);
			}
			Coordinate east = new Coordinate(current.x + 1, current.y);
			if((canFlowTo(east))){
				queue.enqueue(east);
				floodBlock(east);
			}
			Coordinate west = new Coordinate(current.x - 1, current.y);
			if((canFlowTo(west))){
				queue.enqueue(west);
				floodBlock(west);
			}
			
		}
		if(queue.count() != 0){
			return flowsOff(queue.dequeue());
		}
		else{
			return false;
		}
	}
	
	/**
	 * identifies if the given location is an edge
	 * @param location
	 * @return
	 */
	public boolean flowsOff(Coordinate location){
		return(location.x == 0 || location.y == 0 || location.x == WIDTH - 1 || location.y == HEIGHT - 1);
	}
	
	/**
	 * Determines if water can flow to a new, given location given the current location
	 * @param current
	 * @param target
	 * @return
	 */
	public boolean canFlowTo(Coordinate target){
		return (((map[current.x][current.y] + 10) >= map[target.x][target.y]) && !graphicalGrid[target.x][target.y].isVisited());
	}
	
	/**
	 * colors the block at c the shade of green that matches it's elevation
	 * @param c
	 */
	private void colorBlock(Coordinate c){
		int greenVal = ColorPicking.greenValue(map[c.x][c.y], max, min);
		Color shade = new Color(0, greenVal, 0);
		graphicalGrid[c.x][c.y].setBackground(shade);
	}
	
	/**
	 * floods the block at Coordinate c
	 * @param c
	 */
	private void floodBlock(Coordinate c){
		graphicalGrid[c.x][c.y].setBackground(Color.blue);
		graphicalGrid[c.x][c.y].setVisited();
	}
	
	private float average(Coordinate c){
		float average = 0;
		float divisor = 0;
		if(c.y > 0 && map[c.x][c.y-1] != 0){
			average += map[c.x][c.y-1];
			divisor += 1;
		}
		if(c.y < HEIGHT-1 && map[c.x][c.y+1] != 0){
			average += map[c.x][c.y+1];
			divisor += 1;
		}
		if(c.x > 0 && map[c.x-1][c.y] != 0){
			average += map[c.x-1][c.y];
			divisor += 1;
		}
		if(c.x < WIDTH-1 && map[c.x+1][c.y] != 0){
			average += map[c.x+1][c.y];
			divisor += 1;
		}
		if(divisor != 0){
			average = average / divisor;
		}
		return average;
	}
	
	private void fillGaps(){
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < WIDTH; j++){
				if(map[i][j] == 0){
					map[i][j] = average(new Coordinate(i, j));
				}
			}
		}
		
		for(int i = HEIGHT - 1; i >=0; i--){
			for(int j = WIDTH - 1; j >= 0; j--){
				if(map[i][j] == 0){
					map[i][j] = average(new Coordinate(i, j));
				}
			}
		}
	}

	@Override
	public void handleAction(int x, int y) {
		try {
			this.canFlowOffMap(new Coordinate(x, y));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
