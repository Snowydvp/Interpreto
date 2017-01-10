package interpreto.Metier.Type;

public class CARACTERE extends Variable {

	public CARACTERE(String nomVariable) {
		super(nomVariable);
		valeurDefaut = "\0";
	}

	@Override
	public boolean modifierValeur(String val) {
		if(val.length() != 3 || val.charAt(0) !='\'' && val.charAt(2) != '\'' )//On compte aussi les simples quote
			return false;
		valeurs.add(val);
		return true;
	}

}
