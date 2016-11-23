import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Label;
import java.awt.Button;
import javax.swing.JTextArea;
import java.awt.TextArea;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Servidor {

	private JFrame Principal;
	private JTextField IP;
	private JTextField PUERTO;
	static	Lecturadelcliente clientes[]=new Lecturadelcliente [100];
	static int CantidadClientes=0;
	static Button comenzar;
	static TextArea Clientes;
	static TextArea chat;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor window = new Servidor();
					window.Principal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Servidor() {
		initialize();
	}


	private void initialize() {
		try {
		Principal = new JFrame();
		Principal.setTitle("Servidor");
		Principal.setBounds(100, 100, 450, 300);
		Principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Principal.getContentPane().setLayout(null);
		
		IP = new JTextField();
		IP.setBounds(54, 231, 126, 20);
		Principal.getContentPane().add(IP);
		IP.setColumns(10);
		IP.setFocusable(false);
		
			IP.setText(Inet4Address.getLocalHost().getHostAddress());
		
		PUERTO = new JTextField();
		PUERTO.setColumns(10);
		PUERTO.setBounds(282, 231, 126, 20);
		Principal.getContentPane().add(PUERTO);
		PUERTO.setFocusable(false);
		PUERTO.setText("11000");
		
		Label ip = new Label("IP");
		ip.setBounds(26, 231, 22, 22);
		Principal.getContentPane().add(ip);
		
		Label puerto = new Label("PUERTO");
		puerto.setBounds(207, 231, 69, 22);
		Principal.getContentPane().add(puerto);
		
		comenzar = new Button("Comenzar");
		comenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(Principal, "Servidor corriendo","Estado Servidor", 1);
				Clientes.setText("Esperando...");
				chat.setText("Comienza el chat...");
				Servidor.comenzar.enable(false);
				Principal main=new Principal();
				main.start();

			}
		});
		comenzar.setBounds(171, 172, 82, 30);
		Principal.getContentPane().add(comenzar);
		
		Clientes = new TextArea();
		Clientes.setBounds(10, 49, 155, 153);
		Principal.getContentPane().add(Clientes);
		Clientes.setFocusable(false);
		
		Label conectados = new Label("Clientes conectados");
		conectados.setBounds(10, 10, 126, 22);
		Principal.getContentPane().add(conectados);
		
		chat = new TextArea();
		chat.setBounds(259, 49, 155, 153);
		Principal.getContentPane().add(chat);
		chat.setFocusable(false);
		
		Label mensajes = new Label("Mensajeria");
		mensajes.setBounds(259, 10, 112, 22);
		Principal.getContentPane().add(mensajes);
		} catch (UnknownHostException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
}
