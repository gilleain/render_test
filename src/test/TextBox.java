package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.GlyphVector;

public class TextBox {
	
	public int x;
	public int y;
	public String text;
	public GlyphVector glyphs;
	public boolean isLaidOut;
	
	public FontManager fontManager;

	public TextBox(int x, int y, String text, FontManager fontManager) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.glyphs = null;
		this.isLaidOut = false;
		this.fontManager = fontManager;
	}

	/**
	 * Scale relative to a point.
	 * 
	 * @param d
	 * @param p
	 */
	public void scale(double d, Point p) {
		this.x = (int)((this.x - p.x) * d) + p.x;
		this.y = (int)((this.y - p.y) * d) + p.y;
		this.isLaidOut = false;
	}
	
	public void layout(Graphics2D g) {
		this.glyphs = this.fontManager.getGlyphVector(text, g);
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, 4, 4);
		g.setColor(Color.BLACK);
		if (glyphs != null) {
			g.drawGlyphVector(glyphs, x, y);
		}
	}
}
