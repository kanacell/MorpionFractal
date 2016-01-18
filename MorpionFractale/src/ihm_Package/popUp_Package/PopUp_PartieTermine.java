package ihm_Package.popUp_Package;

import javax.swing.JOptionPane;

import engine_Package.Engine;

public class PopUp_PartieTermine extends JOptionPane {
	/*
	 * ATTRIBUTS
	 */
	static String nouvellePartie = "Nouvelle Partie";
	static String quitter = "Quitter";
	static Object[] options = { nouvellePartie, quitter };
	
	String message;
	Engine gameEngine;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public PopUp_PartieTermine (String newMessage, Engine referenceEngine){
		super(newMessage, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		System.out.println(" ===== CONSTRUCTEUR Pop Up ===== ");
		message = "          " + newMessage;
		gameEngine = referenceEngine;
		System.out.println(" ===== FIN CONSTRUCTEUR Pop Up ===== ");
	}
	/*
	 * FIN CONSTRUCTEURS
	 */
	
	public void afficher (){
		int reponse = showOptionDialog(null, message, "titre de la popUp", optionType, messageType, null, options, initialValue);
//		System.out.println("reponse : " + reponse);
		if ( reponse == -1 ){
			
		}
		else if ( options[reponse].equals(quitter) ){
			System.exit(0);
		}
		else if ( options[reponse].equals(nouvellePartie) ){
			gameEngine.nouvellePartie(gameEngine.getDegrePrincipal());
		}
	}
	
	

}
