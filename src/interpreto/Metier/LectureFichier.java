package interpreto.Metier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LectureFichier {

	public ArrayList<String> lireFichier(String fichier) {
		Scanner sc;
		ArrayList<String> lignes = new ArrayList<>();
		try {
			sc = new Scanner(new File(fichier));
			while (sc.hasNextLine())
				lignes.add(sc.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lignes;
	}

}
