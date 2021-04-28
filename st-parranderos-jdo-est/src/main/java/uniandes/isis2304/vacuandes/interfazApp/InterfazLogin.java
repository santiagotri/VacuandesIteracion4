package uniandes.isis2304.vacuandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Desktop.Action;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InterfazLogin extends JFrame implements ActionListener {
	   JPanel panel;
	   JLabel user_label, password_label, message;
	   JTextField userName_text;
	   JPasswordField password_text;
	   JButton submit, cancel;
	   
	   private String usuarioIngresado;
	   private String contrasenaIngresada;
	   
	   InterfazVacuandesApp interfazVacuandes;
	   
	   public InterfazLogin(InterfazVacuandesApp pInterfaz) {
		   
	      // Username Label
		  interfazVacuandes = pInterfaz;
		  usuarioIngresado = "";
		  contrasenaIngresada= "";
		  
	      user_label = new JLabel();
	      user_label.setText("Username:");
	      userName_text = new JTextField();
	      // Password Label
	      password_label = new JLabel();
	      password_label.setText("Contraseña:");
	      password_text = new JPasswordField();
	      // Submit
	      submit = new JButton("Enviar");
	      panel = new JPanel(new GridLayout(3, 1));
	      panel.add(user_label);
	      panel.add(userName_text);
	      panel.add(password_label);
	      panel.add(password_text);
	      message = new JLabel();
	      panel.add(message);
	      panel.add(submit);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      // Adding the listeners to components..
	      submit.addActionListener(pInterfaz);
	      add(panel, BorderLayout.CENTER);
	      setTitle("Iniciar sesion");
	      setSize(450,125);
	      setVisible(true);
	   }
	   
	   @Override
	   public void actionPerformed(ActionEvent ae) {
		  usuarioIngresado = userName_text.getText();
		  contrasenaIngresada = password_text.getText();
	   }
	   
	   public void close() { setVisible(false);}
	   
	   public String darUsuarioIngresado () {return userName_text.getText();}
	   public String darContrasenaIngresada () {return password_text.getText();}
	   

}
