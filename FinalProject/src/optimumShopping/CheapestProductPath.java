package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class to find stores according to Model 3
 * It is subclass of Cheapest Path
 */
import java.sql.SQLException;
import java.util.ArrayList;


public class CheapestProductPath extends CheapestPath{
	ArrayList<Store> stores = new ArrayList<Store>();
	ArrayList<String> cheapestStoresNames = new ArrayList<String>();
	ArrayList<Store> cheapestProductStores = new ArrayList<Store>();
	ArrayList<Product> cheapestProducts= new ArrayList<Product>();
	
	/*
	 * According tocart list finds the stores which has the products in the list
	 */
	public CheapestProductPath(ArrayList<Product> cartList) throws ClassNotFoundException, SQLException{
		stores = Queries.FindStores(cartList);
		findCheapestProduct();
	}
	
	/*
	 * Sends the eliminated stores to find cheapest way direction
	 */
	public Way findCheapestProductsWay() throws SQLException, ClassNotFoundException{
		return findCheapestWay(cheapestProductStores);
	}
	
	/*
	 * Adds the products in the stores
	 */
	public void addCheapestProductsInStore() throws ClassNotFoundException, SQLException{
		ArrayList<Integer> controlledIndexes = new ArrayList<>();
		for(int i = 0; i < cheapestProducts.size(); i++){
			for(int j = 0; j < cheapestProductStores.size(); j++){
				if(cheapestProducts.get(i).getStoreName().equals(cheapestProductStores.get(j).getName())){
					if(!controlledIndexes.contains(i)){
						cheapestProductStores.get(j).getProductList().add(cheapestProducts.get(i));
						controlledIndexes.add(i);
					}
					
				}
			}
		}
		removeEmptyStore(cheapestProductStores);
		findTotalPrice(cheapestProductStores);
	}
	
	/*
	 * Find cheapest products
	 */
	public void findCheapestProduct() throws ClassNotFoundException, SQLException{
		ArrayList<Product> productList = MainScreen.getCartList();
		for(int i = 0; i < productList.size(); i++){
			cheapestProducts.add(Queries.findCheapestProduct(productList.get(i).getId()));
		}
		makeGroupStoreNames();
	}
	
	//Create cheapest store's name list
	public void makeGroupStoreNames() throws ClassNotFoundException, SQLException{
		ArrayList<String> stores = new ArrayList<String>();
		for(int i = 0; i < cheapestProducts.size(); i++){
			cheapestStoresNames.add(cheapestProducts.get(i).getStoreName());
		}
		for(int j = 0; j < cheapestStoresNames.size(); j++){
			if(!stores.contains(cheapestStoresNames.get(j))){
				stores.add(cheapestStoresNames.get(j));
			}
		}
		cheapestStoresNames = stores;
		makeGroupStores();
	}
	
	//Create cheapest stores
	public void makeGroupStores() throws ClassNotFoundException, SQLException{
		for(int i = 0; i < cheapestStoresNames.size(); i++){
			for(int j = 0; j < stores.size(); j++){
				if(stores.get(j).getName().equals(cheapestStoresNames.get(i))){
					cheapestProductStores.add(stores.get(j));
				}
			}
		}
		for(int k = 0; k < cheapestProductStores.size(); k++){
			cheapestProductStores.get(k).getProductList().clear();
		}
		
		addCheapestProductsInStore();
	}
		
		
	public boolean isThereSameStore(String storename, int index){
		boolean isThere = false;
		for(int i = index + 1; i < cheapestProducts.size(); i++){
			if(cheapestProducts.get(i).getStoreName().equals(storename)){
				isThere = true;
			}
		}
		return isThere;
	}
	

}
