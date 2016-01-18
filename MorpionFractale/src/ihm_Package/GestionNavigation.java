package ihm_Package;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import engine_Package.Engine;

public class GestionNavigation implements MouseListener{
	/*
	 * ATTRIBUTS
	 */
	Panneau_Navigation panneauDeNavigation;
	Engine gameEngine;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public GestionNavigation (Panneau_Navigation referenceNavigation, Engine referenceEngine){
		panneauDeNavigation = referenceNavigation;
		gameEngine = referenceEngine;
	}
	/*
	 * FIN CONSTRUCTEURS
	 */
	
	public void mousePressed(MouseEvent e) {
		Point clic = e.getPoint();
		if ( estSurDezoom(clic) && gameEngine.dezoomPossible() ){
			gameEngine.dezoom();
		}
		else if ( estSurZoom(clic) && aUneCaseSelectionnee() ){
			boolean zoomDisponible = gameEngine.estZoomDisponible(panneauDeNavigation.getCaseSelectionnee());
			gameEngine.zoom(zoomDisponible);
		}
		else {
			annulerSelection();
		}
	}
	
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

	/*
	 * Methodes Private de GestionNavigation
	 */
	private boolean estSurDezoom (Point clic){
		return clic.x >= panneauDeNavigation.getCoordXZoom_Dezoom() && clic.x <= panneauDeNavigation.getCoordXZoom_Dezoom() + panneauDeNavigation.getLargeurEffective()
			&& clic.y >= panneauDeNavigation.getCoordYDezoom() && clic.y <= panneauDeNavigation.getCoordYDezoom() + panneauDeNavigation.getHauteurEffective();
	}
	private boolean estSurZoom (Point clic){
		return clic.x >= panneauDeNavigation.getCoordXZoom_Dezoom() && clic.x <= panneauDeNavigation.getCoordXZoom_Dezoom() + panneauDeNavigation.getLargeurEffective()
			&& clic.y >= panneauDeNavigation.getCoordYZoom() && clic.y <= panneauDeNavigation.getCoordYZoom() + panneauDeNavigation.getHauteurEffective();
	}
	
	private boolean aUneCaseSelectionnee (){
		return gameEngine.caseSelectionnee() != null;
	}
	private void annulerSelection (){
		gameEngine.setCaseSelectionnee(null);
	}
}
