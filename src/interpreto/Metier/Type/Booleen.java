package interpreto.Metier.Type;


public class Booleen extends Variable {

	public Booleen(String nomVariable) {
		this(nomVariable, false);
	}
	
	public Booleen(String nomVariable, boolean estConstant)
	{
		super(nomVariable, estConstant);
		valeurDefaut = "faux";
	}
	
	@Override
	public boolean modifierValeur(String val) {
		if (val.equalsIgnoreCase("vrai") || val.equalsIgnoreCase("faux"))
		{
			valeurs.add(val);
			return super.modifierValeur(val);
		}
		return false;
	}
	
	public static String getBoolean(String val)
	{
		if(val.equalsIgnoreCase("faux"))
			return "false";
		return "true";
	}

}
