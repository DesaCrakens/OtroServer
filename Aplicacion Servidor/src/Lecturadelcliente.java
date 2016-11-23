
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Lecturadelcliente extends Thread{
	private OutputStream entrada;
	private InputStream salida;
	private Socket cliente;
	private int numerocliente;
	private int cantidadtotal;
	private String Nombre;
	private int primera;
	public int getCantidadtotal() {
		return cantidadtotal;
	}
	public void setCantidadtotal(int cantidadtotal) {
		this.cantidadtotal = cantidadtotal;
	}
	String mensaje;
	public OutputStream getEntrada() {
		return entrada;
	}
	public void setEntrada(OutputStream entrada) {
		this.entrada = entrada;
	}
	public InputStream getSalida() {
		return salida;
	}
	public void setSalida(InputStream salida) {
		this.salida = salida;
	}
	public Lecturadelcliente(Socket cliente,int tam,String nombre) throws IOException
	{
		super();
		this.cliente=cliente;
		this.Nombre=nombre;
		numerocliente=tam;
		Servidor.clientes[this.numerocliente-1]=new Lecturadelcliente();
		Servidor.clientes[this.numerocliente-1].setCliente(cliente);
		Servidor.clientes[this.numerocliente-1].setName(this.Nombre);
		cantidadtotal=tam;
		this.mensaje="Nada";
		//this.acepte=new Lecturadelcliente[(cantidadtotal)];
			
	}
	public Lecturadelcliente() {
		// TODO Apéndice de constructor generado automáticamente
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public synchronized void run()
	{
		try {
			this.escritura();
			this.lectura();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			//System.out.println("Cliente "+this.numerocliente+" desconectado");
			Servidor.Clientes.setText(Servidor.Clientes.getText()+"\n"+"Cliente "+this.Nombre+" desconectado");
			//System.out.println("Cantidad anterior de clientes: "+Servidor.CantidadClientes);
			Servidor.Clientes.setText(Servidor.Clientes.getText()+"\n"+"Cantidad anterior de clientes: "+Servidor.CantidadClientes);
			Lecturadelcliente aux[]=new Lecturadelcliente[Servidor.CantidadClientes-1];
			int j=0;
			for(int i=0;i<Servidor.CantidadClientes;i++)
			{
				if((this.numerocliente-1)!=i)//NO ES EL Q SE DESCONECTA
					{
					System.out.println(j+" "+i);
					aux[j]=Servidor.clientes[i];
					j++;
					}
				
			}
			Servidor.CantidadClientes--;
			//System.out.println("Cantidad actuales de clientes: "+Servidor.CantidadClientes);
			Servidor.Clientes.setText(Servidor.Clientes.getText()+"\n"+"Cantidad actuales de clientes: "+Servidor.CantidadClientes);
			for(j=0;j<aux.length;j++)
			{
				System.out.println(aux[j].getMensaje());
				Servidor.clientes[j]=aux[j];
			}
			
			String mensaje="";
			for(int i=0;i<Servidor.CantidadClientes;i++)
			{
				mensaje=mensaje+ "Numero de cliente: "+(i+1)+" "+Servidor.clientes[i].getName()+"linea";
			}
			//ACTUALIZO A TODOS LOS QUE SE CONECTARON
			mensaje="0"+"~"+mensaje;
			for(int i=0;i<Servidor.CantidadClientes;i++)
			{
				OutputStream enviar=Servidor.clientes[i].getEntrada();
				DataOutputStream f=new DataOutputStream(enviar);//ES UN MEDIO PARA ESCRIBIR
				try {
					f.writeUTF(mensaje);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//EL UTF
			}
		}
	}
	public int getNumerocliente() {
		return numerocliente;
	}
	public void setNumerocliente(int numerocliente) {
		this.numerocliente = numerocliente;
	}
	public void escritura() throws IOException
	{
		//cuando se conecta el cliente le mando el mensaje
		//System.out.println("Cliente conectado");
		Servidor.Clientes.setText(Servidor.Clientes.getText()+"\n"+"Cliente "+(this.Nombre)+" conectado");
		
		this.entrada=cliente.getOutputStream();
		Servidor.clientes[this.numerocliente-1].setEntrada(this.entrada);
		DataOutputStream e=new DataOutputStream(entrada);//ES UN MEDIO PARA ESCRIBIR
		
		//String recibir="Hola cliente "+numerocliente;
		String mensaje="";
		
		//LE MANDO LA LISTA DE LOS CONECTADOS ACTUALES AL CLIENTE Q SE CONECTO
		for(int i=0;i<Servidor.CantidadClientes;i++)
		{
			mensaje=mensaje+ "Numero de cliente: "+(i+1)+" "+Servidor.clientes[i].getName()+"linea";
		}
		e.writeUTF(mensaje);
		e.write(Servidor.CantidadClientes);
		//ACTUALIZO A TODOS LOS QUE SE CONECTARON
		mensaje="0"+"~"+mensaje;
		for(int i=0;i<Servidor.CantidadClientes;i++)
		{
			OutputStream enviar=Servidor.clientes[i].getEntrada();
			DataOutputStream f=new DataOutputStream(enviar);//ES UN MEDIO PARA ESCRIBIR
			f.writeUTF(mensaje);//EL UTF
		}

		
	}
	public void lectura() throws IOException
	{
		
		while(true)
		{
			this.salida=cliente.getInputStream();
			Servidor.clientes[this.numerocliente-1].setSalida(this.salida);
			DataInputStream s=new DataInputStream(salida);
			String recibido=s.readUTF();
			String[]datos=recibido.split("~");
			this.setMensaje(this.Nombre+": "+datos[1]);
				Servidor.chat.setText(Servidor.chat.getText()+"\n"+this.getMensaje());		
				/*
				for(int i=0;i<Servidor.CantidadClientes;i++)
				{
					if(i!=this.numerocliente-1)
					{
					OutputStream enviar=Servidor.clientes[i].getEntrada();
					DataOutputStream e=new DataOutputStream(enviar);//ES UN MEDIO PARA ESCRIBIR
					e.writeUTF("1"+"~"+this.mensaje);//EL UTF
					}
				}*/
				OutputStream enviar=Servidor.clientes[Integer.parseInt(datos[0])-1].getEntrada();
				DataOutputStream e=new DataOutputStream(enviar);//ES UN MEDIO PARA ESCRIBIR
				e.writeUTF("1"+"~"+this.mensaje);//EL UTF
			
		}

	}
	public Socket getCliente() {
		return cliente;
	}
	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}
	

}
