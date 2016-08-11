package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create expand bar item which includes found way store
 * properties and product list
 */
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;


public class WayView {
	public ExpandItem createExpandBar(Store store, ExpandBar bar, int order){
		  Composite composite = new Composite (bar, SWT.NONE);
		  composite.setLayout(null);
		  
		  final Label lblAdress = new Label(composite, SWT.BORDER);
		  lblAdress.setBackground(SWTResourceManager.getColor(144, 238, 144));
		  lblAdress.setBounds(10, 10, 650, 19);
		  lblAdress.setText(store.getAddress());
		  
		  Button btnCopy = new Button(composite, SWT.NONE);
		  btnCopy.setBounds(660, 9, 46, 19);
		  btnCopy.addSelectionListener(new SelectionAdapter() {
		  	@Override
		  	public void widgetSelected(SelectionEvent e) {
		  		 java.awt.datatransfer.Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
		         StringSelection sel = new StringSelection(lblAdress.getText());
		         system.setContents(sel, sel);
		  	}
		  });
		  btnCopy.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		  btnCopy.setText("Kopyala");
		  String[] products = new String[store.getProductList().size()];
		  for(int i = 0; i < store.getProductList().size(); i++){
			  String name = store.getProductList().get(i).getName();
			  String brand = store.getProductList().get(i).getBrand();
			  System.out.println(brand);
			  if(!brand.equals("#")){
				  products[i] =  name.substring(0,1).toUpperCase()+name.substring(1)+"/"+brand.substring(0,1).toUpperCase()+brand.substring(1)+
						  "\t fiyat: "+store.getProductList().get(i).getPrice();
			  }else{
				  products[i] =  name.substring(0,1).toUpperCase()+name.substring(1)+" \t fiyat: "+store.getProductList().get(i).getPrice();;
			  }
			  
			  System.out.println(products[i]);
		  }
		  List list = new List(composite, SWT.V_SCROLL);
		  list.setBounds(10, 56, 556, 78);
		  list.setItems(products);
		  final Label lblPrice = new Label(composite, SWT.BORDER);
		  lblPrice.setBackground(SWTResourceManager.getColor(144, 238, 144));
		  lblPrice.setBounds(570, 100, 120, 19);
		  lblPrice.setText("Toplam fiyat: "+store.getTotalPrice());
		  ExpandItem item = new ExpandItem (bar, SWT.NONE, order);
		  item.setExpanded(true);
		  item.setText(store.getName()+"/ "+store.getLocation());
		  item.setHeight
		  (composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		  item.setControl(composite);
		  item.setExpanded(false);
		  Label lblProductsAre = new Label(composite, SWT.NONE);
		  lblProductsAre.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		  lblProductsAre.setBounds(10, 35, 500, 19);
		  lblProductsAre.setText("Ürünler: "+store.getName());
		  return item;
	}
}
