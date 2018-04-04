package presentacion;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import logica.GrupoBotones;
import logica.Sistema;

public class Modelo implements Runnable {

    private Sistema sistema;
    private Vista ventana;
    private Thread hilodibujar;
    private boolean running;

    public Modelo() {
        this.hilodibujar = new Thread(this);
        this.running = false;
    }

    public Sistema getSistema() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }

    public Vista getVentana() {
        if (ventana == null) {
            ventana = new Vista(this);
        }
        return ventana;
    }

    public void iniciar() {
        this.getSistema();
        this.getVentana().setVisible(true);
        this.running = true;
        this.hilodibujar.start();
    }

    @Override
    public void run() {
        /*
        while(getSistema().isConectado()){
            //recibirMensajes();
        }
         */
        while (running) {
            try {
                this.hilodibujar.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
            dibujar();
        }

    }

    /*
    private void recibirMensajes() {
        if (sistema.getCliente() != null)
        {
            String host = sistema.getCliente().
                    getInetAddress().getHostAddress();
            String mensaje = sistema.getMensaje();

            boolean comandoValido = sistema.getComandoValido();

            ventana.getLb_ipRemota().setText("IP cliente: " + host);
            ventana.getRespuesta().setText("Respuesta:\n" +
                    comandoValido + "\n" + mensaje);

        }
    }

    public void enviarMensaje() {
        try {
            sistema.enviarMensaje(
                    getVentana().getTxf_ipRemota().getText(),
                    getVentana().getMensaje().getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void esperarConexion() {
        try {
            getSistema().ActivarConexion();
            getVentana().getEscuchar().setText("Detener");
            getVentana().getLb_ipRemota().setText("Esperando conexion.");
            getVentana().getTxf_ipRemota().setEnabled(true);
            getVentana().getTxf_ipRemota().setEditable(true);
            getVentana().getMensaje().setEnabled(true);
            getVentana().getMensaje().setEditable(true);
            getVentana().getEnviar().setEnabled(true);
            hiloDibujo = new Thread(this);
            hiloDibujo.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminarConexion() {
        try {
            hiloDibujo = null;
            getSistema().setConectado(false);
            getSistema().detenerConexion();
            getVentana().getEscuchar().setText("Escuchar");
            getVentana().getLb_ipRemota().setText("IP Remota: 000.000.000.000");
            getVentana().getRespuesta().setText("");
            getVentana().getTxf_ipRemota().setEnabled(false);
            getVentana().getTxf_ipRemota().setEditable(false);
            getVentana().getMensaje().setEnabled(false);
            getVentana().getMensaje().setEditable(false);
            getVentana().getEnviar().setEnabled(false);
        } catch (IOException ex) {
        }
    }
     */
    public void dibujar() {
        this.ventana.getjPTablero().removeAll();
        for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
            this.ventana.getjPTablero().add(this.sistema.getCuadros().get(i));
        }
        
        if(this.sistema.getComunicador().isConectado())
        {
            this.ventana.getjBConectarse().setBackground(Color.GREEN);
        }
        this.ventana.getjTMensajes().setText(this.sistema.getComunicador().getMensajes().toString());

        this.ventana.getjPTablero().repaint();
        this.ventana.getjPTablero().updateUI();
    }

    public void generartablero(int filas, int columnas) {        
               
        this.sistema.borrarcuadros();
        this.sistema.crearcuadros(filas, columnas);
        
        for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
            for (int j = 0; j < this.sistema.getCuadros().get(i).getComponentCount(); j++) {
                JButton boton = (JButton)this.sistema.getCuadros().get(i).getComponent(j);
                boton.addActionListener(this.ventana.getControlador());
            }
        }
    }

    public void imprimir() {
        for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
            System.out.println("_____________________________________________________________________");
            System.out.println("fila : "+this.sistema.getCuadros().get(i).getFila());
            System.out.println("columna : "+this.sistema.getCuadros().get(i).getColumna());
            System.out.println("izq : "+this.sistema.getCuadros().get(i).getBotonizq().getBackground().toString());
            System.out.println("der : "+this.sistema.getCuadros().get(i).getBotonder().getBackground().toString());
        }
    }

    void conectarse() {
        try {
            this.sistema.getComunicador().conectar(this.ventana.getjTDireccionIP().getText(), Integer.parseInt(this.ventana.getjTPuerto().getText()));
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
