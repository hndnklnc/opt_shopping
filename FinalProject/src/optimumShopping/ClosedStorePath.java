package optimumShopping;
/**
 * @author Handan KILINÇ
 * It finds all of the stores that are closed to user according to user’s product list.
 * It is subclass of Cheapest Path
 */
import java.sql.SQLException;
import java.util.ArrayList;


public class ClosedStorePath  extends CheapestPath{
	ArrayList<Store> closestStores = new ArrayList<Store>();
	ArrayList<Store> stores = new ArrayList<Store>();
	
	public ClosedStorePath(ArrayList<Product> cartList) throws ClassNotFoundException, SQLException {
		stores = Queries.FindStores(cartList);
		findClosestStores();
	}

	/*
	 * finds the closest stores which include the products of the cart list
	 */
	public void findClosestStores() throws ClassNotFoundException, SQLException{
		ArrayList<Product> productsInStore = new ArrayList<Product>();
		ArrayList<Integer> controledStoreindxes = new ArrayList<Integer>();
		ArrayList<Product> minProductList = new ArrayList<Product>();
		for(int i = 0; i < stores.size(); i++){
			//to control the i is controlled before. if it isn't looked, it will be looked
			if(!(controledStoreindxes.contains(i))){
				productsInStore = stores.get(i).getProductList();
				minProductList = productsInStore;
				Store minStore = stores.get(i);
				controledStoreindxes.add(i);
				for(int j = 0; j < stores.size(); j++){
					//to control the j is controled before. if it isn't looked, it will be looked
					if(!(controledStoreindxes.contains(j))){
						if(isListsEqual(minProductList, stores.get(j).getProductList())){
							controledStoreindxes.add(j);
							if(minStore.getDistanceToUser().getDistance() > stores.get(j).getDistanceToUser().getDistance()){
								minStore = stores.get(j);
							}
						}
					}
					
				}
				closestStores.add(minStore);				
			}			
		}
		deleteExtraProducts(closestStores);
		removeEmptyStore(closestStores);
		findTotalPrice(closestStores);
	}
	
	/*
	 * deletes the some stores' product which has same products with a store and is not more closed than the store
	 */
	public void deleteExtraProducts(ArrayList<Store> closestStores){
		ArrayList<Store> stores = new ArrayList<Store>();
		for(int i = 0; i < closestStores.size(); i++){
			Store store = closestStores.get(i);
			for(int j = i +1; j < closestStores.size(); j++){
				if(isSubArrayList(store.getProductList(), closestStores.get(j).getProductList())){
					if(store.getDistanceToUser().getDistance() < closestStores.get(j).getDistanceToUser().getDistance()){
						//to remove the product which belong to far store
						deleteProducts(closestStores.get(j).getProductList(), store.getProductList());	
					}else{
						//to remove the product which belong to far store
						deleteProducts(store.getProductList(), closestStores.get(j).getProductList());
					}
				}else if(hasSameElement(store.getProductList(), closestStores.get(j).getProductList())){
					if(store.getDistanceToUser().getDistance() < closestStores.get(j).getDistanceToUser().getDistance()){
						//to remove the product which belong to far store
						deleteProducts(closestStores.get(j).getProductList(), store.getProductList());	
					}else{
						//to remove the product which belong to far store
						deleteProducts(store.getProductList(), closestStores.get(j).getProductList());
					}
				}
			}
			stores.add(store);
		}
		this.closestStores = closestStores;
	}
	

	
	public Way findClosestStoresWays() throws SQLException, ClassNotFoundException{
		return findCheapestWay(closestStores);
	}
	
}
