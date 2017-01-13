package interpreto.IHM;

import java.util.ArrayList;
import java.util.Scanner;
import interpreto.Metier.*;
import interpreto.Metier.Type.Variable;

public class CUI implements IHM {

	private AnalyseCode analyseCode;
	private ArrayList<Variable> traceVariables;
	private Scanner scEntree;
	private boolean lectureEntree;

	public CUI(String nomFichier) {
		scEntree = new Scanner(System.in);
		traceVariables = new ArrayList<>();
		analyseCode = new AnalyseCode(nomFichier, this);
		analyseCode.traiterInitialisation();
		affichVariablesTrace();
		interpreter();
	}

	public void interpreter() {
		while (analyseCode.possedeSuivant() && !analyseCode.getErreur()) {
			
			analyseCode.traiteLigneSuivante();
			if (!lectureEntree) {
				affichage();
				scEntree.nextLine();
			}
			lectureEntree = false;
		}
	}

	/**
	 * Affiche la liste des variables et demande à l'utilisateur celles qui veut
	 * suivre
	 */
	private void affichVariablesTrace() {
		String affich = "|    NOM     |   TYPE     |\n";
		for (Variable var : analyseCode.getVariables())
			affich += "| " + String.format("%-11s", var.getNom()) + "| " + String.format("%-11s", var.getType())
					+ "|\n";

		System.out.println(affich);
		System.out.println("Entrez les variables séparées par une virgule.");
		String entreeVariables = new Scanner(System.in).nextLine();
		for (String strVar : entreeVariables.split(",")) {
			Variable var = analyseCode.rechercherVariable(strVar.trim());
			if (var != null)
				traceVariables.add(var);
		}
	}

	private void affichage() {
		/*
		 * -------------------------- ----- Initialisation -----
		 * --------------------------
		 */
		boolean endData = false; // Permet de savoir si toutes les données ont
									// été affichées
		String sortie = "";
		ArrayList<String> code = analyseCode.getCode();
		String erreur = "";
		if(analyseCode.getErreur())
			erreur = "\u001B[41m";
			else
			erreur = "\u001B[42m";
		/*
		 * -------------------------- --- Fin Initialisation ---
		 * --------------------------
		 */

		/*
		 * ------------------------------------------------- ----- Affichage des
		 * entetes Code et Données -----
		 * -------------------------------------------------
		 */
		sortie += "¨¨¨¨¨¨¨¨¨¨¨" + String.format("%89s", "¨¨¨¨¨¨¨¨¨¨¨\n");
		sortie += "|  CODE   |" + String.format("%89s", "| DONNEES |\n");
		for (int i = 0; i <= 139; i++)
			if (i == 87)
				sortie += " ";
			else
				sortie += "¨"; // Bordure de début du code et données
		/*
		 * ------------------------------------------------- --- Fin Affichage
		 * des entetes Code et Données ---
		 * -------------------------------------------------
		 */

		/*
		 * --------------------------------- ------- Affichage du Code -------
		 * ---------------------------------
		 */
		sortie += "\n";
		int tailleNbLignes = (code.size() / 10);
		for (int cptLig = 0; cptLig < code.size() && cptLig < 39; cptLig++) {
			String ligne = code.get(cptLig);
			// Colorie les fonctions lire et écrire
			ligne = ligne.replaceAll("lire", "\u001B[1;33m" + "lire" + "\u001B[39m");
			ligne = ligne.replaceAll("ecrire", "\u001B[1;36m" + "ecrire" + "\u001B[39m");
			// Remplacement des tabulations par des espaces pour un affichage
			// correct
			// La largeur des tabulations dépendent des systèmes
			ligne.replaceAll("\t", "    ");
			// Definis le nombre de couleurs existant dans la ligne afin
			// d'éviter les espacements
			int nbCouleur = ligne.split("\u001B").length - 1;
		
			// Surligne la ligne actuellement interpretée
			if (analyseCode.getLigneInterpretee() == cptLig)
				sortie += erreur;
			sortie += '|' + String.format("%" + tailleNbLignes + "d", cptLig) + ' ' + ligne
			// Detecter le nombre de couleur par ligne pour rajouter de la
			// longueur
					+ String.format("%" + (83 - ligne.length() + (nbCouleur * 5.5) - 4) + "s", "|");

			if (analyseCode.getLigneInterpretee() == cptLig)
				sortie += "\u001B[0m";

			/*
			 * --------------------------------------- ------- Affichage des
			 * variables ------- ---------------------------------------
			 */
			if (cptLig == 0)
				sortie += "|    NOM     |   TYPE     |   VALEUR               |\n";
			else {
				if (analyseCode.getVariables().size() > 0) {
					if (endData) {
						for (int y = 0; y <= 51; y++)
							sortie += "¨"; // bordure de fin des données
						endData = false;
					} else {
						for (int i = 0; i < traceVariables.size(); i++) {
							if (i == cptLig - 1)
								sortie += "| " + String.format("%-11s", traceVariables.get(i).getNom()) + "| "
										+ String.format("%-11s", traceVariables.get(i).getType()) + "| "
										+ String.format("%-23s", traceVariables.get(i).getValeurActuelle()) + "|";
							if (cptLig == traceVariables.size())
								endData = true;
						}
					}
					sortie += "\n";
				} else {
					if (cptLig - 1 == 0)
						sortie += "| Aucune donnée à afficher.                        |\n";
					else if (cptLig - 1 == 1) {
						for (int i = 0; i <= 51; i++)
							sortie += "¨"; // bordure de fin des données
						sortie += "\n";
					} else
						sortie += "\n";
				}
			}
		}
		for (int i = 0; i <= 86; i++)
			sortie += "¨"; // Bordure de fin de code
		/*
		 * ------------------------------------------------ ----- Fin Affichage
		 * du Code et des Données -----
		 * ------------------------------------------------
		 */

		/*
		 * --------------------------------------- ------- Affichage de la
		 * Console ------- ---------------------------------------
		 */
		sortie += "\n¨¨¨¨¨¨¨¨¨¨¨\n";
		sortie += "| CONSOLE |\n";
		for (int i = 0; i <= 86; i++)
			sortie += "¨";
		sortie += "\n";

		for (int cptLig = 0; cptLig < analyseCode.getConsole().size(); cptLig++) // Peut
																					// afficher
																					// jusqu'à
																					// 999
																					// lignes.
			sortie += '|' + analyseCode.getConsole().get(cptLig)
					+ String.format("%" + (87 - analyseCode.getConsole().get(cptLig).length()) + "s", "|\n");
		// for (int i = 0; i <= 86; i++) sortie += "¨";
		/*
		 * --------------------------------------- ----- Fin Affichage de la
		 * Console ----- ---------------------------------------
		 */

		System.out.println(sortie);
		
	}

	public static void main(String a[]) {
		CUI cui = new CUI(a[0]);
	}

	/**
	 * Cette methode sert à lire les variables via l'interface d'entrée Retourne
	 * le texte inséré dans la console
	 */
	@Override
	public String getEntree() {
		affichage();
		lectureEntree = true;
		return scEntree.nextLine();
	}
}
