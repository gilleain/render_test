package testapp;

import javax.vecmath.Point2d;

import org.openscience.cdk.ChemModel;
import org.openscience.cdk.MoleculeSet;
import org.openscience.cdk.controller.IChemModelRelay;
import org.openscience.cdk.controller.IController2DModel;
import org.openscience.cdk.controller.IMouseEventRelay;
import org.openscience.cdk.controller.IViewEventRelay;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.renderer.IJava2DRenderer;

public class ControllerHub implements IMouseEventRelay, IChemModelRelay {
	
	private IViewEventRelay view;
	private IJava2DRenderer renderer;
	private IController2DModel controllerModel;
	private IChemModel chemModel;
	
	public ControllerHub(
			IController2DModel controllerModel, 
			IJava2DRenderer renderer,
			IViewEventRelay view) {
		this.controllerModel = controllerModel;
		this.renderer = renderer;
		this.view = view;
	}
	
	public void addAtomContainer(IAtomContainer ac) {
		if (this.chemModel == null) {
			this.chemModel = new ChemModel();
			MoleculeSet molecules = new MoleculeSet();
			molecules.addMolecule((IMolecule)ac);
			this.chemModel.setMoleculeSet(molecules);
		} else {
			this.chemModel.getMoleculeSet().addMolecule((IMolecule) ac);
		}
	}

	public void addAtom(String atomType, Point2d worldcoord) {
		// TODO Auto-generated method stub

	}

	public void removeAtom(IAtom atom) {
		// TODO Auto-generated method stub
	
	}

	public IAtom getClosestAtom(Point2d worldCoord) {
		// TODO Auto-generated method stub
		return null;
	}

	public IBond getClosestBond(Point2d worldCoord) {
		// TODO Auto-generated method stub
		return null;
	}

	public IController2DModel getController2DModel() {
		return this.controllerModel;
	}

	// XXX can return null
	public IChemModel getIChemModel() {
		return this.chemModel;
	}

	public IJava2DRenderer getIJava2DRenderer() {
		return this.renderer;
	}

	public void updateView() {
		this.view.updateView();
	}

	public void mouseClickedDouble(int screenCoordX, int screenCoordY) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClickedDown(int screenCoordX, int screenCoordY) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClickedUp(int screenCoordX, int screenCoordY) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDrag(int screenCoordXFrom, int screenCoordYFrom,
			int screenCoordXTo, int screenCoordYTo) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEnter(int screenCoordX, int screenCoordY) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExit(int screenCoordX, int screenCoordY) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMove(int screenCoordX, int screenCoordY) {
		// TODO Auto-generated method stub
		
	}

}
