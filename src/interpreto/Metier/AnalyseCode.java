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

/**
 * Classe permettant l'analyse du speudocode
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class AnalyseCode {

	public ArrayList<String> code, fonctions, conditions, console;
	private ArrayList<Variable> variables;
	private Interpreter interpreteur;
	private IHM ihm;
	private int ligneInterpretee;
	// définis s'il existe une erreur sur la ligne actuellement interpretée
	private boolean erreur;

	/**
	 * Constructeur permettant l'analyse du speudoCode
	 * 
	 * @param lecture
	 *            qui est une sauvegarde du fichier .algo
	 * @param ihm
	 *            qui est l'interface
	 */
	public AnalyseCode(LectureFichier lecture, IHM ihm) {
		this.ihm = ihm;
		erreur = false;
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
	 * @param nomFichier
	 *            chemin d'accee des fonction
	 * @return une ArrayList de toute les fonctions traité par notre programme
	 */
	private ArrayList<String> construireListeMots(String nomFichier) {
		ArrayList<String> motsCles = new ArrayList<>();
		Scanner scFichier = null;
		try {
			scFichier = new Scanner(new File(nomFichier));
			while (scFichier.hasNextLine())
				motsCles.add(scFichier.nextLine().trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scFichier.close();
		}

		return motsCles;
	}

	/**
	 * Cette méthode retourne le code déjà analysé et modifié pour l'affichage
	 * 
	 * @param expression
	 *            permet de savoir si l'expression passer en parametre est une
	 *            fonction
	 * @return texte du code analysé
	 */

	private boolean estFonction(String expression) {
		for (String fonction : fonctions)
			if (expression.contains(fonction))
				return true;
		return false;
	}

	/**
	 * Methode permettant de traiter toute la partie initialisation des
	 * Variables
	 */
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
	 * @param ligneInterpretee
	 *            permet de savoir le nombre de ligne deja interprete
	 */
	private void traiterCode(int ligneInterpretee) {
		String ligne = code.get(ligneInterpretee);
		if(ligne.trim().isEmpty() || ligne.trim().substring(0, 2).equals("//"))
			return;
		else if (ligne.contains("◄—"))
			erreur = !affecterVariable(ligne);
		

		else if (estFonction(ligne)) {
				Object o = traiterFonction(ligne);
				if (o.getClass() == Boolean.class)
					erreur = !(boolean) o;
			} else
				// Si l'expression ne constitue pas une fonction
				try {
				interpreteur.eval(ligne);
				} catch (EvalError e) {
				// erreur = !expression.contains("FIN");
				erreur = true;
				}

		
	}

	/**
	 * Methode recursive qui interprète toutes les fonctions
	 * 
	 * @param ligne
	 *            ligne actuelle en cours de traitement
	 * @return un objet de type String, Double, Integer,...
	 */
	private Object traiterFonction(String declaration) {

		declaration = declaration.trim();
		String fonction = declaration.substring(0, declaration.indexOf('('));
		String parametre = declaration.substring(declaration.indexOf('(') + 1, declaration.lastIndexOf(')'));
		switch (fonction) {
		case "lire":
			return lire(parametre);
		case "ecrire":
			return ecrire(parametre);
		case "enRéel":
			return Fonction.enReel(parametre);
		case "enChaine":
			return Fonction.enChaine(parametre);
		case "enEntier":
			return Fonction.enEntier(parametre);
		case "ord":
			return Fonction.ord(parametre);
		case "plancher":
			return Fonction.plancher(parametre);
		case "plafond":
			return Fonction.plafond(parametre);
		case "arrondi":
			return Fonction.arrondi(parametre);
		case "aujourd'hui":
			return Fonction.aujourdhui();
		case "jour":
			return Fonction.jour();
		case "mois":
			return Fonction.mois();
		case "annee":
			return Fonction.annee();
		case "estRéel":
			return Fonction.estReel(parametre);
		case "estEntier":
			return Fonction.estEntier(parametre);
		case "hasard":
			return Fonction.hasard(parametre);
		case "car":
			return Fonction.car(parametre);
		}

		return false;
	}

	/**
	 * Methode permettant la gestion de la fonction ecrire
	 * 
	 * @param parametre
	 *            parametre contenu dans la fonction ecrire analyser dans le
	 *            speudo code
	 * @return un boolean si l'analyse du ecrire a fonctionner
	 */
	private boolean ecrire(String parametre) {
		String strSortie = "";
		String[] chaines = parametre.split("&"); // Gère la concaténation
		for (String chaine : chaines) {
			// Cas ou ce qui est à afficher contitue une fonction
			if (chaine.contains("(") && chaine.contains(")") && estFonction(chaine)) {
				Object o = traiterFonction(chaine.substring(0, chaine.lastIndexOf(')') + 1));
				if (o == null)
					return false;
				if (o.getClass() == Boolean.class)
					strSortie += Booleen.getBooleen((String) (o + ""));
				else
					strSortie += (String) (o + "");
			}

			// Cas où ce qui est à afficher
			// constitue une chaine de
			// caractères
			else if (chaine.lastIndexOf('"') != chaine.indexOf('"'))
				// On vérifie si deux guillements existent
				strSortie += chaine.substring(chaine.indexOf('"') + 1, chaine.lastIndexOf('"'));

			else if (rechercherVariable(chaine.trim()) != null)// Cas où ce qui
																// est à
																// afficher est
																// une variable
			{
				Variable var = rechercherVariable(chaine.trim());
				strSortie += var.getValeurActuelle();
			} else {
				try {
					interpreteur.eval("result=" + chaine);
					strSortie += interpreteur.get("result");
				} catch (EvalError e) {
					return false;
				}
			}
		}
		console.add(strSortie);
		return true;
	}

	/**
	 * Methode permettant la gestion de la fonction lire
	 * 
	 * @param parametre
	 *            parametre contenu dans la fonction lire analyser dans le
	 *            speudo code
	 * @return un boolean si l'analyse du lire a fonctionner
	 */
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

	/**
	 * Methode permettant l'affectation d'une valeur dans une Variable
	 * 
	 * @param ligne
	 *            ligne en cours d'analyse
	 * @return un boolean si l'affectation a reussi
	 */
	private boolean affecterVariable(String ligne) {
		String nomVariable = ligne.substring(0, ligne.indexOf("◄—")).trim();
		String valeur = ligne.substring(ligne.indexOf("◄—") + 2).trim();

		for (Variable var : variables)
			if (var.getNom().equals(nomVariable)) {
				Variable v = rechercherVariable(nomVariable);
				if (v.modifierValeur(valeur)) {
					if (v.getType().equals("booleen"))
						valeur = Booleen.getBooleen(valeur);
				}
					try {
						
						interpreteur.eval(nomVariable + "=" + valeur);
					} catch (EvalError e) {
						e.printStackTrace();
					}
				
				return true;
			}

		return false;
	}

	/**
	 * Methode permettant la declaration des Variables
	 * 
	 * @param ligne
	 *            ligne en cours d'analyse
	 * @return un boolean si la declaration a reussi
	 */
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
			variables.add((Variable) getType(valeur).getConstructors()[1].newInstance(nomVariable, true, valeur));
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
	 * @param val
	 * @return le type de la valeur
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

	/**
	 * Accesseur de Variable
	 * 
	 * @return l'ArrayList de variables
	 */
	public ArrayList<Variable> getVariables() {
		return this.variables;
	}

	/**
	 * Accesseur de console
	 * 
	 * @return L'arrayList de console
	 */
	public ArrayList<String> getConsole() {
		return this.console;
	}

	/**
	 * Methode permettant de savoir si il reste des lignes a analyser
	 * 
	 * @return un boolean
	 */
	public boolean possedeSuivant() {
		return !code.get(this.ligneInterpretee).contains("FIN");
	}

	/**
	 * Methode permettant de traiter la ligne suivante
	 */
	public void traiteLigneSuivante() {
		traiterCode(++ligneInterpretee);
	}

	/**
	 * Accesseur permettant de connaitre a quel ligne nous en sommes
	 * 
	 * @return le numero de la ligne a laquelle nous sommes
	 */
	public int getLigneInterpretee() {
		return this.ligneInterpretee;
	}

	/**
	 * Accesseur de conditions
	 * 
	 * @return l'ArrayList de conditions
	 */
	public ArrayList<String> getMotsCles() {
		return this.conditions;
	}

	/**
	 * Accesseur de code
	 * 
	 * @return l'ArrayList de code
	 */
	public ArrayList<String> getCode() {
		return this.code;
	}

	/**
	 * Accesseur de erreur
	 * 
	 * @return la valeur de erreur
	 */
	public boolean getErreur() {
		return this.erreur;
	}

	/**
	 * Methode permettant la recherche d'une Variable par son nom
	 * 
	 * @param nom
	 *            nom de la variable
	 * @return la ligne actuellement traitée par l'interpreteur
	 */
	public Variable rechercherVariable(String nom) {
		for (Variable var : variables)
			if (var.getNom().equals(nom))
				return var;
		return null;
	}

}
