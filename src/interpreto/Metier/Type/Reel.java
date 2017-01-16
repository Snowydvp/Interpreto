package interpreto.Metier.Type;

/**
 * Classe permettant la cr�ation des variables de type Reel
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Reel extends Variable {

	/**
	 * Constructeur permettant la cr�ation d'un Reel
	 * 
	 * @param nomVariable
	 *            nom que prendra le Reel
	 */
	public Reel(String nomVariable) {
		this(nomVariable, false, "");
	}

	/**
	 * Constructeur permettant d'initialiser un Reel en constante
	 * 
	 * @param nomVariable
	 *            nom que prendra le Reel
	 * @param estConstant
	 *            boolean permettant de savoir si le Reel est une constante
	 */
	public Reel(String nomVariable, boolean estConstant, String valeur) {
		super(nomVariable, estConstant, valeur);
		super.valeurDefaut = "0.0";
	}

	/**
	 * Methode permettant de modifier la valeur du Reel
	 * 
	 * @param val
	 *            valeur permettant le changement du Reel
	 * @return true si la valeur a bien etait modifier
	 */
	public boolean modifierValeur(String val) {
		try {
			Double.parseDouble(val);
			// mettre la virgule si le nombre est entier
			super.valeurs.add(val);
		} catch (NumberFormatException e) {
			return false;
		}
		return super.modifierValeur(val);
	}

}
