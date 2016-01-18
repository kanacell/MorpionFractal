package object_Package;

public class Case {
	/*
	 * ATTRIBUTS
	 */
	private int numeroVainqueur;
	private int degreDeCase;
	private Plateau plateauInterneDeCase;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public Case (){
		numeroVainqueur = -1;
		degreDeCase = 0;
		plateauInterneDeCase = null;
	}
	public Case (int newDegre){
		degreDeCase = newDegre;
		numeroVainqueur = -1;
		if ( degreDeCase == 0 ){
			plateauInterneDeCase = null;
		}
		else {
			plateauInterneDeCase = new Plateau(degreDeCase);
		}
	}
	/*
	 * FIN CONSTRUCTEURS
	 */
	
	/*
	 * ACCESSEURS
	 */
	public int getNumeroVainqueur (){
		return numeroVainqueur;
	}
	public int getDegre (){
		return degreDeCase;
	}
	public Plateau getPlateau (){
		return plateauInterneDeCase;
	}
	
	public void setNumeroVainqueur (int newNumeroVainqueur){
		numeroVainqueur = newNumeroVainqueur;
	}
	public void setDegre (int newDegre){
		degreDeCase = newDegre;
	}
	/*
	 * FIN ACCESSEURS
	 */
	
	/*
	 * Methodes Public de Case
	 */
	public int getNumeroVainqueurAt (int ligne, int colonne){
		return plateauInterneDeCase.getVainqueurAt(ligne, colonne);
	}
	public void setNumeroVainqueurAt (int ligne, int colonne, int newVainqueur){
		plateauInterneDeCase.setVainqueurAt(ligne, colonne, newVainqueur);
	}
	
	public String toString (){
		String chaine_returned = "";
		chaine_returned += numeroVainqueur + "\n";
		chaine_returned += degreDeCase + "\n";
		return chaine_returned;
	}
	
}
