package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create a dialog when user does not
 * enter the brand or product name
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


public class CartListDialog extends Dialog {

	protected Object result;
	protected Shell shlEmpty;
	//text of attention
	private String attentionString;
	//to arrange the dialog position according to main screen
	private int xPosition;
	private int yPosition;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CartListDialog(Shell parent, int style, int id) {
		super(parent, style);
		if(id == 0) {
			attentionString = "Lütfen bir ürün seçiniz";
		}else{
			attentionString = "Lütfen bir marka seçiniz...";
		}
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlEmpty.open();
		shlEmpty.layout();
		Display display = getParent().getDisplay();
		while (!shlEmpty.isDisposed()) {
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
		shlEmpty = new Shell(getParent(), getStyle());
		shlEmpty.setImage(SWTResourceManager.getImage("images\\unlem.png"));
		shlEmpty.setSize(370, 144);
		shlEmpty.setText("Empty");
		shlEmpty.setLocation(xPosition, yPosition);
		Label lblAttention = new Label(shlEmpty, SWT.NONE);
		lblAttention.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblAttention.setBounds(58, 10, 291, 62);
		lblAttention.setText(attentionString);
		
		Label label = new Label(shlEmpty, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setImage(SWTResourceManager.getImage("images\\unlem.png"));
		label.setBounds(10, 10, 55, 34);
		
		Label label_1 = new Label(shlEmpty, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBounds(10, 40, 55, 32);
		
		Button btnOk = new Button(shlEmpty, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlEmpty.close();
			}
		});
		btnOk.setBounds(276, 78, 75, 25);
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
