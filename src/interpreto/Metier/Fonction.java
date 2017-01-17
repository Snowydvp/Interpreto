package interpreto.Metier;

import java.util.Calendar;

/**
 * Classe permettant la gestion des fonctions
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Fonction {

	/**
	 * Méthode permettant de convertir un entier en reel
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
	 * Méthode permettant de convertir une chaine de caractère en entier
	 * 
	 * @param param
	 * @return null si la conversion a rater
	 */
	public static Integer enEntier(String param) {
		param = param.replace("\"", "");
		try {
			Integer i = (Integer.parseInt(param));
			return i;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Méthode permettant de retouner une chaine
	 * 
	 * @param param
	 * @return le paramètre
	 */
	public static String enChaine(String param) {
		return param;
	}

	/**
	 * Méthode permettant de convertir un char en int
	 * 
	 * @param param
	 * @return résultat de conversion
	 */
	public static Integer ord(String param) {
		if (param.length() == 3)
			try {
				Integer i = (int) (param.charAt(1));
				return i;
			} catch (NumberFormatException e) {
				return null;
			}
		return null;
	}

	/**
	 * Méthode permettant la conversion d'un entier en caractère
	 * 
	 * @param param
	 * @return un caractère
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
	 * Méthode permettant d'arrondir a l'entier inférieur
	 * 
	 * @param param
	 * @return un Integer arrondi a l'entier inférieur
	 */
	public static Integer plancher(String param) {
		try {
			Integer i = (int) Double.parseDouble(param);
			return i;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Méthode permettant d'arrondir a l'entier supérieur
	 * 
	 * @param param
	 * @return un Integer arrondi a l'entier supérieur
	 */
	public static Integer plafond(String param) {
		if (!Fonction.estEntier(param))
			return Fonction.plancher(param) + 1;
		return Fonction.enEntier(param);
	}

	/**
	 * Méthode permettant de faire l'arrondi
	 * 
	 * @param param
	 * @return un Integer arrondi a l'entier inférieur ou supérieur
	 */
	public static Integer arrondi(String param) {
		try {
			Double d = Fonction.enReel(param);
			if (d >= Fonction.plancher(param) + 0.5)
				return Fonction.plafond(param);
			return Fonction.plancher(param);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Méthode permettant de connaitre la date d'aujourd'hui
	 * 
	 * @return un String contenant la date d'aujourd'hui
	 */
	public static String aujourdhui() {
		return Fonction.annee() + "-" + Fonction.mois() + "-" + Fonction.jour();
	}

	/**
	 * Méthode permettant de connaitre le jour actuel
	 * 
	 * @return un int contenant le jour
	 */
	public static int jour() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Méthode permettant de connaitre le mois actuel
	 * 
	 * @return un int contenant le mois
	 */
	public static int mois() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * Méthode permettant de connaitre l'année actuelle
	 * 
	 * @return un int contenant l'année
	 */
	public static int annee() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Méthode permettant de savoir si le paramètre est un réel
	 * 
	 * @param param
	 *            valeur a identifier
	 * @return un boolean
	 */
	public static boolean estReel(String param) {
		if (estEntier(param))
			return false;

		try {
			Double d = Double.parseDouble(param);
			return false;
		} catch (NumberFormatException e) {
			return true;
		}
	}

	/**
	 * Méthode permettant de savoir si le paramètre est un entier
	 * 
	 * @param param
	 *            valeur a identifier
	 * @return un boolean
	 */
	public static boolean estEntier(String param) {
		try {
			new Integer(param);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Méthode permettant de retourner un nombre au hasard
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
