/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Comunicador implements Runnable {

    static private final int PUERTO_SALIDA = 9090;
    static private final int TIMEOUT = 3000; // 3 segundos

    private ServerSocket server;
    private Socket cliente;
    private int puerto;
    private Thread hiloConexiones;
    private boolean esperandoConexiones;

    private StringBuffer sbMensajes;
    private ArrayList<Cliente> listaClientes;

    private boolean comandoValido;
    private boolean clienteconectado;

    private Sistema sistema;

    public Comunicador(Sistema sistema) {
        listaClientes = new ArrayList<Cliente>();
        sbMensajes = new StringBuffer();
        esperandoConexiones = true;
        this.sistema = sistema;
    }

    public void activarEsperaConexiones() throws IOException {
        server = new ServerSocket(PUERTO_SALIDA);
        hiloConexiones = new Thread(this);
        hiloConexiones.start();
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public StringBuffer getSbMensajes() {
        return sbMensajes;
    }

    public void enviarMensaje(String msg) throws IOException {
        // Enviar este mensaje a todos los clientes
        Cliente host;
        sbMensajes.append(this.sistema.getNombreservidor() + " : " + msg + "\n");
        for (int c = 0; c < listaClientes.size(); c++) {
            host = listaClientes.get(c);
            host.enviarMensaje(msg);
        }
    }

    @Override
    public void run() {
        while (esperandoConexiones && this.listaClientes.size() == 0) {
            System.out.println("Permitiendo conexion");
            try {
                cliente = server.accept(); //espera a que alguien se conecte
                server.setSoTimeout(TIMEOUT);
                Cliente nuevoCliente = new Cliente(this, cliente);
                listaClientes.add(nuevoCliente);
                //nuevoCliente.start();
                validarComando(this.listaClientes.get(0).leerMensaje());
                if (this.comandoValido) {
                    this.clienteconectado = true;

                    enviarMensaje("OK," + this.sistema.getFilas() + "," + this.sistema.getColumnas());
                    System.out.println("La conexion fue aceptada");
                    comunicaciondejuego();
                } else {
                    enviarMensaje("NK");
                    detenerConexiones();
                    System.out.println("La conexion fue rechazada");
                }

            } catch (IOException ex) {
            }
        }
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

    private void comunicaciondejuego() throws IOException {
        String mensaje;
        mensaje = this.listaClientes.get(0).leerMensaje();
        validarComando(mensaje);
        if (this.comandoValido) {
            System.out.println(mensaje.substring(20, mensaje.length()));
            this.sistema.setNombrecliente(mensaje.substring(20));
            enviarMensaje("OK," + this.sistema.getNombreservidor());
        }

        mensaje = this.listaClientes.get(0).leerMensaje();
        validarComando(mensaje);
        if (this.comandoValido) {
            int numero = (int) (Math.random() * 2) + 1;
            enviarMensaje("OK," + numero);
        }

        System.out.println("Se debe implementar la asignacion de turno desde el servidor");
        System.out.println("Se deben recibir los comandos de juego");
    }

    public boolean isEsperandoConexiones() {
        return esperandoConexiones;
    }

    public void setEsperandoConexiones(boolean esperandoConexiones) {
        this.esperandoConexiones = esperandoConexiones;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void detenerConexiones() throws IOException {
        Cliente host;

        for (int c = 0; c < listaClientes.size(); c++) {
            host = listaClientes.get(c);
            host.terminarConexiones();
        }
    }

    public boolean isClienteConectado() {
        return this.clienteconectado;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }
    
    
}
