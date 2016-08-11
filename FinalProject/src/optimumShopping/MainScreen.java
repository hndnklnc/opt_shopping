package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is application window
 * All of the GUI actions happens in this class
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.File;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Control;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;


public class MainScreen {

	protected Shell shlOptimumShopping;
	private boolean isAdvanced = false;
	private Table basketList;
	ArrayList<String> suggestionList = new ArrayList<String>();
	ArrayList<String> txtSuggestions = new ArrayList<String>(); //for location suggestion
	private String selectedPrdct = "#";
	private String selectedBrand = "#";
	private boolean isLocListEmpty = true;
	private boolean isBrandListEmpty = true;
	private boolean isProductListEmpty =true;
	private String productPiece = "";
	private String brandPiece = "";
	private String locationPiece = "";
	private static String selectedLocation = ""; //user location	
	private Text productTxt;
	private Text brandTxt;
	private Text locationTxt;
	private boolean isBrandSelected = false;
	private boolean isLocationSelected = false;
	private boolean isProductSelected = false;
	private Group brandComp, productComp, locationComp, endComp, wayComp ;
	private Composite mainComp;
	private Button btnStart, btnAddToCart, advancedRadioBtn, btnDelete ;
	private static ArrayList<Product> cartList = new ArrayList<Product>();	
	private Way foundWay = null;
	private boolean isWayFound = false;
	private Label expenseLbl;
	Queries queries;
	private Group chooseComp, cartComp;
	GoogleMap googlemap;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args){
		
		try {
			MainScreen window = new MainScreen();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Open the window.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void open() throws ClassNotFoundException, SQLException {
		Display display = Display.getDefault();
		createContents();
		shlOptimumShopping.open();
		shlOptimumShopping.layout();
		while (!shlOptimumShopping.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}			
		}
	}

	/**
	 * Create contents of the window.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	protected void createContents() throws ClassNotFoundException, SQLException {
		queries = new Queries();
		shlOptimumShopping = new Shell( SWT.SHELL_TRIM & (~SWT.RESIZE));
		shlOptimumShopping.setModified(true);
		shlOptimumShopping.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shlOptimumShopping.setImage(SWTResourceManager.getImage("images\\logo.gif"));
		shlOptimumShopping.setSize(760, 571);
		shlOptimumShopping.setText("Optimum Shopping");
		System.out.println("content created");
		shlOptimumShopping.setLayout(new FormLayout());
		Menu menu = new Menu(shlOptimumShopping, SWT.BAR);
		shlOptimumShopping.setMenuBar(menu);
		
		MenuItem mnýtmShoppingList = new MenuItem(menu, SWT.CASCADE);
		mnýtmShoppingList.setText("Al\u0131\u015Fveri\u015F");
		
		Menu menu_1 = new Menu(mnýtmShoppingList);
		mnýtmShoppingList.setMenu(menu_1);
		
		MenuItem mnýtmNewList = new MenuItem(menu_1, SWT.NONE);
		mnýtmNewList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				newPage();
			}
		});
		mnýtmNewList.setImage(SWTResourceManager.getImage("images\\new list.png"));
		mnýtmNewList.setText("Yeni Liste");
		
		MenuItem mnýtmOpenList = new MenuItem(menu_1, SWT.NONE);
		mnýtmOpenList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openListAction();
			}
		});
		mnýtmOpenList.setImage(SWTResourceManager.getImage("images\\open list.png"));
		mnýtmOpenList.setText("Liste A\u00E7");
		
		MenuItem mnýtmKaydet = new MenuItem(menu_1, SWT.CASCADE);
		mnýtmKaydet.setImage(SWTResourceManager.getImage("images\\save.png"));
		mnýtmKaydet.setText("Kaydet");
		
		Menu menu_3 = new Menu(mnýtmKaydet);
		mnýtmKaydet.setMenu(menu_3);
		
		MenuItem mnýtmAlverisListesiKaydet = new MenuItem(menu_3, SWT.NONE);
		mnýtmAlverisListesiKaydet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveList();
			}
		});
		mnýtmAlverisListesiKaydet.setText("Al\u0131\u015Fveris Listesi Kaydet");
		
		MenuItem mnýtmOptimumYoluKaydet = new MenuItem(menu_3, SWT.NONE);
		mnýtmOptimumYoluKaydet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveList();
			}
		});
		mnýtmOptimumYoluKaydet.setText("Optimum Yolu Kaydet");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mnýtmExit = new MenuItem(menu_1, SWT.NONE);
		mnýtmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mnýtmExit.setImage(SWTResourceManager.getImage("images\\exit.png"));
		mnýtmExit.setText("\u00C7\u0131k\u0131\u015F");
		
		MenuItem mnýtmHelp = new MenuItem(menu, SWT.CASCADE);
		mnýtmHelp.setText("Yard\u0131m");
		
		Menu menu_2 = new Menu(mnýtmHelp);
		mnýtmHelp.setMenu(menu_2);
		
		MenuItem mnýtmUserManuel = new MenuItem(menu_2, SWT.NONE);
		mnýtmUserManuel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openUserManual();
			}
		});
		mnýtmUserManuel.setImage(SWTResourceManager.getImage("images\\man.jpg"));
		mnýtmUserManuel.setText("Kullan\u0131c\u0131 El Kitab\u0131");
		
		MenuItem mnýtmAbout = new MenuItem(menu_2, SWT.NONE);
		mnýtmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				About about = new About(shlOptimumShopping, SWT.NONE);
				about.setxPosition(shlOptimumShopping.getBounds().x + 170);
				about.setyPosition(shlOptimumShopping.getBounds().y + 130);
				about.open();
			}
		});
		mnýtmAbout.setText("Hakk\u0131nda");
		
		
		
		ToolBar toolBar = new ToolBar(shlOptimumShopping, SWT.FLAT | SWT.RIGHT);
		FormData fd_toolBar = new FormData();
		fd_toolBar.top = new FormAttachment(0, 5);
		fd_toolBar.left = new FormAttachment(0, 5);
		toolBar.setLayoutData(fd_toolBar);
		toolBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		ToolItem newlistTool = new ToolItem(toolBar, SWT.NONE);
		newlistTool.addSelectionListener(new SelectionAdapter() {
			@Override		
			public void widgetSelected(SelectionEvent e) {
				newPage();
				System.out.println("radio"+btnAddToCart.getSelection());
			}
		});
		newlistTool.setToolTipText("Yeni Liste");
		newlistTool.setImage(SWTResourceManager.getImage("images\\new list.png"));
		
		ToolItem openlistTool = new ToolItem(toolBar, SWT.NONE);
		openlistTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openListAction();			
			}
		});
		openlistTool.setToolTipText("Liste A\u00E7");
		openlistTool.setImage(SWTResourceManager.getImage("images\\open list.png"));
		
		ToolItem saveTool = new ToolItem(toolBar, SWT.DROP_DOWN);
		
		saveTool.addSelectionListener(new SelectionAdapter() {
			Menu dropMenu2 = null;
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(dropMenu2 == null) {
	                dropMenu2 = new Menu(shlOptimumShopping, SWT.POP_UP);
	                shlOptimumShopping.setMenu(dropMenu2);
	                MenuItem itemSaveList = new MenuItem(dropMenu2, SWT.RADIO);
	                itemSaveList.setText("Save Shopping List");
	                itemSaveList.addSelectionListener(new SelectionAdapter() {
	        			@Override
	        			public void widgetSelected(SelectionEvent e) {
	        				saveList();
	        			}
	        		});
	                MenuItem itemSaveWay = new MenuItem(dropMenu2, SWT.RADIO);
	                itemSaveWay.setText("Save Optimal Way");
	                itemSaveWay.addSelectionListener(new SelectionAdapter() {
	        			@Override
	        			public void widgetSelected(SelectionEvent e) {
	        				saveWay();
	        			}
	        		});
	                
	            }

	            if (e.detail == SWT.ARROW) {		            	
	                // Position the menu below and vertically aligned with the the drop down tool button.
	                final ToolItem toolItem = (ToolItem) e.widget;
	                final ToolBar  toolBar = toolItem.getParent();

	                Point point = toolBar.toDisplay(new Point(e.x, e.y));
	                dropMenu2.setLocation(point.x, point.y);
	                dropMenu2.setVisible(true);
	            } 
			}
		});
		saveTool.setToolTipText("Kaydet");
		saveTool.setImage(SWTResourceManager.getImage("images\\save.png"));
		ToolItem runTool = new ToolItem(toolBar, SWT.DROP_DOWN);
		runTool.setToolTipText("\u00C7al\u0131\u015Ft\u0131r");
		
		mainComp = new Composite(shlOptimumShopping, SWT.NONE);
		FormData fd_mainComp = new FormData();
		fd_mainComp.right = new FormAttachment(0, 744);
		fd_mainComp.top = new FormAttachment(0, 43);
		fd_mainComp.left = new FormAttachment(0, 5);
		mainComp.setLayoutData(fd_mainComp);
		mainComp.setBackground(SWTResourceManager.getColor(255, 255, 255));

		chooseComp = new Group(mainComp, SWT.None);
		chooseComp.setBounds(0, 0, 496, 405);
		chooseComp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		chooseComp.setLayout(null);
		chooseComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		btnStart = new Button(chooseComp, SWT.NONE);
		btnStart.setToolTipText("Ba\u015Fla butonu");
		btnStart.setBounds(304, 37, 50, 25);
		btnStart.setText("Ba\u015Fla");
		btnStart.setEnabled(false);
		advancedRadioBtn = new Button(chooseComp, SWT.RADIO);
		advancedRadioBtn.setBounds(375, 41, 111, 16);
		advancedRadioBtn.setToolTipText("Gelismis arama");
		
		advancedRadioBtn.setText("Geli\u015Fmi\u015F Arama");
		
		productComp = new Group(chooseComp, SWT.NONE);
		productComp.setBounds(10, 144, 344, 122);
		productComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		productComp.setVisible(false);
		
		Label lblChooseProduct = new Label(productComp, SWT.NONE);
		lblChooseProduct.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseProduct.setBounds(10, 0, 84, 15);
		lblChooseProduct.setText("\u00DCr\u00FCn\u00FC se\u00E7iniz");
		
		productTxt = new Text(productComp, SWT.BORDER);
		productTxt.setToolTipText("\u00FCr\u00FCn");
		productTxt.setBounds(10, 23, 311, 21);
		
		final List productSuggestLst = new List(productComp, SWT.BORDER);
		productSuggestLst.setBounds(10, 50, 311, 66);
		productSuggestLst.setVisible(false);
		brandComp = new Group(chooseComp, SWT.NONE);
		brandComp.setBounds(10, 273, 344, 122);
		brandComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lblChooseBrand = new Label(brandComp, SWT.NONE);
		lblChooseBrand.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseBrand.setBounds(10, 0, 87, 15);
		lblChooseBrand.setText("Marka se\u00E7iniz");
		brandTxt = new Text(brandComp, SWT.BORDER);
		brandTxt.setToolTipText("Marka");
		brandTxt.setBounds(10, 21, 307, 21);
		final List brandSuggestList = new List(brandComp, SWT.BORDER);
		brandSuggestList.setBounds(10, 48, 307, 68);
		brandSuggestList.setVisible(false);
		brandComp.setVisible(false);
		locationComp = new Group(chooseComp, SWT.NONE);
		locationComp.setBounds(10, 17, 260, 122);
		locationComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lblChooseDestination = new Label(locationComp, SWT.NONE);
		lblChooseDestination.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblChooseDestination.setBounds(10, 0, 167, 15);
		lblChooseDestination.setText("Bulundu\u011Funuz konumu giriniz");	
		locationTxt = new Text(locationComp, SWT.BORDER);
		locationTxt.setToolTipText("konum");
		locationTxt.setBounds(10, 21, 234, 25);
		
		final List locationSuggestList = new List(locationComp, SWT.BORDER);
		locationSuggestList.setBounds(10, 52, 232, 65);
		locationSuggestList.setVisible(false);
		
		Label lblNewLabel = new Label(chooseComp, SWT.NONE);
		lblNewLabel.setToolTipText("Se\u00E7im alan\u0131");
		lblNewLabel.setForeground(SWTResourceManager.getColor(199, 21, 133));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(10, 0, 89, 15);
		lblNewLabel.setText("Se\u00E7im Alan\u0131");
		
		
		
		cartComp = new Group(mainComp, SWT.NONE);
		cartComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		cartComp.setBounds(502, 0, 235, 405);
		
		
		btnAddToCart = new Button(cartComp, SWT.NONE);
		btnAddToCart.setToolTipText("Sepete at butonu");
		btnAddToCart.setBounds(33, 326, 104, 25);
		
		btnAddToCart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isProductSelected && !isBrandSelected && isAdvanced){
					CartListDialog dialog = new CartListDialog(shlOptimumShopping, SWT.CLOSE, 1);
					dialog.setxPosition(shlOptimumShopping.getBounds().x + 140);
					dialog.setyPosition(shlOptimumShopping.getBounds().y + 200);
					shlOptimumShopping.setEnabled(false);
					dialog.open();
					shlOptimumShopping.setEnabled(true);
					//uyarý çýkar
				}else if(!isProductSelected){
					CartListDialog dialog = new CartListDialog(shlOptimumShopping, SWT.CLOSE, 0);
					dialog.setxPosition(shlOptimumShopping.getBounds().x + 140);
					dialog.setyPosition(shlOptimumShopping.getBounds().y + 200);
					shlOptimumShopping.setEnabled(false);
					dialog.open();
					shlOptimumShopping.setEnabled(true);
				
				}else{
					Product product = null;
					basketList.setVisible(true);
					TableItem tableItem = new TableItem(basketList, SWT.NONE);
					tableItem.setText(productTxt.getText());
					
					if(!isAdvanced){
						try {
							product = new Product(productTxt.getText());
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						};
					}else{
						try {
							product = new Product(productTxt.getText(), brandTxt.getText());
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						};
					}
					productTxt.setText("");
					brandTxt.setText("");
					cartList.add(product);
				}					
			}
		});
		btnAddToCart.setImage(SWTResourceManager.getImage("images\\addToCard.gif"));
		btnAddToCart.setText("Sepete at");
		btnAddToCart.setEnabled(false);
		
//		mainComp.setBackgroundImage(temp8);
//		locationComp.setBackgroundImage(temp8);
//		productComp.setBackgroundImage(temp8);
//		markComp.setBackgroundImage(temp8);
//		toolBar.setBackgroundImage(temp8);
		btnDelete = new Button(cartComp, SWT.NONE);
		btnDelete.setToolTipText("Sepetten sil butonu");
		btnDelete.setBounds(165, 326, 60, 25);
		
		btnDelete.setEnabled(false);
		
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(basketList.getSelectionIndex() != -1){
					cartList.remove(basketList.getSelectionIndex() );
					basketList.remove(basketList.getSelectionIndex());					
				}
				
			}
		});
		btnDelete.setText("Sil");
		btnDelete.setEnabled(false);
		
		basketList = new Table(cartComp, SWT.BORDER | SWT.FULL_SELECTION);
		basketList.setBounds(33, 27, 192, 293);
		basketList.setToolTipText("Sepet");
		basketList.setLinesVisible(true);
		basketList.setHeaderVisible(true);
		
		TableColumn tblclmnShp = new TableColumn(basketList, SWT.NONE);
		tblclmnShp.setText("Sepet");
		tblclmnShp.setWidth(188);
		basketList.setVisible(true);
		Image cartImage = ImageConst.getImage("images\\flueBasket.gif");
		basketList.setBackgroundImage(cartImage);
		
		Label lblCartGroup = new Label(cartComp, SWT.NONE);
		lblCartGroup.setBackground(SWTResourceManager.getColor(255, 255, 255));
		lblCartGroup.setForeground(SWTResourceManager.getColor(199, 21, 133));
		lblCartGroup.setBounds(10, 0, 87, 15);
		lblCartGroup.setText("Al\u0131\u015Fveri\u015F Sepeti");
		
		
		
				locationSuggestList.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						// 13 is ENTER keycode
						if(e.keyCode == 13){						
							if(!isLocListEmpty){
								selectedLocation = suggestionList.get(locationSuggestList.getSelectionIndex()); //the selected product from productSuggestLst
								locationTxt.setText(selectedLocation);
								locationSuggestList.setVisible(false);
								isLocationSelected = true;
								btnStart.setEnabled(true);
							}else{
								locationTxt.setText("");
								isLocationSelected = false;
								btnStart.setEnabled(false);
							}
						}
					}
				});
				
				locationSuggestList.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						if(!isLocListEmpty){
							//if any item wasn't selected not to do anything.
							if(locationSuggestList.getSelectionIndex() != -1){
								selectedLocation = suggestionList.get(locationSuggestList.getSelectionIndex()); //the selected product from productSuggestLst
								locationTxt.setText(selectedLocation);
								locationSuggestList.setVisible(false);
								isLocationSelected = true;
								btnStart.setEnabled(true);
							}
							
						}else{
							locationTxt.setText("");
							isLocationSelected = false;
						}
						
					}
				});
				locationTxt.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent arg0) {
						isLocationSelected = false;
						locationPiece = locationTxt.getText();	
						if(!locationPiece.equals("")){	
							locationPiece = locationTxt.getText();					
							String[] suggestions = null;
							try {
								suggestions = autoSuggest(2, locationPiece);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							locationSuggestList.setItems(suggestions);
							locationSuggestList.setVisible(true);
							if(suggestions.length == 0 ){
								isLocListEmpty = true;
								locationSuggestList.setItems(new String[] {"Öneri yok"});
							}else{
								isLocListEmpty = false;
							}
							brandSuggestList.setVisible(false);
							locationSuggestList.setVisible(true);
							productSuggestLst.setVisible(false);
						}else{
							isLocListEmpty = true;
							locationSuggestList.setVisible(false);
						}
						
					}
				});
				
				locationTxt.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						//if user press down arrow (16777218) the suggestion list is active and the selected index is first one
						//if user press enter (13), the first element of the list is chosen.
						if (e.keyCode == 16777218)
				        {		          
				            locationSuggestList.setSelection(0);
				            locationSuggestList.setFocus();
				        }else if(e.keyCode == 13){
				        	if(isLocListEmpty){
				        		locationTxt.setText("");
				        		isLocationSelected = false;
				        		btnStart.setEnabled(false);
				        	}else{
				        		selectedLocation = locationSuggestList.getItem(0);			        	
					        	locationTxt.setText(selectedLocation);		
					        	isLocationSelected = true;
					        	btnStart.setEnabled(true);
				        	}
				        	locationSuggestList.setVisible(false);
				        	
				        }
						
						
					}
				});
				
				
				brandSuggestList.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						if(!isBrandListEmpty){
							//if any item wasn't selected not to do anything.
							if(brandSuggestList.getSelectionIndex() != -1){
								selectedBrand= suggestionList.get(brandSuggestList.getSelectionIndex()); //the selected product from productSuggestLst
								brandTxt.setText(selectedBrand);
								brandSuggestList.setVisible(false);
								isBrandSelected = true;
							}							
						}else{
							brandTxt.setText("");
							isBrandSelected = false;
						}
					}
				});
				brandSuggestList.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						// 13 is ENTER keycode
						if(e.keyCode == 13){					
							if(!isBrandListEmpty){
								selectedBrand= suggestionList.get(brandSuggestList.getSelectionIndex()); //the selected product from productSuggestLst
								brandTxt.setText(selectedBrand);
								brandSuggestList.setVisible(false);
								isBrandSelected = true;
							}else{
								brandTxt.setText("");
								isBrandSelected = false;
							}
						}
					}
				});
				brandTxt.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent arg0) {
						isBrandSelected = false;
						brandPiece = brandTxt.getText();								
						String[] suggestions = null;
						if(isProductSelected){
							if(!brandTxt.getText().equals("")){
								try {
									suggestions = autoSuggest(1, brandPiece);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								brandSuggestList.setItems(suggestions);
								brandSuggestList.setVisible(true);				
								if(suggestions.length == 0 ){
									brandSuggestList.setItems(new String[] {"Öneri yok"});
									isBrandListEmpty = true;
								}else if(suggestions[0].equals("#")){
									isBrandListEmpty = false;
									brandSuggestList.setItems(new String[] {"Markasýz ürün"});
								}else{
									isBrandListEmpty = false;
								}
								brandSuggestList.setVisible(true);
								locationSuggestList.setVisible(false);
								productSuggestLst.setVisible(false);
							}else{
								brandSuggestList.setVisible(false);
							}						
						}else{
							brandSuggestList.setItems(new String[] {"Ürün seçiniz"});
							isBrandListEmpty = true;
						}
					}
				});
				brandTxt.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						//if user press down arrow (16777218) the suggestion list is active and the selected index is first one
						//if user press enter (13), the first element of the list is chosen.
						if (e.keyCode == 16777218)
				        {		          
				            brandSuggestList.setSelection(0);
				            brandSuggestList.setFocus();
				        }else if(e.keyCode == 13){
				        	if(isBrandListEmpty){
				        		brandTxt.setText("");
				        		isBrandSelected = false;
				        	}else{
				        		selectedBrand = brandSuggestList.getItem(0);		
					        	brandTxt.setText(selectedBrand);
					        	isBrandSelected = true;
				        	}
				        	brandSuggestList.setVisible(false);
				        }
					}
				});
				
				
				productSuggestLst.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						if(!isProductListEmpty){
							//if any item wasn't selected not to do anything.
							if(productSuggestLst.getSelectionIndex() != -1){
								selectedPrdct = suggestionList.get(productSuggestLst.getSelectionIndex()); //the selected product from productSuggestLst
								productTxt.setText(selectedPrdct);
								productSuggestLst.setVisible(false);
								isProductSelected = true;
							}
							
						}else{
							productTxt.setText("");
							isProductSelected = false;
						}
					}
				});
				productSuggestLst.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						// 13 is ENTER keycode
						
						if(e.keyCode == 13){	
							if(!isProductListEmpty){
								selectedPrdct = suggestionList.get(productSuggestLst.getSelectionIndex()); //the selected product from productSuggestLst
								productTxt.setText(selectedPrdct);
								productSuggestLst.setVisible(false);
								isProductSelected = true;
							}else{
								productTxt.setText("");
								isProductSelected = false;
							}
						}
					}
				});
				productTxt.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						//if user press down arrow (16777218) the suggestion list is active and the selected index is first one
						//if user press enter (13), the first element of the list is chosen.
						if (e.keyCode == 16777218)
				        {		          
				            productSuggestLst.setSelection(0);
				            productSuggestLst.setFocus();
				        }else if(e.keyCode == 13){
				        	if(isProductListEmpty){
				        		productTxt.setText("");
				        		isProductSelected = false;
				        	}else{
				        		selectedPrdct = productSuggestLst.getItem(0);			        	
					        	productTxt.setText(selectedPrdct);
					        	isProductSelected = true;
				        	}
				        	productSuggestLst.setVisible(false);
				        	
				        }
					}
				});
				productTxt.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent arg0) {
						isProductSelected = false;
						if(!productTxt.getText().equals("")){
							productPiece = productTxt.getText();
							String[] suggestions = null;
							try {
								suggestions = autoSuggest(0, productPiece);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							productSuggestLst.setItems(suggestions);
							productSuggestLst.setVisible(true);				
							if(suggestions.length == 0 ){
								productSuggestLst.setItems(new String[] {"Öneri yok"});
								isProductListEmpty = true;
							}else{
								isProductListEmpty = false;
							}
							if(isAdvanced){
								brandTxt.setText("");				
							}
							brandSuggestList.setVisible(false);
							locationSuggestList.setVisible(false);
							productSuggestLst.setVisible(true);
						}else{
							productSuggestLst.setVisible(false);
						}
						
						
					}
				});
				
				advancedRadioBtn.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(isAdvanced){
							isAdvanced = false;					
						}else{
							isAdvanced = true;						
						}
						advancedRadioBtn.setSelection(isAdvanced);
						brandComp.setVisible(false);
						productComp.setVisible(false);			
					}
				});
				
				btnStart.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						productComp.setVisible(true);
						btnAddToCart.setEnabled(true);
						btnDelete.setEnabled(true);
						basketList.setVisible(true);
						if(isAdvanced){
							brandComp.setVisible(true);
						}else{
							brandComp.setVisible(false);
						}
					}
				});
		wayComp = new Group(shlOptimumShopping, SWT.BORDER);
		FormData fd_wayComp = new FormData();
		fd_wayComp.bottom = new FormAttachment(0, 445);
		fd_wayComp.right = new FormAttachment(0, 744);
		fd_wayComp.top = new FormAttachment(0, 40);
		fd_wayComp.left = new FormAttachment(0, 5);
		wayComp.setLayoutData(fd_wayComp);
		wayComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		wayComp.setVisible(false);
		wayComp.setLayout(new BorderLayout(0, 0));
		runTool.addSelectionListener(new SelectionAdapter() {
			Menu dropMenu = null;
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(cartList.size());
					if(dropMenu == null) {
						
		                dropMenu = new Menu(shlOptimumShopping, SWT.POP_UP);
		                shlOptimumShopping.setMenu(dropMenu);
		                MenuItem itemRunOptimal = new MenuItem(dropMenu, SWT.RADIO);
		                itemRunOptimal.setText("Optimum rotayý bul");
		                itemRunOptimal.addSelectionListener(new SelectionAdapter() {
		        			@Override
		        			public void widgetSelected(SelectionEvent e) {
		        				try {
									findOptimumWay();
								} catch (ClassNotFoundException | SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		        			}
		        		});
		                MenuItem itemRunCheapest = new MenuItem(dropMenu, SWT.RADIO);
		                itemRunCheapest.setText("Ucuz ürünleri bul");
		                itemRunCheapest.addSelectionListener(new SelectionAdapter() {
		        			@Override
		        			public void widgetSelected(SelectionEvent e) {
		        				try {
									findCheapProductsWay();
								} catch (ClassNotFoundException | SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		        			}
		        		});
		                
		            }

		            if (e.detail == SWT.ARROW) {		            	
		                // Position the menu below and vertically aligned with the the drop down tool button.
		                final ToolItem toolItem = (ToolItem) e.widget;
		                final ToolBar  toolBar = toolItem.getParent();

		                Point point = toolBar.toDisplay(new Point(e.x, e.y));
		                dropMenu.setLocation(point.x, point.y);
		                dropMenu.setVisible(true);
		            } 
			}
			
		});
		runTool.setImage(SWTResourceManager.getImage("images\\run.jpg"));
	    toolBar.pack();
	    
	    ToolItem manuelTool = new ToolItem(toolBar, SWT.NONE);
	    manuelTool.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		openUserManual();
	    	}
	    });
	    manuelTool.setToolTipText("El kitab\u0131");
	    manuelTool.setImage(SWTResourceManager.getImage("images\\man.jpg"));		
		
		
		
		/*final ProductsComp productComposite;
		productComposite =  new ProductsComp(shlOptimumShopping, SWT.NONE);
		productComposite.setBounds(5, 166, 316, 235);
		
		productComposite.setVisible(false);
		final Composite markComp = productComposite.getMarkComp();*/
		
		
		final GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.widthHint = 464;
		gd_composite.heightHint = 101;
		endComp = new Group(shlOptimumShopping, SWT.NONE);
		FormData fd_endComp = new FormData();
		fd_endComp.top = new FormAttachment(mainComp, 6);
		fd_endComp.left = new FormAttachment(toolBar, 0, SWT.LEFT);
		fd_endComp.bottom = new FormAttachment(0, 516);
		fd_endComp.right = new FormAttachment(0, 744);
		endComp.setLayoutData(fd_endComp);
		endComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		endComp.setLayout(null);
		endComp.setVisible(false);
		Button btnBack = new Button(endComp, SWT.NONE);
		btnBack.setBounds(10, 10, 44, 42);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mainComp.setVisible(true);
				
				wayComp.setVisible(false);
				endComp.setVisible(false);
			}
		});
		btnBack.setImage(SWTResourceManager.getImage("images\\back.png"));
		
		Link link = new Link(endComp, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String [] addressList = new String[foundWay.getStoreWay().size() - 2];
				for(int i = 1; i < foundWay.getStoreWay().size() - 1; i++){
					addressList[i - 1] = foundWay.getStoreWay().get(i).getAddress();
				}
				googlemap = new GoogleMap(shlOptimumShopping, SWT.NONE, addressList);
				googlemap.open();
			}
		});
		link.setBounds(674, 10, 65, 15);
		link.setText("<a>Google Map</a>");
		
		expenseLbl = new Label(endComp, SWT.NONE);
		expenseLbl.setBounds(60, 10, 436, 15);
	
		
		Label noteLbl = new Label(endComp, SWT.NONE);
		noteLbl.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		noteLbl.setBounds(60, 31, 436, 15);
		noteLbl.setText("Not: Yap\u0131lan hesaplamalara alaca\u011F\u0131n\u0131z \u00FCr\u00FCnlerin miktar\u0131 kat\u0131lmam\u0131\u015Ft\u0131r.");
		shlOptimumShopping.setTabList(new Control[]{toolBar, mainComp, wayComp, endComp});
		
	}
	
	
	/**
	 * @param id is id to control it is for Brand, Product or Location
	 * @param pieceOfWord
	 * @return String[]
	 * @throws SQLException
	 */
	public String[] autoSuggest(int id, String pieceOfWord) throws SQLException{
		suggestionList.clear();
		
		switch (id) {
		case 0:
			suggestionList = Queries.GetAutoProducts(pieceOfWord);
			break;
		case 1:
			suggestionList = Queries.GetAutoBrands(pieceOfWord, productTxt.getText());
			break;
		case 2:
			suggestionList = Queries.GetAutoLocations(pieceOfWord);
			break;
		}
		String[] suggestions = listToArray(suggestionList);
		return suggestions;
	}
	
	/**
	 * @param list
	 * @return String[]
	 */
	public static String[] listToArray(ArrayList<String> list){
		String[] array = new String[list.size()];
		for(int i = 0; i<list.size(); i++){
			array[i] = list.get(i);
		}
		return array;
	}

	public String getLocation() {
		return selectedLocation;
	}

	public void setLocation(String location) {
		selectedLocation = location;
	}

	/**
	 * @return the selectedPrdct
	 */
	public String getSelectedPrdct() {
		return selectedPrdct;
	}

	/**
	 * @return the selectedBrand
	 */
	public String getSelectedMark() {
		return selectedBrand;
	}
	
	/*
	 * to reset all variables and widgets
	 */
	public void newPage(){
		isAdvanced = false;
		suggestionList.clear();
		selectedPrdct = "";
		selectedBrand = "";
		isLocListEmpty = true;
		isBrandListEmpty = true;
		isProductListEmpty =true;
		productPiece = "";
		brandPiece = "";
		locationPiece = "";
		selectedLocation = ""; //user location
		isBrandSelected = false;
		isLocationSelected = false;
		isProductSelected = false;
		brandComp.setVisible(false);
		productComp.setVisible(false);
		brandTxt.setText("");
		productTxt.setText("");
		locationTxt.setText("");
		btnStart.setEnabled(false);
		btnAddToCart.setEnabled(false);
		btnDelete.setEnabled(false);
		basketList.clearAll();
		advancedRadioBtn.setSelection(false);
		basketList.setVisible(true);
		cartList.clear();
		isWayFound = false;
		endComp.setVisible(false);
		wayComp.setVisible(false);
		mainComp.setVisible(true);
		basketList.removeAll();
		
	}

	
	public void openNewPage(){
		isAdvanced = false;
		suggestionList.clear();
		selectedPrdct = "";
		selectedBrand = "";
		isLocListEmpty = true;
		isBrandListEmpty = true;
		isProductListEmpty =true;
		productPiece = "";
		brandPiece = "";
		locationPiece = "";
		//selectedLocation = ""; //user location
		isBrandSelected = false;
		isLocationSelected = true;
		isProductSelected = false;
		brandComp.setVisible(false);
		productComp.setVisible(false);
		brandTxt.setText("");
		productTxt.setText("");
		locationTxt.setText("");
		btnStart.setEnabled(false);
		btnAddToCart.setEnabled(false);
		btnDelete.setEnabled(false);
		basketList.clearAll();
		advancedRadioBtn.setSelection(isAdvanced);
		basketList.setVisible(true);
		isWayFound = false;
		//cartList.clear();
		endComp.setVisible(false);
		wayComp.setVisible(false);
		mainComp.setVisible(true);
		
	}
	/**
	 * @return the cartList
	 */
	public static ArrayList<Product> getCartList() {
		return cartList;
	}
	
	public void saveList(){
		if(cartList.isEmpty()){
			EmptyDialog dialog = new EmptyDialog(shlOptimumShopping, SWT.CLOSE,0);
			dialog.setxPosition(shlOptimumShopping.getBounds().x + 140);
			dialog.setyPosition(shlOptimumShopping.getBounds().y + 200);
			shlOptimumShopping.setEnabled(false);
			dialog.open();
			shlOptimumShopping.setEnabled(true);
		}else{
			FileWorks fileWorks = new FileWorks();
			FileDialog saveDialog = new FileDialog(shlOptimumShopping, SWT.SAVE);
			saveDialog.setText("Save");
			saveDialog.setFilterNames(new String[] { "Shopping List" });
			saveDialog.setFilterExtensions(new String[] { "*.shLst" }); 
			saveDialog.setFilterPath("\\"); 
			saveDialog.open();	
			if(!saveDialog.getFileName().equals("")){
				try {
					fileWorks.saveList(saveDialog.getFilterPath()+"\\"+saveDialog.getFileName(),
							saveDialog.getFileName(), locationTxt.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	/*
	 * When user wants to open a saved shopping list, it uploads it
	 */
	public void openListAction(){
		FileDialog openDialog = new FileDialog(shlOptimumShopping,SWT.OPEN);
		openDialog.setText("Open");
		openDialog.setFilterNames(new String[] {"Shopping List"});
		openDialog.setFilterExtensions(new String[] { "*.shLst"}); 
		openDialog.open();
		if(!openDialog.getFileName().equals("")){
			StringTokenizer st = new StringTokenizer(openDialog.getFileName(), ".");
			ArrayList<String> tokenList = new ArrayList<String>();
			while(st.hasMoreTokens()){
				tokenList.add(st.nextToken());
			}
			if(tokenList.contains("shLst")){
				basketList.removeAll();
				FileWorks fileWorks = new FileWorks();
				try {
					fileWorks.openFile(openDialog.getFileName(), shlOptimumShopping);
					openNewPage();
					locationTxt.setText(MainScreen.getSelectedLocation());
					if(locationTxt.getText().equals("")){
						btnStart.setEnabled(false);
						isLocationSelected = false;
					}else{
						btnStart.setEnabled(true);
						for(int i = 0; i < cartList.size(); i++){
							TableItem item = new TableItem(basketList, SWT.NONE);
							item.setText(cartList.get(i).getName());
							
						}
						
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}else{
				FileNotFound dialog = new FileNotFound(shlOptimumShopping, SWT.CLOSE,0);
				dialog.setxPosition(shlOptimumShopping.getLocation().x + 140);
				dialog.setyPosition(shlOptimumShopping.getLocation().y + 200);
				dialog.open();
			}
			
		}
	}
	
	
	
	/**
	 * @param selectedLocation the selectedLocation to set
	 */
	public static void setSelectedLocation(String selectedLocation) {
		MainScreen.selectedLocation = selectedLocation;
	}

	/**
	 * @return the selectedLocation
	 */
	public static String getSelectedLocation() {
		return MainScreen.selectedLocation;
	}

	/**
	 * @param cartList the cartList to set
	 */
	public static void setCartList(ArrayList<Product> cartList) {
		MainScreen.cartList = cartList;
	}
	/*
	 * When user want to find cheapest product this methods calls model 3
	 */
	public void findCheapProductsWay() throws ClassNotFoundException, SQLException{
		//if the cart lis is not empty, else the attention window appears
		if(!cartList.isEmpty()){
			long startTime = System.currentTimeMillis();
			CheapestProductPath path = new CheapestProductPath(cartList);
			Way way = path.findCheapestProductsWay();
			wayComp.dispose();
			wayComp = new Group(shlOptimumShopping, SWT.BORDER);
			wayComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			wayComp.setBounds(5, 40, 739, 405);
			wayComp.setLayout(null);
			mainComp.setVisible(false);
			endComp.setVisible(true);
			this.wayComp.setVisible(true);
			WayView view = new WayView();
			//creates the all stores properties to put into expand bar
			ExpandBar bar = new ExpandBar(wayComp,  SWT.V_SCROLL);
			bar.setBounds(0, 0, 735, 405);
			ArrayList<Store> stores = way.getStoreWay();
			ExpandItem item = new ExpandItem(bar, 0);
			for(int i = 1; i < stores.size() - 1; i++){
				item = view.createExpandBar(stores.get(i), bar, i);
			}	
			isWayFound = true;
			foundWay = way;
			expenseLbl.setText("Yol masraflar\u0131 dahil olmak \u00FCzere toplam " +
					""+foundWay.getTotalExpense()+" tl harcama yap\u0131lacakt\u0131r.");
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("cart list:"+ cartList.size()+" time:"+totalTime);
		}else{
			EmptyDialog dialog = new EmptyDialog(shlOptimumShopping, SWT.CLOSE, 0);
			dialog.setxPosition(shlOptimumShopping.getBounds().x + 140);
			dialog.setyPosition(shlOptimumShopping.getBounds().y + 200);
			shlOptimumShopping.setEnabled(false);
			dialog.open();
			shlOptimumShopping.setEnabled(true);
		}

		
	}
	
	/*
	 * When user want to find optimum way this methods calls all of the models
	 * classes to take their cheapest way.
	 */
	public void findOptimumWay() throws SQLException, ClassNotFoundException{
		//if the cart lis is not empty, else the attention window appears
		if(!cartList.isEmpty()){
			long startTime = System.currentTimeMillis();			
			Queries.FindStores(cartList);
			CheapestStorePath cheapestStorePath = new CheapestStorePath(cartList); 
			Way cheapestStoreWay =  cheapestStorePath.getCheapestProductWay();//model 1 cheapest way
			ClosedStorePath closedStorePath =  new ClosedStorePath(cartList);
			Way closedStoreWay = closedStorePath.findClosestStoresWays();//model 2 cheapest way
			CheapestProductPath path = new CheapestProductPath(cartList);
			Way cheapestProductWay = path.findCheapestProductsWay(); // model 3 cheapest way
			Way choosenWay = cheapestStoreWay;
			
			//To check which way's total expense is cheapest
			if(choosenWay.getTotalExpense() > closedStoreWay.getTotalExpense()){
				choosenWay = closedStoreWay;
			}
			if(choosenWay.getTotalExpense() > cheapestProductWay.getTotalExpense()){
				choosenWay = cheapestProductWay;
			}
			wayComp.dispose();
			wayComp = new Group(shlOptimumShopping, SWT.BORDER);
			wayComp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			wayComp.setBounds(5, 40, 739, 405);
			wayComp.setLayout(null);
			mainComp.setVisible(false);
			endComp.setVisible(true);
			WayView view = new WayView();
			//creates the all stores properties to put into expand bar
			ExpandBar bar = new ExpandBar(wayComp,  SWT.V_SCROLL);
			bar.setBounds(0, 0, 735, 405);
			foundWay = choosenWay;
			expenseLbl.setText("Yol masraflar\u0131 dahil olmak \u00FCzere toplam "
			+foundWay.getTotalExpense()+" tl harcama yap\u0131lacakt\u0131r.");
			ArrayList<Store> stores = choosenWay.getStoreWay();		
			ExpandItem item = new ExpandItem(bar, 0);		
			isWayFound =true;
			for(int i = 1; i < stores.size() - 1; i++){
				item = view.createExpandBar(stores.get(i), bar, i);
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("cart list:"+ cartList.size()+" time:"+totalTime);
			System.out.println("cheapes store:"+cheapestStoreWay.getTotalExpense());
			System.out.println("closestore:"+closedStoreWay.getTotalExpense());
			System.out.println("cheapes product:"+cheapestProductWay.getTotalExpense());
		}else{
			EmptyDialog dialog = new EmptyDialog(shlOptimumShopping, SWT.CLOSE, 0);
			dialog.setxPosition(shlOptimumShopping.getBounds().x + 140);
			dialog.setyPosition(shlOptimumShopping.getBounds().y + 200);
			shlOptimumShopping.setEnabled(false);
			dialog.open();
			shlOptimumShopping.setEnabled(true);
		}


	}
	
	/*
	 * Saves the found way if the way has already found
	 */
	public void saveWay(){
		//if the way has not found, the attention dialog appears
		if(!isWayFound){
			EmptyDialog dialog = new EmptyDialog(shlOptimumShopping, SWT.CLOSE, 1);
			dialog.setxPosition(shlOptimumShopping.getBounds().x + 140);
			dialog.setyPosition(shlOptimumShopping.getBounds().y + 200);
			shlOptimumShopping.setEnabled(false);
			dialog.open();
			shlOptimumShopping.setEnabled(true);
		}else{
			//calls the file work to write the way in  a file
			FileWorks fileWorks = new FileWorks();
			FileDialog saveDialog = new FileDialog(shlOptimumShopping, SWT.SAVE);
			saveDialog.setText("Save");
			saveDialog.setFilterNames(new String[] { "Way" });
			saveDialog.setFilterExtensions(new String[] { "*.txt" }); 
			saveDialog.setFilterPath("\\"); 
			saveDialog.open();	
			System.out.println(foundWay.getStoreWay().size());
			
			if(!saveDialog.getFileName().equals("")){
				try {
					fileWorks.saveWay(saveDialog.getFilterPath()+"\\"+saveDialog.getFileName(),
							saveDialog.getFileName(), foundWay);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			newPage();
		}
	}
	
	/*
	 * opens pdf user manual
	 */
	public void openUserManual(){
		try {	    			 
			if ((new File("user manual\\Kullanýcý El kitabý.pdf")).exists()) {	    	 
				Process p = Runtime
				   .getRuntime()
				   .exec("rundll32 url.dll,FileProtocolHandler user manual\\Kullanýcý El kitabý.pdf");
				p.waitFor();
	 
			} else {	    	 
				System.out.println("File is not exists");	    	 
			}	    	 
			System.out.println("Done");	    	 
	  	  } catch (Exception ex) {
			ex.printStackTrace();
		  }
	 
	}
//	public ArrayList<Product> removeSameProduct(ArrayList<Product> products){
//		//the list of indexes that will delete
//		ArrayList<Integer> indexList = new ArrayList<Integer>();
//		for(int i = 0; i < products.size(); i++){
//			for(int j = i + 1; j < products.size(); j++){
//				if(products.get(i).getId() == products.get(j).getId()){
//					indexList.add(j);
//				}
//			}
//		}
//		for(int i = 0; i < indexList.size(); i++){
//			products.remove(indexList.get(i));
//		}
//		return products;
//	}
}
