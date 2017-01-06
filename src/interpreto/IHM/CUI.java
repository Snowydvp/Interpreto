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
		ArrayList<String> code = analyseCode.getCodeAffichage();
		ArrayList<String> resultConsole = new ArrayList<String>();
		resultConsole.add("etape1 blablabla");
		resultConsole.add("etape2");
		resultConsole.add("etape3");
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		
		

		String sRet = "¨¨¨¨¨¨¨¨¨¨¨";
		sRet += String.format("%89s", "¨¨¨¨¨¨¨¨¨¨¨\n");
		sRet += "|  CODE   |";
		sRet += String.format("%89s", "| DONNEES |\n");
		for (int i = 0; i <= 86; i++)// bordure de début de code
			sRet += "¨";
		sRet += " ";
		for (int i = 0; i <= 51; i++)// bordure de début de code
			sRet += "¨";
		sRet += "\n";
		int tailleNbLignes = 1 + code.size() / 10;
		for (int cptLig = 0; cptLig < code.size(); cptLig++) {
			// Peut afficher jusqu'à 999 lignes.
			sRet += '|' + String.format("%" + tailleNbLignes + "d", cptLig) + ' ' + code.get(cptLig)
					+ String.format("%" + (84 - code.get(cptLig).length()) + "s", "|\n");
		}
		for (int i = 0; i <= 86; i++)// Bordure de fin de code
			sRet += "¨";
		sRet += "\n¨¨¨¨¨¨¨¨¨¨¨\n";
		sRet += "| CONSOLE |\n";
		for (int i = 0; i <= 86; i++)
			sRet += "¨";
		sRet += "\n";
		for (int cptLig = 0; cptLig < resultConsole.size(); cptLig++) {
			// Peut afficher jusqu'à 999 lignes.
			sRet += '|' + resultConsole.get(cptLig)
					+ String.format("%" + (87 - resultConsole.get(cptLig).length()) + "s", "|\n");
		}
		sRet += "\n";
		for (int i = 0; i <= 86; i++)
			sRet += "¨";
		
		
		System.out.println(sRet);
	}
}
