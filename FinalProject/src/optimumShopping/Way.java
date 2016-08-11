package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create wayobject with its properties
 * 
 */
import java.util.ArrayList;


public class Way {
	private ArrayList<Store> storeWay = new ArrayList<Store>();
	private double wayExpense; //the fuel expense of all way
	private double totalExpense; //way expense and products' expense
	
	public Way(ArrayList<Store> storeWay, double wayExpense) {
		super();
		this.storeWay = storeWay;
		this.wayExpense = wayExpense;
	}
	
	/**
	 * @return the totalExpense
	 */
	public double getTotalExpense() {
		return totalExpense;
	}
	/**
	 * @param totalExpense the totalExpense to set
	 */
	public void setTotalExpense(double totalExpense) {
		this.totalExpense = totalExpense;
	}
	
	/**
	 * @return the wayExpense
	 */
	public double getWayExpense() {
		return wayExpense;
	}

	public ArrayList<Store> getStoreWay() {
		return storeWay;
	}

	public void setStoreWay(ArrayList<Store> storeWay) {
		this.storeWay = storeWay;
	}
	
}
