package interpreto.IHM;

import java.util.ArrayList;
import interpreto.Metier.*;

public class CUI implements IHM{
	
	private AnalyseCode analyseCode;
	private String nomFichier;
	
	public CUI(String nomFichier) {
		this.nomFichier = nomFichier;
		analyseCode = new AnalyseCode(nomFichier, this);
		analyseCode.traiterCode();
		
	}

	private void affichage() {
		/* --------------------------
		   ----- Initialisation -----
		   -------------------------- */
		boolean endData = false; // Permet de savoir si toutes les données ont été affichées
		String sRet = "";
		ArrayList<String> code = analyseCode.getCodeAnalyse();
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
		
		/* ---------------------------------
		   ------- Affichage du Code -------
		   --------------------------------- */
		sRet += "\n";
		int tailleNbLignes = (code.size() / 10);
		for (int cptLig = 0; cptLig < code.size(); cptLig++) {
			String line = code.get(cptLig).replaceAll("\t", "    "); // Remplacement des tabulations par des espaces
			int nbCouleur = line.split("\u001B").length; //Definis le nombre de couleurs existant dans la ligne
			
			sRet += '|'
				 + String.format("%" + tailleNbLignes + "d", cptLig)
				 + ' ' 
				 + line
				 //Detecter le nombre de couleur par ligne pour rajouter de la longueur
				 + String.format("%" + (81 - line.length() + (nbCouleur * 5.5) - 4) + "s", "| ");
			/* ---------------------------------------
			   ------- Affichage des variables -------
			   --------------------------------------- */
			if(cptLig == 0)
				sRet += "|    NOM     |   TYPE     |   VALEUR               |\n";
			else{
				if(analyseCode.getVariables().size() > 0){
					if(endData){
						for (int y = 0; y <= 51; y++) sRet += "¨"; // bordure de fin des données
						endData = false;
					}else{
						for(int i = 0 ; i < analyseCode.getVariables().size() ; i++){
							if(i==cptLig-1)
								sRet += "| "
							         + String.format("%-11s", analyseCode.getVariables().get(i).getNomVariable())
							         + "| " 
							         + String.format("%-11s", analyseCode.getVariables().get(i).getType()) 
							         + "| " 
							         + String.format("%-23s", analyseCode.getVariables().get(i).getValeurActuelle())
							         + "|";
							if(cptLig==analyseCode.getVariables().size())
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
		
		
		for (int cptLig = 0; cptLig < analyseCode.getConsole().size(); cptLig++) // Peut afficher jusqu'à 999 lignes.
			sRet += '|' + analyseCode.getConsole().get(cptLig)
				 + String.format("%" + (87 - analyseCode.getConsole().get(cptLig).length()) + "s", "|\n");
		//for (int i = 0; i <= 86; i++) sRet += "¨";
		/* ---------------------------------------
		   ----- Fin Affichage de la Console -----
		   --------------------------------------- */
		
		System.out.println(sRet);
	}
	
	public void rafraichir()
	{
		System.out.println("refresh");
		affichage();
	}
	
	public static void main(String a[])
	{
		CUI cui = new CUI(a[0]);
	}
}
