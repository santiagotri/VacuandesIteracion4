package uniandes.isis2304.vacuandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


//CLASE TOMADA DE : http://www.java2s.com/example/java/swing/create-wait-window.html

public class InterfazWaitWindow extends JFrame{
	
	private JLabel labelTitulo;
	
	public static JDialog createWaitWindow(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Wait, please...", true);
        dialog.add(new JLabel("Wait, please, operation is in progress..."));
        dialog.setSize(new Dimension(300, 100));
        dialog.setResizable(false);
        moveToCenter(dialog);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        return dialog;
    }

    public static void moveToCenter(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
                (int) (screenSize.getWidth() / 2.0 - window.getWidth() / 2.0),
                (int) (screenSize.getHeight() / 2.0 - window.getHeight() / 2.0));
    }
    
    public InterfazWaitWindow(InterfazVacuandesApp pInterfaz, String contenido) {
		labelTitulo = new JLabel (contenido);
	
		JPanel panel = new JPanel();
        BoxLayout layoutMgr = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layoutMgr);

        ClassLoader cldr = this.getClass().getClassLoader();
        ImageIcon imageIcon = new ImageIcon("./src/main/resources/config/carga.gif");
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(imageIcon);
        imageIcon.setImageObserver(iconLabel);

        JLabel label = new JLabel("Loading...");
        panel.add(iconLabel);
        panel.add(label);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adding the listeners to components..
		add(labelTitulo, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		setTitle("Cargando");
		setSize(300,300);
		//moveToCenter(this);
		setVisible(false);
	}
	

	public void close() { setVisible(false);}
    

	public void mostrar() {
		setVisible(true);
		
	}

}
