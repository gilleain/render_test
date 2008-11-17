package test.atom;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public interface AtomSymbol {
	
	public void addToList(ArrayList<TextElement> list);
	
	public Rectangle2D getBounds();
	
	public int getCenterX();
	
	public int getCenterY();
	
	public void accept(Visitor v);
	
	public void move(int dx, int dy);

}
