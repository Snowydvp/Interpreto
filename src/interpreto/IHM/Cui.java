package interpreto.IHM;

import java.util.ArrayList;
import interpreto.Metier.*;

public class Cui {
	public Cui(){
		affichage();
	}
	
	public void affichage(){
		ArrayList<String> list = new ArrayList<String>();
		list = LectureFichier.lireFichier("./src/fichierdemerde.txt");
		String sRet = "иииииииииии";
		sRet += String.format("%89s", "иииииииииии\n");
		sRet+="|  CODE   |";
		sRet += String.format("%89s", "| DONNEES |\n");
		for(int i = 0 ; i <= 86 ; i++)
			sRet+="и";
		sRet+=" ";
		for(int i = 0 ; i <= 51 ; i++)
			sRet+="и";
		//System.out.println(list);
		System.out.println(sRet);
	}
}
