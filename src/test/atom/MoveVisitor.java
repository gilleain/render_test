package test.atom;

public class MoveVisitor implements Visitor {
	
	public int dx;
	public int dy;
	
	public MoveVisitor(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void visitAtomTextSymbol(AtomTextSymbol a) {
		a.move(this.dx, this.dy);
	}
	
	public void visitAtomDecoration(AtomDecoration a) {
		a.move(dx, dy);
		a.decoratedAtomSymbol.accept(this);
	}

}
