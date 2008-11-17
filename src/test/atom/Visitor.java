package test.atom;

public interface Visitor {
	
	public void visitAtomTextSymbol(AtomTextSymbol a);

	public void visitAtomDecoration(AtomDecoration a);
}
