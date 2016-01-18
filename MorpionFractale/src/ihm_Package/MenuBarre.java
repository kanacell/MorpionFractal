package ihm_Package;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import engine_Package.Engine;

public class MenuBarre extends JMenuBar{
	/*
	 * ATTRIBUTS
	 */
	Fenetre fenetrePrincipale;
	Engine gameEngine;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public MenuBarre (Fenetre referenceFenetre, Engine referenceEngine){
		super();
		fenetrePrincipale = referenceFenetre;
		gameEngine = referenceEngine;
		initialisation();
	}
	/*
	 * FIN CONSTRUCTEURS
	 */
	
	/*
	 * Methodes Private de MenuBarre
	 */
	private void initialisation (){
		initialisationMenuPartie();
		initialisationMenuInfos();
	}
	private void initialisationMenuPartie (){
		JMenu partie = new JMenu("Partie");
		add(partie);
		
		JMenuItem nouvellePartie = new JMenuItem("Nouvelle Partie");
		nouvellePartie.addActionListener(new Action.popUpNouvellePartie(fenetrePrincipale, gameEngine));
		
		JMenuItem recommencer = new JMenuItem("Recommencer");
		recommencer.addActionListener(new Action.recommencer(gameEngine));
		
		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new Action.quitter());
		
		partie.add(nouvellePartie);
		partie.add(recommencer);
		partie.addSeparator();
		partie.add(quitter);
	}
	private void initialisationMenuInfos (){
		JMenu infos = new JMenu("?");
		add(infos);
		
		JMenuItem aide_regles = new JMenuItem("Afficher aide");
		JMenuItem aPropos = new JMenuItem("A Propos");
		
		infos.add(aide_regles);
		infos.add(aPropos);
	}
	/*
	 * FIN Methodes Private de MenuBarre
	 */
}
