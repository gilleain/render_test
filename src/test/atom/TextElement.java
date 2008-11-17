package test.atom;

import java.awt.geom.Rectangle2D;

public class TextElement {
	
	public int x;
	
	public int y;
	
	public String text;
	
	public TextElement(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(x, y - 10, 10, 10);
	}

}
