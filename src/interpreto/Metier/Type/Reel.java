package interpreto.Metier.Type;

public class Reel extends Variable{

	public Reel(String nomVariable, boolean estTableau) {
		super(nomVariable, estTableau);
		type = Double.class;
	}

}
