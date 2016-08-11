package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class opens the google map page with browser
 * Adds the addresses of the found way into a list
 */
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


public class GoogleMap extends Dialog {

	protected Object result;
	protected Shell shlGoogleMap;
	private String[] addressList;
	private Table addressTable;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GoogleMap(Shell parent, int style, String[] addressList) {
		super(parent, style);
		setText("SWT Dialog");
		this.addressList = addressList;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlGoogleMap.open();
		shlGoogleMap.layout();
		Display display = getParent().getDisplay();
		while (!shlGoogleMap.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlGoogleMap = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MAX);
		shlGoogleMap.setSize(1008, 666);
		shlGoogleMap.setText("Google Map");
		shlGoogleMap.setLayout(new FormLayout());
		System.out.println("baþladý");
		for(int i = 0; i < addressList.length; i++){
			System.out.println(addressList[i]);
		}
		
		Browser browser = new Browser(shlGoogleMap, SWT.NONE);
		browser.setUrl("https://maps.google.com/");
		FormData fd_browser = new FormData();
		fd_browser.top = new FormAttachment(0);
		fd_browser.right = new FormAttachment(100, -10);
		fd_browser.bottom = new FormAttachment(100, -4);
		fd_browser.left = new FormAttachment(0, 278);
		browser.setLayoutData(fd_browser);
		Button btnCopy = new Button(shlGoogleMap, SWT.NONE);
		
		addressTable = new Table(shlGoogleMap, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_addressTable = new FormData();
		fd_addressTable.bottom = new FormAttachment(btnCopy, -6);
		fd_addressTable.top = new FormAttachment(0);
		fd_addressTable.right = new FormAttachment(browser, -6);
		fd_addressTable.left = new FormAttachment(0, 10);
		addressTable.setLayoutData(fd_addressTable);
		addressTable.setHeaderVisible(true);
		addressTable.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(addressTable, SWT.None);
		tblclmnNewColumn.setWidth(258);
		tblclmnNewColumn.setText("Adresler");
		for(int i = 0; i < addressList.length; i++){
			TableItem item = new TableItem(addressTable, SWT.V_SCROLL|SWT.H_SCROLL);
			item.setText(addressList[i]);
		}

		
		btnCopy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(addressTable.getSelectionIndex() != -1){
					java.awt.datatransfer.Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
			        StringSelection sel = new StringSelection(addressTable.getItem(addressTable.getSelectionIndex()).getText());
			        system.setContents(sel, sel);
				}				
			}
		});
		FormData fd_btnCopy = new FormData();
		fd_btnCopy.right = new FormAttachment(browser, -6);
		fd_btnCopy.bottom = new FormAttachment(100, -4);
		fd_btnCopy.left = new FormAttachment(0, 174);
		btnCopy.setLayoutData(fd_btnCopy);
		btnCopy.setText("Kopyala");
		
		
	}
}
