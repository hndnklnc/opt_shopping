package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create a dialog when "about" option is selected
 * It is subclass of CheapestPath class
 */
import java.sql.SQLException;
import java.util.ArrayList;


public class CheapestStorePath extends CheapestPath {
	ArrayList<Store> stores = new ArrayList<Store>();
	ArrayList<Store> cheapestStores = new ArrayList<Store>();
	Way cheapestProductWay;
	
	public CheapestStorePath(ArrayList<Product> cartList) throws ClassNotFoundException, SQLException{
		stores = Queries.FindStores(cartList);
		findTotalPrice(stores);
		findCheapestStores();
	}	
	
	/*
	 * this method make group stores which contains same product list and choose cheapest one
	 */
	
	public void findCheapestStores() throws SQLException, ClassNotFoundException{
		ArrayList<Product> productsInStore = new ArrayList<Product>();
		ArrayList<Integer> controledStoreindxes = new ArrayList<Integer>();
		ArrayList<Product> minProductList = new ArrayList<Product>();
		int minStoreId;
		String minLocation;
		String minAddress;
		String minName;
		for(int i = 0; i < stores.size(); i++){
			//to control the i is controled before. if it isn't looked, it will be looked
			if(!(controledStoreindxes.contains(i))){
				productsInStore = stores.get(i).getProductList();
				minProductList = productsInStore;
				minStoreId = stores.get(i).getId();
				minLocation = stores.get(i).getLocation();
				minAddress = stores.get(i).getAddress();
				minName = stores.get(i).getName();
				controledStoreindxes.add(i);
				for(int j = 0; j < stores.size(); j++){
					//to control the j is controled before. if it isn't looked, it will be looked
					if(!(controledStoreindxes.contains(j))){
						if(isListsEqual(minProductList, stores.get(j).getProductList())){
							controledStoreindxes.add(j);
							if(stores.get(i).getTotalPrice() < stores.get(j).getTotalPrice()){
								minStoreId = stores.get(i).getId();
								minProductList = stores.get(i).getProductList();
								minLocation = stores.get(i).getLocation();
								minAddress = stores.get(i).getAddress();
								minName = stores.get(i).getName();
							}else{
								minStoreId = stores.get(j).getId();
								minProductList = stores.get(j).getProductList();
								minLocation = stores.get(j).getLocation();
								minAddress = stores.get(j).getAddress();
								minName = stores.get(j).getName();
							}
						}
					}
					
				}
				Store store = new Store(minStoreId, minProductList);
				store.setLocation(minLocation);
				store.setAddress(minAddress);
				store.setName(minName);
				cheapestStores.add(store);				
			}			
		}
		findTotalPrice(cheapestStores);
		//to check is there cheaper option from the cheapestStores list
		updateCheapestStore();		
	}
	
	/*
	 * If there is stores which has same products, checks the which store is better
	 */
	public void updateCheapestStore() throws SQLException{
		ArrayList<Product> productList = new ArrayList<Product>();
		ArrayList<Store> storeList = new ArrayList<Store>();
		for(int i = 0; i < cheapestStores.size(); i++){
			//the list which is compared with the other product lists
			productList = cheapestStores.get(i).getProductList();
			for(int j = i + 1; j < cheapestStores.size(); j++){
				if(cheapestStores.get(i).getProductList().size() != 0){
					ArrayList<Product> subList = cheapestStores.get(j).getProductList();
					//controls the subList is sublist of productList
					if(isSubArrayList(productList, subList)){
						//finds the price with the way cost when go this store from the i. store
						double pricewithWay = cheapestStores.get(j).getTotalPrice() + 
								wayCost(cheapestStores.get(i), cheapestStores.get(j));
						//calculates the same products costs and then compares
						if(subListTotalPrice(productList, subList) > pricewithWay ){
							Store store1 = cheapestStores.get(i);
							store1.setProductList(deleteProducts(productList, subList));
							cheapestStores.get(i).setProductList(store1.getProductList());
							findStoreExpense(cheapestStores.get(i));
						}else{
							cheapestStores.get(j).setProductList(deleteProducts(subList, productList));
							findStoreExpense(cheapestStores.get(j));
						}
					}else if(isSubArrayList(subList, productList)){
						//finds the price with the way cost when go this store from the i. store
						double pricewithWay = cheapestStores.get(i).getTotalPrice() + 
								wayCost(cheapestStores.get(i), cheapestStores.get(j));
						//calculates the same products costs and then compares
						if(subListTotalPrice(subList, productList) > pricewithWay ){
							Store store1 = cheapestStores.get(j);
							store1.setProductList(deleteProducts(subList, productList));
							cheapestStores.get(j).setProductList(store1.getProductList());
							findStoreExpense(cheapestStores.get(j));
						}else{
							cheapestStores.get(i).setProductList(deleteProducts(productList, subList));
							findStoreExpense(cheapestStores.get(i));
						}
					}
					//checks if stores has same products
					else if(hasSameElement(subList, productList)){ 
						//removes expensive product
						removeProduct(productList, subList, wayCost(cheapestStores.get(i), cheapestStores.get(j)));
						findStoreExpense(cheapestStores.get(i));
						findStoreExpense(cheapestStores.get(j));
					}
				}
				
			}
			storeList.add(cheapestStores.get(i));
		}
		cheapestStores = storeList;
		removeEmptyStore(cheapestStores);
	}
	
	//finds the total prices of the stores' product
	public void findStoreExpense(Store store){
		double total = 0;
		for(int i = 0; i <store.getProductList().size(); i++){
			total = total + store.getProductList().get(i).getPrice();
		}
		store.setTotalPrice(total);
	}	
	
	/*
	 * Returns the distance cost between two stores
	 */
	public double wayCost(Store store1, Store store2) throws SQLException{
		double wayCost = 0;
		if(store1.getLocation().equals(store2.getLocation())){
			wayCost = 0.5;
		}else{
			double distance = Queries.findDistance(store1.getLocation(), store2.getLocation()).getDistance();
			wayCost = distance * 0.4;
		}
		return wayCost;
	}
	
	/*
	 * Calculates the prices of product1 which are same with product2's products
	 */
	public double subListTotalPrice(ArrayList<Product> product1, ArrayList<Product> product2){
		double totalPrice = 0;
		int index = 0;
		for(int i = 0; i< product2.size(); i++){
			for(int j = 0; j < product1.size(); j++){
				if(product1.get(j).getId() == product2.get(i).getId()){
					index = j;
				}
			}
			totalPrice = totalPrice + product1.get(index).getPrice();
		}
		return totalPrice;
	}
	
	
	

	/**
	 * @return the cheapestProductWay
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Way getCheapestProductWay() throws ClassNotFoundException, SQLException {
		return cheapestProductWay = findCheapestWay(cheapestStores);
	}

	
	
}
