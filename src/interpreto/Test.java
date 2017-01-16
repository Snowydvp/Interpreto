package interpreto;

import java.util.Calendar;
import java.util.Date;

import interpreto.IHM.CUI;
import interpreto.Metier.AnalyseCode;
import interpreto.Metier.LectureFichier;
import interpreto.Metier.Type.*;

public class Test {

	public static void main(String args[]) {
		
		AnalyseCode an = new AnalyseCode(LectureFichier.creerLectureFichier("codes/codeTest.algo"), null);
		System.out.println(an.fonctions.get(0));
			System.out.println("car".contains(an.fonctions.get(0)));
		}
}
