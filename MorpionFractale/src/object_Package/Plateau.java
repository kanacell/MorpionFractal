package object_Package;

public class Plateau {

	private static int nbMaxLignes_Colonnes = 3;
	
	/*
	 * ATTRIBUTS
	 */
	int numeroVainqueur;
	int degre;
	Case[][] plateau = new Case[nbMaxLignes_Colonnes][nbMaxLignes_Colonnes];
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public Plateau (){
		numeroVainqueur = -1;
		degre = 1;
		for (int indexLigne = 0; indexLigne < plateau.length; indexLigne++){
			for (int indexColonne = 0; indexColonne < plateau.length; indexColonne++){
				plateau[indexLigne][indexColonne] = new Case();
			}
		}
	}
	public Plateau (int newDegre){
		System.out.println("=== DEBUT plateau de degre : " + degre);
		numeroVainqueur = -1;
		degre = newDegre;
		if ( newDegre == 0 ){
			for (int indexLigne = 0; indexLigne < plateau.length; indexLigne++){
				for (int indexColonne = 0; indexColonne < plateau.length; indexColonne++){
					plateau[indexLigne][indexColonne] = new Case();
				}
			}
		}
		else {
			for (int indexLigne = 0; indexLigne < plateau.length; indexLigne++){
				for (int indexColonne = 0; indexColonne < plateau.length; indexColonne++){
					plateau[indexLigne][indexColonne] = new Case(newDegre-1);
				}
			}
		}
		System.out.println("=== FIN plateau de degre : " + degre);
	}
	/*
	 * FIN CONSTRUCTEURS
	 */
	
	/*
	 * Methodes Public de Plateau
	 */
	public int length (){
		return plateau.length;
	}
	public int getVainqueur (){
		return numeroVainqueur;
	}
	public int getDegre (){
		return degre;
	}
	public int getDegreAt (int ligne, int colonne){
		return plateau[ligne][colonne].getDegre();
	}
	public int getVainqueurAt (int ligne, int colonne){
		return plateau[ligne][colonne].getNumeroVainqueur();
	}
	public Plateau getPlateauAt (int ligne, int colonne){
		return plateau[ligne][colonne].getPlateau();
	}
	public Case getCaseAt (int ligne, int colonne){
		return plateau[ligne][colonne];
	}
	
	public boolean possedeVainqueur (){
		return numeroVainqueur != -1;
	}
	
	public void setVainqueurAt (int ligne, int colonne, int numeroVainqueur){
		plateau[ligne][colonne].setNumeroVainqueur(numeroVainqueur);
	}
	public void setCaseAt (int ligne, int colonne){
		plateau[ligne][colonne] = new Case();
	}
	public void updateGagnant (int numeroJoueurCourant){
//		System.out.println("\n ===== update ===== ");
		if ( plateauNul() ){
			numeroVainqueur = 2;
		}
		else {
			for (int index = 0; index < plateau.length; index++){
				if ( ligneGagnante(index) == numeroJoueurCourant || colonneGagnante(index) == numeroJoueurCourant ){
					numeroVainqueur = numeroJoueurCourant;
					break;
				}
			}
			
			if ( diagonaleGagnante1() == numeroJoueurCourant || diagonaleGagnante2() == numeroJoueurCourant ){
				numeroVainqueur = numeroJoueurCourant;
			}
		}
//		System.out.println(" ===== FIN update ===== \n");
	}
	
	public String toString (){
		String chaine_returned = "";
		for (int indexLigne = 0; indexLigne < plateau.length; indexLigne++){
			for (int indexColonne = 0; indexColonne < plateau.length; indexColonne++){
				chaine_returned += plateau[indexLigne][indexColonne].getNumeroVainqueur() + " ";
			}
			chaine_returned += "\n";
		}
		return chaine_returned;
	}
	/*
	 * FIN Methodes Public de Plateau
	 */
	
	/*
	 * Methodes Private de Plateau
	 */
	private int ligneGagnante (int numeroLigne){
		int gagnantDeLigne = -1;
		if ( plateau[numeroLigne][0].getNumeroVainqueur() == plateau[numeroLigne][1].getNumeroVainqueur() 
		&& 	 plateau[numeroLigne][1].getNumeroVainqueur() == plateau[numeroLigne][2].getNumeroVainqueur() ){
			gagnantDeLigne = plateau[numeroLigne][1].getNumeroVainqueur();
		}
		return gagnantDeLigne;
	}
	private boolean ligneComplete (int numeroLigne){
		return plateau[numeroLigne][0].getNumeroVainqueur() != -1 && plateau[numeroLigne][1].getNumeroVainqueur() != -1 && plateau[numeroLigne][2].getNumeroVainqueur() != -1;
	}
	private int colonneGagnante (int numeroColonne){
		int gagnantDeColonne = -1;
		if ( plateau[0][numeroColonne].getNumeroVainqueur() == plateau[1][numeroColonne].getNumeroVainqueur()
		&&	 plateau[1][numeroColonne].getNumeroVainqueur() == plateau[2][numeroColonne].getNumeroVainqueur() ){
			gagnantDeColonne = plateau[1][numeroColonne].getNumeroVainqueur();
		}
		return gagnantDeColonne;
	}
	private boolean colonneComplete (int numeroColonne){
		return plateau[0][numeroColonne].getNumeroVainqueur() != -1 && plateau[1][numeroColonne].getNumeroVainqueur() != -1 && plateau[2][numeroColonne].getNumeroVainqueur() != -1;
	}
	private int diagonaleGagnante1 (){
		int gagnantDiagonale = -1;
		if ( plateau[0][0].getNumeroVainqueur() == plateau[1][1].getNumeroVainqueur() && plateau[1][1].getNumeroVainqueur() == plateau[2][2].getNumeroVainqueur() ){
			gagnantDiagonale = plateau[1][1].getNumeroVainqueur();
		}
		return gagnantDiagonale;
	}
	private int diagonaleGagnante2 (){
		int gagnantDiagonale = -1;
		if (plateau[2][0].getNumeroVainqueur() == plateau[1][1].getNumeroVainqueur() && plateau[1][1].getNumeroVainqueur() == plateau[0][2].getNumeroVainqueur() ){
			gagnantDiagonale = plateau[1][1].getNumeroVainqueur();
		}
		return gagnantDiagonale;
	}
	private boolean diagonaleComplete (){
		return plateau[0][0].getNumeroVainqueur() != -1 && plateau[1][1].getNumeroVainqueur() != -1 && plateau[2][2].getNumeroVainqueur() != -1 
			&& plateau[2][0].getNumeroVainqueur() != -1 && plateau[0][2].getNumeroVainqueur() != -1;
	}
	private boolean plateauNul (){
		boolean estComplet = true;
		int indexLigne = 0, indexColonne = 0;
		
		while ( indexLigne < plateau.length && estComplet ){
			estComplet = estComplet && ligneComplete(indexLigne) && ligneGagnante(indexLigne) == -1;
			indexLigne++;
		}
		while ( indexColonne < plateau.length && estComplet ){
			estComplet = estComplet && colonneComplete(indexColonne) && colonneGagnante(indexColonne) == -1;
			indexColonne++;
		}
		
		estComplet = estComplet && diagonaleComplete() && diagonaleGagnante1() == -1 && diagonaleGagnante2() == -1;
		
		return estComplet;
	}
	/*
	 * FIN Methodes Private de Plateau
	 */
	
	/*
	 * Methodes de TEST
	 */
	public void initBidonDegre1 (){
		plateau[0][0].setNumeroVainqueur(0);
		plateau[0][2].setNumeroVainqueur(1);
		plateau[2][1].setNumeroVainqueur(2);
	}
	public void initVainqueurRond (){
		numeroVainqueur = 0;
		plateau[0][0].setNumeroVainqueur(0);
		plateau[1][1].setNumeroVainqueur(0);
		plateau[2][2].setNumeroVainqueur(0);
		plateau[0][1].setNumeroVainqueur(1);
		plateau[2][1].setNumeroVainqueur(1);
	}
	public void initVainqueurCroix (){
		numeroVainqueur = 1;
		plateau[0][1].setNumeroVainqueur(1);
		plateau[1][1].setNumeroVainqueur(1);
		plateau[2][1].setNumeroVainqueur(1);
		plateau[0][0].setNumeroVainqueur(0);
		plateau[2][2].setNumeroVainqueur(0);
	}
	public void initVainqueurNul (){
		numeroVainqueur = 2;
		plateau[0][0].setNumeroVainqueur(0);
		plateau[0][1].setNumeroVainqueur(1);
		plateau[0][2].setNumeroVainqueur(1);
		
		plateau[1][0].setNumeroVainqueur(1);
		plateau[1][1].setNumeroVainqueur(0);
		plateau[1][2].setNumeroVainqueur(0);
		
		plateau[2][0].setNumeroVainqueur(1);
		plateau[2][1].setNumeroVainqueur(0);
		plateau[2][2].setNumeroVainqueur(0);
	}
	
}
