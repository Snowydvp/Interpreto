package interpreto;

import interpreto.IHM.*;

/**
 * Classe Controleur permettant de faire le lien entre Metier et IHM
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Controleur {
	/**
	 * Constructeur par defaut
	 */
	public Controleur() {
		// new GUI();
		new CUI("src/codes/test_encodage.algo");
	}

	/**
	 * Methode permettant le lancement du programme
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new Controleur();
	}
}