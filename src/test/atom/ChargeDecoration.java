package test.atom;

public class ChargeDecoration extends AtomDecoration {
	
	public ChargeDecoration(AtomSymbol atomSymbol, String charge) {
		super(atomSymbol.getCenterX() + 10,
				atomSymbol.getCenterY() - 5, charge, atomSymbol);
	}
	
}
