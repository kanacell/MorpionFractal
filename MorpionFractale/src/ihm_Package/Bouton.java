package ihm_Package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class Bouton extends JButton {
	/*
	 * ATTRIBUTS
	 */
	Graphics2D crayon;
	int largeur, hauteur;
	String name;
	/*
	 * FIN ATTRIBUTS
	 */
	
	/*
	 * CONSTRUCTEURS
	 */
	public Bouton (String newName){
		super(newName);
		name = newName;
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
		
		crayon.setColor(Color.black);
		crayon.drawString(name, largeur/2 - 3, hauteur/2 + 3);
	}
	
}
