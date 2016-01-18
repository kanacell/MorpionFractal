package ihm_Package;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import object_Package.Plateau;
import engine_Package.Engine;

public class Fenetre extends JFrame{
	/*
	 * ATTRIBUTS
	 */
	int dimensionFenetre;
	/*
	 * FIN ATTRIBUTS
	 */

	/*
	 * CONSTRUCTEURS
	 */
	public Fenetre (int dimension, String titreFenetre){
		super(titreFenetre);
		dimensionFenetre = dimension;
		setSize(dimensionFenetre, dimensionFenetre);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setResizable(false);

		setVisible(true);
	}
	/*
	 * FIN CONSTRUCTEURS
	 */

	public void disposition (Plateau referencePlateau, Engine referenceEngine){

		MenuBarre barre = new MenuBarre(this, referenceEngine);
		setJMenuBar(barre);
		barre.setSize(barre.getPreferredSize());
		
		Panneau_Jeu panneauJeu = new Panneau_Jeu(referencePlateau, referenceEngine);
		panneauJeu.setPreferredSize(new Dimension(dimensionFenetre, dimensionFenetre));
		referenceEngine.setPanneauDeJeu(panneauJeu);
		getContentPane().add(panneauJeu, BorderLayout.CENTER);
		
		Dimension dimension_Navigation = new Dimension(70, dimensionFenetre);
		Panneau_Navigation navigation = new Panneau_Navigation(referenceEngine);
		
		referenceEngine.setPanneauDeNavigation(navigation);
		navigation.setPreferredSize(dimension_Navigation);
		
		getContentPane().add(navigation, BorderLayout.EAST);
		pack();
	}
}
