package interpreto.Metier.Type;

public class Entier extends Variable {

	public Entier(String nomVariable) {
		this(nomVariable, false);
	}

	public Entier(String nomVariable, boolean estConstant) {
		super(nomVariable, estConstant);
		valeurDefaut = "0";
	}

	@Override
	public boolean modifierValeur(String val) {
		try {
			Integer.parseInt(val);
			valeurs.add(val);
		} catch (NumberFormatException e) {
			return false;
		}
		return super.modifierValeur(val);

	}

}
