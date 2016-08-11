package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create a dialog when "about" option is selected
 *
 */

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;


public class About extends Dialog {

	protected Object result;
	protected Shell shlHakknda;
	//to arrange the dialog position according to main screen
	private int xPosition;
	private int yPosition;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public About(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlHakknda.open();
		shlHakknda.layout();
		Display display = getParent().getDisplay();
		while (!shlHakknda.isDisposed()) {
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
		shlHakknda = new Shell(getParent(), SWT.CLOSE);
		shlHakknda.setLocation(xPosition, yPosition);		
		shlHakknda.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shlHakknda.setSize(358, 312);
		shlHakknda.setText("Hakk\u0131nda");
		
		Image image = ImageConst.getImage("images\\logo.gif");
		shlHakknda.setLayout(null);
		Composite composite = new Composite(shlHakknda, SWT.NONE);
		
		composite.setBounds(0, 0, 350, 240);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBackgroundImage(image);
		composite.setLayout(null);
		
		Label lblVersiyon = new Label(shlHakknda, SWT.NONE);
		lblVersiyon.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblVersiyon.setBounds(0, 246, 350, 15);
		lblVersiyon.setText("Version 1.1          08.08.2012 ");
		
		Label lblThisSoftwareBy = new Label(shlHakknda, SWT.NONE);
		lblThisSoftwareBy.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblThisSoftwareBy.setText("This software was created by Handan K\u0131l\u0131n\u00E7");
		lblThisSoftwareBy.setBounds(0, 267, 253, 15);

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
