package interpreto.Metier.Type;

public class ENTIER extends Variable {

	public ENTIER(String nomVariable) {
		super(nomVariable);
		valeurDefaut = "0";
	}

	@Override
	public boolean modifierValeur(String val) {
		try{
			Integer.parseInt(val);
			valeurs.add(val);
		}catch(NumberFormatException e)
		{
			return false;
		}
		return super.modifierValeur(val);
		
	}


}
