package interpreto.IHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements IHM{

    public GUI() {
       setTitle("Interpreto"); 
       setSize(800,600);
       setExtendedState(JFrame.MAXIMIZED_BOTH);

       addWindowListener (new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				System.exit(0);
			}
		});
       
       JPanel main = new JPanel();
       main.setLayout(new BorderLayout());
       JPanel code = new JPanel();
       code.setLayout(new BorderLayout());
       JPanel data = new JPanel();
       data.setLayout(new BorderLayout());
       JPanel console = new JPanel();
       console.setLayout(new BorderLayout());
       
       JLabel labelCode = new JLabel("Code");
       JLabel labelData = new JLabel("Données");
       JLabel labelConsole = new JLabel("Console");
       
       code.add(labelCode);
       data.add(labelData);
       console.add(labelConsole, BorderLayout.NORTH);
       JTextArea textArea = new JTextArea(
    		    "This is an editable JTextArea. \n" +
    		    "A text area is a \"plain\" text component, " +
    		    "which means that although it can display text " +
    		    "in any font, all of the text is in the same font."
    		);
       textArea.setFont(new Font("Sans-Serif", 0, 16));
       textArea.setLineWrap(true);
       textArea.setWrapStyleWord(true);
       textArea.setMinimumSize(new Dimension(200,0));
       console.add(textArea, BorderLayout.CENTER);
       
       main.add(code, BorderLayout.WEST);
       main.add(data, BorderLayout.EAST);
       main.add(console, BorderLayout.SOUTH);
       add(main);
       
                
       setVisible(true);
    }

	@Override
	public String getEntree() {
		// TODO Auto-generated method stub
		return null;
	}
}