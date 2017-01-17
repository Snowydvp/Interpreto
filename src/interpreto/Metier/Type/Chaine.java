package interpreto.Metier.Type;

/**
 * Classe permettant la cr�ation des variables de type Chaine
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Chaine extends Variable {

	/**
	 * Constructeur permettant la cr�ation d'une Chaine de caractere
	 * 
	 * @param nomVariable
	 *            nom que prendra la Chaine de caractere
	 */
	public Chaine(String nomVariable) {
		super(nomVariable, false, "null");
	}

	/**
	 * Constructeur permettant d'initialiser une Chaine de caractere en
	 * constante
	 * 
	 * @param nomVariable
	 *            nom que prendra la Chaine de caractere
	 * @param estConstant
	 *            boolean permettant de savoir si la Chaine de caractere est une
	 *            constante
	 */
	public Chaine(String nomVariable, boolean estConstant, String valeur) {
		super(nomVariable, estConstant, valeur);
	}

	/**
	 * Methode permettant de modifier la valeur de la Chaine de caractere
	 * 
	 * @param val
	 *            valeur permettant le changement de la Chaine de caractere
	 * @return true si la valeur a bien etait modifier
	 */
	public boolean modifierValeur(String val) {
		if (val.charAt(0) != '"' || val.charAt(val.length() - 1) != '"')
			return false;
		return super.modifierValeur(val);
	}
}
