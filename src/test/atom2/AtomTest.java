package test.atom2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AtomTest extends JPanel implements ActionListener {
	
	public class TextElement {
		public String text;
		public int x;
		public int y;
		
		public TextElement(String text, int x, int y) {
			this.text = text;
			this.x = x;
			this.y = y;
		}
	}
	
	public class AtomSymbol {
		
		public TextElement element;
		public TextElement mass;
		public TextElement charge;
		public boolean showElement;
		public boolean showMass;
		public boolean showCharge;
		
		public AtomSymbol(String text,int x, int y) {
			this.element = new TextElement(text, x, y);
			this.mass = null;
			this.charge = null;
			this.showElement = true;
			this.showMass = true;
			this.showCharge = true;
		}
		
		public void setMass(String massString) {
			this.mass = new TextElement(massString, element.x - 10, element.y - 10);
		}
		
		public void setCharge(String chargeString) {
			this.charge = new TextElement(chargeString, element.x + 10, element.y - 10);
		}
		
	}
	
	public ArrayList<AtomSymbol> atoms = new ArrayList<AtomSymbol>();
	
	public AtomTest() {
		this.setPreferredSize(new Dimension(400,400));
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		for (AtomSymbol a : atoms) {
			if (a.showElement) {
				g.drawString(a.element.text, a.element.x, a.element.y);
			}
			if (a.showMass) {
				g.drawString(a.mass.text, a.mass.x, a.mass.y);
			}
			if (a.showCharge) {
				g.drawString(a.charge.text, a.charge.x, a.charge.y);
			}
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		if (command.equals("E")) {
			for (AtomSymbol a : atoms) {
				a.showElement = !a.showElement; 
			}
		} else if (command.equals("M")) {
			for (AtomSymbol a : atoms) {
				a.showMass = !a.showMass; 
			}
		} else if (command.equals("C")) {
			for (AtomSymbol a : atoms) {
				a.showCharge = !a.showCharge; 
			}
		}
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(2, 2));
		AtomTest test = new AtomTest();
		
		AtomSymbol a = test.new AtomSymbol("N", 100, 100);
		a.setMass("13");
		a.setCharge("+");
		
		test.atoms.add(a);
		f.add(test);
		
		JButton eButton = new JButton("E");
		JButton mButton = new JButton("M");
		JButton cButton = new JButton("C");
		
		eButton.setActionCommand("E");
		mButton.setActionCommand("M");
		cButton.setActionCommand("C");
		
		eButton.addActionListener(test);
		mButton.addActionListener(test);
		cButton.addActionListener(test);
		
		f.add(eButton);
		f.add(mButton);
		f.add(cButton);
		
		f.pack();
		f.setVisible(true);
	}

}
