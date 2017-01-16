package interpreto.IHM;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import interpreto.Metier.*;
import interpreto.Metier.Type.Variable;

/**
 * Classe GUI permettant la construction de l'interface utilisateur
 * 
 * @author Equipe 7
 * @version 14/01/17
 */

public class GUI extends JFrame implements IHM{
	
	JPanel panelMain = new JPanel();
	JPanel panelCode = new JPanel();
	JPanel panelData = new JPanel();
	JPanel panelBottom = new JPanel();
	JPanel panelConsole = new JPanel();
	JPanel panelButtons = new JPanel();
	JLabel labelCode = new JLabel("Code");
    JLabel labelData = new JLabel("Données");
    JLabel labelConsole = new JLabel("Console");
    JLabel labelButtons = new JLabel("Actions");
    
    JButton buttonNext = new JButton("Suivant");
    JButton buttonPrevious = new JButton("Précédent");
    JButton buttonStop = new JButton("Arrêt");
    JButton buttonStartAuto = new JButton("Début Auto");
    JButton buttonStartManu = new JButton("Début Manuel");
    
    private DefaultListModel<String> dfmCode = new DefaultListModel<String>();
    private JList<String>            listCode = new JList<String>(dfmCode);
    private DefaultListModel<String> dfmData = new DefaultListModel<String>();
    private DefaultListModel<String> theNewModel;
    private JList<String>            listData = new JList<String>(dfmData);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu    menu1   = new JMenu("Fichier");
	private JMenu    menu2   = new JMenu("Edition");
	private JMenu    menu3   = new JMenu("A propos");
	private JMenuItem menu1_item1 = new JMenuItem("Charger un fichier");
	
	static GUI guiFrame;
	
	JFileChooser dialogue = new JFileChooser();
	
	private AnalyseCode analyseCode;
	
	private ArrayList<String> traceVariables = new ArrayList<>();
	
	private JFrame demanderTraceVariable = new JFrame();

    public GUI() {
       setTitle("Interpreto"); 
       setSize(800,600);
       setExtendedState(JFrame.MAXIMIZED_BOTH);

       addWindowListener (new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				System.exit(0);
			}
		});
       
       
       /*
        * Initialisation
        */
       demanderTraceVariable.setTitle("Choisir les variables à afficher");
       demanderTraceVariable.setSize(400,400);
       
       
       panelMain.setLayout(new BorderLayout());
       panelCode.setLayout(new BorderLayout());
       panelData.setLayout(new BorderLayout());
       panelBottom.setLayout(new BorderLayout());
       panelButtons.setLayout(new GridLayout(3,2));
       panelConsole.setLayout(new BorderLayout());
       menuBar.add(menu1);
       menuBar.add(menu2);
       menuBar.add(menu3);
       menu1_item1.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		   dialogue.showOpenDialog(null);
    		   LectureFichier lecture = LectureFichier.creerLectureFichier(dialogue.getSelectedFile().getPath());
    		   if(lecture != null){
    			   update(lecture);
    		   }else{
    			   JOptionPane.showMessageDialog(null,"Erreur lors du chargement du fichier. Celui-ci doit être du au format .algo.","Erreur",JOptionPane.ERROR_MESSAGE);
    		   }
    	   }
       });
       menu1.add(menu1_item1);
       setJMenuBar(menuBar);
       dfmCode.addElement("Veuillez ouvrir un fichier .algo via le menu Fichier.");
       dfmData.addElement("Aucune données à afficher.");
       
       /*
        * Fin Initialisation
        */
       
       panelCode.add(labelCode, BorderLayout.NORTH);
       panelCode.add(listCode, BorderLayout.CENTER);
       panelData.add(labelData, BorderLayout.NORTH);
       panelData.add(listData, BorderLayout.CENTER);
       
       panelButtons.add(labelButtons);
       panelButtons.add(buttonStop);
       
       panelButtons.add(buttonNext);
       panelButtons.add(buttonPrevious);
       
       panelButtons.add(buttonStartAuto);
       panelButtons.add(buttonStartManu);
       
       
       panelConsole.add(labelConsole, BorderLayout.NORTH);
       
       
       JTextArea textArea = new JTextArea("");
       textArea.setFont(new Font("Sans-Serif", 0, 16));
       textArea.setLineWrap(true);
       textArea.setWrapStyleWord(true);
       textArea.setPreferredSize(new Dimension(200,200));
       panelConsole.add(textArea, BorderLayout.CENTER);
       
       
       
       panelMain.add(panelCode, BorderLayout.CENTER);
       panelMain.add(panelData, BorderLayout.EAST);
       panelBottom.add(panelConsole,BorderLayout.CENTER);
       panelBottom.add(panelButtons,BorderLayout.EAST);
       panelMain.add(panelBottom, BorderLayout.SOUTH);
       add(panelMain);
                
       setVisible(true);
    }
    
    public void update(LectureFichier lecture){
    	analyseCode = new AnalyseCode(lecture, guiFrame);
		analyseCode.traiterInitialisation();
    	ArrayList<String> code = analyseCode.getCode();
    	theNewModel = new DefaultListModel<String>();
    	for(String s : code)
    		theNewModel.addElement(s.replaceAll("\t", "    "));
    	listCode.setModel(theNewModel);
    	if (!analyseCode.getErreur()) {
    		demanderTraceVariable.setLayout(new BorderLayout());
    		JPanel listVar = new JPanel(new GridLayout(analyseCode.getVariables().size(),1));
    		for(Variable v : analyseCode.getVariables()){
    			listVar.add(new JLabel(v.getNom()));
    		}
    		System.out.println(analyseCode.getVariables().size());
    		JButton closeDialog = new JButton("Fermer");
    		closeDialog.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				demanderTraceVariable.setVisible(false);
    			}
		    });
			demanderTraceVariable.add(listVar,BorderLayout.CENTER);
			demanderTraceVariable.add(closeDialog,BorderLayout.SOUTH);   
			demanderTraceVariable.setVisible(true);
    	}else{
    		JOptionPane.showMessageDialog(null,"Erreur lors de l'initialisation. Vérifiez l'authenticité de votre fichier"+analyseCode.getLigneInterpretee()+" .algo.","Erreur",JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public static void main(String[] args) {
		guiFrame = new GUI();
	}

	/**
	 * Cette methode sert à lire les variables via l'interface d'entrée
	 * 
	 * @return le texte inséré dans la console
	 */
	public String getEntree() {
		// TODO Auto-generated method stub
		return null;
	}
}