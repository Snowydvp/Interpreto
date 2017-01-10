package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import bsh.EvalError;
import bsh.Interpreter;
import interpreto.Metier.Type.Variable;

public class AnalyseCode {

	ArrayList<String> codeBrut, codeAnalyse, fonctions, conditions, motsClefs;
	ArrayList<Variable> variables;
	Interpreter interpreteur;

	public AnalyseCode(String fichier) {
		interpreteur = new Interpreter();
		codeBrut = new LectureFichier(fichier).getCode();
		fonctions = construireListeMots("src/interpreto/Metier/fonctions.txt");
		conditions = construireListeMots("src/interpreto/Metier/conditions.txt");
		motsClefs = new ArrayList<>(fonctions);
		motsClefs.addAll(conditions);
		codeAnalyse = getMotsCouleur();
		variables = new ArrayList<>();

		traiterCode(codeBrut);
	}

	/**
	 * Parcours tout les mots existant dans le code, et colorie les mots-clés
	 * 
	 * @return nouvelle liste des mots coloriés
	 */
	private ArrayList<String> getMotsCouleur() {
		ArrayList<String> codeCouleur = new ArrayList<>();
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			for (String motcle : this.motsClefs) {
				if (codeCouleur.size() <= cptLig)
					// on colorie pour la premiere fois la ligne
					codeCouleur.add(cptLig,
							codeBrut.get(cptLig).replaceAll(motcle + "", "\u001B[1;36m" + motcle + "\u001B[0m"));
				else
					// on reprend la ligne deja coloriée afin de colorier les
					// autres mots-clés
					codeCouleur.set(cptLig,
							codeCouleur.get(cptLig).replaceAll(motcle + "", "\u001B[1;36m" + motcle + "\u001B[0m"));

			}
		}
		return codeCouleur;
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

	public ArrayList<String> getCodeBrut() {
		return this.codeBrut;
	}

	public ArrayList<String> getCodeAnalyse() {
		return this.codeAnalyse;
	}

	public boolean estFonction(String expression) {
		for (String fonction : fonctions)
			if (expression.contains(fonction))
				return true;
		return false;
	}

	/**
	 * Interpretation du code dans son integralite
	 * 
	 * @param motCle
	 */
	public void traiterCode(ArrayList<String> code) {
		for (int i = 0; i < code.size(); i++) {
			String ligne = code.get(i);
			Scanner scLigne = new Scanner(ligne);
			if (ligne.contains("variable:")) {
				// Un programme ne possédant pas de variable, n'aura pas la
				// ligne variable:

				String declaration = code.get(++i);

				while (!declaration.equals("DEBUT")) {
					if (declaration.contains(":"))
						declarerVariable(declaration);
					declaration = code.get(++i);
				}
			}

			while (scLigne.hasNext()) {
				String expression = scLigne.next();
				/*
				 * ----- Déclaration des variables -----
				 */

				if (estFonction(expression)) {
					// System.out.println("fonction : " + expression);
				} else if (expression.equals("◄—"))
					instancierVariable(ligne);

			}
			scLigne.close();
		}
	}
	
	public void traiterFonction(String expression)
	{
		
	}

	public void instancierVariable(String ligne) {
		String nomVariable = ligne.substring(0, ligne.indexOf("◄—"));
		String valeur = ligne.substring(ligne.indexOf("◄—") + 2);
		try {
			if (valeur.contains(nomVariable))
				interpreteur.eval(nomVariable + "=" + valeur);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void declarerVariable(String ligne) {
		// Dans le cas ou une seul affectation par ligne est autorisé
		String type = ligne.substring(ligne.indexOf(':') + 1);
		String nomsVariable[] = ligne.substring(0, ligne.indexOf(':')).split(",");
		// instancier et enregistrer la variable
		try {
			for (String nom : nomsVariable) {
				variables.add((Variable) Class.forName("interpreto.Metier.Type." + type.trim().toUpperCase())
						.getConstructors()[0].newInstance(nom.trim()));
				interpreteur.eval(nom);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
				| InvocationTargetException | SecurityException | EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Variable> getVariables()
	{
		return this.variables;
	}
	
	public String getValeurVariable(Variable var)
	{
		return "null";
	}

	// test de cette classe
	public static void main(String arg[]) {
		AnalyseCode an = new AnalyseCode("codes/fichierdemerde.txt");
		for (Variable s : an.variables)
			System.out.println(s.getNomVariable());
	}
}
