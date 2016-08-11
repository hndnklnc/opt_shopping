package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create a dialog when user wants to save
 * a shopping list or way list
 */

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class EmptyDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Label lblYourCartIs;
	//to arrange the dialog position according to main screen
	private int xPosition;
	private String attentionString;

	private int yPosition;
	

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EmptyDialog(Shell parent, int style, int id) {
		super(parent, style);
		setText("SWT Dialog");
		if(id == 0){
			attentionString = "Aliþveriþ sepetiniz boþ.";
		}else{
			attentionString = "Kaydedilecek bir yol henüz bulunmadý.";
		}
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
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
		shell = new Shell(getParent(), getStyle());
		shell.setImage(SWTResourceManager.getImage("images\\unlem.png"));
		shell.setSize(347, 135);
		shell.setText("Empty");
		shell.setLocation(xPosition, yPosition);
		lblYourCartIs = new Label(shell, SWT.NONE);
		lblYourCartIs.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblYourCartIs.setBounds(57, 10, 274, 58);
		lblYourCartIs.setText(attentionString);
		
		Label label = new Label(shell, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setImage(SWTResourceManager.getImage("images\\unlem.png"));
		label.setBounds(10, 10, 63, 32);
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBounds(10, 36, 83, 32);
		
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnOk.setBounds(256, 74, 75, 25);
		btnOk.setText("OK");

	}
	
	/**
	 * @param xPosition the xPosition to set
	 */
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * @param yPosition the yPosition to set
	 */
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
}
