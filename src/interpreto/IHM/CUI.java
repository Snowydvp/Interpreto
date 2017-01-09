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
		/* --------------------------
		   ----- Initialisation -----
		   -------------------------- */
		boolean endData = false; // Variables permettant de stocker si toutes les données ont été affichées
		String sRet = "";
		ArrayList<String> code = analyseCode.getCodeAnalyse();
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
		/* --------------------------
		   --- Fin Initialisation ---
		   -------------------------- */
		

		/* -------------------------------------------------
		   ----- Affichage des entetes Code et Données -----
		   ------------------------------------------------- */
		sRet += "¨¨¨¨¨¨¨¨¨¨¨" + String.format("%89s", "¨¨¨¨¨¨¨¨¨¨¨\n");
		sRet += "|  CODE   |" + String.format("%89s", "| DONNEES |\n");
		for (int i = 0; i <= 139; i++) if(i==87) sRet += " "; else sRet += "¨"; // Bordure de début du code et données
		/* -------------------------------------------------
		   --- Fin Affichage des entetes Code et Données ---
		   ------------------------------------------------- */
		
		/* ------------------------------------------------
		   ------- Affichage du Code et des Données -------
		   ------------------------------------------------ */
		sRet += "\n";
		int tailleNbLignes = 1 + code.size() / 10;
		for (int cptLig = 0; cptLig < code.size(); cptLig++) { // Peut afficher jusqu'à 999 lignes.
			String line = code.get(cptLig).replaceAll("\t", "    "); // Remplacement des tabulations par des espaces
			sRet += '|'
				 + String.format("%" + tailleNbLignes + "d", cptLig)
				 + ' ' 
				 + line
				 + String.format("%" + (81 - line.length()) + "s", "| ");
			if(cptLig == 0)
				sRet += "|    NOM     |   TYPE     |   VALEUR               |\n";
			else{
				if(data.size()>0){
					if(endData){
						for (int y = 0; y <= 51; y++) sRet += "¨"; // bordure de fin des données
						endData = false;
					}else{
						for(int i = 0 ; i < data.size() ; i++){
							if(i==cptLig-1)
								sRet += "| "
							         + String.format("%-11s", data.get(i).get(0)) 
							         + "| " 
							         + String.format("%-11s", data.get(i).get(1)) 
							         + "| " 
							         + String.format("%-23s", data.get(i).get(2)) 
							         + "|";
							if(cptLig==data.size())
								endData=true;
						}
					}
					sRet+="\n";
				}else{
					if(cptLig-1==0) sRet+="| Aucune donnée à afficher.                        |\n";
					else if(cptLig-1==1){
						for (int i = 0; i <= 51; i++) sRet += "¨"; // bordure de fin des données
						sRet+="\n";
					} else sRet+="\n";
				}
			}
		}
		for (int i = 0; i <= 86; i++) sRet += "¨"; // Bordure de fin de code
		/* ------------------------------------------------
		   ----- Fin Affichage du Code et des Données -----
		   ------------------------------------------------ */
		
		/* ---------------------------------------
		   ------- Affichage de la Console -------
		   --------------------------------------- */
		sRet += "\n¨¨¨¨¨¨¨¨¨¨¨\n";
		sRet += "| CONSOLE |\n";
		for (int i = 0; i <= 86; i++) sRet += "¨";
		sRet += "\n";
		for (int cptLig = 0; cptLig < resultConsole.size(); cptLig++) // Peut afficher jusqu'à 999 lignes.
			sRet += '|' + resultConsole.get(cptLig)
				 + String.format("%" + (87 - resultConsole.get(cptLig).length()) + "s", "|\n");
		for (int i = 0; i <= 86; i++) sRet += "¨";
		/* ---------------------------------------
		   ----- Fin Affichage de la Console -----
		   --------------------------------------- */
		
		System.out.println(sRet);
	}
	
	public static void main(String a[])
	{
		CUI cui = new CUI();
	}
}
