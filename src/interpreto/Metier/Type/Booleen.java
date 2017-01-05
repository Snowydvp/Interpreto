package interpreto.Metier.Type;

import java.util.ArrayList;

public class Booleen extends Variable {

	public Booleen(String nomVariable, boolean estConstante, boolean estTableau) {
		super(nomVariable, estTableau);
		valeurs = new ArrayList<Boolean>();
		type = Boolean.class;
		
	}

}
