package test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinalDiagramTest extends JPanel {
	
	public final class Point {
		public final int x;
		public final int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public final class Diagram {
		
		public double scale;
		public double dx;
		public double dy;
		
		public final ArrayList<Point> points;
		
		public Diagram(double scale, double dx, double dy) {
			this.points = new ArrayList<Point>();
			this.scale = scale;
			this.dx = dx;
			this.dy = dy;
		}
		
		public void addPoint(final int x, final int y) {
			this.points.add(new Point(x, y));
		}
		
	}
	
	private Diagram dA;
	private Diagram dB;

	
	private int screenW = 800;
	private int screenH = 800;
	
	public FinalDiagramTest() {
		double cX = this.screenW / 2;
		double cY = this.screenH / 2;
		
		this.dA = new Diagram(20.0, cX, cY);
		dA.addPoint( 1,  1);
		dA.addPoint( 1, -1);
		dA.addPoint(-1, -1);
		dA.addPoint(-1,  1);
		
		this.dB = new Diagram(40.0, cX - 50, cY - 20);
		dB.addPoint( 1,  1);
		dB.addPoint( 1, -1);
		dB.addPoint(-1, -1);
		dB.addPoint(-1,  1);
		
		this.setPreferredSize(new Dimension(this.screenW, this.screenH));
	}
	
	public int transformX(Diagram d, int x) {
		return (int)(d.dx + (d.scale * x));
	}
	
	public int transformY(Diagram d, int y) {
		return (int)(d.dy + (d.scale * y));
	}
	
	public void paint(Graphics g) {
		paintDiagram(g, this.dA);
		paintDiagram(g, this.dB);
	}
	
	public void paintDiagram(Graphics g, Diagram d) {
		for (Point point : d.points) {
			int x = this.transformX(d, point.x);
			int y = this.transformY(d, point.y);
			int r = 2;
			int w = 2 * r;
			g.fillOval(x - r, y - r, w, w);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		FinalDiagramTest rt = new FinalDiagramTest();
		f.add(rt);
		f.pack();
		f.setVisible(true);
	}

}
