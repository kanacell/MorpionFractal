package ihm_Package.popUp_Package;

import ihm_Package.Action;
import ihm_Package.Bouton;
import ihm_Package.Fenetre;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine_Package.Engine;

public class PopUp_NouvellePartie extends JDialog{
	/*
	 * ATTRIBUTS
	 */
	Engine gameEngine;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEUR
	 */
	public PopUp_NouvellePartie (Fenetre referenceFenetre, String newMessage, Engine referenceEngine){
		super(referenceFenetre, "Nouvelle Partie");
		gameEngine = referenceEngine;
		setSize(300, 130);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout());
		PanelDegre selectionDegre = new PanelDegre();
		PanelValidation validationSelection = new PanelValidation(selectionDegre, this);
		add( selectionDegre, BorderLayout.NORTH );
		add( validationSelection, BorderLayout.CENTER );

		setVisible(true);
	}
	/*
	 * FIN CONSTRUCTEUR
	 */
	
	
	/*
	 * Classes Private de PopUp_NouvellePartie
	 */
	private class PanelDegre extends JPanel {
		JFormattedTextField champTextuel;
		
		/*
		 * CONSTRUCTEUR
		 */
		public PanelDegre (){
			setLayout( new FlowLayout() );
//			champTextuel = new JFormattedTextField(gameEngine.getDegrePrincipal() ); //, String.valueOf(gameEngine.getDegre()));
			
			champTextuel = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
			champTextuel.setValue(gameEngine.getDegrePrincipal());
			
			champTextuel.setHorizontalAlignment(JTextField.CENTER);
			champTextuel.setPreferredSize(new Dimension(2*20, 2*20));
			
			JLabel label = new JLabel("Degre choisi : ");
			add( label );
			add( champTextuel );
			add( boutonsIncrDecr_Degre(champTextuel) );
		}
		/*
		 * FIN CONSTRUCTEUR
		 */
		
		public JTextField getChampTextuel (){
			return champTextuel;
		}
		
		private JPanel boutonsIncrDecr_Degre (JTextField referenceChampTextuel){
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 1));
			
			Bouton boutonIncr = new Bouton("+");
			Bouton boutonDecr = new Bouton(" -");
			int dim = 20;
			Dimension dimBouton = new Dimension(dim, dim);
			boutonIncr.setPreferredSize(dimBouton);
			boutonDecr.setPreferredSize(dimBouton);
			
			boutonIncr.addActionListener(new Action.incrDegre(referenceChampTextuel));
			boutonDecr.addActionListener(new Action.decrDegre(referenceChampTextuel));
			
			panel.add( boutonIncr );
			panel.add( boutonDecr );
			return panel;
		}
	}
	
	private class PanelValidation extends JPanel {
		/*
		 * CONSTRUCTEUR
		 */
		public PanelValidation ( PanelDegre referencePanelDegre, PopUp_NouvellePartie referencePopUp ){
			setLayout(new FlowLayout());
			initBoutons(referencePanelDegre, referencePopUp);
		}
		/*
		 * FIN CONSTRUCTEUR
		 */
		
		private void initBoutons ( PanelDegre referencePanelDegre, PopUp_NouvellePartie referencePopUp ){
			JButton validation = new JButton("Ok");
			JButton annulation = new JButton("Annuler");
			
			validation.addActionListener(new Action.lancerNouvellePartie(gameEngine, referencePopUp, referencePanelDegre.getChampTextuel()));
			annulation.addActionListener(new Action.fermerPopup(referencePopUp));
			
			add( validation );
			add( annulation );
		}
	}

}
