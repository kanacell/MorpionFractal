package main_Package;

import javax.swing.SwingUtilities;
import ihm_Package.Fenetre;
import object_Package.Plateau;
import engine_Package.Engine;

public class ClassMain implements Runnable{

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClassMain());
	}
	
	public void run (){
//		System.out.println("\n ===== DEBUT RUN ===== ");
		int degre = 2, dimension = 600;
		Plateau plateauDeJeu = new Plateau(degre);
		
//		plateauDeJeu.initBidonDegre1();
		Engine gameEngine = new Engine(plateauDeJeu);
	
		Fenetre fenetreDeJeu = new Fenetre(dimension, "Morpion Fractale");
		fenetreDeJeu.disposition(plateauDeJeu, gameEngine);
		
//		System.out.println(" ===== FIN RUN ===== \n");
	}

}
