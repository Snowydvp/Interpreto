package interpreto.Metier.Type;

/**
 * Classe permettant la création des variables de type Entier
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Entier extends Variable {

	/**
	 * Constructeur permettant la création d'un Entier
	 * 
	 * @param nomVariable
	 *            nom que prendra l'Entier
	 */
	public Entier(String nomVariable) {
		this(nomVariable, false);
	}

	/**
	 * Constructeur permettant d'initialiser un Entier en constante
	 * 
	 * @param nomVariable
	 *            nom que prendra l'Entier
	 * @param estConstant
	 *            boolean permettant de savoir si l'Entier est une constante
	 */
	public Entier(String nomVariable, boolean estConstant) {
		super(nomVariable, estConstant);
		super.valeurDefaut = "0";
	}

	/**
	 * Methode permettant de modifier la valeur de l'Entier
	 * 
	 * @param val
	 *            valeur permettant le changement de l'Entier
	 * @return true si la valeur a bien etait modifier
	 */
	public boolean modifierValeur(String val) {
		try {
			Integer.parseInt(val);
			super.valeurs.add(val);
		} catch (NumberFormatException e) {
			return false;
		}
		return super.modifierValeur(val);

	}

}
