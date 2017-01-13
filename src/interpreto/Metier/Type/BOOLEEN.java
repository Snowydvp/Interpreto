package interpreto.Metier.Type;


public class BOOLEEN extends Variable {

	public BOOLEEN(String nomVariable) {
		super(nomVariable);
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
