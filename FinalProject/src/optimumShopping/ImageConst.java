package optimumShopping;
/**
 * @author Handan KILINÇ
 * This class is to create Image
 *
 */
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Image;

public class ImageConst {

	public static Image getImage(String image){
		Image temp = new Image(new Device() {
			
			@Override
			public long internal_new_GC(GCData data) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void internal_dispose_GC(long hDC, GCData data) {
				// TODO Auto-generated method stub
				
			}
		}, image);
		return temp;
	}

}
