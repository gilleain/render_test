package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextText extends JPanel {
	
	private Diagram diagram;
	
	public TextText() {
		this.diagram = new Diagram();
			
		this.setPreferredSize(new Dimension(800, 800));
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent m) {
				if (m.getButton() == MouseEvent.BUTTON1) {
					scale(1.1);
				} else if (m.getButton() == MouseEvent.BUTTON2) {
					diagram.centerOn(m.getPoint());
					repaint();
				} else {
					scale(0.9);
				}
			}
		});
	}
	
	public Point canvasCenter() {
		return new Point(this.getWidth() / 2, this.getHeight() / 2);
	}
	
	public void scale(double d) {
		this.diagram.scale(d);
		this.repaint();
	}
	
	public void center() {
		Point canvasCenter = canvasCenter();
		this.diagram.centerOn(canvasCenter);
		this.repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		this.diagram.paint(g2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		TextText tt = new TextText();
		f.add(tt);
		f.setLocation(100, 100);
		f.pack();
		f.setVisible(true);
		tt.center();
	}

}
