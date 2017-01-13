package interpreto.IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements IHM{
	
	JPanel panelMain = new JPanel();
	JPanel panelCode = new JPanel();
	JPanel panelData = new JPanel();
	JPanel panelConsole = new JPanel();
	JLabel labelCode = new JLabel("Code");
    JLabel labelData = new JLabel("Données");
    JLabel labelConsole = new JLabel("Console");
    
    private DefaultListModel dfmCode = new DefaultListModel();
    private JList            listCode = new JList(dfmCode);
    private DefaultListModel dfmData = new DefaultListModel();
    private JList            listData = new JList(dfmData);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu    menu1   = new JMenu("Fichier");
	private JMenu    menu2   = new JMenu("Actions");
	private JMenu    menu3   = new JMenu("Aide");
	private JMenuItem menu1_item1 = new JMenuItem("Charger un fichier");

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
       panelMain.setLayout(new BorderLayout());
       panelCode.setLayout(new BorderLayout());
       panelData.setLayout(new BorderLayout());
       panelConsole.setLayout(new BorderLayout());
       menuBar.add(menu1);
       menuBar.add(menu2);
       menuBar.add(menu3);
       menu1_item1.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		   JOptionPane.showMessageDialog(null,"Fonction non implémentée.","Erreur",JOptionPane.ERROR_MESSAGE);
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
       panelConsole.add(labelConsole, BorderLayout.NORTH);
       JTextArea textArea = new JTextArea("");
       textArea.setFont(new Font("Sans-Serif", 0, 16));
       textArea.setLineWrap(true);
       textArea.setWrapStyleWord(true);
       textArea.setPreferredSize(new Dimension(200,200));
       panelConsole.add(textArea, BorderLayout.CENTER);
       
       
       
       panelMain.add(panelCode, BorderLayout.CENTER);
       panelMain.add(panelData, BorderLayout.EAST);
       panelMain.add(panelConsole, BorderLayout.SOUTH);
       add(panelMain);
       
                
       setVisible(true);
    }
    
    public static void main(String[] args) {
		new GUI();
	}

	@Override
	public String getEntree() {
		// TODO Auto-generated method stub
		return null;
	}
}