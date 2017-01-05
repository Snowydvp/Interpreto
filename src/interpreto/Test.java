package interpreto;

import interpreto.Metier.Type.*;

public class Test {
	
	
	public static void main(String args[]) {
		Booleen b = new Booleen("bool", false, false);
		b.modifierValeur("okok");
		b.modifierValeur(new Boolean(true));
		
	}
	

}
