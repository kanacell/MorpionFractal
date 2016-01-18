package ihm_Package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;

import engine_Package.Engine;

public class Panneau_Navigation extends JComponent{
	/*
	 * ATTRIBUTS
	 */
	private Engine gameEngine;
	private Graphics2D crayon;
	private int largeur, hauteur;
	private int dimensionBoutonTheorique, largeurBoutonEffective, hauteurBoutonEffective;
	private int degreActuel;
	private int coordXZoom_Dezoom, coordYZoom, coordYDezoom;
	private Color boutonActif, boutonInactif;
	
	private Point caseSelectionne;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public Panneau_Navigation (Engine referenceEngine){
//		System.out.println("\n ===== CONSTRUCTEUR Navigation ===== ");
		gameEngine = referenceEngine;
		degreActuel = 0;
//		boutonActif = new Color(0, 255, 255);
		boutonActif = Color.yellow;
		boutonInactif = new Color(193, 205, 205);
		addMouseListener(new GestionNavigation(this, gameEngine));
//		System.out.println(" ===== FIN CONSTRUCTEUR Navigation ===== \n");
	}
	/*
	 * FIN CONSTRUCTEURS
	 */
	
	public void paintComponent (Graphics g){
		crayon = (Graphics2D) g;
		largeur = getWidth();
		hauteur = getHeight();
		
		crayon.setColor(Color.white);
		crayon.fillRect(0, 0, largeur, hauteur);
		
		placement();
	}
	
	/*
	 * ACCESSEURS
	 */
	public Point getCaseSelectionnee (){
		return caseSelectionne;
	}
	public int getCoordXZoom_Dezoom (){
		return coordXZoom_Dezoom;
	}
	public int getCoordYZoom (){
		return coordYZoom;
	}
	public int getCoordYDezoom (){
		return coordYDezoom;
	}
	public int getLargeurEffective (){
		return largeurBoutonEffective;
	}
	public int getHauteurEffective (){
		return hauteurBoutonEffective;
	}
	
	public void setCaseSelectionnee (Point newCase){
		caseSelectionne = newCase;
	}
	/*
	 * FIN ACCESSEURS
	 */
	
	/*
	 * METHODES Public de Panneau_Navigation
	 */
	public void setDegreActuel (int newDegre){
		degreActuel = newDegre;
	}
	public void nouvellePartie (int degreNouvellePartie){
		degreActuel = degreNouvellePartie;
		repaint();
	}
	/*
	 * FIN METHODES Public de Panneau_Navigation
	 */
	
	/*
	 * METHODES Private de Panneau_Navigation
	 */
	private void placement (){
		int decalage = 5;
		
		dimensionBoutonTheorique = (hauteur < largeur) ? hauteur : largeur;
		largeurBoutonEffective = dimensionBoutonTheorique - 2*decalage;
		hauteurBoutonEffective = dimensionBoutonTheorique-5;
		
		int coordY = (hauteur - 3*dimensionBoutonTheorique)/2;
		int coordYPrime = coordY/7;
		
		dessinerOnglet(decalage, coordYPrime, Color.black, Color.white);
		dessinerSymbole(0, decalage, coordYPrime);
		coordYPrime += largeur;
		
		dessinerOnglet(decalage, coordYPrime, Color.black, Color.white);
		dessinerSymbole(1, decalage, coordYPrime);
		
		coordXZoom_Dezoom = decalage;
		coordYDezoom = coordY;
		if ( gameEngine.dezoomPossible() ){
			dessinerOnglet(decalage, coordY, Color.black, boutonActif);
		}
		else {
			dessinerOnglet(decalage, coordY, Color.black, boutonInactif);
		}
		ecrire(15, coordY + dimensionBoutonTheorique/2 + 2, Color.black, "dezoom");
		
		coordY += largeur;
		dessinerOnglet(decalage, coordY, Color.black, Color.white);
		if ( degreActuel == 0 ){
			ecrire(dimensionBoutonTheorique/2-5, coordY + dimensionBoutonTheorique/2 + 2, Color.black, "---");
		}
		else {
			ecrire(dimensionBoutonTheorique/2-5, coordY + dimensionBoutonTheorique/2 + 2, Color.black, String.valueOf(degreActuel) );
		}
		
		coordY += largeur;
		coordYZoom = coordY;
		if ( gameEngine.zoomPossible() ){
			dessinerOnglet(decalage, coordY, Color.black, boutonActif);
		}
		else {
			dessinerOnglet(decalage, coordY, Color.black, boutonInactif);
		}
		ecrire(20, coordY + dimensionBoutonTheorique/2 + 2, Color.black, "zoom");
		
	}
	
	private void dessinerOnglet (int coordX, int coordY, Color contours, Color fond){
		dessinerFond(coordX, coordY, fond);
		dessinerContours(coordX, coordY, contours);
	}
	private void dessinerContours (int coordX, int coordY, Color couleur){
		crayon.setColor(couleur);
		crayon.drawRect(coordX, coordY, largeurBoutonEffective, hauteurBoutonEffective);
	}
	private void dessinerFond (int coordX, int coordY, Color couleur){
		crayon.setColor(couleur);
		crayon.fillRect(coordX, coordY, largeurBoutonEffective, hauteurBoutonEffective);
	}
	private void ecrire (int coordX, int coordY, Color ecriture, String chaine){
		crayon.setColor(ecriture);
		crayon.drawString(chaine, coordX, coordY);
	}
	
	private void dessinerSymbole (int numeroJoueur, int coordX, int coordY){
		switch (numeroJoueur){
		case 0 :
			dessinerRond(coordX, coordY);
			break;
		case 1 :
			dessinerCroix(coordX, coordY);
			break;
		}
		if ( numeroJoueur == gameEngine.getJoueurCourant() ){
			dessinerJoueurCourant(coordX, coordY);
		}
	}
	private void dessinerRond (int coordXTheorique, int coordYTheorique){
		int decalage = 10;

		crayon.setColor(Color.black);
		crayon.drawOval(coordXTheorique + decalage, coordYTheorique + decalage, largeurBoutonEffective - 2*decalage, hauteurBoutonEffective - 2*decalage);
		decalage++;
		crayon.drawOval(coordXTheorique + decalage, coordYTheorique + decalage, largeurBoutonEffective - 2*decalage, hauteurBoutonEffective - 2*decalage);
		decalage++;
		crayon.drawOval(coordXTheorique + decalage, coordYTheorique + decalage, largeurBoutonEffective - 2*decalage, hauteurBoutonEffective - 2*decalage);
	}
	private void dessinerCroix (int coordXTheorique, int coordYTheorique){
		int decalage = 5;
		int coordXEffective = coordXTheorique + 2*decalage;
		int coordYEffective = coordYTheorique + 2*decalage;
		
		crayon.setColor(Color.black);
		crayon.drawLine(coordXEffective, coordYEffective, largeurBoutonEffective - decalage, coordYEffective + hauteurBoutonEffective - 4*decalage);
		crayon.drawLine(coordXEffective + 1, coordYEffective, largeurBoutonEffective - decalage, coordYEffective + hauteurBoutonEffective - 4*decalage - 1);
		crayon.drawLine(coordXEffective, coordYEffective + 1, largeurBoutonEffective - decalage - 1, coordYEffective + hauteurBoutonEffective - 4*decalage);
		
		crayon.drawLine(coordXEffective, coordYEffective + hauteurBoutonEffective - 4*decalage, largeurBoutonEffective - decalage, coordYEffective);
		crayon.drawLine(coordXEffective + 1, coordYEffective + hauteurBoutonEffective - 4*decalage, largeurBoutonEffective - decalage, coordYEffective + 1);
		crayon.drawLine(coordXEffective, coordYEffective + hauteurBoutonEffective - 4*decalage - 1, largeurBoutonEffective - decalage - 1, coordYEffective);

	}
	private void dessinerJoueurCourant (int coordX, int coordY){
		crayon.setColor(Color.green);
		int decalage = 2;
		crayon.drawRect(coordX + decalage, coordY + decalage, largeurBoutonEffective - 2*decalage, hauteurBoutonEffective - 2*decalage);
		decalage++;
		crayon.drawRect(coordX + decalage, coordY + decalage, largeurBoutonEffective - 2*decalage, hauteurBoutonEffective - 2*decalage);
	}
}
