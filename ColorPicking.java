
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author josephcassidy
 * purpose: to read a file with a list of ints into an array
 * and then convert these into a collection of green values
 */

public class ColorPicking {
	public final static int GREEN_MAX = 250;
	public final static int GREEN_MIN = 100;

	/**
	 * read ints from a chosen file and return green values based on them.
	 * @param args
	 * @throws FileNotFoundException
	 */
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		File filename = new File(scan.next());
		Scanner fileScan = new Scanner(filename);
		int count = 0;
		int[] ints = new int[100];
		while(count < 100 && fileScan.hasNextInt()){
			ints [count] = fileScan.nextInt();
			count ++;
		}
		int min = ints[0];
		int max = ints[0];
		for(int i = 0; i <= count; i ++){
			if(ints[i] < min){
				min = ints[i];
			}
			if(ints[i] > max){
				max = ints[i];
			}
		}
		for(int i = 0; i <= count; i ++){
			System.out.println(ints[i] + " " + greenValue(ints[i], max, min));
		}
	}
	
	/**
	 * 
	 * @param val: a value in the list
	 * @param max: maximum value in the list
	 * @param min: minimum value in the list
	 * @return green: a green value based on the values given
	 */
	public static int greenValue(float val, float max, float min){
		int green = (int)((((val - min) / (max - min)) * (GREEN_MAX - GREEN_MIN) + GREEN_MIN));
		return green;
	}

}
