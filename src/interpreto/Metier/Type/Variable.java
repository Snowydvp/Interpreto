package interpreto.Metier.Type;

import java.util.ArrayList;

/**
 * Classe mere recouvrant tout les types de Variables
 * 
 * @author Equipe 7
 * @version 14/01/17
 *
 */
public abstract class Variable {

	protected String nom;
	protected ArrayList<String> valeurs;
	protected boolean estTableau, estConstante;
	protected String valeurDefaut = "null";
	/**
	 * Cette variable est utilise uniquement lorsque la variable est un tableau
	 **/
	protected int taille;

	/**
	 * Constructeur permettant la création d'une Variable
	 * 
	 * @param nomVariable
	 *            nom que prendra la Variable
	 */
	public Variable(String nom) {
		this(nom, false);
	}

	/**
	 * Constructeur permettant d'initialiser une Variable en constante
	 * 
	 * @param nomVariable
	 *            nom que prendra la Variable
	 * @param estConstant
	 *            boolean permettant de savoir si la Variable est une constante
	 */
	public Variable(String nom, boolean estConstante) {
		this.nom = nom;
		this.estConstante = estConstante;

		this.estTableau = false;
		this.valeurs = new ArrayList<String>();
	}

	// a travailler plus tard
	/**
	 * Constructeur permettant d'initialiser un tableau de Variable
	 * 
	 * @param nomVariable
	 *            nom que prendra le tableau de Variable
	 * @param taille
	 *            entier permettant de connaitre la dimension du tableau
	 */
	public Variable(String nomVariable, int taille) {
		this(nomVariable);
		this.estTableau = true;
		this.taille = taille;
	}

	/**
	 * Accesseur permettant de retourner le nom de la variable
	 * 
	 * @return le nom de la variable
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Accesseur permettant de recuperer toute les valeurs qu'a pu prendre la
	 * variable
	 * 
	 * @return une ArrayList de toutes les valeurs qu'a prise la variable
	 */
	public ArrayList getValeurs() {
		return this.valeurs;
	}

	/**
	 * Accesseur permettant de recuperer la toute derniere valeur qu'a prise la
	 * variable
	 * 
	 * @return la valeur actuelle de la variable
	 */
	public String getValeurActuelle() {
		if (!this.valeurs.isEmpty())
			return this.valeurs.get(this.valeurs.size() - 1);
		return this.valeurDefaut;
	}

	/**
	 * Methode permettant de savoir si la variable est un tableau
	 * 
	 * @return le boolean estTableau
	 */
	public boolean isEstTableau() {
		return this.estTableau;
	}

	/**
	 * Methode permettant de savoir si la variable est une constante
	 * 
	 * @return le boolean estConstante
	 */
	public boolean isEstConstante() {
		return this.estConstante;
	}

	/**
	 * Methode permettant de revenir sur la valeur precedent la valeur actuelle
	 * tout en supprimant la valeur actuelle
	 */
	public void retourArriere() {
		if (!this.valeurs.isEmpty() && !this.estConstante)
			this.valeurs.remove(this.valeurs.size() - 1);
	}

	/**
	 * Methode permettant de savoir si on peut modifier la valeur de la Variable
	 * 
	 * @param val
	 *            valeur de changement de la Variable
	 * @return un boolean pour savoir si on peut modifier la Variable
	 */
	public boolean modifierValeur(String val) {
		return !this.estConstante;
	}

	/**
	 * Accesseur permettant de connaitre le type de la Variable (Entier, Reel,
	 * Chaine...)
	 * 
	 * @return un String qui est le type de la Variable
	 */
	public String getType() {

		return getClass().getSimpleName().toLowerCase();
	}

}
