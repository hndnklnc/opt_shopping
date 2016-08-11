package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create store object with its properties
 * 
 */
import java.util.ArrayList;

public class Store {
	
	private int id; //the store id
	private String name; //the store name
	private String location;  //the store location
	private String address;
	private ArrayList<Product> productList; //the products that can buy from store
	private double totalPrice;
	private Distance distanceToUser; //distance bitween store location and user location
	
	public Store(int id, ArrayList<Product> productList) {
		super();
		this.setId(id);
		this.productList = productList;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the productList
	 */
	public ArrayList<Product> getProductList() {
		return productList;
	}
	/**
	 * @param productList the productList to set
	 */
	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Distance getDistanceToUser() {
		return distanceToUser;
	}

	public void setDistanceToUser(Distance distanceToUser) {
		this.distanceToUser = distanceToUser;
	}
	

}
