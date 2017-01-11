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
			double d = Double.parseDouble(val);
			//mettre la virgule si le nombre est entier
			valeurs.add(val);
		}catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}

}
