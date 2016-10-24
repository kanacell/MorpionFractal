package ihm_Package;

import ihm_Package.popUp_Package.PopUp_PartieTermine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import engine_Package.Engine;

public class GestionJeu implements MouseListener{
	/*
	 * ATTRIBUTS
	 */
	private Panneau_Jeu panneauDeJeu;
	private Engine gameEngine;
	/*
	 * FIN ATTRIBUTS
	 */

	/*
	 * CONSTRUCTEURS
	 */
	public GestionJeu (Panneau_Jeu referencePanneau, Engine referenceEngine){
		panneauDeJeu = referencePanneau;
		gameEngine = referenceEngine;
	}
	/*
	 * FIN CONSTRUCTEURS
	 */

	public void mousePressed(MouseEvent e) {
		System.out.println(" ===== DEBUT Clic de Souris ===== ");
		System.out.println("position Obligatoire : " + gameEngine.getPositionForcee() );
		System.out.println("degre de validite : " + gameEngine.getDegreZoom() );

		if ( e.getButton() == MouseEvent.BUTTON1 ){
			Point positionClic = e.getPoint();

			int ligne = positionClic.y / panneauDeJeu.getDimCase();
			int colonne = positionClic.x / panneauDeJeu.getDimCase();
			Point caseClicked = new Point(ligne, colonne);
			
			/* ================================	*/
			/* PLATEAU COURANT AVEC DEGRE == 1	*/
			/* ================================	*/
			if ( gameEngine.plateauSimple() ){
				try{
					gameEngine.jouerTour(ligne, colonne);
					gameEngine.ajouterCoordonnees(caseClicked);
					gameEngine.finaliserAction();
				} catch (Exception exception){
					System.out.println(gameEngine.messageVainqueur());
					
					PopUp_PartieTermine fenetreFinale = new PopUp_PartieTermine(gameEngine.messageVainqueur(), gameEngine);
					fenetreFinale.afficher();
				}
				
			}
			/* ================================	*/
			/* PLATEAU COURANT AVEC DEGRE > 1	*/
			/* ================================	*/
			else {
				int ligneInterne = coordonneInterne(positionClic.y, ligne);
				int colonneInterne = coordonneInterne(positionClic.x, colonne);

				if ( ligneInterne != -1 && colonneInterne != -1 ){
					Point caseInternClicked = new Point (ligneInterne, colonneInterne);
					
					if ( gameEngine.zoomActif() && gameEngine.zoomValide() ){
						if ( gameEngine.caseSelectionnee() == null ){
							gameEngine.ajouterCoordonnees(caseClicked);
						}
						else if ( !caseClicked.equals(gameEngine.caseSelectionnee()) && gameEngine.zoomValide() )  {
							gameEngine.supprimerdCoordonnees();
							gameEngine.ajouterCoordonnees(caseClicked);
						}
//						System.out.println("");
					}
			/* ================================	*/
			/* PLATEAU COURANT AVEC DEGRE == 2	*/
			/* ================================	*/
					if ( gameEngine.plateauDouble() ){
						if ( gameEngine.zoomValide() && gameEngine.estZoomDisponible(caseClicked) ){
							System.out.println(" === POSITION AUTORISEE === ");
/*							if ( gameEngine.getCaseSelectionnee() == null || !caseClicked.equals(gameEngine.getCaseSelectionnee()) ){
								gameEngine.setCaseSelectionnee(caseClicked);
							}
							else {
*/								try {
									if ( gameEngine.jouerTour(ligne, colonne, ligneInterne, colonneInterne) ){
										System.out.println("TOUR VALIDE");
										gameEngine.ajouterCoordonnees(caseInternClicked);
										gameEngine.finaliserAction();
										gameEngine.disableSelection();
									}
									System.out.println("caseClicked : " + caseClicked);
								} catch (Exception exception) {
									System.out.println();
									System.out.println(gameEngine.messageVainqueur());
									System.out.println();

									PopUp_PartieTermine fenetreFinale = new PopUp_PartieTermine(gameEngine.messageVainqueur(), gameEngine);
									fenetreFinale.afficher();

								}
//							}
						}
						else {
							System.out.println();
							System.out.println("\t validite du zoom : " + gameEngine.zoomValide());
							System.out.println("\t position Autorisee : " + gameEngine.estZoomDisponible(caseClicked));
							System.out.println("\t === POSITION REFUSEE === \n");
						}
						System.out.println("liste coordonnees apres ajout (s'il y a eu) : " + gameEngine.getListeCouranteCoordonnees());
					}
			/* ================================	*/
			/* PLATEAU COURANT AVEC DEGRE > 2	*/
			/* ================================	*/
					else if ( gameEngine.plateauSuperieur() ){		
						if ( selectionIdentique(caseClicked) ){
							System.out.println("\n\t === SELECTION IDENTIQUE === ");
							boolean zoomDisponible = gameEngine.estZoomDisponible(caseClicked);
							System.out.println("zoomAutorise : " + zoomDisponible);
							gameEngine.zoom(zoomDisponible);
//							gameEngine.disableSelection();
						}
						else {
							updateSelection(caseClicked);
						}
					}
				}
			}
			System.out.println();
			System.out.println("fin traitement clic gauche");
			System.out.println("\t === TOUR SUIVANT === ");
		}
		else if ( e.getButton() == MouseEvent.BUTTON3 ){
			System.out.println();
			if ( gameEngine.dezoomPossible() ){
				System.out.println("degreValide_Incorret AVANT : " + panneauDeJeu.getValiditeZoom() );
				gameEngine.dezoom();
				System.out.println(" == DEZOOM EFFECTUE == ");
				System.out.println("liste coordonnees apres dezoom : " + gameEngine.getListeCouranteCoordonnees());
			}
			else {
				System.out.println(" == DEZOOM ANNULE == ");
			}
			System.out.println();
		}

		System.out.println();
		System.out.println("ListeTampon : " + gameEngine.getListeTamponCoordonnees() );
		System.out.println("lisetCoordonneesPrecedentes : " + gameEngine.getListePrecedentesCoordonnees() );
		System.out.println("ListeCouranteCoordonnees : " + gameEngine.getListeCouranteCoordonnees() );

		System.out.println(" ===== FIN Clic de Souris ===== \n");
	}

	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	/*
	 * Methodes Private de GestionGame
	 */
	private int coordonneInterne (int coordClic, int decalageCase){
		int ligne_colonne = -1;
		int borneGauche_Haute = decalageCase*panneauDeJeu.getDimCase() + panneauDeJeu.getDecalageInterne();
		int borneDroite_Basse = borneGauche_Haute + panneauDeJeu.getDimCaseInterne();
		
		for (int indexCase = 0; indexCase < gameEngine.getPlateauCourant().length() && ligne_colonne == -1 ; indexCase++){
			if ( coordClic >= borneGauche_Haute && coordClic <= borneDroite_Basse ){
				ligne_colonne = indexCase;
			}
			else {
				borneGauche_Haute = borneDroite_Basse + 1;
				borneDroite_Basse = borneGauche_Haute + panneauDeJeu.getDimCaseInterne();
			}
		}
		
		
		return ligne_colonne;
	}
	private void updateSelection(Point newCaseSelected) {
		System.out.println("\n\t === UPDATE SELECTION === ");
		gameEngine.setCaseSelectionnee(newCaseSelected);
	}

	private boolean selectionIdentique(Point caseClicked) {
		return caseClicked.equals(gameEngine.caseSelectionnee());
	}
}
