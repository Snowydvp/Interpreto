package interpreto.Metier;

import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseCode {

	ArrayList<String> codeBrut;
	ArrayList<String> codeAnalyse;

	public AnalyseCode(String fichier) {
		codeBrut = new LectureFichier(fichier).getCode();
		codeAnalyse = new ArrayList<>(codeBrut);
		//colorierPrimitives();
	}

	private ArrayList<String> colorierPrimitives(ArrayList<String> mots) {
		ArrayList<String> motsColor = new ArrayList<>();
		for (String mot : mots) {
			if (estMotCle(mot)) {
				// coloriage de la primitive en bleu
				mot = "\u001B[1;36m" + mot + "\u001B[0m";
				traiter(ligneBrut.substring(ligneBrut.indexOf(motBrut), ligneBrut.lastIndexOf(')') + 1));
			} else
				motAnalyse += motBrut;
			ligneAnalyse += motAnalyse + ' ';
		}
	}

	private ArrayList<String> getMots() {
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			String ligneAnalyse = "";
			String ligneBrut = codeBrut.get(cptLig);
			// recherher un moyen de decouper en gardant les espaces

			Scanner scLigne = new Scanner(ligneBrut);
			scLigne.useDelimiter("[ \t]");
			while (scLigne.hasNext()) {
				String motBrut = scLigne.next();
				String motAnalyse = "";

				

			}

			codeAnalyse.set(cptLig, ligneAnalyse);
		}
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
		mot = mot.trim();
		if (mot.equalsIgnoreCase("ecrire") || mot.equalsIgnoreCase("lire"))
			return true;
		return false;
	}

	/**
	 * Cette méthode retourne le code déjà analysé et modifié pour l'affichage
	 * 
	 * @return texte du code analysé
	 */
	public ArrayList<String> getCodeAnalyse() {
		return codeAnalyse;
	}

	public ArrayList<String> getCodeBrut() {
		return this.codeBrut;
	}

	public void traiter(String motCle) {
		System.out.println(motCle);
	}

}
