
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EscrituraenelServidor extends Thread{
	OutputStream entrada;
	InputStream salida;
	Socket cliente;
	static DataOutputStream e;
	static OutputStream Salida;
	public EscrituraenelServidor(Socket cliente)
	{
		super();
		this.cliente=cliente;
	}
	public synchronized void run()
	{
		try {
			this.escritura();
			this.leer();
		} catch (IOException | InterruptedException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
	}
	public void leer() throws IOException, InterruptedException
	{
		InputStream aux= cliente.getInputStream();//PARA TEXTO, ENTRADA DE TEXTO
		DataInputStream r=new DataInputStream(aux);
		EscrituraenelServidor l=new EscrituraenelServidor(cliente);
		Cliente.textArea.setText(Cliente.textArea.getText()+"\n"+r.readUTF());
		//System.out.println(r.readUTF());
		
	}
	public void escritura() throws IOException
	{
		Cliente.textArea.setText("-----------------EMPIEZA EL CHAT-------------------");
		Cliente.textArea.setText(Cliente.textArea.getText()+"\n"+"Escriba *desconectar* o *DESCONECTAR* para salir de la aplicacion");
		while(true)
		{
		Salida=cliente.getOutputStream();
		e=new DataOutputStream(Salida);//ES UN MEDIO PAR
		String Mensaje="Entrar";
		Scanner sc;
		sc = new Scanner(System.in);
		//Mensaje=sc.nextLine();
		//if((Mensaje.compareTo("*desconectar*"))==0||(Mensaje.compareTo("*DESCONECTAR*"))==0)
		//	System.exit(0);
		//e.writeUTF(Mensaje);
		}
	}

}


