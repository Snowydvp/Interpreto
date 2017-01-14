package interpreto.Metier;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe permettant la gestion des fonctions
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Fonction {

	/**
	 * Methode permettant de convertir un entier en reel
	 * 
	 * @param param
	 * @return null si la conversion a rater
	 */
	public static Double enReel(String param) {
		try {
			Double d = Double.parseDouble(param);
			return d;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Methode permettant de convertir un reel en entier
	 * 
	 * @param param
	 * @return null si la conversion a rater
	 */
	public static Integer enEntier(String param) {
		try {
			Integer i = (int) (Double.parseDouble(param));
			return i;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Methode permettant de retouner une chaine
	 * 
	 * @param param
	 * @return le parametre
	 */
	public static String enChaine(String param) {
		return param;
	}

	/**
	 * Methode permettant de convertir un char en int
	 * 
	 * @param param
	 * @return un integer
	 */
	public static Integer ord(String param) {
		if (param.length() == 1)
			try {
				Integer i = (int) (param.charAt(0));
				return i;
			} catch (NumberFormatException e) {
				return null;
			}
		return null;
	}

	/**
	 * Methode permettant la conversion d'un int en char
	 * 
	 * @param param
	 * @return un Character
	 */
	public static Character car(String param) {
		try {
			Character c = (char) (Integer.parseInt(param));
			return c;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Methode permettant d'arrondir a l'entier inferieur
	 * 
	 * @param param
	 * @return un Integer arrondi a l'entier inferieur
	 */
	public static Integer plancher(String param) {
		return Fonction.enEntier(param);
	}

	/**
	 * Methode permettant d'arrondir a l'entier superieur
	 * 
	 * @param param
	 * @return un Integer arrondi a l'entier superieur
	 */
	public static Integer plafond(String param) {
		if (!Fonction.estEntier(param))
			return Fonction.enEntier(param) + 1;
		return Fonction.enEntier(param);
	}

	/**
	 * Methode permettant de faire l'arrondi
	 * 
	 * @param param
	 * @return un Integer arrondi a l'entier inferieur ou superieur
	 */
	public static Integer arrondi(String param) {
		if (Fonction.enReel(param) >= Fonction.enEntier(param) + 0.5)
			return Fonction.plafond(param);
		return Fonction.plancher(param);
	}

	/**
	 * Methode permettant de connaitre la date d'aujourd'hui
	 * 
	 * @return un String contenant la date d'aujourd'hui
	 */
	public static String aujourdhui() {
		return Fonction.jour() + "/" + Fonction.mois() + "/" + Fonction.annee();
	}

	/**
	 * Methode permettant de connaitre le jour actuelle
	 * 
	 * @return un int contenant le jour
	 */
	public static int jour() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Methode permettant de connaitre le mois actuelle
	 * 
	 * @return un int contenant le mois
	 */
	public static int mois() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * Methode permettant de connaitre l'annee actuelle
	 * 
	 * @return un int contenant l'année
	 */
	public static int annee() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Methode permettant de savoir si le parametre est un reel
	 * 
	 * @param param
	 *            valeur a identifier
	 * @return un boolean
	 */
	public static boolean estReel(String param) {
		if (!estEntier(param))
			try {
				Double i = Double.parseDouble(param);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		return false;
	}

	/**
	 * Methode permettant de savoir si le parametre est un entier
	 * 
	 * @param param
	 *            valeur a identifier
	 * @return un boolean
	 */
	public static boolean estEntier(String param) {
		try {
			Integer i = new Integer(param);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Methode permettant de retourner un nombre au hasard
	 * 
	 * @param param
	 *            intervalle de hasard
	 * @return un Integer
	 */
	public static Integer hasard(String param) {
		try {
			Integer i = new Integer(param);
			return new Integer((int) (Math.random() * i));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
