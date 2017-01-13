package interpreto.Metier.Type;

public class Chaine extends Variable {

	public Chaine(String nomVariable) {
		this(nomVariable, false);
	}

	public Chaine(String nomVariable, boolean estConstant) {
		super(nomVariable, estConstant);
	}

	@Override
	public boolean modifierValeur(String val) {
		if (val.charAt(0) != '"' || val.charAt(val.length() - 1) != '"')
			return false;
		valeurs.add(val);
		return super.modifierValeur(val);
	}
}
