import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;


public class chat{

	private JFrame chat;
	static JTextField ingreso;
	static TextArea textArea;
	static boolean vacio=true;
	private JTextField informacion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chat window = new chat();
					window.chat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public chat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		chat = new JFrame();
		chat.setBounds(100, 100, 450, 300);
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
		chat.getContentPane().add(informacion);
     	ingreso.addKeyListener( new KeyAdapter(){
         		public void keyPressed( KeyEvent e ){
         			if(e.getKeyCode()==KeyEvent.VK_ENTER)
         			{
         				if(vacio==false)
         				textArea.setText(textArea.getText()+"\n"+ingreso.getText());
         				else
         				{
         					textArea.setText(ingreso.getText());
         					vacio=false;
         				}
         				ingreso.setText("");
         			}
         		}
         		}); 
	}
}
