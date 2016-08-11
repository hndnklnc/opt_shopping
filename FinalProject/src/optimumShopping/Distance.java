package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create distance object with time
 * 
 */
public class Distance {
	private int time;
	private double distance;	
	
	public Distance(int time, double distance) {
		super();
		this.time = time;
		this.distance = distance + (time/ 3); // every three minutes means 1 km more.
		
	}
	
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
