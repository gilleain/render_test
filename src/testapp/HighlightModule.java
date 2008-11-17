package testapp;

import javax.vecmath.Point2d;

import org.openscience.cdk.controller.IChemModelRelay;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;

public class HighlightModule extends ControllerModuleAdapter {
	
	private IAtom prevHighlightAtom;
	private IBond prevHighlightBond;
	
	public HighlightModule(IChemModelRelay chemModelRelay) {
		super(chemModelRelay);
	}
	
	public void mouseMove(Point2d p) {
		IAtom closestAtom = super.chemModelRelay.getClosestAtom(p);
		IBond closestBond = super.chemModelRelay.getClosestBond(p);

		// nothing close enough
		if (closestAtom == null && closestBond == null) {
			return;
		}
		
		// found a bond only
		if (closestAtom == null && closestBond != null) {
			
		}
		
		// found an atom only
		if (closestAtom != null && closestBond == null) {
			
		}
		
		// found both a bond and an atom
		double dA = closestBond.get2DCenter().distance(p);
		double dB = closestAtom.getPoint2d().distance(p);
		if (dA < dB) {
			
		} else if (dB < dA) {
			
		}

	}

}
