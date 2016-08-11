package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create product object with its properties
 * 
 */
import java.sql.SQLException;


public class Product {
	
	private String name;
	private String brand;
	private int id;
	private double price;
	private String storeName;
	public Product(String name, String brand) throws ClassNotFoundException, SQLException {
		super();
		this.name = name;
		this.brand = brand;
		this.id = Queries.FindProductwithBrandId(brand, name);
	}
	
	public Product(String name) throws ClassNotFoundException, SQLException {
		super();
		this.name = name;
		this.id = Queries.FindProductId(name) ;
		this.brand = Queries.FindProductBrand(id);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String name) throws SQLException {		
		this.storeName = name;
	}

}
