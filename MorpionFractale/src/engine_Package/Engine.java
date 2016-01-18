package engine_Package;

import ihm_Package.Panneau_Jeu;
import ihm_Package.Panneau_Navigation;

import java.awt.Point;
import java.util.EmptyStackException;
import java.util.Stack;

import object_Package.Case;
import object_Package.Plateau;

public class Engine {
	/*
	 * ATTRIBUTS
	 */
	private Plateau plateauCourant;
	private Panneau_Jeu panneauDeJeu;
	private Panneau_Navigation panneauDeNavigation;

	private int joueurCourant;
	private int degrePrincipal;

	private Stack<Plateau> pileDePlateauxPrecedents;
	private Stack<Point> pileCouranteCoordonnees;
	private Stack<Point> pilePrecedentesCoordonnees;
	private Stack<Point> pileTampon;
	private Point caseSelectionnee;

	private boolean zoomActif;
	private int degreZoom;
	private int degreMarqueZoomInvalide;
	private int vainqueur = -1;
	/*
	 * FIN ATTRIBUTS
	 */

	/*
	 * CONSTRUCTEURS
	 */
	public Engine (){
//		System.out.println("\n ===== CONSTRUCTEUR Engine sans parametre ===== ");
		plateauCourant = new Plateau();
		degrePrincipal = 0;
		initialisation();
//		System.out.println(" ===== FIN CONSTRUCTEUR Engine sans parametre ===== \n");
	}
	public Engine (Plateau referencePlateau){
//		System.out.println("\n ===== CONSTRUCTEUR Engine avec Plateau en parametre ===== ");
		plateauCourant = referencePlateau;
		degrePrincipal = plateauCourant.getDegre();
		initialisation();
//		System.out.println(" ===== FIN CONSTRUCTEUR Engine avec Plateau en parametre ===== \n");
	}
	/*
	 * FIN CONSTRUCTEURS
	 */

	/*
	 * ACCESSEURS
	 */
	public int getDegre (){
		return plateauCourant.getDegre();
	}
	public int getDegreAt (int ligne, int colonne){
		return plateauCourant.getDegreAt(ligne, colonne);
	}
	
	public int getDegreZoom (){
		return degreZoom;
	}
	
	public int getDegrePrincipal (){
		return degrePrincipal;
	}
	public int getVainqueur (){
		return vainqueur;
	}
	public int getJoueurCourant (){
		return joueurCourant;
	}
	
	public Point getPositionForcee (){
		if ( !pileCouranteCoordonnees.isEmpty() ){
			return pileCouranteCoordonnees.peek();
		}
		else {
			return null;
		}
	}
	public Point caseSelectionnee (){
		return caseSelectionnee;
	}
	public Plateau getPlateauCourant (){
		return plateauCourant;
	}
	public Plateau getPlateauAt (int ligne, int colonne){
		return plateauCourant.getPlateauAt(ligne, colonne);
	}
	public Case getCaseAt (int ligne, int colonne){
		return plateauCourant.getCaseAt(ligne, colonne);
	}
	
	public Stack<Point> getListeCouranteCoordonnees (){
		return pileCouranteCoordonnees;
	}
	public Stack<Point> getListePrecedentesCoordonnees (){
		return pilePrecedentesCoordonnees;
	}
	public Stack<Point> getListeTamponCoordonnees (){
		return pileTampon;
	}
	
	public boolean zoomValide (){
		return degreZoom >= 0;
	}
	public boolean zoomActif (){
		return zoomActif == true;
	}
	
	public Panneau_Jeu getPanneau (){
		return panneauDeJeu;
	}
	
	public void setCaseSelectionnee (Point newCase){
		caseSelectionnee = newCase;
		panneauDeJeu.setCaseSelectionnee(caseSelectionnee);
		panneauDeJeu.repaint();
		panneauDeNavigation.setCaseSelectionnee(caseSelectionnee);
	}
	public void setPanneauDeJeu(Panneau_Jeu referencePanneauJeu){
		panneauDeJeu = referencePanneauJeu;
	}
	public void setPanneauDeNavigation (Panneau_Navigation referencePanneauNavigation){
		panneauDeNavigation = referencePanneauNavigation;
		panneauDeNavigation.setDegreActuel(plateauCourant.getDegre());
		panneauDeNavigation.repaint();
	}
	/*
	 * FIN ACCESSEURS
	 */
	
	/*
	 * Methodes Public de Engine
	 */
/***	Methodes de Tests du Degre du Plateau	***/
	public boolean plateauSimple (){
		return plateauCourant.getDegre() == 1;
	}
	public boolean plateauDouble (){
		return plateauCourant.getDegre() == 2;
	}
	public boolean plateauSuperieur (){
		return plateauCourant.getDegre() > 2;
	}
	public boolean plateauPrincipal (){
		return false;
	}
	
/***	Methodes de Tests de position du Clic	***/
	public boolean positionLibre (){
		return pileCouranteCoordonnees.isEmpty();
	}
	public boolean zoomPossible (){
		return plateauCourant.getDegre() > 2;
	}
	public boolean dezoomPossible (){
		return !estPlateauPrincipal();
	}
	public boolean estZoomDisponible (Point caseCible){
		boolean actionOk = false;

		if ( pileCouranteCoordonnees.isEmpty() ){
			actionOk = true;
		}
		else {
			Point caseTheoriqueObligatoire = pileCouranteCoordonnees.peek();
			if ( caseCible.equals(caseTheoriqueObligatoire) && plateauCourant.getVainqueurAt(caseTheoriqueObligatoire.x, caseTheoriqueObligatoire.y) == -1 ){
				System.out.println("case cible identique a case obligatoire et plateau cible sans vainqueur");
				actionOk = true;
			}
			else if ( !caseCible.equals(caseTheoriqueObligatoire) && plateauCourant.getVainqueurAt(caseTheoriqueObligatoire.x, caseTheoriqueObligatoire.y) != -1 ){
				System.out.println("case cible differente a case obligatoire et plateau cible avec un vainqueur");
				actionOk = true;
			}
		}
		return actionOk;
	}

/***	Methodes de Gestion du Zoom/Dezoom		***/
/*	
	public void zoom (int ligne, int colonne){
		System.out.println("degre de zoom Valide_Invalide AVANT ZOOM : " + panneauDeJeu.getDegreZoomValide_Invalide() );
		
		pileDePlateauxPrecedents.push(plateauCourant);
		plateauCourant = plateauCourant.getPlateauAt(ligne, colonne);
		zoomActif = true;
		transfertZOOM();
		if ( zoomValide() ){
			System.out.println("zoom valide");
			plateauInterdit_Correct(1);
		}
		else {
			System.out.println("zoom invalide");
			plateauInterdit_Correct(-1);
		}
		panneauDeJeu.setPlateau(plateauCourant);
		panneauDeJeu.repaint();
		
		panneauDeNavigation.setDegreActuel(plateauCourant.getDegre());
		panneauDeNavigation.repaint();
		System.out.println("degre de zoom Valide_Invalide APRES ZOOM : " + panneauDeJeu.getDegreZoomValide_Invalide() );
		System.out.println();
	}
*/
/*
	public void zoom (){
		System.out.println("degre de zoom Valide_Invalide AVANT ZOOM : " + panneauDeJeu.getDegreZoomValide_Invalide() );
		
		pileDePlateauxPrecedents.push(plateauCourant);
		plateauCourant = plateauCourant.getPlateauAt(caseSelectionnee.y, caseSelectionnee.x);
		zoomActif = true;
		transfertZOOM();
		if ( zoomValide() ){
			panneauDeJeu.updateDegreValiditeZoom(1);
		}
		else {
			panneauDeJeu.updateDegreValiditeZoom(-1);
		}
		panneauDeJeu.setPlateau(plateauCourant);
		panneauDeJeu.repaint();
		
		panneauDeNavigation.setDegreActuel(plateauCourant.getDegre());
		panneauDeNavigation.repaint();
		System.out.println("degre de zoom Valide_Invalide APRES ZOOM : " + panneauDeJeu.getDegreZoomValide_Invalide() );
		System.out.println();
	}
*/
	public void zoom (boolean disponibiliteZoom){
		System.out.println("\n ===== DEBUT ZOOM ===== ");
		System.out.println("\t degre de zoom Valide_Invalide AVANT ZOOM : " + panneauDeJeu.getValiditeZoom() );
		
		pileDePlateauxPrecedents.push(plateauCourant);
		plateauCourant = plateauCourant.getPlateauAt(caseSelectionnee.y, caseSelectionnee.x);
		zoomActif = true;
		transfertZOOM();
		
		if ( zoomValide() && disponibiliteZoom ){
			System.out.println("\t zoom OK");
			degreZoom = degreZoom + 1;
			degreMarqueZoomInvalide = plateauCourant.getDegre()-1;
		}
		else if ( zoomValide() && !disponibiliteZoom ){
			System.out.println("\t inversion de la valeur");
			panneauDeJeu.setDegreCasesAutoriseesNonVisibles(plateauCourant.getDegre());
			degreZoom = -1* degreZoom - 1;
			degreMarqueZoomInvalide = plateauCourant.getDegre();
		}
		else if ( !zoomValide() ){
			System.out.println("\t zoom toujours invalide");
			degreZoom = degreZoom - 1;
		}
		
		panneauDeJeu.setDegreCasesAutoriseesNonVisibles(degreMarqueZoomInvalide);
		
		panneauDeJeu.setValiditeZoom(degreZoom);
		disableSelection();
		
		System.out.println("\t degreZoom : " + degreZoom);
		System.out.println("\t degreZoom de PanneauDeJeu : " + panneauDeJeu.getValiditeZoom());
		
		panneauDeJeu.setPlateau(plateauCourant);
		panneauDeJeu.repaint();
		
		panneauDeNavigation.setDegreActuel(plateauCourant.getDegre());
		panneauDeNavigation.repaint();
		System.out.println("\t degre de zoom Valide_Invalide APRES ZOOM : " + panneauDeJeu.getValiditeZoom() );
		System.out.println(" ===== FIN ZOOM ===== \n");
	}
	public void dezoom (){
		System.out.println("\n ===== DEBUT DEZOOM ===== ");
		System.out.println("degre de zoom Valide_Invalide AVANT DEZOOM : " + panneauDeJeu.getValiditeZoom() );
		
//		if ( pileDePlateauxPrecedents.size() > 0 ){
			plateauCourant = pileDePlateauxPrecedents.pop();
			if ( !pileTampon.isEmpty() ){
				pileTampon.pop();
			}

			for (int indexLigne = 0; indexLigne < plateauCourant.length(); indexLigne++){
				for (int indexColonne = 0; indexColonne < plateauCourant.length(); indexColonne++){
					int transfertVainqueur = plateauCourant.getVainqueurAt(indexLigne, indexColonne);
					plateauCourant.setVainqueurAt(indexLigne, indexColonne, transfertVainqueur);
				}
			}
			if ( plateauCourant.getDegre() > degreMarqueZoomInvalide && degreZoom < 0){
				degreZoom = -1 * degreZoom;
				panneauDeJeu.setValiditeZoom(degreZoom);				
			}
			disableSelection();

			panneauDeJeu.setPlateau(plateauCourant);
			panneauDeJeu.repaint();
			
			appliquerDezoomAtDegre();
			
			transfertDEZOOM();
			
			panneauDeNavigation.setDegreActuel(plateauCourant.getDegre());
			panneauDeNavigation.repaint();
//		}
		zoomActif = false;
		
		panneauDeJeu.reverseDegreZoom(1);
		
		System.out.println("degre de zoom Valide_Invalide APRES DEZOOM : " + panneauDeJeu.getValiditeZoom() );
		System.out.println(" ===== FIN DEZOOM ===== \n");
	}
	
	public void disableSelection (){
		caseSelectionnee = null;
		panneauDeJeu.setCaseSelectionnee(null);
	}
	
/***	Methodes de Gestion de Coordonnees		***/
	public void ajouterCoordonnees (Point caseClic){
//		System.out.println("\t === AjouterCoordonnees === ");
		pileTampon.push(caseClic);
//		System.out.println("\t === FIN AjouterCoordonnees === \n");
	}
	public void supprimerdCoordonnees (){
		pileTampon.pop();
	}

/***	Methode pour Finaliser une Action		***/
	public void finaliserAction (){
		System.out.println("\n ===== DEBUT FINALISER Action ===== ");
		System.out.println("degre de zoom Valide_Invalide AVANT FINALISER ACTION : " + panneauDeJeu.getValiditeZoom() );
		System.out.println();
		
		pileCouranteCoordonnees.clear();
		pilePrecedentesCoordonnees.clear();
		
		panneauDeJeu.setDegreCasesAutoriseesNonVisibles(degrePrincipal-1);
		panneauDeJeu.inverserDegreZoom();
		degreZoom = -1 * degreZoom;
		degreMarqueZoomInvalide = degrePrincipal;
		
		System.out.println("pileTampon SIZE : " + pileTampon.size());
		while ( !pileTampon.isEmpty() ){
			pileCouranteCoordonnees.push(pileTampon.pop());
		}
		updateCasesAutorisees();
		
		System.out.println("degre de zoom Valide_Invalide APRES FINALISER ACTION : " + panneauDeJeu.getValiditeZoom() );
		System.out.println("\n ===== FIN FINALISER Action ===== ");
		System.out.println();
	}

/***	Methodes pour Jouer un Tour				***/
	public void jouerTour (int ligne, int colonne) throws Exception {
		System.out.println("\n ===== JouerTour ===== ");
		System.out.println("joueurCourant : " + joueurCourant);

		if ( plateauCourant.getVainqueurAt(ligne, colonne) == -1 && plateauCourant.getVainqueur() == -1){
			plateauCourant.setVainqueurAt(ligne, colonne, joueurCourant);
			plateauCourant.updateGagnant(joueurCourant);
			panneauDeJeu.repaint();
			joueurCourant = (joueurCourant+1) % 2;
			panneauDeNavigation.repaint();
			
			if ( estPlateauPrincipal() && plateauCourant.possedeVainqueur() ){
				vainqueur = plateauCourant.getVainqueur();
				throw new Exception();
			}
		}
		resetSelection();
		System.out.println("joueur gagnant : " + plateauCourant.getVainqueur());

		System.out.println(" ===== FIN JouerTour ===== \n");
	}
	public boolean jouerTour (int ligne, int colonne, int ligneInterne, int colonneInterne) throws Exception{
				System.out.println("\n ===== JouerTour sur 4 arguments ===== ");
		boolean tourOk = false;
		if ( plateauCourant.getVainqueur() == -1 && plateauCourant.getVainqueurAt(ligne, colonne) == -1 ){
			Plateau plateauInterne = plateauCourant.getPlateauAt(ligne, colonne);
			if ( plateauInterne.getVainqueur() == -1 && plateauInterne.getVainqueurAt(ligneInterne, colonneInterne) == -1 ){
				plateauInterne.setVainqueurAt(ligneInterne, colonneInterne, joueurCourant);
				plateauInterne.updateGagnant(joueurCourant);
				plateauCourant.setVainqueurAt(ligne, colonne, plateauInterne.getVainqueur());
				
				tourOk = true;
				plateauCourant.updateGagnant(joueurCourant);
				joueurCourant = (joueurCourant+1)%2;
				panneauDeJeu.repaint();
				
				panneauDeNavigation.repaint();
				
				if ( estPlateauPrincipal() && plateauCourant.possedeVainqueur() ){
					vainqueur = plateauCourant.getVainqueur();
					throw new Exception();
				}
			}
			System.out.println("vainqueur de plateauCourant : " + plateauCourant.getVainqueur());
		}
				System.out.println(" ===== FIN JouerTour sur 4 arguments ===== \n");
		return tourOk;
	}

/***	Methode pour afficher le vainqueur		***/
	public String messageVainqueur (){
		String message;
		if ( getVainqueur() != 2 ){
			message = "VICTOIRE DU JOUEUR " + (getVainqueur()+1);
		}
		else {
			message = "MATCH NUL";
		}
		return message;
	}

/***	Methodes pour Nouvelle Partie			***/
	public void nouvellePartie (int degreNouvellePartie){
		plateauCourant = new Plateau(degreNouvellePartie);
		degrePrincipal = degreNouvellePartie;
		initialisation();
		panneauDeJeu.setPlateau(plateauCourant);
		panneauDeJeu.nouvellePartie();
		panneauDeJeu.repaint();
		panneauDeNavigation.nouvellePartie(degreNouvellePartie);
	}
	/*
	 * FIN Methodes Public de Engine
	 */

	/*
	 * Methodes Private de Engine
	 */
	private boolean estPlateauPrincipal (){
		return pileDePlateauxPrecedents.isEmpty();
	}
	
	private void initialisation (){
		joueurCourant = 0;
		pileDePlateauxPrecedents = new Stack<Plateau>();
		pileCouranteCoordonnees = new Stack<Point>();
		pilePrecedentesCoordonnees = new Stack<Point>();
		pileTampon = new Stack<Point>();
		caseSelectionnee = null;
		zoomActif = false;
		degreZoom = 0;
	}
	
	private void resetSelection (){
		caseSelectionnee = null;
	}
	private void updateCasesAutorisees (){
		panneauDeJeu.clearCasesAutorisees();
		if ( !pileCouranteCoordonnees.isEmpty() ){
			Point destinationTheorique = pileCouranteCoordonnees.peek();
			System.out.println("destination theorique : " + destinationTheorique);
			if ( plateauCourant.getVainqueurAt(destinationTheorique.x, destinationTheorique.y) == -1 ){
				System.out.println("cible theorique autorisee");
				panneauDeJeu.addCasesAutorisees(destinationTheorique);
			}
			else {	// La case theoriquement obligatoire a deja un vainqueur
				System.out.println("cible theorique deja prise");
				for (int indexLigne = 0; indexLigne < plateauCourant.length(); indexLigne++){
					for (int indexColonne = 0; indexColonne < plateauCourant.length(); indexColonne++){
						if ( plateauCourant.getVainqueurAt(indexLigne, indexColonne) == -1 ){
							panneauDeJeu.addCasesAutorisees(new Point(indexLigne, indexColonne));
						}
					}
				}
			}
		}
	}

	private void transfertZOOM (){
		if ( !pileCouranteCoordonnees.isEmpty() ){
			pilePrecedentesCoordonnees.push(pileCouranteCoordonnees.pop());
		}
		updateCasesAutorisees();
	}
	private void transfertDEZOOM (){
		if ( !pilePrecedentesCoordonnees.isEmpty() ){
			pileCouranteCoordonnees.push(pilePrecedentesCoordonnees.pop());
			updateCasesAutorisees();
		}
	}
	private void appliquerDezoomAtDegre (){
		if ( degreZoom > 0 ){
			degreZoom--;
		}
		else if ( degreZoom < 0 ){
			degreZoom++;
		}
	}
	
}
