package interpreto.Metier;

import java.util.Date;

public class Fonction {
	
	public static Double enReel(String param)
	{
		try {
			Double d = Double.parseDouble(param);
			return d;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Integer enEntier(String param)
	{
		try {
			Integer i = Integer.parseInt(param);
			return i;
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Integer ord(String param)
	{
		return null;
	}
	
	public static Integer plancher(String param)
	{
		return null;
	}
	
	public static Integer plafond(String param)
	{
		return null;
	}
	
	public static Integer arrondi(String param)
	{
		return null;
	}
	
	public static String aujourdhui()
	{
		return null;
	}

}
