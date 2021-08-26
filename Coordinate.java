/**
  * structure to hold simple x,y coordinate pair
  * B.Catron  Spring 2015
**/
public class Coordinate {
	public int x;
	public int y;
		
	/**
         * provided the x and y values, initialize the coordinate's to these
         * x,y are any integer value
        **/
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//test equality of two coordinate objects
	public boolean equals (Object obj)
	{
		if ((obj == null) || !(obj instanceof Coordinate))
			return false;
		return ( this.x == ((Coordinate)obj).x && this.y==((Coordinate)obj).y);
	}
}
