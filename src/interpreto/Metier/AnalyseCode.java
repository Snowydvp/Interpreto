package interpreto.Metier;

import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseCode {

	ArrayList<String> codeBrut;
	ArrayList<String> codeAnalyse;

	public AnalyseCode(String fichier) {
		codeBrut = new LectureFichier(fichier).getCode();
		codeAnalyse = getMotsColors(getMots());
	}

	/**
	 * Parcours tout les mots existant dans le code, et colorie les mots-clés
	 * 
	 * @param mots
	 *            liste des mots
	 * @return nouvelle liste des mots coloriés
	 */
	private ArrayList<String> getMotsColors(ArrayList<String> mots) {
		ArrayList<String> motsColor = new ArrayList<>();
		for (String mot : mots) {
			if (estMotCle(mot))
				// coloriage de la primitive en bleu
				motsColor.add("\u001B[1;36m" + mot + "\u001B[0m");
			// traiter(ligneBrut.substring(ligneBrut.indexOf(motBrut),
			// ligneBrut.lastIndexOf(')') + 1));
			else
				motsColor.add(mot);
		}
		return motsColor;
	}

	private ArrayList<String> getMots() {
		ArrayList<String> mots = new ArrayList<>();
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			// Correspond a chaque ligne
			Scanner scLigne = new Scanner(codeBrut.get(cptLig));
			scLigne.useDelimiter("[ \t]");
			while (scLigne.hasNext())
				mots.add(scLigne.next().trim());
			scLigne.close();
		}
		return mots;
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

	/**
	 * Reconstitue entièrement le texte destiné à l'affichage. Ce texte comporte
	 * donc les primitives colories,...
	 */
	public ArrayList<String> getCodeAffichage() {
		ArrayList<String> texteAffichage = new ArrayList<>();
		int indiceCodeAnalyse = 0;
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			String ligneBrut = codeBrut.get(cptLig);
			String ligneAffichage = "";
			int indChar = 0;
			for (char c : ligneBrut.toCharArray()) {
				if (c == ' ' || c == '\t')
					ligneAffichage += 't'; // a modifier en = c
				else {
					// on ajoute tout ce qui est un mot
					if (indiceCodeAnalyse < 30) {
						String mot = codeAnalyse.get(indiceCodeAnalyse++);
						ligneAffichage +=" " + mot;
						// on avance le caractère puisqu'on rajoute un mot
						// entier
						if (indChar + mot.length() < ligneBrut.length())
							c = ligneBrut.charAt(indChar + mot.length());
					}
				}
				c++;
			}
			// ajoute chaque ligne pour l'affichage
			texteAffichage.add(ligneAffichage);
		}
		return texteAffichage;
	}

	// test de cette classe
	public static void main(String arg[]) {
		AnalyseCode an = new AnalyseCode("codes/fichierdemerde.txt");
		for (String lignes : an.getCodeAffichage())
			System.out.println(lignes);
	}
}
