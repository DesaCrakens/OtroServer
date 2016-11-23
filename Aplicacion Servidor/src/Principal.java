import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Principal extends Thread {
	
	public Principal(){
		
		super();
	}
	public synchronized void run()	
	{
		Lecturadelcliente hilos[]=new Lecturadelcliente[100];
		Socket cliente[]=new Socket[100];
		try
		{

			ServerSocket server=new ServerSocket(11000); //NUMERO DE PUERTO
			int i;
			int j;
			for(i=0;i<10;i++)
			{
	
				cliente[i]=server.accept();
				
				//SE CONECTO ESE CLIENTE
				//ACA RECIBO EL NOMBRE DEL CLIENTE
				Servidor.CantidadClientes++;
				InputStream aux= cliente[i].getInputStream();//PARA TEXTO, ENTRADA DE TEXTO
				DataInputStream r=new DataInputStream(aux);
				String nombre=r.readUTF();
				hilos[i]=new Lecturadelcliente(cliente[i],Servidor.CantidadClientes,nombre);//creo un hilo
				hilos[i].start();
				
			}
				 
		}
		catch(Exception e)
		{
			e.printStackTrace();//Mensaje de error standar
		}
	}

}
