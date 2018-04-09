/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Estudiantes
 */
public class Cliente extends Thread implements Runnable{

    private final String clienteConectado;
    private final DataInputStream datosEntrada;
    private final DataOutputStream datosSalida;
    private final Socket cliente;

    private boolean conectado;    
    private Thread hiloLectura;       
    private final Comunicador sistemaServer;
    
    Date date;
    DateFormat dateFormat;
    DateFormat hourFormat;
    

    
    public Cliente(Comunicador aThis, Socket cliente) throws IOException {
        this.sistemaServer = aThis;
        this.cliente = cliente;
        clienteConectado = cliente.getInetAddress().getHostAddress();        
        datosEntrada = new DataInputStream(cliente.getInputStream());
        datosSalida = new DataOutputStream(cliente.getOutputStream());      
        hiloLectura = new Thread(this);   
        conectado = true;
        date = new Date();
        dateFormat = new SimpleDateFormat("YYYYMMDD");
        hourFormat = new SimpleDateFormat("HHmmss");
        //hiloLectura.start();
    }
    
    public String leerMensaje() throws IOException 
    // Es un mensaje que este cliente envía al servidor. Debemos replicarlo a los demás clientes
    {
        byte buffer[] = new byte[256];
        String mensaje;
        datosEntrada.read(buffer);
        mensaje = new String(buffer);
        sistemaServer.getSbMensajes().append(this.sistemaServer.getSistema().getNombrecliente()+" : " + cliente.getInetAddress().getHostAddress() + ": " + mensaje + "\n");
        System.out.println(this.sistemaServer.getSistema().getNombrecliente()+" : " + cliente.getInetAddress().getHostAddress() + ": " + mensaje + "\n");
        // replicamos a los demás clientes este mensaje
        //sistemaServer.enviarMensaje(cliente.getInetAddress().getHostAddress() + ": " + mensaje);7
        if(mensaje.substring(17, 20).equals("JUG"))
        {
            int fila = Integer.parseInt(mensaje.substring(21, 23));
            int columna = Integer.parseInt(mensaje.substring(24, 26));
            int orientacion = Integer.parseInt(mensaje.substring(27, 28));
            this.sistemaServer.getSistema().pintarboton(fila, columna, orientacion);
        }
        if(mensaje.substring(0, 2).equals("OK") && mensaje.length()==6)
        {
            int cerrado = Integer.parseInt(mensaje.substring(3, 4));
            int juegosigue = Integer.parseInt(mensaje.substring(5, 6));
            if(cerrado==0)
            {
                this.sistemaServer.getSistema().setTurno(false);
            }else if(cerrado==2)
            {
                this.sistemaServer.getSistema().setTurno(true);
            }
            if(juegosigue==1)
            {
                System.out.println("Se termina el juego");
            }
            
        }
        return mensaje;
    }
    
    public void enviarMensaje(String msg) throws IOException {
        datosSalida.write(msg.getBytes());
        //mensajes.append("Enviamos: " + msg + "\n");
    }
    
    public void terminarConexiones() throws IOException{
        conectado = false;
        hiloLectura = null;
        datosEntrada.close();
        datosSalida.close();
        cliente.close();
    }

    @Override
    public void run() {
        while(conectado){
            try {
                this.hiloLectura.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                leerMensaje();
            } catch (IOException ex) {
            }
        }
    }
    
    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public String getClienteConectado() {
        return clienteConectado;
    }

    public void enviarMovimiento(int fila, int columna, int orientacion) throws IOException {
        String fil,col;
        if(fila<10)
        {
            fil="0"+fila;
        }else
        {
            fil=""+fila;
        }
        if(columna<10)
        {
            col="0"+columna;
        }else
        {
            col=""+columna;
        }
        enviarMensaje("QDT"+this.dateFormat.format(date)+""+this.hourFormat.format(date)+"JUG,"+fil+","+col+","+orientacion);
        
    }

    public Thread getHiloLectura() {
        return hiloLectura;
    }

    public void setHiloLectura(Thread hiloLectura) {
        this.hiloLectura = hiloLectura;
    }

    void respondermovimiento(int cerrado, int siguejuego) throws IOException {
        enviarMensaje("OK,"+cerrado+","+siguejuego);       
    }

    
    
}
