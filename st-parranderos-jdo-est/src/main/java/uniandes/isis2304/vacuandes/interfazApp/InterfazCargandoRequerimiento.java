package uniandes.isis2304.vacuandes.interfazApp;

import javax.swing.*;
import java.awt.*;

public class InterfazCargandoRequerimiento {
    JFrame frame;
    JLabel image=new JLabel(new ImageIcon("./src/main/resources/config/vacuna-covid-n.png"));
    JLabel text=new JLabel("Cargando...");
    JProgressBar progressBar=new JProgressBar();
    JLabel message=new JLabel();
    InterfazCargandoRequerimiento()
    {
        createGUI();
        addText();
    }
    public void createGUI(){
        frame=new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(200,100);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(false);

    }
    public void addText()
    {
        text.setFont(new Font("arial",Font.BOLD,30));
        text.setBounds(20,0,200,100);
        
        text.setForeground(Color.blue);
        frame.add(text);
    }
    
    public void mostrar () {
    	frame.setVisible(true);
    	frame.toBack();
    }
    
    public void traerAlfrente () {
    	frame.toFront();
    }
    public void ocultar () {
    	frame.setVisible(false);
    }
    
}
