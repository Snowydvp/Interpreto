package interpreto.Metier.Type;

import java.util.ArrayList;

/**
 * Classe m�re recouvrant tout les types de variables
 * 
 * @author Equipe 7
 *
 */
public abstract class Variable {

	protected String nomVariable;
	protected ArrayList valeurs;
	protected boolean estTableau, estConstante;
	/**Cette variable est utilis� uniquement lorsque la variable est un tableau**/
	protected int taille;
	protected Class<?> type;

	public Variable(String nomVariable) {
		this.nomVariable = nomVariable;
		this.estConstante = false; //a travailler plus tard
		
		this.estTableau = false;
		valeurs = new ArrayList<>();
	}
	
	//a travailler plus tard
	public Variable(String nomVariable, int taille)
	{
		this(nomVariable);
		estTableau = true;
		this.taille = taille;
	}
	

	public String getNomVariable() {
		return nomVariable;
	}

	public ArrayList getValeurs() {
		return valeurs;
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

	public boolean modifierValeur(Object val) {
		if(estConstante && valeurs.isEmpty())
		{
			valeurs.add(val);
			return true;
		}
		if (val.getClass() == type.getClass()) {
			valeurs.add(val);
			return true;
		}
		return false;
	}

}
