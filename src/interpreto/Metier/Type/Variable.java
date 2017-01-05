package interpreto.Metier.Type;

import java.util.ArrayList;

/**
 * Classe mère recouvrant tout types de variables
 * 
 * @author Equipe 7
 *
 */
public abstract class Variable {

	protected String nomVariable;
	protected ArrayList valeurs;
	protected boolean estTableau, estConstante;
	protected Class<?> type;

	public Variable(String nomVariable, boolean estTableau) {
		this.nomVariable = nomVariable;
		this.estConstante = estConstante;
		this.estTableau = estTableau;
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

	public boolean modifierValeur(Object val) {//a verifier si le type de la nouvelle valeur correspond
		if (!estConstante && val.getClass() == type.getClass()) {
			valeurs.add(val);
			return true;
		}
		return false;
	}

}
