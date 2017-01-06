package interpreto.Metier;

import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseCode {

	ArrayList<String> codeBrut;
	ArrayList<String> codeAnalyse;

	public AnalyseCode(String fichier) {
		codeBrut = new LectureFichier(fichier).getCode();
		codeAnalyse = new ArrayList<>(codeBrut);
		colorierPrimitives();
	}

	private void colorierPrimitives() {
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			String ligneAnalyse = "";
			// recherher un moyen de decouper en gardant les espaces

			String[] ligneBrut = codeBrut.get(cptLig).split("[ \t]");
			for (int cptMot = 0; cptMot < ligneBrut.length; cptMot++) {
				String motBrut = ligneBrut[cptMot];
				String motAnalyse = "";
				if (codeBrut.indexOf(ligneBrut[cptMot]) < 0)
					motAnalyse += codeBrut.get(cptLig).charAt( codeBrut.get(cptLig).indexOf(ligneBrut[cptMot] ) - 1);

				if (estMotCle(motBrut))
					// coloriage de la primitive en bleu
					motAnalyse += "\u001B[1;36m" + motBrut + "\u001B[0m";
				else
					motAnalyse += motBrut;
				ligneAnalyse += motAnalyse + ' ';

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
		System.out.println(mot);
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

}
