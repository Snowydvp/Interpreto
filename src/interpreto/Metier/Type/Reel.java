package interpreto.Metier.Type;

public class Reel extends Variable{

	public Reel(String nomVariable) {
		this(nomVariable, false);
	}
	
	public Reel(String nomVariable, boolean estConstant) {
		super(nomVariable, estConstant);
		valeurDefaut = "0.0";
	}
	
	@Override
	public boolean modifierValeur(String val) {
		try
		{
			Double.parseDouble(val);
			//mettre la virgule si le nombre est entier
			valeurs.add(val);
		}catch(NumberFormatException e)
		{
			return false;
		}
		return super.modifierValeur(val);
	}

}
