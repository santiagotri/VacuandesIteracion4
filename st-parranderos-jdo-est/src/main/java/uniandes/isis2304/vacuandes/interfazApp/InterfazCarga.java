package uniandes.isis2304.vacuandes.interfazApp;

import javax.swing.*;
import java.awt.*;

public class InterfazCarga {
    JFrame frame;
    JLabel image=new JLabel(new ImageIcon("./src/main/resources/config/vacuna-covid-n.png"));
    JLabel text=new JLabel("VacuAndes v3.0");
    JProgressBar progressBar=new JProgressBar();
    JLabel message=new JLabel();
    InterfazCarga()
    {
        createGUI();
        addImage();
        addText();
        addProgressBar();
        addMessage();
        runningPBar();
    }
    public void createGUI(){
        frame=new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);

    }
    public void addImage(){
        image.setSize(600,200);
        frame.add(image);
    }
    public void addText()
    {
        text.setFont(new Font("arial",Font.BOLD,30));
        text.setBounds(170,220,600,40);
        text.setForeground(Color.BLUE);
        frame.add(text);
    }
    public void addMessage()
    {
        message.setBounds(250,320,200,40);
        message.setForeground(Color.black);
        message.setFont(new Font("arial",Font.BOLD,15));
        frame.add(message);
    }
    public void addProgressBar(){
        progressBar.setBounds(100,280,400,30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        frame.add(progressBar);
    }
    public void runningPBar(){
        int i=0;

        while( i<=100)
        {
            try{
                Thread.sleep(100);
                progressBar.setValue(i);
                message.setText("LOADING "+Integer.toString(i)+"%");
                i++;
                if(i==100)
                    frame.dispose();
            }catch(Exception e){
                e.printStackTrace();
            }



        }
    }
}
