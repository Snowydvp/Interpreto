package interpreto.Metier.Type;

/**
 * Classe permettant la creation des variables de type Booleen
 * 
 * @author Equipe 7
 * @version 14/01/17
 */
public class Booleen extends Variable {

	/**
	 * Constructeur permettant la creation d'un Booleen
	 * 
	 * @param nomVariable
	 *            nom que prendra le booleen
	 */
	public Booleen(String nomVariable) {
		this(nomVariable, false, "");
	}

	/**
	 * Constructeur permettant d'initialiser un Booleen en constante
	 * 
	 * @param nomVariable
	 *            nom que prendra le booleen
	 * @param estConstant
	 *            boolean permettant de savoir si le Booleen est une constante
	 */
	public Booleen(String nomVariable, boolean estConstant, String valeur) {
		super(nomVariable, estConstant, valeur);
		super.valeurDefaut = "faux";
	}

	/**
	 * Methode permettant de modifier la valeur du Booleen
	 * 
	 * @param val
	 *            valeur permettant le changement du Booleen
	 * @return true si la valeur a bien etait modifier
	 */
	public boolean modifierValeur(String val) {
		if (val.equalsIgnoreCase("vrai") || val.equalsIgnoreCase("faux")) {
			super.valeurs.add(val);
			return super.modifierValeur(val);
		}
		return false;
	}

	/**
	 * pas compris ici
	 * 
	 * @param val
	 * @return
	 */
	public static String getBooleen(String val) {
		if (val.equalsIgnoreCase("faux"))
			return "false";
		return "true";
	}

}
