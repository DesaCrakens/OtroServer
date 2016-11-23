

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LecturadelServidor extends Thread {
	
	OutputStream entrada;
	InputStream salida;
	Socket cliente;
	int numerocliente;
	String mensaje;
	public LecturadelServidor(Socket cliente)
	{
		super();
		this.cliente=cliente;
	}
	public synchronized void run()
	{
		try {
			this.leer();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
	}
	public void leer() throws IOException
	{
		while(true)
		{
		InputStream aux= cliente.getInputStream();//PARA TEXTO, ENTRADA DE TEXTO
		DataInputStream r=new DataInputStream(aux);
		EscrituraenelServidor l=new EscrituraenelServidor(cliente);
		String mensaje=r.readUTF();
		String[] protocolo=mensaje.split("~");//1 es para actualizar clientes conectados
		if(protocolo[0].compareTo("0")==0)//es un protocolo para actualizar
		{
			Cliente.listaclientes.clear();
			
			String[] agregar=protocolo[1].split("linea");
			for(int j=0;j<agregar.length;j++)
			Cliente.listaclientes.add(agregar[j]);
		}
		else if(protocolo[0].compareTo("1")==0)//Escribo un mensaje comun
		Cliente.textArea.setText(Cliente.textArea.getText()+"\n"+protocolo[1]);
		}
		
	}

}
