package interpreto.Metier.Type;

public class REEL extends Variable{

	public REEL(String nomVariable) {
		super(nomVariable);
		valeurDefaut = "0.0";
	}

	@Override
	public boolean modifierValeur(String val) {
		try
		{
			Double.parseDouble(val);
			valeurs.add(val);
		}catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}

}
