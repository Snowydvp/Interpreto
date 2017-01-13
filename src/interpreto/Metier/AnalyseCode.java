package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Scanner;
import bsh.EvalError;
import bsh.Interpreter;
import interpreto.IHM.IHM;
import interpreto.Metier.Type.BOOLEEN;
import interpreto.Metier.Type.CARACTERE;
import interpreto.Metier.Type.Variable;
import sun.reflect.Reflection;

public class AnalyseCode {

	private ArrayList<String> code, fonctions, conditions, console;
	private ArrayList<Variable> variables;
	private Interpreter interpreteur;
	private IHM ihm;
	private int ligneInterpretee;
	// définis s'il existe une erreur sur la ligne actuellement interpretée
	private boolean erreur;

	public AnalyseCode(String fichier, IHM ihm) {
		this.ihm = ihm;
		ligneInterpretee = 0;
		interpreteur = new Interpreter();
		console = new ArrayList<>();
		code = new LectureFichier(fichier).getCode();
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
		int i = 1;
		while (!code.get(i).contains("DEBUT")) {
			String ligne = code.get(i);
			
			
		 if (ligne.contains("constante:")) {
			System.out.println("constante detectée");
			String declaration = code.get(i++);
			while (!declaration.contains("DEBUT") && !declaration.contains("variable:")) {
				/*if (declaration.contains("◄—")) // declarer constante
					declarerConstante(declaration);*/
				declaration = code.get(i++);
			}
			
		 }else if (ligne.contains("variable:")) {
				System.out.println("variable detectée");
				// Un programme ne possédant pas de variable, n'aura pas la
				// ligne variable:
				String declaration = code.get(++i);
				while (!declaration.contains("DEBUT") && !declaration.contains("constante:")) {
					if (declaration.contains(":"))
						declarerVariable(declaration);
					declaration = code.get(i++);
				}

			}
		 i++;
		}
		this.ligneInterpretee = i;
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
		// Dans le cas ou une seule expression est autorise par ligne
		ligne = ligne.trim();

		String parametre = ligne.substring(ligne.indexOf('(') + 1, ligne.lastIndexOf(')'));
		if (ligne.contains("lire")) {
			Variable var = rechercherVariable(parametre);
			return var != null && lire(var);

		} else if (ligne.contains("ecrire")) {
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
		}

		return true;
	}

	private boolean lire(Variable var) {
		boolean erreurEntree = false;
		String entree = ihm.getEntree();
		if (var.getType().equals("chaine"))
			entree = '"' + entree + '"';
		else if (var.getType().equals("caractere"))
			entree = "'" + entree + "'";

		if (!var.modifierValeur(entree))
			// Gerer les exceptions autrement
			erreurEntree = true;

		// On laisse ce qu'as rentré l'utilisateur dans la console
		console.add(entree);

		return erreurEntree;
	}

	private boolean affecterVariable(String ligne) {
		String nomVariable = ligne.substring(0, ligne.indexOf("◄—")).trim();
		String valeur = ligne.substring(ligne.indexOf("◄—") + 2).trim();

		for (Variable var : variables)
			if (var.getNom().equals(nomVariable)) {
				Variable v = rechercherVariable(nomVariable);
				if (v.modifierValeur(valeur)) {
					if (v.getType().equals("booleen"))
						valeur = BOOLEEN.getBoolean(valeur);
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
		String type = ligne.substring(ligne.indexOf(':') + 1);
		String nomsVariable[] = ligne.substring(0, ligne.indexOf(':')).split(",");
		// instancier et enregistrer la variable

		for (String nom : nomsVariable) {
			// verification de la non-existence de la variable
			for (Variable varExist : variables)
				if (varExist.getNom().equals(nom.trim()))
					return false;
			try {
				variables.add((Variable) Class.forName("interpreto.Metier.Type." + type.trim().toUpperCase())
						.getConstructors()[0].newInstance(nom.trim()));
				interpreteur.eval(nom);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
					| InvocationTargetException | SecurityException | EvalError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
		}
		return false;
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
		for (Class<?> c : Package.getPackage("interpreto.Metier.Type").getClass().getClasses())
			if (c.getSimpleName().equals("Variable")) {
				try {
					Variable v = (Variable) c.getConstructors()[0].newInstance("null");
					if (v.modifierValeur(val))
						return v.getClass();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | SecurityException e) {
					e.printStackTrace();
				}
			}

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
