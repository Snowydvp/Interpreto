package interpreto.Metier.Type;

public class Caractere extends Variable {

	public Caractere(String nomVariable, boolean estTableau) {
		super(nomVariable, estTableau);
		type = Character.class;
	}

}
