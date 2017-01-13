package interpreto.Metier.Type;

public class Caractere extends Variable {

	public Caractere(String nomVariable) {
		this(nomVariable, false);
	}

	public Caractere(String nomVariable, boolean estConstant) {
		super(nomVariable, estConstant);
		valeurDefaut = "\0";
	}

	@Override
	public boolean modifierValeur(String val) {
		if (val.length() != 3 || val.charAt(0) != '\'' && val.charAt(2) != '\'')// On
																				// compte
																				// aussi
																				// les
																				// simples
																				// quote
			return false;
		valeurs.add(val);
		return super.modifierValeur(val);
	}

}
