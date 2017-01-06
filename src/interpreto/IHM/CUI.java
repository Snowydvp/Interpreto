package interpreto.IHM;

import java.util.ArrayList;
import interpreto.Metier.*;

public class CUI {
	
	private AnalyseCode analyseCode;
	
	public CUI() {
		analyseCode = new AnalyseCode("codes/fichierdemerde.txt");
		affichage("codes/fichierdemerde.txt");
	}

	public void affichage(String nomFichier) {
		ArrayList<String> code = analyseCode.getCodeAnalyse();
		ArrayList<String> codeBrut = analyseCode.getCodeBrut();
		

		String sRet = "-----------";
		sRet += String.format("%89s", "-----------\n");
		sRet += "|  CODE   |";
		sRet += String.format("%89s", "| DONNEES |\n");
		for (int i = 0; i <= 85; i++)// bordure de d�but de code
			sRet += "-";
		sRet += "\n";
		int tailleNbLignes = 1 + code.size() / 10;
		for (int cptLig = 0; cptLig < code.size(); cptLig++) {
			// Peut afficher jusqu'à 999 lignes.
			sRet += '|' + String.format("%" + tailleNbLignes + "d", cptLig) + ' ' + code.get(cptLig)
					+ String.format("%" + (84 - codeBrut.get(cptLig).length()) + "s", "|\n");
		}
		for (int i = 0; i <= 85; i++)// Bordure de fin de code
			sRet += "-";

		System.out.println(sRet);
	}
}
