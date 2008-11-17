package test.atom;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class TestAtom extends JPanel {
	
	public ArrayList<AtomSymbol> atoms = new ArrayList<AtomSymbol>();
	
	public TestAtom() {
		this.setPreferredSize(new Dimension(400,400));
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
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
		TestAtom test = new TestAtom();
		
		test.atoms.add(
				new MassDecoration(
						new ChargeDecoration(
								new AtomTextSymbol(100, 100, "N"), "-"), "14"));
		test.atoms.add(
				new ChargeDecoration(
						new MassDecoration(
								new AtomTextSymbol(200, 200, "C"), "13"), "+"));
		
		MoveVisitor m = new MoveVisitor(30, 30);
		for (AtomSymbol atom : test.atoms) {
			atom.accept(m);
		}
		f.add(test);
		f.pack();
		f.setVisible(true);
	}

}
