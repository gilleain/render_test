package test;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class FontManager {
	// arbitrary, for now
	public static final int NUMBER_OF_SIZES = 20;
	
	public static final String FONT_FAMILY_NAME = "Arial";
	
	public Font[] fonts = new Font[NUMBER_OF_SIZES];
	
	public int minFontSize;
	
	public int currentFontIndex;
	
	// these two values track the font position if it falls
	// off the end of the array so that font and scale are always in synch
	public int lowerVirtualCount;
	
	public int upperVirtualCount;
	
	public FontManager() {
		// apparently 9 pixels per em is the minimum
		// but I don't know if (size 9 == 9 px.em-1)... 
		this.minFontSize = 9;
		
		int s = this.minFontSize;
		for (int i = 0; i < FontManager.NUMBER_OF_SIZES; i++) {
			this.fonts[i] = new Font(FontManager.FONT_FAMILY_NAME, Font.PLAIN, s);
			s += 1;	//?
		}
		
		this.currentFontIndex = FontManager.NUMBER_OF_SIZES / 2;
		
		this.lowerVirtualCount = 0;
		this.upperVirtualCount = FontManager.NUMBER_OF_SIZES - 1;
	}
	
	public GlyphVector getGlyphVector(String text, Graphics2D g) {
		FontRenderContext frc = g.getFontRenderContext();
		Font currentFont = this.fonts[this.currentFontIndex];
		return currentFont.createGlyphVector(frc, text);
	}
	
	public void increaseFontSize() {
		// move INTO range if we have just moved OUT of lower virtual
		if (this.inRange() || (this.atMin() && this.lowerVirtualCount == 0)) {
			this.currentFontIndex++;
		} else if (this.atMax()){
			this.upperVirtualCount++;
		} else if (this.atMin() && this.lowerVirtualCount < 0){ 
			this.lowerVirtualCount++;
		}
		System.err.println(this.upperVirtualCount + " " +
				this.lowerVirtualCount + " " +
				this.currentFontIndex + " " + FontManager.NUMBER_OF_SIZES +
				"font now " + this.fonts[this.currentFontIndex]);
	}
	
	public void decreaseFontSize() {
		// move INTO range if we have just moved OUT of upper virtual
		if (this.inRange() 
				|| (this.atMax() 
						&& this.upperVirtualCount == FontManager.NUMBER_OF_SIZES - 1)) {
			this.currentFontIndex--;
		} else if (this.atMin()) {
			this.lowerVirtualCount--;
		} else if (this.atMax() 
				&& this.upperVirtualCount > FontManager.NUMBER_OF_SIZES - 1) {
			this.upperVirtualCount--;
		}
		System.err.println(this.upperVirtualCount + " " +
				this.lowerVirtualCount + " " +
				this.currentFontIndex + " " + FontManager.NUMBER_OF_SIZES +
				"font now " + this.fonts[this.currentFontIndex]);
	}

	public boolean inRange() {
		return this.currentFontIndex > 0 
				&& this.currentFontIndex < FontManager.NUMBER_OF_SIZES - 1;
	}
	
	public boolean atMax() {
		return this.currentFontIndex == FontManager.NUMBER_OF_SIZES - 1;
	}
	
	public boolean atMin() {
		return this.currentFontIndex == 0;
	}
	
	public void scale(double d) {
		// hmmm
		if (d < 1) {
			this.decreaseFontSize();
		} else {
			this.increaseFontSize();
		}
	}
}
