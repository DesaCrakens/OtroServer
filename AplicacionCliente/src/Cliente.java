import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Window.Type;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.awt.Frame;
import javax.swing.JTextPane;
import javax.swing.JList;
import java.awt.List;


public class Cliente {

	static JFrame Principal;
	static JFrame chat;
	static String Ip;
	static int numerocliente;
	static String Puerto;
	static String nombre="";
	private JButton salir;
	private JButton conectar;
	private TextField ip;
	private TextField puerto;
	static Cliente window;
	static int empezo=0;
	static JTextField ingreso;
	static JTextField informacion;
	static TextArea textArea;
	static TextField name;
	static List listaclientes;
	static boolean vacio=true;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window = new Cliente();
					window.Principal.setVisible(true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Cliente() throws Exception {
		Ip="";
		Puerto="";
		if(empezo==0)
		initialize();
		else 
		{
			iniciarchat();
		}
	}

	private void initialize() throws Exception {
		Principal = new JFrame();
		Principal.getContentPane().setForeground(Color.BLACK);
		Principal.setForeground(Color.LIGHT_GRAY);
		Principal.setTitle("Chat");
		Principal.setBounds(100, 100, 450, 300);
		Principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Principal.getContentPane().setLayout(null);
		Puerto=new String();
		conectar = new JButton("Conectar");
		conectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(puerto.getText()!=""&&ip.getText()!="")
				{
					Puerto=puerto.getText();
					Ip=ip.getText();
					Cliente.nombre=name.getText();
					
					try
					{
				    empezo=1;
				   
					Socket cliente= new Socket(Ip,Integer.parseInt(Puerto));//IP Y PUERTO
					 window.Principal.setVisible(false);
					 window=new Cliente();
				     window.chat.setVisible(true);	
				     chat.setTitle(Cliente.nombre);
					JOptionPane.showMessageDialog(Principal, "CONEXION EXITOSA","Resultado conexion", 1);
					
					//envio mi nombre
					DataOutputStream e=new DataOutputStream(cliente.getOutputStream());//ES UN MEDIO PAR
					e.writeUTF(Cliente.nombre);
					
					//
					
					
					InputStream aux= cliente.getInputStream();//PARA TEXTO, ENTRADA DE TEXTO
					DataInputStream r=new DataInputStream(aux);
					EscrituraenelServidor es=new EscrituraenelServidor(cliente);
					LecturadelServidor ls=new LecturadelServidor(cliente);
					//listaclientes.setToolTipText(" ");
					String mensaje=r.readUTF();
					numerocliente=r.read();
					listaclientes.add("Clientes conectados");
					String[] datos;
					datos=mensaje.split("linea");
					for(int i=0;i<datos.length;i++)
					{
						String agregar=datos[i];
						listaclientes.add(agregar);
					}
					listaclientes.select(1);
					es.start();
					ls.start();
					
					}catch(Exception e)
					{
						JOptionPane.showMessageDialog(Principal, "ERROR DE CONEXION","Resultado conexion", 0);	
					}
					
				}

			}
		});
		conectar.setBounds(168, 146, 89, 23);
		Principal.getContentPane().add(conectar);
		
		salir = new JButton("Salir");
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		salir.setBounds(168, 180, 89, 23);
		Principal.getContentPane().add(salir);
		
		ip = new TextField();
		ip.setBounds(75, 102, 89, 22);
		Principal.getContentPane().add(ip);
		name = new TextField();
		name.setBounds(169, 30, 89, 22);
		Principal.getContentPane().add(name);
		
		puerto = new TextField();
		puerto.setBounds(263, 102, 89, 22);
		Principal.getContentPane().add(puerto);
		
		Label direccionip = new Label("Ingrese la direccion ip");
		direccionip.setBounds(64, 61, 111, 22);
		Principal.getContentPane().add(direccionip);
		
		Label direccionpuerto = new Label("Ingrese el puerto");
		direccionpuerto.setBounds(263, 61, 100, 22);
		Principal.getContentPane().add(direccionpuerto);

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void iniciarchat() {
		chat = new JFrame();
		chat.setBounds(100, 100, 745, 294);
		chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chat.getContentPane().setLayout(null);
		
		textArea = new TextArea();
		textArea.setBounds(10, 56, 414, 169);
		chat.getContentPane().add(textArea);
		textArea.setFocusable(false);
		
		ingreso = new JTextField();
		ingreso.setBounds(98, 231, 247, 20);
		chat.getContentPane().add(ingreso);
		ingreso.setColumns(10);
		
		informacion = new JTextField();
		informacion.setColumns(10);
		informacion.setBounds(10, 30, 414, 20);
		informacion.setFocusable(false);
		chat.getContentPane().add(informacion);
		
		Label clientes = new Label("Clientes Conectados");
		clientes.setBounds(531, 28, 113, 22);
		chat.getContentPane().add(clientes);
		

        listaclientes = new List();
		listaclientes.setBounds(461, 56, 247, 169);
		chat.getContentPane().add(listaclientes);
     	ingreso.addKeyListener( new KeyAdapter(){
         		
     	public void keyPressed( KeyEvent e ){
         			if(e.getKeyCode()==KeyEvent.VK_ENTER)
         			{
         					String mensaje=listaclientes.getSelectedItem();
         					String[]datos=mensaje.split(" ");
         			
         					try {
         						if(!listaclientes.isIndexSelected(numerocliente-1))
         						{
         							textArea.setText(textArea.getText()+"\n"+ingreso.getText());
								EscrituraenelServidor.e.writeUTF(datos[3]+"~"+ingreso.getText());
         						}
								else 
         							JOptionPane.showMessageDialog(Principal, "No se puede enviar mensajes a usted mismo","Error", 0);	
							} catch (IOException e1) {
								// TODO Bloque catch generado automáticamente
								e1.printStackTrace();
							}
         					ingreso.setText("");
         			}
         		}
         		});
         		
	}
}
