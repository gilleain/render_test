package test.bond;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import test.atom.AtomSymbol;
import test.atom.AtomTextSymbol;
import test.atom.ChargeDecoration;
import test.atom.MassDecoration;
import test.atom.TextElement;

public class TestBondSymbol extends JPanel {
	
	public ArrayList<AtomSymbol> atoms = new ArrayList<AtomSymbol>();
	public ArrayList<BondSymbol> bonds = new ArrayList<BondSymbol>();
	
	public TestBondSymbol() {
		this.setPreferredSize(new Dimension(400,400));
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (BondSymbol bondSymbol : bonds) {
			g2.draw(bondSymbol.bondShape);
		}
		
		ArrayList<TextElement> textElements = new ArrayList<TextElement>();
		ArrayList<Rectangle2D> bounds = new ArrayList<Rectangle2D>();
		
		for (AtomSymbol atomSymbol : atoms) {
			atomSymbol.addToList(textElements);
			bounds.add(atomSymbol.getBounds());
		}
		
		for (TextElement element : textElements) {
			g2.drawString(element.text, element.x, element.y);
		}
		
		for (Rectangle2D bound : bounds) {
			g2.draw(bound);
		}
		
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		TestBondSymbol test = new TestBondSymbol();
		
		test.bonds.add(new BondSymbol(10, 10, 100, 10, BondSymbol.ShapeType.SINGLE_LINE));
		test.bonds.add(new BondSymbol(10, 50, 100, 50, BondSymbol.ShapeType.DASHED));
		
		test.atoms.add(
				new MassDecoration(
						new ChargeDecoration(
								new AtomTextSymbol(100, 100, "N"), "-"), "14"));
		test.atoms.add(
				new ChargeDecoration(
						new MassDecoration(
								new AtomTextSymbol(200, 200, "C"), "13"), "+"));
		
		f.add(test);
		f.pack();
		f.setVisible(true);
	}

}
