package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AnalyseCode {

	ArrayList<String> codeBrut;
	ArrayList<String> codeAnalyse;
	ArrayList<String> fonctions;

	public AnalyseCode(String fichier) {
		codeBrut = new LectureFichier(fichier).getCode();
		fonctions = getMotsFonction();
		codeAnalyse = getMotsCouleur();
		for(String ligne : codeBrut)
			traiterCode(ligne);
	}

	/**
	 * Parcours tout les mots existant dans le code, et colorie les mots-clés
	 * 
	 * @return nouvelle liste des mots coloriés
	 */
	private ArrayList<String> getMotsCouleur() {
		ArrayList<String> codeCouleur = new ArrayList<>();
		for (int cptLig = 0; cptLig < codeBrut.size(); cptLig++) {
			for (String motcle : this.fonctions) {
				if (codeCouleur.size() <= cptLig)
					// on colorie pour la premiere fois la ligne
					codeCouleur.add(cptLig,
							codeBrut.get(cptLig).replaceAll(motcle, "\u001B[1;36m" + motcle + "\u001B[0m"));
				else
					// on reprend la ligne deja coloriée afin de colorier les
					// autres mots-clés
					codeCouleur.set(cptLig,
							codeCouleur.get(cptLig).replaceAll(motcle, "\u001B[1;36m" + motcle + "\u001B[0m"));

			}
		}
		return codeCouleur;
	}

	/**
	 * Construit la liste de toutes les fonctions a partir du fichier texte
	 * 
	 * @return
	 */
	private ArrayList<String> getMotsFonction() {
		ArrayList<String> motsCles = new ArrayList<>();
		Scanner scFichier = null;
		try {
			scFichier = new Scanner(new File("src/interpreto/Metier/fonctions.txt"));
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
	
	public boolean estFonction(String expression)
	{
		for(String fonction : fonctions)
			if(expression.contains(fonction))
				return true;
		return false;
	}

	/**
	 * Cette méthode recursive interprète le code
	 * @param motCle
	 */
	public void traiterCode(String ligne) {
		Scanner sc = new Scanner(ligne);
		while(sc.hasNext())
		{
			String expression = sc.next();
			if(estFonction(expression))
			{
				System.out.println("fonction : "+expression);
			}
		}
		sc.close();
	}

	// test de cette classe
	public static void main(String arg[]) {
		AnalyseCode an = new AnalyseCode("codes/fichierdemerde.txt");
		for (String lignes : an.codeAnalyse)
			System.out.println(lignes);
	}
}
