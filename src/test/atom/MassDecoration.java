package test.atom;

public class MassDecoration extends AtomDecoration {
	
	public AtomSymbol decoratedAtomSymbol;
	
	public MassDecoration(AtomSymbol atomSymbol, String mass) {
		super(atomSymbol.getCenterX() - 10,
				atomSymbol.getCenterY() - 5, mass, atomSymbol);
	}

}
