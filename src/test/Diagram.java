package test;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Diagram {
	private ArrayList<TextBox> boxes;
	private FontManager fontManager;
	private double scale;
	
	public Diagram() {
		this.fontManager = new FontManager();
		
		boxes = new ArrayList<TextBox>();
		boxes.add(new TextBox(100, 100, "Fe", fontManager));
		boxes.add(new TextBox(100, 200, "Ni", fontManager));
		boxes.add(new TextBox(200, 100, "Mg", fontManager));
		boxes.add(new TextBox(200, 200, "Cl", fontManager));
		
		this.scale = 1;
	}
	
	public void translate(int dx, int dy) {
		for (TextBox box : boxes) {
			box.x += dx;
			box.y += dy;
		}
	}
	
	public int size() {
		return boxes.size();
	}
	
	public Point calculateCenter() {
		int centerX = 0;
		int centerY = 0;
		for (TextBox box : boxes) {
			centerX += box.x;
			centerY += box.y;
		}
		centerX /= this.size();
		centerY /= this.size();
		return new Point(centerX, centerY);
	}
	
	public void scale(double d) {
		Point center = calculateCenter();
		
		for (TextBox box : boxes) {
			box.scale(d, center);
		}
		this.scale *= d;
		System.out.println(this.scale + " " + d + " ");
		this.fontManager.scale(d);
	}
	
	public void centerOn(Point p) {
		Point c = calculateCenter();
		translate(p.x - c.x, p.y - c.y);
	}
	
	public void paint(Graphics2D g2) {
		for (TextBox box : boxes) {
			if (!box.isLaidOut) {
				box.layout(g2);
			}
			box.paint(g2);
		}
	}
}