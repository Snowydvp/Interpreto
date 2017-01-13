package interpreto.Metier;

public class Fonction {
	
	public static Double enReel(String param)
	{
		try{
		Double d = Double.parseDouble(param);
		return d;
		}catch(NumberFormatException e)
		{
			return Double.NaN;
		}
	}
	
	

}
