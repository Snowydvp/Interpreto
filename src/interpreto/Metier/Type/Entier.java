package interpreto.Metier.Type;

public class Entier extends Variable {

	public Entier(String nomVariable, boolean estTableau) {
		super(nomVariable);
		type = Integer.class;
	}

}
