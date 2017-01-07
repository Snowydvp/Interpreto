package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseCode {

	ArrayList<String> codeBrut;
	ArrayList<String> codeAnalyse;
	ArrayList<String> motCles;

	public AnalyseCode(String fichier) {
		codeBrut = new LectureFichier(fichier).getCode();
		motCles = getMotsCles();
		codeAnalyse = getMotsCouleur();

	}

	/**
	 * Parcours tout les mots existant dans le code, et colorie les mots-clés
	 * 
	 * @return nouvelle liste des mots coloriés
	 */
	private ArrayList<String> getMotsCouleur() {
		ArrayList<String> codeCouleur = new ArrayList<>();
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			for (String motcle : this.motCles) {
				if (codeCouleur.size() <= cptLig)
					//on colorie pour la premiere fois la ligne
					codeCouleur.add(cptLig,
							codeBrut.get(cptLig).replaceAll(motcle, "\u001B[1;36m" + motcle + "\u001B[0m"));
				else
					//on reprend la ligne deja colorie
					codeCouleur.set(cptLig,
							codeCouleur.get(cptLig).replaceAll(motcle, "\u001B[1;36m" + motcle + "\u001B[0m"));

			}
		}
		return codeCouleur;
	}

	/**
	 * Un mot clé est un mot reconnu nativement par le pseudo-code (primitives,
	 * fonctions, conditions)
	 * 
	 * @param mot
	 * @return
	 */
	private boolean estMotCle(String mot) {
		// A modifier (utiliser enumerations, ...)
		if (mot.equalsIgnoreCase("ecrire") || mot.equalsIgnoreCase("lire"))
			return true;
		return false;
	}

	/**
	 * Construit la liste de tout les mots clés a partir du fichier texte
	 * 
	 * @return
	 */
	private ArrayList<String> getMotsCles() {
		ArrayList<String> motsCles = new ArrayList<>();
		Scanner scFichier = null;
		try {
			scFichier = new Scanner(new File("src/interpreto/Metier/Mots-clés.txt"));
			while (scFichier.hasNextLine())
				motsCles.add(scFichier.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			scFichier.close();
		}

		return motsCles;
	}

	/**
	 * Cette méthode retourne le code déjà analysé et modifié pour l'affichage
	 * 
	 * @return texte du code analysé
	 */

	public ArrayList<String> getCodeBrut() {
		return this.codeBrut;
	}

	public void traiter(String motCle) {
		System.out.println(motCle);
	}

	// test de cette classe
	public static void main(String arg[]) {
		AnalyseCode an = new AnalyseCode("codes/fichierdemerde.txt");
		for (String lignes : an.codeAnalyse)
			System.out.println(lignes);
	}
}
