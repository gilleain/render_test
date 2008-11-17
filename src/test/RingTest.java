package test;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

public class RingTest extends JPanel implements ActionListener {
	Point2d c;
	ArrayList<Point2d> points = new ArrayList<Point2d>();
	
	int w = 800;
	int h = 800;
	Point2d cc = new Point2d(w / 2, h / 2);
	JPanel buttonPanel;
	
	public BasicStroke outerStroke = new BasicStroke(3);
	public BasicStroke innerStroke = new BasicStroke(2);
	public BasicStroke productionStroke = new BasicStroke(1);
	public boolean showRing;
	public boolean showInnerBonds;
	public boolean showDecoration;
	public boolean asOval;

	public RingTest() {
		this.setPreferredSize(new Dimension(w, h));
		points = this.makeHexagon();
		
		showRing = true;
		showInnerBonds = false;
		showDecoration = true;
		
		scaleAndCenter(10);
		
		buttonPanel = new JPanel(new GridLayout(2, 5));
		makeScaleButton(2);
		makeScaleButton(5);
		makeScaleButton(0.5);
		makeScaleButton(0.25);
		makeShapeButton("SQ");
		makeShapeButton("PE");
		makeShapeButton("HX");
		makeShapeButton("HP");
		
		makeToggleButton("ring");
		makeToggleButton("bond");
		makeToggleButton("decor");
		makeToggleButton("oval");
		
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.NORTH);
		
		this.add(new InnerPanel(), BorderLayout.CENTER);
	}
	
	public void makeToggleButton(String command) {
		JButton toggleBond = new JButton(command);
		toggleBond.setActionCommand(command);
		toggleBond.addActionListener(this);
		buttonPanel.add(toggleBond);
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.length() > 5 && command.substring(0, 5).equals("SCALE")) {
			double sf = Double.parseDouble(command.substring(5));
			System.out.println("scaling by " + sf);
			scaleAndCenter(sf);
		} else if (command.equals("ring")) {
			this.showRing = !this.showRing;
		} else if (command.equals("bond")) {
			this.showInnerBonds = !this.showInnerBonds;
		} else if (command.equals("decor")) {
			this.showDecoration = !this.showDecoration;
		} else if (command.equals("oval")) {
			this.asOval = !this.asOval;
		} else {
			if (command.equals("SQ")) {
				points = this.makeSquare();
			} else if(command.equals("PE")) {
				points = this.makePentagon();
			} else if (command.equals("HX")) {
				points = this.makeHexagon();
			} else if (command.equals("HP")) {
				points = this.makeHeptagon();
			}
			scaleAndCenter(40);
		}
//		dumpPoints("scale");
		repaint();
	}
	
	private void scaleAndCenter(double s) {
		c = findCenter(points);
		System.out.println("center = " + c);
		
		translate(points, cc);
		dumpPoints("trans");
		c = cc;
		
		scale(points, s, cc);
	}

	private void dumpPoints(String caller) {
		for (Point2d p : points) { 
			System.out.println(String.format("%s [%2.0f %2.0f]", caller, p.x, p.y)); 
		}
	}

	private void makeShapeButton(String command) {
		JButton b = new JButton(command);
		b.setActionCommand(command);
		b.addActionListener(this);
		buttonPanel.add(b);
	}

	private void makeScaleButton(double scale) {
		JButton button = new JButton(String.valueOf(scale));
		button.addActionListener(this);
		button.setActionCommand("SCALE " + String.valueOf(scale));
		buttonPanel.add(button);
	}
	
	private void dumpPoint(String message, Point2d p) {
		System.out.println(String.format("%s [%2.0f %2.0f]", message, p.x, p.y));
	}

	private void scale(ArrayList<Point2d> points, double scale, Point2d origin) {
		dumpPoint("origin", origin);
		for (Point2d p : points) {
			dumpPoint("before scale", p);
//			p.x = origin.x + (scale * (p.x - origin.x));
//			p.y = origin.y + (scale * (p.y - origin.y));
			p.interpolate(origin, p, scale);
			dumpPoint("after  scale", p);
		}
	}
	
	private void translate(ArrayList<Point2d> points, Point2d cc) {
		for (Point2d p : points) {
			p.x += (cc.x - c.x);
			p.y += (cc.y - c.y);
		}
	}
	
	private Point2d findCenter(ArrayList<Point2d> points) {
		Point2d center = new Point2d(0, 0);
		for (Point2d p : points) {
			center.x += p.x;
			center.y += p.y;
		}
		center.x /= points.size();
		center.y /= points.size();
		return center;
	}
	
	private ArrayList<Point2d> makeSquare() {
		ArrayList<Point2d> p = new ArrayList<Point2d>();
		p.add(new Point2d(1, 1));
		p.add(new Point2d(1, 4));
		p.add(new Point2d(4, 4));
		p.add(new Point2d(4, 1));
		return p;
	}
	
	private ArrayList<Point2d> makePentagon() {
		ArrayList<Point2d> p = new ArrayList<Point2d>();
		p.add(new Point2d(2.5, 0));
		p.add(new Point2d(5  , 2));
		p.add(new Point2d(4  , 5));
		p.add(new Point2d(1  , 5));
		p.add(new Point2d(0  , 2));
		return p;
	}
	
	private ArrayList<Point2d> makeHexagon() {
		ArrayList<Point2d> p = new ArrayList<Point2d>();
		p.add(new Point2d( 8,  4.5));
//		points.add(new Point2d( 8,  3));
		
		p.add(new Point2d(11,  6));
		p.add(new Point2d(11, 10));
		
		
		p.add(new Point2d( 8, 11.5));
//		points.add(new Point2d( 8, 13));
		
		p.add(new Point2d( 5, 10));
		p.add(new Point2d( 5,  6));
		return p;
	}
	
	private ArrayList<Point2d> makeHeptagon() {
		ArrayList<Point2d> p = new ArrayList<Point2d>();
		p.add(new Point2d(3, 1));
		p.add(new Point2d(5, 2));
		p.add(new Point2d(5.5, 4));
		p.add(new Point2d(4, 5.5));
		p.add(new Point2d(2, 5.5));
		p.add(new Point2d(0.5, 4));
		p.add(new Point2d(1, 2));
		return p;
	}

	public double dist(Point2d p1, Point2d p2) {
		double x1 = p1.x;
		double y1 = p1.y;
		double x2 = p2.x;
		double y2 = p2.y;
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}
	
	private Line2D calc(Graphics2D g, Point2d a, Point2d b) {
//		
		final double DIST = 0.30;

		Point2d w = new Point2d();
		w.interpolate(a, c, DIST);
		Point2d u = new Point2d();
		u.interpolate(b, c, DIST);
		
		double alpha = 0.1;
		Point2d ww = new Point2d();
		ww.interpolate(w, u, alpha);
		Point2d uu = new Point2d();
		uu.interpolate(u, w, alpha);
		
		if (showDecoration) {
			drawPoint(g, w);
			drawPoint(g, u);
		}
//		drawPoint(g, ww);
//		drawPoint(g, uu);
//		return new Line2D.Double(w.x, w.y, u.x, u.y);
		return new Line2D.Double(ww.x, ww.y, uu.x, uu.y);
	}
	
	private void drawPoint(Graphics2D g, Point2d p) {
		int r = 2;
		int d = r * 2;
		g.setColor(Color.RED);
		g.drawOval((int)p.x - r, (int)p.y - r, d, d);
	}
	
	private void drawOuterLine(Graphics2D g, Point2d a, Point2d b) {
		g.setStroke(outerStroke);
		g.setColor(Color.BLACK);
		g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
	}
	
	private void drawInnerLine(Graphics2D g, Point2d a, Point2d b) {
		g.setStroke(innerStroke);
		Line2D line = calc(g, a, b); 
		g.setColor(Color.BLACK);
		if (showInnerBonds)
			g.draw(line);
	}
	
	private void drawProductionLine(Graphics2D g, Point2d a, Point2d b) {
		g.setStroke(productionStroke);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
	}
	
	private Rectangle2D minDimension() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		for (Point2d p : points) {
			if (p.x < minX) minX = p.x;
			if (p.y < minY) minY = p.y;
			if (p.x > maxX) maxX = p.x;
			if (p.y > maxY) maxY = p.y;
		}
		
		double maxWidth  = maxX - minX;
		double maxHeight = maxY - minY;
		return new Rectangle2D.Double(minX, minY, maxWidth, maxHeight);
	}
	
	private void drawRing(Graphics2D g) {
		Rectangle2D d = minDimension();
		if (showDecoration) {
			g.draw(d);
		}
		
		double alpha = 0.30;
		double ww = d.getWidth();
		double hh = d.getHeight();
		if (asOval) {
			ww = hh = Math.min(ww, hh);
		}
		int w = (int)((1 - alpha) * ww);
		int h = (int)((1 - alpha) * hh);
		
		int x = (int)c.x - (w / 2);
		int y = (int)c.y - (h / 2);

		g.setColor(Color.BLACK);
		g.setStroke(innerStroke);
		g.drawOval(x, y, w, h);
	}
	
	public class InnerPanel extends JPanel {
		
		public void paint(Graphics gg) {
			Graphics2D g = (Graphics2D) gg;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.WHITE);
			g.fill(getBounds());
	
			for (int i = 0; i < points.size() - 1; i++) {
				drawOuterLine(g, points.get(i), points.get(i + 1));
				if (i % 2 == 0) {
					drawInnerLine(g, points.get(i), points.get(i + 1));
				}
			}
			drawOuterLine(g, points.get(points.size() - 1), points.get(0));
	//		drawInnerLine(g, points.get(points.size() - 1), points.get(0));
			
			if (showDecoration) {
				for (Point2d p : points) {
					drawProductionLine(g, p, c);
				}
			}
			
			if (showRing) {
				drawRing(g);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		RingTest rt = new RingTest();
		f.add(rt);
		f.pack();
		f.setVisible(true);
	}

}
