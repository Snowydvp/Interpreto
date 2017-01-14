package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe permettant la lecture du fichier passer en parametre
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class LectureFichier {

	private ArrayList<String> code;
	File fichier;

	/**
	 * Constructeur permettant la lecture et l'enregistrement du fichier
	 * 
	 * @param nomFichier
	 *            chemin d'accee du fichier
	 */
	private LectureFichier(String nomFichier) {
		this.fichier = new File(nomFichier);
		code = lireFichier();
	}

	/**
	 * Methode permettant la creation de LectureFichier, Methode utile pour
	 * savoir si le fichier passer en parametre est valide
	 * 
	 * @param nomFichier
	 *            chemin d'accee du fichier
	 * @return null si le nomFichier est invalide sinon retourne LectureFichier
	 */
	public static LectureFichier creerLectureFichier(String nomFichier) {
		if (nomFichier.endsWith(".algo"))
			return new LectureFichier(nomFichier);
		return null;
	}

	/**
	 * Methode permettant la sauvegarde du fichier dans une ArrayList de String
	 * 
	 * @return une ArrayList de type String contenant tout le fichier
	 */
	public ArrayList<String> lireFichier() {
		Scanner sc;
		ArrayList<String> lignes = new ArrayList<>();
		try {
			sc = new Scanner(fichier);
			while (sc.hasNextLine())
				lignes.add(sc.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lignes;
	}

	/**
	 * Accesseur su code
	 * 
	 * @return l'ArrayList de code
	 */
	public ArrayList<String> getCode() {
		return this.code;
	}

}
