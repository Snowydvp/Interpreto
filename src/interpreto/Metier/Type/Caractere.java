package interpreto.Metier.Type;

/**
 * Classe permettant la cr�ation des variables de type Caractere
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Caractere extends Variable {

	/**
	 * Constructeur permettant la cr�ation d'un Caractere
	 * 
	 * @param nomVariable
	 *            nom que prendra le Caractere
	 */
	public Caractere(String nomVariable) {
		super(nomVariable, false, "'\0'");
	}

	/**
	 * Constructeur permettant d'initialiser un Caractere en constante
	 * 
	 * @param nomVariable
	 *            nom que prendra le Caractere
	 * @param estConstant
	 *            boolean permettant de savoir si le Caractere est une constante
	 */
	public Caractere(String nomVariable, boolean estConstant, String valeur) {
		super(nomVariable, estConstant, valeur);
	}

	/**
	 * Methode permettant de modifier la valeur du Caractere
	 * 
	 * @param val
	 *            valeur permettant le changement du Caractere
	 * @return true si la valeur a bien etait modifier
	 */
	public boolean modifierValeur(String val) {
		if (val.length() != 3 || val.charAt(0) != '\'' && val.charAt(2) != '\'')
			return false;
		return super.modifierValeur(val);
	}

}
