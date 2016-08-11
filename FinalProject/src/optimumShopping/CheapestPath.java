package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is super class of the all Path classes
 * It has the helpful methods for the other Path classes
 */
import java.sql.SQLException;
import java.util.ArrayList;


public class CheapestPath {	
	

	/*
	 * to find all of the stores total price of their product
	 */
	public void findTotalPrice(ArrayList<Store> stores) throws SQLException, ClassNotFoundException{	
		ArrayList<Store> newStores = new ArrayList<Store>();
		double total = 0;
		for(int i = 0; i < stores.size(); i++){
			ArrayList<Product> productsInStore = new ArrayList<Product>();
			total = 0; //to reset previous total price
			Store store = stores.get(i);
			productsInStore.addAll(store.getProductList());			
			ArrayList<Product> newProducts = new ArrayList<Product>();
			for(int j = 0; j < productsInStore.size(); j++){
				double price = Queries.FindPrice(stores.get(i).getId(), productsInStore.get(j).getId());
				total = total + price;
				Product product = new Product(store.getProductList().get(j).getName(), store.getProductList().get(j).getBrand());
				product.setPrice(price);
				product.setStoreName(store.getName());
				newProducts.add(product);
			}
			store.setProductList(newProducts);
			store.setTotalPrice(total);
			newStores.add(store);
		}
		stores = newStores;
	}
	
	/*
	 * control if product2 is sublist of product1
	 */
	public boolean isSubArrayList(ArrayList<Product> product1, ArrayList<Product> product2){
		boolean isSubList = false;
		ArrayList<Integer> ids1 = new ArrayList<Integer>();
		ArrayList<Integer> ids2 = new ArrayList<Integer>();
		for(int j = 0; j < product1.size(); j++){
			ids1.add(product1.get(j).getId());
		}
		for(int j = 0; j < product2.size(); j++){
			ids2.add(product2.get(j).getId());
		}
		if(ids1.containsAll(ids2)){
			isSubList = true;
		}
		return isSubList;
	}
	
	/*
	 * Controls that the product list has a same product or not
	 */
	public boolean hasSameElement(ArrayList<Product> product1, ArrayList<Product> product2){
		boolean isContain = false;
		ArrayList<Integer> ids1 = new ArrayList<Integer>();
		ArrayList<Integer> ids2 = new ArrayList<Integer>();
		for(int j = 0; j < product1.size(); j++){
			ids1.add(product1.get(j).getId());
		}
		for(int j = 0; j < product2.size(); j++){
			ids2.add(product2.get(j).getId());
		}
		for(int i = 0; i < product2.size(); i++){
			if(ids1.contains(ids2.get(i))){
				isContain = true;
				break;
			}
		}		
		return isContain;
	}
	
	/*
	 * Checks the same product in the lists and then deletes the more expensive ones
	 */
	public void removeProduct(ArrayList<Product> product1, ArrayList<Product> product2, double wayCost){
		ArrayList<Integer> ids1 = new ArrayList<Integer>();
		ArrayList<Integer> ids2 = new ArrayList<Integer>();
		for(int j = 0; j < product1.size(); j++){
			ids1.add(product1.get(j).getId());
		}
		for(int j = 0; j < product2.size(); j++){
			ids2.add(product2.get(j).getId());
		}
		ArrayList<Product> product1Temp = new ArrayList<Product>();
		ArrayList<Product> product2Temp = new ArrayList<Product>();
		product1Temp.addAll(product1);
		product2Temp.addAll(product2);
		for(int i = 0; i < product2Temp.size(); i++){
			for(int j = 0; j < product1Temp.size(); j++){
				if(product2Temp.get(i).getId() == product1Temp.get(j).getId()){
					removeExpensiveProduct(product1, product2, product1Temp.get(j), product2Temp.get(i), wayCost);
				}
			}
		}	
	}
	
	/*
	 * Compare the product's prices with given product. Removes more expensive one
	 */
	public void removeExpensiveProduct(ArrayList<Product> product1, ArrayList<Product> product2, Product p1, Product p2, double wayCost){
		if(p1.getPrice() <p2.getPrice() + wayCost){
			product2.remove(p2);
		}else{
			product1.remove(p1);
		}
	}
	
	/*
	 * finds the way with started index
	 */
	public  ArrayList<Store> findOptimalWay(int startedIndex, ArrayList<Store> cheapestStores) throws SQLException, ClassNotFoundException{
		//when the closest store is found to given store, it is added this list
		ArrayList<Store> storeWay = new ArrayList<Store>();
		storeWay.add(cheapestStores.get(startedIndex)); //start point is added on way
		ArrayList<Store> uncontroledStores = new ArrayList<Store>();
		uncontroledStores.addAll(cheapestStores);
		boolean isFirstIndxControlled = false;		
		ArrayList<Store> storeList = new ArrayList<>(); 
		storeList.addAll(cheapestStores); 
		int otherIndex = 0; //closest store index
		uncontroledStores.remove(startedIndex);
		Store closedStore = null;
		while(!uncontroledStores.isEmpty()){
			if(!isFirstIndxControlled){
				closedStore = findCheapestDistance(storeList.get(startedIndex), uncontroledStores);
				storeWay.add(closedStore);				
				isFirstIndxControlled = true;
			}else{
				closedStore = findCheapestDistance(storeList.get(otherIndex), uncontroledStores);
				storeWay.add(closedStore);	
			}
			uncontroledStores.remove(closedStore);
			//to find the index number of the store which will be used as started point next loop
			for(int j = 0; j < storeList.size(); j++){
				if(closedStore.getId() == storeList.get(j).getId()){
					otherIndex = j;
				}
			}
		}
		storeWay.add(storeWay.get(0)); //to add started point into way
		return storeWay;
	}
	/*
	 * finds the cheapest way with nearest neighbor algorithm
	 */
	public Way findCheapestWay(ArrayList<Store> cheapestStores) throws SQLException, ClassNotFoundException{	
		ArrayList<Way> wayList = new ArrayList<Way>();
		Store userLoc = new Store(-1, null);
		userLoc.setLocation(MainScreen.getSelectedLocation());
		userLoc.setTotalPrice(0);
		cheapestStores.add(userLoc);
		//finds all of the way distance with different start point
		for(int i = 0; i < cheapestStores.size(); i++){
			ArrayList<Store> optimalWay = findOptimalWay(i, cheapestStores);
			double totalDistance = 0;
			for(int j = 0; j < optimalWay.size(); j++){
				if(j != optimalWay.size() -1){
					totalDistance = totalDistance + Queries.findDistance(optimalWay.get(j).getLocation(), 
							optimalWay.get(j+1).getLocation()).getDistance();	
				}
				
			}
			//adds the starting point at the end of way list 
			totalDistance = totalDistance + Queries.findDistance(optimalWay.get(optimalWay.size() -1).getLocation(), 
					optimalWay.get(0).getLocation()).getDistance();
			//find the way cost by multiply total distance with fuel cost 
			Way way = new Way(optimalWay, totalDistance * 0.4);
			wayList.add(way);
		}		
		
		//to choose cheapest ways in the wayList
		Way cheapestWay = null;
		double minWayExpence = 1000;
		for(int k = 0 ; k < wayList.size(); k++){
			if(wayList.get(k).getWayExpense() < minWayExpence){
				minWayExpence = wayList.get(k).getWayExpense();
				cheapestWay = wayList.get(k);
			}
		}

		//to find total expense (with way and product prices)
		double totalExpense = 0;
		for(int k = 0; k < cheapestWay.getStoreWay().size(); k++){
			totalExpense = totalExpense + cheapestWay.getStoreWay().get(k).getTotalPrice();
		}
		cheapestWay.setTotalExpense(totalExpense + cheapestWay.getWayExpense());			
		cheapestWay = arrangeCheapestWay(cheapestWay);		
		return cheapestWay;
	}
	
	/*
	 * to arrange starting point to user location without changing direction of cheapest way
	 */
	public  Way arrangeCheapestWay(Way cheapestWay){
		ArrayList<Store> storeList = new ArrayList<Store>(); //new ordered cheapest way
		int userLocationIndex = 0;
		for(int i = 0; i < cheapestWay.getStoreWay().size(); i++){
			//to find users location index in cheapestWay
			if(cheapestWay.getStoreWay().get(i).getId() == -1){
				userLocationIndex = i;
				break;
			}
		}		
		
		if(userLocationIndex != 0){
			storeList.addAll(cheapestWay.getStoreWay().subList(userLocationIndex, cheapestWay.getStoreWay().size()));
			storeList.addAll(cheapestWay.getStoreWay().subList(1, userLocationIndex));
			storeList.add(storeList.get(0));
		}else{
			storeList.addAll(cheapestWay.getStoreWay());
		}
		cheapestWay.setStoreWay(storeList);
		return cheapestWay;
	}
	
	/*
	 * to find closest location in the uncontrolled list to given store
	 */
	public  Store findCheapestDistance(Store store, ArrayList<Store> uncontrolledStores) throws SQLException, ClassNotFoundException{
		double minDistance = 1000;		
		Store closedStore = null;
		for(int i = 0; i < uncontrolledStores.size(); i++){
			Distance distance = Queries.findDistance(store.getLocation(), uncontrolledStores.get(i).getLocation());
			if(distance.getDistance() < minDistance){
				minDistance = distance.getDistance();
				closedStore = uncontrolledStores.get(i);
			}
		}
		return closedStore;		
	}
	
	/*
	 * to control the products list are equal or not equal
	 */
	public boolean isListsEqual(ArrayList<Product> list1, ArrayList<Product> list2){
		boolean isEqual = false;
		if(list1.size() == list2.size()){
			ArrayList<Integer> ids1 = new ArrayList<Integer>();
			ArrayList<Integer> ids2 = new ArrayList<Integer>();
			for(int i = 0; i < list1.size(); i++){
				ids1.add(list1.get(i).getId());
				ids2.add(list2.get(i).getId());
			}
			if(ids1.containsAll(ids2)){
				isEqual = true;
			}
		}
		return isEqual;
	}
	
	/*
	 * deletes some products of product1 list the more expensive or far products
	 */
	public ArrayList<Product> deleteProducts(ArrayList<Product> product1, ArrayList<Product> product2){
		ArrayList<Product> products = new ArrayList<Product>();
		products.addAll(product1);
		for(int j = 0; j < products.size(); j++){
			for(int k = 0; k < product2.size(); k++){
				if(products.get(j).getId() == product2.get(k).getId()){
					product1.remove(products.get(j));		
				}						
			}
		}			
		return product1;
	}
	/*
	 * After elimination, if there is  store which does't have an product,
	 * it removes it
	 */
	public void removeEmptyStore(ArrayList<Store> storeList){
		ArrayList<Store> stores = new ArrayList<Store>();
		stores.addAll(storeList);
		for(int i = 0;i < stores.size(); i++){
			if(stores.get(i).getProductList().size() == 0){
				storeList.remove(stores.get(i));
			}
		}
	}
}
