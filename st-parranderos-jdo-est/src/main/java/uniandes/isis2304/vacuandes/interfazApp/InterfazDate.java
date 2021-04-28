package uniandes.isis2304.vacuandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class InterfazDate extends JFrame {
	private JPanel panel;
	private DateTextField dateTextField;
	
	private JLabel labelCedula;
	private JLabel labelFecha;
	private JLabel labelHora;
	
	private JButton submit;
	
	private JTextField fieldCedula;

	private String usuarioIngresado;
	private String contrasenaIngresada;

	private InterfazVacuandesApp interfazVacuandes;
	
	private JComboBox optionList1;
	
	private String [] opciones1 = {
			"7:00", //1 
			"7:30", //2
			"8:00", //3
			"8:30", //4
			"9:00", //5
			"9:30", //6
			"10:00", //7
			"10:30", //8
			"11:00", //9
			"11:30",//10
			"12:00", //11
			"12:30", //12
			"13:00", //13
			"13:30", //14
			"14:00", //15
			"14:30", //16
			"15:00", //17
			"15:30",//18
			"16:00", //19
			"16:30", //20
			"17:00"};//21

	public InterfazDate(InterfazVacuandesApp pInterfaz) {
		labelCedula = new JLabel ("Cedula ciudadano");
		fieldCedula = new JTextField();
		labelFecha = new JLabel ("Fecha de la cita");
		labelHora = new JLabel ("Hora de la cita");
		dateTextField= new DateTextField();
		panel = new JPanel(new GridLayout(3, 2));
		 submit = new JButton("Verificar_disponibilidad");
		 
		
		 optionList1 = new JComboBox(opciones1);
		optionList1.setSelectedIndex(0);
		
		panel.add(labelCedula);
		panel.add(fieldCedula);
		panel.add(labelFecha);
		panel.add(dateTextField);
		panel.add(labelHora);
		panel.add(optionList1);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adding the listeners to components..
		add(panel, BorderLayout.CENTER);
		submit.addActionListener(pInterfaz);
		add(submit, BorderLayout.SOUTH);
		setTitle("Iniciar sesion");
		setSize(450,125);
		setVisible(true);
	}
	
	public Date getDate () {
		return dateTextField.getDate();
	}
	
	public Long getCedula() {
		try {
			return Long.parseLong(fieldCedula.getText());
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Error en formato cedula", "Error en fecha", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}
	
	public int getHora() {
		String textoSinFormato = opciones1[optionList1.getSelectedIndex()];
		String[] numeros = textoSinFormato.split(":");
		return Integer.parseInt(numeros[0]+numeros[1]);
	}

	public void close() { setVisible(false);}
}
