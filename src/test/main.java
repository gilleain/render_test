package test;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class BNode {
	static final int HORIZONTAL = 0;
	static final int VERTICAL = 1;

	public BNode() {
		this(-1, new Point());
	}

	public BNode(int i, Point newElement) {
		this(i, newElement, null, null);
	}

	public BNode(int i, Point newElement, BNode l, BNode r) {
		x = newElement;
		y = new Point(newElement.y, newElement.x);
		count = i;
		lc = l;
		rc = r;
	}

	public BNode(BNode b) {
		x = new Point(b.x.x, b.x.y);
		y = new Point(b.y.x, b.y.y);
		count = b.count;
		lc = b.lc;
		rc = b.rc;
	}

	private String printPoints() {
		String s = " ";
		if (lc != null) {
			s = lc.printPoints() + " ";
		}
		if (count != -1)
			s = s + (count + 1);
		if (rc != null) {
			s = s + " " + rc.printPoints();
		}
		return s;
	}

	Rectangle split(Rectangle r1) {
		Rectangle r2;
		switch (x.x) {
		case HORIZONTAL:
			r2 = new Rectangle(r1.x, x.y, r1.width, r1.height - (x.y - r1.y));
			r1.height = (x.y - r1.y + 1 > 1) ? x.y - r1.y + 1 : 0;
			break;
		case VERTICAL:
			r2 = new Rectangle(x.y, r1.y, r1.width - (x.y - r1.x), r1.height);
			r1.width = (x.y - r1.x + 1 > 1) ? x.y - r1.x + 1 : 0;
			break;
		default:
			r2 = new Rectangle();
			break;
		}
		return r2;
	}

	public void drawSingle(Graphics g) {
		g.drawString(new Integer(count + 1).toString(), x.x, x.y);
		g.fillOval(x.x - 2, x.y - 2, 5, 5);
	}

	public void draw(Graphics g, Rectangle r1) {
		if (lc == null && rc == null) {
			drawSingle(g);
		} else {
			Rectangle r2 = new Rectangle();
			switch (x.x) {
			case HORIZONTAL:
				r2 = split(r1);
				g.drawLine(r1.x, x.y, r2.x + r2.width - 1, x.y);
				break;
			case VERTICAL:
				r2 = split(r1);
				g.drawLine(x.y, r1.y, x.y, r2.y + r2.height - 1);
				break;
			default:
				System.out.println("Drawing: Incorrect node in tree");
				break;
			}

			if (lc != null)
				lc.draw(g, r1);
			if (rc != null)
				rc.draw(g, r2);
		}
	}

	public String report() {
		return printPoints();

	}

	public String report(Rectangle r) {
		if (r.contains(x)) {
			return printPoints();
		} else
			return "";
	}

	Point x;
	Point y;
	int count;
	BNode lc;
	BNode rc;
}

class KDTree {

	BNode root;
	private int n;
	private BNode[] p;
	private int dim;

	public KDTree(int max) {
		dim = 2;
		root = null;
		p = new BNode[max];
		n = 0;
	}

	public int addPoint(Point newP) {
		root = null;
		p[n] = new BNode(n, newP);
		n++;
		return n;
	}

	public void draw(Graphics g, Rectangle r) {
		if (root == null)
			for (int i = 0; i < n; i++)
				p[i].drawSingle(g);
		else
			root.draw(g, r);
	}

	private boolean contains(Rectangle r1, Rectangle r2) {
		if (r1.x <= r2.x && r1.y <= r2.y && r1.x + r1.width >= r2.x + r2.width
				&& r1.y + r1.height >= r2.y + r2.height)
			return true;
		else
			return false;
	}

	private void message(Graphics g, String s) {
		g.setColor(Color.black);
		g.drawString(s, 0, 15);
	}

	public String searchKDTree(BNode v, Rectangle cur, Rectangle q, Graphics g,
			Color c) {
		String s = "";
		if (root == null) {
			message(g, new String("Quering a nongenerated tree"));
		} else {
			c = new Color(0, (c.getGreen() - 20), 0);
			if (v.lc == null && v.rc == null) {
				g.setColor(c);
				g.fillRect(cur.x, cur.y, cur.width, cur.height);
				s = s + v.report(q);
			} else {
				Rectangle r2 = v.split(cur);
				if (contains(q, cur)) {
					g.setColor(c);
					g.fillRect(cur.x, cur.y, cur.width, cur.height);
					s = s + v.lc.report();
				} else {
					if (q.intersects(cur)) {

						s = s + searchKDTree(v.lc, cur, q, g, c);
					}
				}
				if (contains(q, r2)) {
					g.setColor(c);
					g.fillRect(r2.x, r2.y, r2.width, r2.height);
					s = s + v.rc.report();
				} else if (q.intersects(r2)) {
					s = s + searchKDTree(v.rc, r2, q, g, c);
				}
			}
		}
		return s;
	}

	public BNode generate() {
		return generateInner(0, n - 1, 0);
	}

	private BNode generateInner(int r, int s, int d) {
		if (r == s)
			return root = new BNode(p[r]);
		int n = randomizedSelect(p, r, s,
				(int) Math.floor((r + s + 2) / 2) - r, d);

		Point l = (d == 0) ? new Point(1, p[n].x.x) : new Point(0, p[n].x.y);
		d = (d + 1) % dim;

		BNode vl = generateInner(r, n, d);
		BNode vr = generateInner(n + 1, s, d);
		root = new BNode(-1, l, vl, vr);
		return root;
	}

	private void exchange(BNode i, BNode j) {
		try {
			BNode tmp = new BNode(i);
			i.x.x = j.x.x;
			i.x.y = j.x.y;
			i.y.x = j.y.x;
			i.y.y = j.y.y;
			i.count = j.count;
			j.x.x = tmp.x.x;
			j.x.y = tmp.x.y;
			j.y.x = tmp.y.x;
			j.y.y = tmp.y.y;
			j.count = tmp.count;
		} catch (Exception e) {
		}
	}

	private int greater(Point p1, Point p2) {
		if ((p1.x == p2.x) && (p1.y == p2.y)) {
			System.err.println("Two points equal");
			return 0;
		} else if ((p1.x > p2.x) || ((p1.x == p2.x) && (p1.y > p2.y)))
			return 1;
		else
			return 0;
	}

	private int partition(BNode[] A, int p, int r, int d) {
		Point curP = (d == 0) ? A[r].x : A[r].y;
		int i = p - 1;

		for (int j = p; j <= r - 1; j++) {
			if (d == 0) {
				if (greater(curP, A[j].x) == 1) {
					i++;
					exchange(A[i], A[j]);
				}
			} else {
				curP = A[r].y;
				if (greater(curP, A[j].y) == 1) {
					i++;
					exchange(A[i], A[j]);
				}
			}
		}
		exchange(A[i + 1], A[r]);
		return i + 1;
	}

	private int randomizedPartition(BNode[] A, int p, int r, int d) {
		int h = p + (int) Math.round((r - p) * Math.random());
		exchange(A[r], A[h]);
		return partition(A, p, r, d);
	}

	private int randomizedSelect(BNode[] A, int p, int r, int i, int d) {
		if (p == r)
			return p;
		int q = randomizedPartition(A, p, r, d);
		int k = q - p + 1;
		if (i == k)
			return q;
		else if (i < k)
			return randomizedSelect(A, p, q - 1, i, d);
		else
			return randomizedSelect(A, q + 1, r, i - k, d);
	}
}

class KDCanvas extends Canvas implements MouseListener {
	KDTree kd;
	Graphics g;
	int canvasHeight, canvasWidth;
	Point q1;
	Rectangle q, screen;
	boolean query;

	KDCanvas(int x, int y, int m) {
		kd = new KDTree(m);
		canvasWidth = x;
		canvasHeight = y;
		this.setSize(x, y);
		addMouseListener(this);
		query = false;
		screen = new Rectangle(canvasWidth + 1, canvasHeight + 1);
	}

	public void init() {
		g = this.getGraphics();
		update(g);
	}

	public void q(boolean b) {
		query = b;
	}

	public void query() {
		if (kd.root == null)
			kd.generate();
		if (q == null) return;
		String s = kd.searchKDTree(kd.root, new Rectangle(screen),
				new Rectangle(q), g, Color.green);

		g.setColor(Color.gray);
		g.drawRect(q.x, q.y, q.width - 1, q.height - 1);

		g.setColor(Color.black);
		g.drawString(new String("Query result:") + s, 0, 15);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (query)
			query();
		g.setColor(Color.black);
		kd.draw(g, new Rectangle(screen));
	}

	public void update(Graphics g) {
		paintAll(g);
	}

	public void message(String s) {
		g.setColor(Color.black);
		g.drawString(s, 0, 15);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		Point p = e.getPoint();
		if (query) {
			q = new Rectangle(q1.x, q1.y, p.x - q1.x + 1, p.y - q1.y + 1);
			query();
			repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		if (query) {
			q1 = p;
		} else {
			try {
				kd.addPoint(p);
				update(g);
			} catch (Exception ex) {
			}
		}
	}

	public void gen() {
		kd.generate();
	}

}

public class main extends Applet implements ActionListener, ItemListener {
	int appWidth, appHeight;
	Button draw;
	KDCanvas canv;
	Checkbox queryCheck;

	public void actionPerformed(ActionEvent a) {
		String str = a.getActionCommand();
		System.out.println(str);
		if (str == "Draw") {
			canv.gen();
			canv.repaint();
		}
	}

	public void itemStateChanged(ItemEvent e) {
		canv.q(queryCheck.getState());
	}

	public void init() {
//		appWidth = getSize().width;
		appWidth = 500;
//		appHeight = getSize().height;
		appHeight = 500;

//		int max = Integer.valueOf(getParameter("maxp")).intValue();
		int max = 100;
		this.setLayout(new BorderLayout());

		draw = new Button("Draw");
		queryCheck = new Checkbox("Query", false);
		canv = new KDCanvas(appWidth, appHeight - 30, max);

		this.add(canv, "North");
		canv.init();

		Panel p = new Panel();
		p.add(draw);
		p.add(queryCheck);
		this.add(p, "South");

		draw.addActionListener(this);
		queryCheck.addItemListener(this);
	}

	public void run() {
		while (true)
			;
	}

	public void start() {
	}

	public void stop() {
	}

	public void destroy() {
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, appWidth, appHeight);
	}
}
