package test.atom;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class AtomDecoration implements AtomSymbol {

	public TextElement textElement; 
	public AtomSymbol decoratedAtomSymbol;
	
	public AtomDecoration(int x, int y, String text, AtomSymbol decoratedAtomSymbol) {
		this.textElement = new TextElement(x, y, text);
		this.decoratedAtomSymbol = decoratedAtomSymbol;
	}

	public void addToList(ArrayList<TextElement> list) {
		decoratedAtomSymbol.addToList(list);
		list.add(this.textElement);
	}

	public Rectangle2D getBounds() {
		Rectangle2D returnValue = new Rectangle2D.Double();
		Rectangle.union(this.textElement.getBounds(), 
				this.decoratedAtomSymbol.getBounds(), returnValue);
		return returnValue;
	}

	public int getCenterX() {
		return this.decoratedAtomSymbol.getCenterX();
	}

	public int getCenterY() {
		return this.decoratedAtomSymbol.getCenterY();
	}

	public void move(int dx, int dy) {
		this.textElement.x += dx;
		this.textElement.y += dy;
	}

	public void accept(Visitor v) {
		v.visitAtomDecoration(this);
	}

}
