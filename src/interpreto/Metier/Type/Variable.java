package interpreto.Metier.Type;
import java.util.ArrayList;
/**
 * Classe mère recouvrant tout typesde variables
 * @author Equipe 7
 *
 */
public abstract class Variable {

	protected String nomVariable;
	protected ArrayList valeurs;
	protected boolean estTableau, estConstante;

	public Variable(String nomVariable, boolean estConstante, boolean estTableau) {
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
	
	public void retourArriere()
	{
		if(!valeurs.isEmpty() && !estConstante)
			valeurs.remove(valeurs.size()-1);
	}

}
