package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectureFichier {
	
	private ArrayList<String> code;
	File fichier;
	
	//A l'appel du constructeur, le fichier est lu et enregistr�
	public LectureFichier(String nomFichier)
	{
		code = new ArrayList<>();
		this.fichier = new File(nomFichier);
		if(nomFichier.endsWith(".algo"))
			code = lireFichier();
		else
			System.out.println("Erreur: le nom de fichier doit avoir pour extension \".algo\".");
	}

	public ArrayList<String> lireFichier() {
		Scanner sc;
		ArrayList<String> lignes = new ArrayList<>();
		try {
			sc = new Scanner(fichier);
			while (sc.hasNextLine())
				lignes.add(sc.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lignes;
	}
	/**
	 * 
	 * @return Texte du code
	 */
	public ArrayList<String> getCode()
	{
		return this.code;
	}

}
