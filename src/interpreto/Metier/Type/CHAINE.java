package interpreto.Metier.Type;

public class CHAINE extends Variable {

	public CHAINE(String nomVariable) {
		super(nomVariable);
	}

	@Override
	public boolean modifierValeur(String val) {
		if(val.charAt(0) != '"' || val.charAt(val.length() - 1) != '"')
			return false;
		valeurs.add(val);
		return true;
	}
}
