package interpreto;

import bsh.EvalError;
import bsh.Interpreter;
import interpreto.Metier.Type.*;

public class Test {
	
	
	public static void main(String args[]) {
		Booleen b = new Booleen("bool", false);
		
		Interpreter interpreter = new Interpreter();
		try {
			interpreter.eval("Entier a = 5");
			System.out.println(interpreter.get("a"));
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Controleur();
		
		
	}
	

}
