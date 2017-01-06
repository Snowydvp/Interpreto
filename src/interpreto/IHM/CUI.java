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
		ArrayList<String> var = new ArrayList<>();
		var.add("x");
		var.add("entier");
		var.add("5");
		ArrayList<String> var2 = new ArrayList<>();
		var2.add("y");
		var2.add("entier");
		var2.add("7");
		data.add(var);
		data.add(var2);
		
		

		String sRet = "¨¨¨¨¨¨¨¨¨¨¨";
		sRet += String.format("%89s", "¨¨¨¨¨¨¨¨¨¨¨\n");
		sRet += "|  CODE   |";
		sRet += String.format("%89s", "| DONNEES |\n");
		for (int i = 0; i <= 86; i++)// bordure de début de code
			sRet += "¨";
		sRet += " ";
		for (int i = 0; i <= 51; i++)// bordure de début des données
			sRet += "¨";
		
		/*for(int i = 0; i < data.size();i++){
			sRet += "";
		}*/
		
		boolean endData = false;
		
		sRet += "\n";
		int tailleNbLignes = 1 + code.size() / 10;
		for (int cptLig = 0; cptLig < code.size(); cptLig++) {
			// Peut afficher jusqu'à 999 lignes.
			sRet += '|' + String.format("%" + tailleNbLignes + "d", cptLig) + ' ' + code.get(cptLig)
					+ String.format("%" + (84 - code.get(cptLig).length()) + "s", "| ");
			if(cptLig == 0)
				sRet += "|    NOM     |   TYPE     |   VALEUR               |\n";
			else{
				if(data.size()>0){
					for(int i = 0 ; i < data.size() ; i++){
						if(i==cptLig-1)
							sRet+= "| " + String.format("%-11s", data.get(i).get(0)) + "| " + String.format("%-11s", data.get(i).get(1)) + "| " + String.format("%-23s", data.get(i).get(2)) + "|";
						/*else if(i+1==data.size())
							for (int y = 0; y <= 51; y++)// bordure de fin des données
								sRet += "¨";*/
						else
							sRet+="\n";
						if(cptLig+1==data.size())
							endData=true;
					}
					/*if(endData)
						for (int y = 0; y <= 51; y++)// bordure de fin des données
							sRet += "¨";*/
				}else{
					if(cptLig-1==0){
						sRet+="| Aucune donnée à afficher.                        |\n";
					}else if(cptLig-1==1){
						for (int i = 0; i <= 51; i++)// bordure de fin des données
							sRet += "¨";
						sRet+="\n";
					}else{
						sRet+="\n";
					}
				}
			}
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
