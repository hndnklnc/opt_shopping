package optimumShopping;
/**
 * @author Handan KILINÇ
 * Queries class is to execute postgre SQL queries.
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Queries {
	
	private static Connection conn;
	private static ArrayList<String> suggestions = new ArrayList<String>();
	private static ArrayList<Store> stores = new ArrayList<Store>();
	

	/*
	 * Supply to connect with Database
	 */
	public Queries() throws ClassNotFoundException, SQLException{
		// TODO Auto-generated constructor stub
		//the database connection is created
		conn = null;
		Class.forName("org.postgresql.Driver");
		conn = DriverManager
				.getConnection("jdbc:postgresql://127.0.0.1:5432/Shoppingdb",
						"postgres", "o");
	}
	
	/*
	 * Return the products name which is similar with user writing
	 */
	public static ArrayList<String> GetAutoProducts(String pieceOfWord) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select productname from product where productname like " +
				"'%"+pieceOfWord+"%' group by productname order by productname asc;");
		suggestions.clear();
		while(result.next()){
			suggestions.add(result.getString("productname"));
		}
		return suggestions;
	}
	
	/*
	 * Return the brand name which is similar with user writing
	 */
	public static ArrayList<String> GetAutoBrands(String pieceOfWord, String productName) throws SQLException{
		String upperKind = pieceOfWord.substring(0, 1).toUpperCase() + pieceOfWord.substring(1,pieceOfWord.length());	
		Statement suggestStat1 = conn.createStatement();
		Statement suggestStat2 = conn.createStatement();
		ResultSet result1 = suggestStat1.executeQuery("select brand from product where productname = '"+productName+"';");
		result1.next();	
		if(result1.getString("brand").equals("#")){
			suggestions.add("#");
		}else{
			ResultSet result2 = suggestStat2.executeQuery("select brand from product where productname = '"+productName+"' " +
					"and (brand like '%"+pieceOfWord+"%' or brand like '%"+upperKind+"%') group by brand order by brand asc;");
			suggestions.clear();
			while(result2.next()){
				suggestions.add(result2.getString("brand"));
			}
		}		
		return suggestions;
	}
	
	/*
	 * Return the locations name which is similar with user writing
	 */
	public static ArrayList<String> GetAutoLocations(String pieceOfWord) throws SQLException{
		String upperKind = pieceOfWord.substring(0, 1).toUpperCase() + pieceOfWord.substring(1,pieceOfWord.length());		
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select location1 from distances where (location1 like " +
				"'%"+pieceOfWord+"%' or location1 like '%"+upperKind+"%') group by location1 order by location1 asc;");
		suggestions.clear();
		while(result.next()){
			suggestions.add(result.getString("location1"));
		}
		return suggestions;
	}
	
	/*
	 * find the product id with the specific brand and product name
	 */
	public static int FindProductwithBrandId(String brand, String product) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select productid from product where brand='"+brand+"'" +
				" and productname = '"+product+"'");
		int id = 0;
		while(result.next()){
			id = result.getInt("productid");
		}
		return id;
	}
	
	/*
	 * finds the cheapest id with the given product name
	 */
	public static int FindProductId(String product) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select productid from productproperties where productname = '"+product+"'" +
				" and price = (select min(price) from productproperties where productname = '"+product+"');");
		result.next();
		return result.getInt("productid");
	}
	
	/*
	 * Find the brand with the specific product id
	 */
	public static String FindProductBrand(int id) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select brand from product where productid ="+id+";");
		result.next();
		return result.getString("brand");
	}
	
	/*
	 * Finds stores that have the some cart list element
	 * From these stores chooses the closest store to user location
	 */
	public static ArrayList<Store> FindStores(ArrayList<Product> productList) throws SQLException{
		Product product = null;
		stores.clear();
		ArrayList<Store> queryStores = new ArrayList<Store>();
		ArrayList<Store> storeGroups = new ArrayList<Store>();
		for(int i = 0; i < productList.size(); i++){
			Statement suggestStat = conn.createStatement();
			product = productList.get(i);
			ResultSet result = suggestStat.executeQuery("(select productproperties.storeid, storename from productproperties inner join" +
					" store on productproperties.storeid = store.storeid where productid = '"+product.getId()+"');");
			while(result.next()){
				boolean isStoreinList = false;
				//control that the same store is in list or not
				for(int j = 0; j < queryStores.size(); j++){
					if(queryStores.get(j).getId() == result.getInt("storeid")){
						product.setStoreName(result.getString("storename"));
						queryStores.get(j).getProductList().add(product);
						isStoreinList = true;
						break;
					}
				}
				//if the store isn't in queryStores, adds the new store
				if(!isStoreinList){
					ArrayList<Product> products = new ArrayList<Product>();
					products.add(product);
					Store store = new Store(result.getInt("storeid"), products);
					store.setName(result.getString("storename"));
					queryStores.add(store);
				}
			}
		}
		

		for(int i = 0; i < queryStores.size(); i++){
			ArrayList<Product> productGroup = queryStores.get(i).getProductList();
			Store store = FindMinDistanceStore(queryStores.get(i).getId());
			store.setName(queryStores.get(i).getName());
			store.setId(queryStores.get(i).getId());
			store.setProductList(productGroup);
			storeGroups.add(store);
		}
		System.out.println("fin store"+storeGroups.size());
		return storeGroups;
	}
	
	
	/*
	 * finds the closest store to user
	 */
	public static Store FindMinDistanceStore(int storeid) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select * from storeproperties inner" +
				" join distances on storeproperties.location = distances.location1 where" +
				" storeid = "+storeid+" and location2 = '"+MainScreen.getSelectedLocation()+"' " +
						"and distance = (select min(distance) from storeproperties inner join " +
						"distances on storeproperties.location = distances.location1 where" +
						" storeid = "+storeid+" and location2 = '"+MainScreen.getSelectedLocation()+"');");
		result.next();
		Store store = new Store(storeid, null);
		store.setLocation(result.getString("location"));
		store.setAddress(result.getString("address"));
		Distance distance = new Distance(result.getInt("period"), result.getDouble("distance"));
		store.setDistanceToUser(distance);
		return store;
	}	
	
	/*
	 * Finds the price of the given product in the store wit ids
	 */
	public static double FindPrice(int storeId, int productId) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select price from productproperties where storeId = "+storeId+"" +
				" and productId = "+productId+";");
		double price = 0;
		result.next();
		price = result.getDouble("price");
		return price;
	}
	
	/*
	 * Finds the distance between two location
	 */
	public static Distance findDistance(String location1, String location2) throws SQLException{
		Distance distance = null;
			Statement suggestStat = conn.createStatement();
			ResultSet result = suggestStat.executeQuery("select distance, period from distances where location1 = '"+location1+"'" +
					" and location2 = '"+location2+"';");
			result.next();
			distance = new Distance(result.getInt("period"), result.getDouble("distance"));
		
		return distance;
	}
	
	/*
	 * Finds the cheapest product in given name
	 */
	public static Product findCheapestProduct(int id) throws SQLException, ClassNotFoundException{
		Statement suggestStat = conn.createStatement();		
		ResultSet result = suggestStat.executeQuery("select * from productproperties where productid = "+id+" and" +
				" price = (select min(price) from productproperties where productid = "+id+");");
		result.next();
		Product product = new Product(result.getString("productname"), result.getString("brand"));
		System.out.println("result setten gelen !!!!!"+result.getString("brand"));
		System.out.println("sonraki");
		product.setPrice(result.getDouble("price"));
		String storename = findStoreName(result.getInt("storeid"));
		product.setStoreName(storename);
		return product;
	}
	/*
	 * Finds the name of store with the given id
	 */
	public static String findStoreName(int id) throws SQLException{
		Statement suggestStat = conn.createStatement();
		ResultSet result = suggestStat.executeQuery("select storename from store where storeid = "+id+";");
		result.next();
		return result.getString("storename");
	}
		
}
