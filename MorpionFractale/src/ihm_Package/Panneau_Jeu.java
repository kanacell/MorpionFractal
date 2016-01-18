package ihm_Package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

import object_Package.Plateau;
import engine_Package.Engine;

public class Panneau_Jeu extends JComponent{
	private static int decalageQuadrillageInterne = 10;

	/*
	 * ATTRIBUTS
	 */
	private Plateau plateauDeJeu;
	private Graphics2D crayon;
	private int dimensionFenetre, dimCase, dimCaseInterne;
	private ArrayList<Point> listeCasesAutorisees = new ArrayList<Point>();
	private Point caseSelectionnee;
	private int validiteZoom;
	private int degreCasesAutoriseesNonVisibles;
	/*
	 * FIN ATTRIBUTS
	 */

	/*
	 * CONSTRUCTEURS
	 */
	public Panneau_Jeu (Plateau referencePanneau, Engine referenceEngine){
		plateauDeJeu = referencePanneau;
		addMouseListener(new GestionGame(this, referenceEngine));
		initialisation();
	}
	/*
	 * FIN CONSTRUCTEURS
	 */

	/*
	 * Methode paintComponent
	 */
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;

		dimensionFenetre = getWidth();
		dimCase = dimensionFenetre/3;
		dimCaseInterne = (dimCase-2*decalageQuadrillageInterne)/3;
		dessinerQuadrillage();
		dessinerPlateau();
	}
	/*
	 * FIN Methode paintComponent
	 */

	/*
	 * ACCESSEURS
	 */
	public int getDimCase (){
		return dimCase;
	}
	public int getDimCaseInterne (){
		return dimCaseInterne;
	}
	public int getDecalageInterne (){
		return decalageQuadrillageInterne;
	}
	public int getValiditeZoom (){
		return validiteZoom;
	}

	public void setValiditeZoom (int newValiditeZoom){
		validiteZoom = newValiditeZoom;
	}
	public void setDegreCasesAutoriseesNonVisibles (int newDegreCasesAutoriseesNonVisibles){
		degreCasesAutoriseesNonVisibles = newDegreCasesAutoriseesNonVisibles;
	}
	public void setPlateau (Plateau referencePlateau){
		plateauDeJeu = referencePlateau;
	}
	public void setCaseSelectionnee (Point newCase){
		caseSelectionnee = newCase;
	}
	/*
	 * FIN ACCESSEURS
	 */

	/*
	 * Methodes Public de Panneau
	 */
	public void ajouterSymbole (int numeroJoueur, int indexLigne, int indexColonne){
		if ( plateauDeJeu.getVainqueurAt(indexLigne, indexColonne) == -1 ){
			plateauDeJeu.setVainqueurAt(indexLigne, indexColonne, numeroJoueur);
//			repaint();
		}
	}
	public void addCasesAutorisees (Point newCase){
		listeCasesAutorisees.add(newCase);
	}
	public void clearCasesAutorisees (){
		listeCasesAutorisees.clear();
	}

	public void updateDegreValiditeZoom (int ajoutModif){
		validiteZoom += ajoutModif;
	}
	public void reverseDegreZoom (int soustractionModif){
//		degreZoomValide_Invalide -= soustractionModif;
		
		if ( validiteZoom < 0 ){
			validiteZoom += soustractionModif;
		}
		else if ( validiteZoom > 0 ){
			validiteZoom -= soustractionModif;
		}
		
	}
	public void inverserDegreZoom (){
		validiteZoom = -1 * validiteZoom;
	}

	public void nouvellePartie (){
		initialisation();
	}
	/*
	 * FIN Methodes Public de Panneau
	 */

	/*
	 * Methodes Private de Panneau
	 */
	private void dessinerQuadrillage (){
		crayon.setColor(new Color(126, 51, 0));
		for (int nbTraits = 0; nbTraits < 2; nbTraits++){
			crayon.drawLine(0, (nbTraits+1)*dimCase, dimensionFenetre, (nbTraits+1)*dimCase);
			crayon.drawLine((nbTraits+1)*dimCase, 0, (nbTraits+1)*dimCase, dimensionFenetre);
		}

	}
	private void dessinerPlateau (){
		System.out.println("\n ===== dessiner Plateau ===== ");
		System.out.println("degreCasesNonAuto : " + degreCasesAutoriseesNonVisibles);
		
		for (int indexLigne = 0; indexLigne < plateauDeJeu.length(); indexLigne++){
			for (int indexColonne = 0; indexColonne < plateauDeJeu.length(); indexColonne++){
				Point caseCourante = new Point(indexLigne, indexColonne);
				if ( validiteZoom >= 0 && estCaseAutorisee(caseCourante) 
						&& plateauDeJeu.getDegre() > degreCasesAutoriseesNonVisibles){
//					System.out.println("zoom correct");
					dessinerZoneAutorisee(indexLigne, indexColonne);
				}
				else {
/*
					System.out.println("zoom incorrect");
					System.out.println("\t degreZommValide_Invalide >= 0 : " + (degreZoomValide_Invalide >= 0) );
					System.out.println("\t estCaseAutorisee(caseCourante) : " + estCaseAutorisee(caseCourante) );
					System.out.println("\t plateauDeJeu.getDegre() > degreCasesAutoriseesNonVisibles) : " + (plateauDeJeu.getDegre() > degreCasesAutoriseesNonVisibles) );
*/
				}
				if ( caseSelectionnee != null ){
					dessinerSelection();
				}
				if ( plateauDeJeu.getDegreAt(indexLigne, indexColonne) == 0 ){
					dessinerGagnant(plateauDeJeu.getVainqueurAt(indexLigne, indexColonne), indexLigne*dimCase, indexColonne*dimCase, dimCase, Color.black);
				}
				else {
					dessinerQuadrillageInterne(indexLigne, indexColonne);
					dessinerPlateauInterne(plateauDeJeu.getPlateauAt(indexLigne, indexColonne), indexLigne, indexColonne);
					int vainqueurExistant = plateauDeJeu.getVainqueurAt(indexLigne, indexColonne);
					if (  vainqueurExistant != -1 ){
						dessinerGagnant(vainqueurExistant, indexLigne*dimCase, indexColonne*dimCase, dimCase, Color.cyan);
					}
				}
			}
		}
		System.out.println(" ===== FIN dessiner Plateau ===== \n");
	}
	private void dessinerQuadrillageInterne (int coordY, int coordX){
		crayon.setColor(new Color(126, 51, 0));
		for (int nbTraits = 0; nbTraits < 2; nbTraits++){
			crayon.drawLine(coordX*dimCase + decalageQuadrillageInterne, coordY*dimCase + (nbTraits+1)*dimCaseInterne + decalageQuadrillageInterne, (coordX+1)*dimCase - decalageQuadrillageInterne, coordY*dimCase + (nbTraits+1)*dimCaseInterne + decalageQuadrillageInterne);
			crayon.drawLine(coordX*dimCase + (nbTraits+1)*dimCaseInterne + decalageQuadrillageInterne, coordY*dimCase + decalageQuadrillageInterne, coordX*dimCase + (nbTraits+1)*dimCaseInterne + decalageQuadrillageInterne, (coordY+1)*dimCase - decalageQuadrillageInterne);
		}
	}
	private void dessinerPlateauInterne (Plateau plateauInterne, int ligneCase, int colonneCase){
		crayon.setColor(Color.black);
		for (int indexLigne = 0; indexLigne < plateauInterne.length(); indexLigne++){
			for (int indexColonne = 0; indexColonne < plateauInterne.length(); indexColonne++){
				dessinerGagnant(plateauInterne.getVainqueurAt(indexLigne, indexColonne), 
						ligneCase*dimCase + indexLigne*dimCaseInterne + decalageQuadrillageInterne + dimCaseInterne/10, 
						colonneCase*dimCase + indexColonne*dimCaseInterne + decalageQuadrillageInterne + dimCaseInterne/10, 
						dimCaseInterne - decalageQuadrillageInterne,
						Color.black);
			}
		}
	}
	private void dessinerZoneAutorisee (int coordY, int coordX){
		crayon.setColor(Color.green);
		int decalage = 3;
		crayon.drawRect(coordX*dimCase + decalage, coordY*dimCase + decalage, dimCase - 2*decalage, dimCase - 2*decalage);
		decalage++;
		crayon.drawRect(coordX*dimCase + decalage, coordY*dimCase + decalage, dimCase - 2*decalage, dimCase - 2*decalage);
		decalage++;
		crayon.drawRect(coordX*dimCase + decalage, coordY*dimCase + decalage, dimCase - 2*decalage, dimCase - 2*decalage);
	}

	private void dessinerGagnant (int numeroGagnant, int coordY, int coordX, int dimension, Color newColor){
		crayon.setColor(newColor);
		switch (numeroGagnant){
		case 0:
			dessinerRond(coordX, coordY, dimension);
			break;
		case 1:
			dessinerCroix(coordX, coordY, dimension);
			break;
		case 2:
			dessinerRond(coordX, coordY, dimension);
			dessinerCroix(coordX, coordY, dimension);
			break;
		}
	}
	private void dessinerRond (int coordX, int coordY, int dimension){
		int decalage = 1;
		int autreDecalage = dimension/10;
		crayon.drawOval(coordX + decalage + autreDecalage, coordY + decalage + autreDecalage, dimension - 2*(decalage+autreDecalage), dimension - 2*(decalage+autreDecalage));
		decalage++;
		crayon.drawOval(coordX + decalage + autreDecalage, coordY + decalage + autreDecalage, dimension - 2*(decalage+autreDecalage), dimension - 2*(decalage+autreDecalage));
		decalage++;
		crayon.drawOval(coordX + decalage + autreDecalage, coordY + decalage + autreDecalage, dimension - 2*(decalage+autreDecalage), dimension - 2*(decalage+autreDecalage));
	}
	private void dessinerCroix (int coordX, int coordY, int dimension){
		int decalage = dimension/10;
		// diagonale gauche superieure -> droite inferieure
		crayon.drawLine(coordX + 1 + decalage, coordY + 1 + decalage, coordX + dimension - 2 - decalage, coordY + dimension - 2 - decalage);
		crayon.drawLine(coordX + 2 + decalage, coordY + 1 + decalage, coordX + dimension - 2 - decalage, coordY + dimension - 3 - decalage);
		crayon.drawLine(coordX + 1 + decalage, coordY + 2 + decalage, coordX + dimension - 3 - decalage, coordY + dimension - 2 - decalage);

		// diagonale gauche inferieure -> droite superieure
		crayon.drawLine(coordX + 1 + decalage, coordY + dimension - 2 - decalage, coordX + dimension - 2 - decalage, coordY + 1 + decalage);
		crayon.drawLine(coordX + 1 + decalage, coordY + dimension - 3 - decalage, coordX + dimension - 3 - decalage, coordY + 1 + decalage);
		crayon.drawLine(coordX + 2 + decalage, coordY + dimension - 2 - decalage, coordX + dimension - 2 - decalage, coordY + 2 + decalage);
	}
	private void dessinerSelection (){
		crayon.setColor(Color.yellow);
		int decalage = 5;
		crayon.drawRect(caseSelectionnee.y*dimCase + decalage, caseSelectionnee.x*dimCase + decalage, dimCase - 2*decalage, dimCase - 2*decalage);
		decalage++;
		crayon.drawRect(caseSelectionnee.y*dimCase + decalage, caseSelectionnee.x*dimCase + decalage, dimCase - 2*decalage, dimCase - 2*decalage);
		decalage++;
		crayon.drawRect(caseSelectionnee.y*dimCase + decalage, caseSelectionnee.x*dimCase + decalage, dimCase - 2*decalage, dimCase - 2*decalage);
	}

	private boolean estCaseAutorisee (Point caseTestee){
		//		boolean caseAutorisee = false;
		return listeCasesAutorisees.isEmpty() || listeCasesAutorisees.contains(caseTestee);
		//		return caseAutorisee;
	}
	
	private void initialisation (){
		listeCasesAutorisees.clear();
		validiteZoom = 0;
		degreCasesAutoriseesNonVisibles = 0;
		caseSelectionnee = null;
	}
	/*
	 * FIN Methodes Private de Panneau
	 */
}
