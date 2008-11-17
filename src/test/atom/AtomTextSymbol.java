package test.atom;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class AtomTextSymbol implements AtomSymbol {
	
	public int centerX;
	
	public int centerY;
	
	public TextElement textElement;
	
	public AtomTextSymbol(int x, int y, String text) {
		this.centerX = x;
		this.centerY = y;
		this.textElement = new TextElement(x - 5, y + 5, text);
	}
	
	public void addToList(ArrayList<TextElement> list) {
		list.add(this.textElement);
	}
	
	public Rectangle2D getBounds() {
		return textElement.getBounds();
	}
	
	public Point2D getCenter() {
		return new Point2D.Double(this.centerX, this.centerY);
	}

	public int getCenterX() {
		return this.centerX; 
	}

	public int getCenterY() {
		return this.centerY;
	}
	
	public void move(int dx, int dy) {
		this.textElement.x += dx;
		this.textElement.y += dy;
	}
	
	public void accept(Visitor v) {
		v.visitAtomTextSymbol(this);
	}

}
