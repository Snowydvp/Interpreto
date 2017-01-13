package interpreto.Metier.Type;

import java.util.ArrayList;

/**
 * Classe mère recouvrant tout les types de variables
 * 
 * @author Equipe 7
 * @param <type>
 *
 */
public abstract class Variable {

	protected String nom;
	protected ArrayList<String> valeurs;
	protected boolean estTableau, estConstante;
	protected String valeurDefaut = "null";
	/**
	 * Cette variable est utilisé uniquement lorsque la variable est un tableau
	 **/
	protected int taille;

	public Variable(String nom) {
		this(nom, false);
	}
	
	public Variable(String nom, boolean estConstante)
	{
		this.nom = nom;
		this.estConstante = estConstante;

		this.estTableau = false;
		valeurs = new ArrayList<String>();
	}

	// a travailler plus tard
	public Variable(String nomVariable, int taille) {
		this(nomVariable);
		estTableau = true;
		this.taille = taille;
	}

	public String getNom() {
		return nom;
	}

	public ArrayList getValeurs() {
		return valeurs;
	}
	
	public String getValeurActuelle()
	{
		if(!valeurs.isEmpty())
			return valeurs.get(valeurs.size() - 1);
		return valeurDefaut;
	}

	public boolean isEstTableau() {
		return estTableau;
	}

	public boolean isEstConstante() {
		return estConstante;
	}

	public void retourArriere() {
		if (!valeurs.isEmpty() && !estConstante)
			valeurs.remove(valeurs.size() - 1);
	}

	public boolean modifierValeur(String val)
	{
		return !estConstante;
	}

	public String getType() {
		
		return getClass().getSimpleName().toLowerCase();
	}
	

}
