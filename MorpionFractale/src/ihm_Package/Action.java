package ihm_Package;

import ihm_Package.popUp_Package.PopUp_NouvellePartie;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import engine_Package.Engine;

public class Action {
	
	public static class popUpNouvellePartie implements ActionListener {
		Fenetre fenetrePrincipale;
		Engine gameEngine;
		public popUpNouvellePartie (Fenetre referenceFenetre, Engine referenceEngine){
			fenetrePrincipale = referenceFenetre;
			gameEngine = referenceEngine;
		}
		public void actionPerformed (ActionEvent e) {
			System.out.println("Nouvelle Partie");
			PopUp_NouvellePartie endGame = new PopUp_NouvellePartie(fenetrePrincipale, gameEngine.messageVainqueur(), gameEngine);
//			gameEngine.nouvellePartie(gameEngine.getDegrePrincipal());
		}
	}
	
	public static class recommencer implements ActionListener {
		Engine gameEngine;
		public recommencer (Engine referenceEngine){
			gameEngine = referenceEngine;
		}
		public void actionPerformed (ActionEvent e){
			gameEngine.nouvellePartie(gameEngine.getDegrePrincipal());
		}
	}
	
	public static class quitter implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.exit(0);
		}
	}
	
	public static class incrDegre implements ActionListener {
		JTextField champText;
		public incrDegre (JTextField referenceChampText){
			champText = referenceChampText;
		}
		public void actionPerformed (ActionEvent e){
			try {
				int valeur = Integer.parseInt(champText.getText());
				champText.setText( String.valueOf(valeur+1) );
			} catch (Exception error){}
		}
	}
	
	public static class decrDegre implements ActionListener {
		JTextField champText;
		public decrDegre (JTextField referenceChampText){
			champText = referenceChampText;
		}
		public void actionPerformed (ActionEvent e){
			try {
				int valeur = Integer.parseInt(champText.getText());
				if ( valeur > 1 ){
					champText.setText(String.valueOf(valeur-1));
				}
			} catch (Exception error){}
		}
	}

	public static class lancerNouvellePartie implements ActionListener {
		private Engine gameEngine;
		private PopUp_NouvellePartie popUpOuverte;
		private JTextField champTextuel;
		public lancerNouvellePartie (Engine referenceEngine, PopUp_NouvellePartie referencePopUp, JTextField referenceChampTextuel ){
			gameEngine = referenceEngine;
			popUpOuverte = referencePopUp;
			champTextuel = referenceChampTextuel;
		}
		public void actionPerformed (ActionEvent e){
			System.out.println("degreSelectionne : " + champTextuel.getText());
			gameEngine.nouvellePartie( Integer.parseInt(champTextuel.getText()) );
			System.out.println("nouvelle partie lancee");
			popUpOuverte.dispose();
		}
	}
	
	public static class fermerPopup implements ActionListener {
		private PopUp_NouvellePartie popUpOuverte;
		public fermerPopup ( PopUp_NouvellePartie referencePopUp ){
			popUpOuverte = referencePopUp;
		}
		
		public void actionPerformed (ActionEvent e){
			popUpOuverte.dispose();
		}
	}
	
}
