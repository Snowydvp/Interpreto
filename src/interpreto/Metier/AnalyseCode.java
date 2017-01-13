package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;
import bsh.EvalError;
import bsh.Interpreter;
import interpreto.IHM.IHM;
import interpreto.Metier.Type.*;

public class AnalyseCode {

	private ArrayList<String> code, fonctions, conditions, console;
	private ArrayList<Variable> variables;
	private Interpreter interpreteur;
	private IHM ihm;
	private int ligneInterpretee;
	// définis s'il existe une erreur sur la ligne actuellement interpretée
	private boolean erreur;

	public AnalyseCode(LectureFichier lecture, IHM ihm) {
		this.ihm = ihm;
		ligneInterpretee = 0;
		interpreteur = new Interpreter();
		console = new ArrayList<>();
		code = lecture.getCode();
		fonctions = construireListeMots("src/interpreto/Metier/fonctions.txt");
		conditions = construireListeMots("src/interpreto/Metier/conditions.txt");
		variables = new ArrayList<>();

	}

	/**
	 * Construit la liste de toutes les fonctions a partir du fichier texte
	 * 
	 * @return
	 */
	private ArrayList<String> construireListeMots(String nomFichier) {
		ArrayList<String> motsCles = new ArrayList<>();
		Scanner scFichier = null;
		try {
			scFichier = new Scanner(new File(nomFichier));
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

	private boolean estFonction(String expression) {
		for (String fonction : fonctions)
			if (expression.contains(fonction))
				return true;
		return false;
	}

	public void traiterInitialisation() {
		ligneInterpretee = 1;
		String ligne = "";
		while (!code.get(ligneInterpretee).contains("DEBUT") && !erreur) {
			ligne = code.get(ligneInterpretee);

			if (ligne.contains("constante")) {
				ligne = code.get(++ligneInterpretee);
				while (!ligne.contains("DEBUT") && !ligne.contains("variable:") && !erreur) {
					if (ligne.contains("◄—")) // declarer constante
						if (!declarerConstante(ligne))
							erreur = true;
						else
							ligne = code.get(++ligneInterpretee);
				}

			} else if (ligne.contains("variable:")) {
				System.out.println("detect variable");
				ligne = code.get(++ligneInterpretee);
				while (!ligne.contains("DEBUT") && !erreur) {
					if (ligne.contains(":"))
						if (!declarerVariable(ligne))
							erreur = true;
						else
							ligne = code.get(++ligneInterpretee);
				}

			} else
				ligneInterpretee++;
		}
	}

	/**
	 * Interpretation du code dans son integralite
	 * 
	 * @param motCle
	 */
	private void traiterCode(int ligneInterpretee) {

		String ligne = code.get(ligneInterpretee);
		Scanner scLigne = new Scanner(ligne);

		if (ligne.contains("◄—"))
			erreur = affecterVariable(ligne);

		else {
			while (scLigne.hasNext()) {
				String expression = scLigne.next();

				if (estFonction(expression))
					erreur = !traiterFonction(ligne);
				else
					// Si l'expression ne constitue pas une fonction
					erreur = !expression.contains("FIN");

			}
		}
		scLigne.close();
	}

	/**
	 * Methode recursive qui interprète toutes les fonctions
	 * 
	 * @param expression
	 */
	private boolean traiterFonction(String ligne) {
		ligne = ligne.trim();
		String fonction = ligne.substring(0, ligne.indexOf('('));
		String parametre = ligne.substring(ligne.indexOf('(') + 1, ligne.lastIndexOf(')'));
		switch(fonction)
		{
		case "lire":
			return lire(parametre);
		case "ecrire":
			return ecrire(parametre);
		case "enRéel":
			return Fonction.enReel(parametre);
		}

		return false;
	}

	private boolean ecrire(String parametre) {
		String strSortie = "";
		String[] chaines = parametre.split("&"); // Gère la concaténation
		for (String chaine : chaines) {
			// Cas où ce qui est à afficher
			// constitue une chaine de
			// caractères

			if (chaine.lastIndexOf('"') != chaine.indexOf('"')) // On
																// vérifie
																// si deux
																// guillements
																// existent
				strSortie += chaine.substring(chaine.indexOf('"') + 1, chaine.lastIndexOf('"'));
			else// Cas où ce qui est à afficher est une variable
			{
				Variable var = rechercherVariable(chaine.trim());
				if (var == null)
					return false;
				strSortie += var.getValeurActuelle();
			}
		}
		console.add(strSortie);
		return true;
	}

	private boolean lire(String parametre) {
		Variable var = rechercherVariable(parametre);
		if (var == null)
			return false;
		boolean entreeCorrecte = true;
		String entree = ihm.getEntree();
		if (var.getType().equals("chaine"))
			entree = '"' + entree + '"';
		else if (var.getType().equals("caractere"))
			entree = "'" + entree + "'";

		if (!var.modifierValeur(entree))
			// Gerer les exceptions autrement
			entreeCorrecte = false;

		// On laisse ce qu'as rentré l'utilisateur dans la console
		console.add(entree);

		return entreeCorrecte;
	}

	private boolean affecterVariable(String ligne) {
		String nomVariable = ligne.substring(0, ligne.indexOf("◄—")).trim();
		String valeur = ligne.substring(ligne.indexOf("◄—") + 2).trim();

		for (Variable var : variables)
			if (var.getNom().equals(nomVariable)) {
				Variable v = rechercherVariable(nomVariable);
				if (v.modifierValeur(valeur)) {
					if (v.getType().equals("booleen"))
						valeur = Booleen.getBoolean(valeur);
					try {
						interpreteur.eval(nomVariable + "=" + valeur);
					} catch (EvalError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return true;
			}

		return false;
	}

	private boolean declarerVariable(String ligne) {
		// Dans le cas ou une seul affectation par ligne est autorisée
		String type = ligne.substring(ligne.indexOf(':') + 1).trim();
		String nomsVariable[] = ligne.substring(0, ligne.indexOf(':')).split(",");

		for (String nom : nomsVariable) {
			// verification de la non-existence de la variable
			for (Variable varExist : variables)
				if (varExist.getNom().equals(nom.trim()))
					return false;
			char typeChar[] = type.toCharArray();
			typeChar[0] = Character.toUpperCase(typeChar[0]);
			type = new String(typeChar);
			try {
				// Instancie et enregistre la variable
				variables.add((Variable) Class.forName("interpreto.Metier.Type." + type).getConstructors()[0]
						.newInstance(nom.trim()));
				interpreteur.eval(nom);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Délare une constante
	 * 
	 * @param ligne
	 *            Déclaration entière
	 * @return Erreur sur la déclaration
	 */
	private boolean declarerConstante(String ligne) {
		String nomVariable = ligne.substring(0, ligne.indexOf("◄—")).trim();
		String valeur = ligne.substring(ligne.indexOf("◄—") + 2).trim();
		for (Variable varExist : variables)
			if (varExist.getNom().equals(nomVariable))
				return false;
		try {
			variables.add((Variable) getType(valeur).getConstructors()[1].newInstance(nomVariable, true));
			interpreteur.equals(nomVariable);
			return true;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NullPointerException
				| InvocationTargetException | SecurityException e) {
			return false;
		}
	}

	/**
	 * Cette classe renvoie le type d'une valeur en évaluant son expression.
	 * 
	 * @param valeur
	 * @return
	 */
	private Class<? extends Variable> getType(String val) {
		// On teste la methode modifierValeur() de toutes les classes héritants
		// de Variables
		Variable v;
		if ((v = new Booleen("")).modifierValeur(val))
			return v.getClass();
		if ((v = new Caractere("")).modifierValeur(val))
			return v.getClass();
		if ((v = new Chaine("")).modifierValeur(val))
			return v.getClass();
		if ((v = new Entier("")).modifierValeur(val))
			return v.getClass();
		if ((v = new Reel("")).modifierValeur(val))
			return v.getClass();

		return null;
	}

	public ArrayList<Variable> getVariables() {
		return this.variables;
	}

	public ArrayList<String> getConsole() {
		return this.console;
	}

	public boolean possedeSuivant() {
		return !code.get(this.ligneInterpretee).contains("FIN");
	}

	public void traiteLigneSuivante() {
		traiterCode(++ligneInterpretee);
	}

	public int getLigneInterpretee() {
		return this.ligneInterpretee;
	}

	public ArrayList<String> getMotsCles() {
		return this.conditions;
	}

	public ArrayList<String> getCode() {
		return this.code;
	}

	public boolean getErreur() {
		return this.erreur;
	}

	/**
	 * Retourne la ligne actuellement traitée par l'interpreteur
	 */
	public Variable rechercherVariable(String nom) {
		for (Variable var : variables)
			if (var.getNom().equals(nom))
				return var;
		return null;
	}

}
