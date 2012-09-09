package cs5031.catalogplayer.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

/**
 * Fixes bug 4832765 by overiding getScrollableUnitIncrement - code taken from 
 * http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDialogRunnerProject/src/components/ListDialog.java
 * @author Dilyan Rusev
 *
 * @param <E> Type of the data held in JList
 */
public class MyJList extends JList {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -5461146056964796484L;

	/**
	 * Creates a new empty list that used the default ListModel
	 */
	public MyJList() {
		super();
	}
	
	/**
	 * Creates a new list which is bound to a custom ListModel
	 * @param model Model to bind the list to
	 */
	public MyJList(ListModel model) {
		super(model);
	}
	
	/**
	 * Binds the list to a data, wrapped in the default model
	 * @param data Data to bind to
	 */
	public MyJList(Object[] data) {
		super(data);
	}
	
	/**
	 * Binds the list to data, wrapped in the default model
	 * @param vec Data to bind to
	 */
	public MyJList(Vector<?> vec) {
		super(vec);
	}
	
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		int row;
        if (orientation == SwingConstants.VERTICAL &&
              direction < 0 && (row = getFirstVisibleIndex()) != -1) {
            Rectangle r = getCellBounds(row, row);
            if ((r.y == visibleRect.y) && (row != 0))  {
                Point loc = r.getLocation();
                loc.y--;
                int prevIndex = locationToIndex(loc);
                Rectangle prevR = getCellBounds(prevIndex, prevIndex);

                if (prevR == null || prevR.y >= r.y) {
                    return 0;
                }
                return prevR.height;
            }
        }
        return super.getScrollableUnitIncrement(
                        visibleRect, orientation, direction);
	}
}