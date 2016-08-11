package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to write and read file
 * Saved shopping list or way list
 * Opens the saved shopping list
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.dnd.SwtUtil;


public class FileWorks {
	
	/*
	 * Writes the shopping list into a file
	 */
	public void saveList(String filePath, String fileName, String location) throws IOException{
		String[] trimedName = fileName.split("\\.");
		String justFileName = "documents\\"+trimedName[0]+ ".txt";
		FileWriter fstream = new FileWriter(filePath);
		FileWriter fpath = new FileWriter(justFileName);
		BufferedWriter out1 = new BufferedWriter(fstream);
		BufferedWriter out2 = new BufferedWriter(fpath);
		out1.write(location);
		out1.newLine();
		out2.write(location);
		out2.newLine();
		String product;
		String brand;
		for(int i = 0; i < MainScreen.getCartList().size(); i++){
			product = MainScreen.getCartList().get(i).getName();
			brand = MainScreen.getCartList().get(i).getBrand();
			if(brand.equals("#")){
				brand = "@";
			}
			out1.write(product+", "+brand);
			out1.newLine();
			out2.write(product+", "+brand);
			out2.newLine();
		}
		out1.close();
		out2.close();
	}
	
	/*
	 * Writes the found way into a file
	 */
	public void saveWay(String filePath, String fileName, Way way) throws IOException{		
		FileWriter fstream = new FileWriter(filePath);
		BufferedWriter out1 = new BufferedWriter(fstream);
		for(int i = 1; i < way.getStoreWay().size() -1; i++){			
			out1.write(way.getStoreWay().get(i).getName()+":"+way.getStoreWay().get(i).getAddress());
			out1.newLine();
			for(int j = 0; j < way.getStoreWay().get(i).getProductList().size(); j++){
				System.out.println(way.getStoreWay().get(i).getProductList().get(j).getName()
						+"/"+way.getStoreWay().get(i).getProductList().get(j).getBrand());
				out1.write(way.getStoreWay().get(i).getProductList().get(j).getName()
						+"/"+way.getStoreWay().get(i).getProductList().get(j).getBrand());
				out1.newLine();
			}
		}
		out1.close();
	
	}
	
	/*
	 * Reads the shopping list file  saved
	 */
	public void openFile(String fileName, Shell shell) throws ClassNotFoundException, SQLException{
		String[] trimedName = fileName.split("\\.");
		fileName = "documents\\"+trimedName[0]+ ".txt";
		File file = new File(fileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			try {
				line = br.readLine();
				MainScreen.setSelectedLocation(line);
				MainScreen.getCartList().clear();
				String product;
				String brand;
				String [] splitedLine;
				while ((line = br.readLine()) != null){
					splitedLine = line.split(", ");
					product = splitedLine[0];
					System.out.println(splitedLine[1]);
					if(splitedLine[1].equals("@")){
						brand = "";
					}else{
						brand = splitedLine[1];
					}				
					if(brand.equals("")){
						MainScreen.getCartList().add(new Product(product));
					}else{
						MainScreen.getCartList().add(new Product(product, brand));
					}
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			FileNotFound dialog = new FileNotFound(shell, SWT.CLOSE,1);
			dialog.setxPosition(shell.getLocation().x + 140);
			dialog.setyPosition(shell.getLocation().y + 200);
			dialog.open();
			e1.printStackTrace();
		}
		
	}
}
