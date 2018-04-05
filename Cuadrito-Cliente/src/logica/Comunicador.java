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

    Date date;
    DateFormat dateFormat;
    DateFormat hourFormat;

    public Comunicador(Sistema sistema) {
        this.sistema = sistema;
        conectado = false;
        mensajes = new StringBuffer();
        date = new Date();
        dateFormat = new SimpleDateFormat("YYYYMMDD");
        hourFormat = new SimpleDateFormat("HHmmss");
    }

    public void conectar(String host, int puerto) throws IOException {

        // 1. Establecer contacto
        servidor = new Socket(host, puerto);
        servidor.setSoTimeout(3000);

        //2. Capturar flujos(stream)
        datosEntrada = new DataInputStream(servidor.getInputStream());
        datosSalida = new DataOutputStream(servidor.getOutputStream());

        hiloLectura = new Thread(this);
        //hiloLectura.start();
        enviarcabecera();
    }

    //envia el mensaje al servidor 
    public void enviarMensaje(String msg) throws IOException {
        datosSalida.write(msg.getBytes());
        mensajes.append(this.sistema.getNombrecliente() + " : " + msg + "\n");
        System.out.println(this.sistema.getNombrecliente() + " : " + msg + "\n");
    }

    //lee los mensajes
    public String leermensaje() throws IOException {
        byte buffer[] = new byte[256];
        String msg;
        datosEntrada.read(buffer);
        // si llego aqui, es porque algo llego
        msg = new String(buffer);
        mensajes.append(this.sistema.getNombreservidor() + " : " + msg + "\n");
        recibeMensaje = true;
        if (msg.substring(17, 20).equals("JUG")) {
            int fila = Integer.parseInt(msg.substring(21, 23));
            int columna = Integer.parseInt(msg.substring(24, 26));
            int orientacion = Integer.parseInt(msg.substring(27, 28));
            this.sistema.pintarboton(fila, columna, orientacion);
        }

        if (msg.substring(0, 2).equals("OK") && msg.length() == 6) {
            int cerrado = Integer.parseInt(msg.substring(3, 4));
            int juegosigue = Integer.parseInt(msg.substring(5, 6));
            if (cerrado == 0) {
                this.sistema.setTurno(false);
            } else if (cerrado == 2) {
                this.sistema.setTurno(true);
            }
            if (juegosigue == 1) {
                System.out.println("Se termina el juego");
            }

        }
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
        System.out.println("Parametro : " + param);

    }

    public void enviarcabecera() throws IOException {

        enviarMensaje("QDT" + dateFormat.format(date) + "" + hourFormat.format(date) + "INI");//se envia el mensaje con la cabecera para aceptar la conexion
        String mensaje = leermensaje();//se lee y valida el mensaje del servidor para saber si se acepto la conexion
        validarComando(mensaje);//se valida si el mensaje es valido

        //si el mensaje es valido y la respuesta es OK se valida la conexion y se crea el tablero
        if (this.comandoValido && "OK".equals(mensaje.substring(0, 2))) {
            System.out.println("La conexion es valida");
            conectado = true;
            this.mensajes.append("Se realizo la conexion con " + servidor.getInetAddress().getHostName() + ":" + servidor.getPort() + "\n");

            System.out.println("filas " + mensaje.substring(3, 5));
            System.out.println("Columnas " + mensaje.substring(6, 8));
            this.sistema.crearcuadros(Integer.parseInt(mensaje.substring(3, 5)), Integer.parseInt(mensaje.substring(6, 8)));

            comunicaciondejuego();//aqui se counica con el servidor para enviar nombre, asignar turno y comunicar acciones de juego

            //si alguna de las condiciones es falsa se desconecta del servidor
        } else {
            System.out.println("La conexion fue rechazada");
            desconectar();
        }
    }

    public void comunicaciondejuego() throws IOException {
        String mensaje;

        enviarMensaje("QDT" + dateFormat.format(date) + "" + hourFormat.format(date) + "SNM" + this.sistema.getNombrecliente());//se envia el mensaje con nombre de usuario
        mensaje = leermensaje();//se lee el mensaje con el nombre del servidor
        validarComando(mensaje);
        this.sistema.setNombreservidor(mensaje.substring(3));
        System.out.println(mensaje.substring(3, mensaje.length()));

        enviarMensaje("QDT" + dateFormat.format(date) + "" + hourFormat.format(date) + "TUR");//se envia el mensaje para saber el turno
        mensaje = leermensaje();//se lee el mensaje del servidor con el turno para el cliente
        validarComando(mensaje);
        if ("1".equals(mensaje.substring(3, 4))) {
            this.sistema.setTurno(true);
            this.mensajes.append("Inicia el Juego : Es turno de " + this.sistema.getNombrecliente() + "\n");
            this.hiloLectura.start();
        } else {
            this.sistema.setTurno(false);
            this.mensajes.append("Inicia el Juego : Es turno de " + this.sistema.getNombreservidor() + "\n");
            this.hiloLectura.start();
        }
        System.out.println(mensaje.substring(3, mensaje.length()));

        servidor.setSoTimeout(0);
        System.out.println("Se deben recibir los comandos de juego");

    }

    public void enviarMovimiento(int fila, int columna, int orientacion) throws IOException {
        String fil, col;
        if (fila < 10) {
            fil = "0" + fila;
        } else {
            fil = "" + fila;
        }
        if (columna < 10) {
            col = "0" + columna;
        } else {
            col = "" + columna;
        }
        enviarMensaje("QDT" + this.dateFormat.format(date) + "" + this.hourFormat.format(date) + "JUG," + fil + "," + col + "," + orientacion);

    }

    public void respondermovimiento(int cerrado, int siguejuego) throws IOException {
        enviarMensaje("OK," + cerrado + "," + siguejuego);
    }

    public boolean isRecibeMensaje() {
        return recibeMensaje;
    }

    public void setRecibeMensaje(boolean recibeMensaje) {
        this.recibeMensaje = recibeMensaje;
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
}
