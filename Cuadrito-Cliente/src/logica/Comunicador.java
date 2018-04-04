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
 * @author ASUS
 */
public class Comunicador implements Runnable {

    private String nombreHost;
    private int puerto;
    private Socket servidor;
    private StringBuffer mensajes;
    private boolean conectado;

    private DataInputStream datosEntrada;
    private DataOutputStream datosSalida;
    private boolean recibeMensaje;
    private Thread hiloLectura;

    private boolean comandoValido;
    private Sistema sistema;

    public Comunicador(Sistema sistema) {
        this.sistema=sistema;
        conectado = false;
        mensajes = new StringBuffer();
    }

    public String getNombreHost() {
        return nombreHost;
    }

    public int getPuerto() {
        return puerto;
    }

    public StringBuffer getMensajes() {
        return mensajes;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void conectar(String host, int puerto) throws IOException {
        // 1. Establecer contacto
        servidor = new Socket(host, puerto);

        //2. Capturar flujos(stream)
        datosEntrada = new DataInputStream(servidor.getInputStream());
        datosSalida = new DataOutputStream(servidor.getOutputStream());

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
        System.out.println("Fecha: " + dateFormat.format(date));

        DateFormat hourFormat = new SimpleDateFormat("HHmmss");
        System.out.println("Hora: " + hourFormat.format(date));

        enviarMensaje("QDT"+dateFormat.format(date)+""+hourFormat.format(date)+"INI");//se envia el mensaje con la cabecera para aceptar la conexion
        String mensaje = leermensaje();//se lee y valida el mensaje del servidor para saber si se acepto la conexion
        validarComando(mensaje);//se valida si el mensaje es valido
        
        //si el mensaje es valido y la respuesta es OK se valida la conexion y se crea el tablero
        if (this.comandoValido && "OK".equals(mensaje.substring(0, 2))) {
            System.out.println("La conexion es valida");
            conectado = true;
            this.mensajes.append("Se realizo la conexion con " + host + ":" + puerto + "\n");
            
            System.out.println("filas "+mensaje.substring(3, 5));
            System.out.println("Columnas "+mensaje.substring(6, 8));
            this.sistema.crearcuadros(Integer.parseInt(mensaje.substring(3, 5)), Integer.parseInt(mensaje.substring(6, 8)));
        //si alguna de las condiciones es falsa se desconecta del servidor
        } else {
            System.out.println("La conexion fue rechazada");
            desconectar();
        }
        //hiloLectura = new Thread(this);
        //hiloLectura.start();
    }
    
    //envia el mensaje al servidor 
    public void enviarMensaje(String msg) throws IOException {
        datosSalida.write(msg.getBytes());
        mensajes.append(">: " + msg + "\n");
        System.out.println(">: " + msg + "\n");
    }
    
    //lee los mensajes
    public String leermensaje() throws IOException {
        byte buffer[] = new byte[256];
        String msg;

        datosEntrada.read(buffer);
        // si llego aqui, es porque algo llego
        msg = new String(buffer);
        mensajes.append("<: " + msg + "\n");
        recibeMensaje = true;
        return msg;
    }
    
    //hilo para leer mensajes, no se esta iniciando porque no se esta utilizando
    //conectando multiples usuarios
    @Override
    public void run() {

        while (conectado) {
            try {
                validarComando(leermensaje());
            } catch (IOException ex) {
                Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //permite desconectarse del servidor
    public void desconectar() throws IOException {
        conectado = false;
        datosEntrada.close();
        datosSalida.close();
        servidor.close();
    }

    public boolean isRecibeMensaje() {
        return recibeMensaje;
    }

    public void setRecibeMensaje(boolean recibeMensaje) {
        this.recibeMensaje = recibeMensaje;
    }

    private void validarComando(String mensaje) {
        String fecha = mensaje.substring(3, 11);
        String hora = mensaje.substring(11, 14);
        String comando = mensaje.substring(17, 20);
        String param = null;
        if (mensaje.substring(0, 3).equals("QDT")) {
            try {
                int fechaInt = Integer.parseInt(fecha);
                int horaInt = Integer.parseInt(hora);
                if (comando.equals("INI")
                        || comando.equals("SNM")
                        || comando.equals("TUR")
                        || comando.equals("JUG")) {
                    param = mensaje.substring(20);
                    comandoValido = true;
                }
            } catch (NumberFormatException e) {
                comandoValido = false;
            }
        } else if (mensaje.substring(0, 2).equals("OK")
                || mensaje.substring(0, 2).equals("NK")) {
            comandoValido = true;
            param = mensaje.substring(3);
        } else {
            comandoValido = false;
        }
        System.out.println(param);

    }
}
