package interpreto.Metier.Type;

public class Chaine extends Variable {

	public Chaine(String nomVariable, boolean estTableau) {
		super(nomVariable, estTableau);
		type = String.class;
	}

}
