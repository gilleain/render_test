package test.bond;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

public class BondSymbol {
	
	public Shape bondShape;
	
	public enum ShapeType {
		SINGLE_LINE, DASHED
	}
	
	public BondSymbol(int x1, int y1, int x2, int y2, BondSymbol.ShapeType type) {
		if (type == ShapeType.SINGLE_LINE) {
			bondShape = new Line2D.Double(x1, y1, x2, y2);
		} else {
			GeneralPath dashes = new GeneralPath();
			// for the purpose of testing, assume that the bond is level, ie y1 == y2
			int gap = 3;	// arbitrary
			int w = 2;  // arbitrary
			for (int i = x1; i < x2; i += gap) {
				dashes.append(new Line2D.Double(i, y1 - w, i, y1 + w), false);
			}
			bondShape = dashes;
		}
	}
}
