package presentacion;

import java.awt.Color;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import logica.Comunicador;
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
        while (running) {
            try {
                this.hilodibujar.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
            dibujar();
        }

    }

    public void dibujar() {
        this.ventana.getjPTablero().removeAll();
        for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
            this.ventana.getjPTablero().add(this.sistema.getCuadros().get(i));
        }

        if (this.sistema.getComunicador().isConectado()) {
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
                JButton boton = (JButton) this.sistema.getCuadros().get(i).getComponent(j);
                boton.addActionListener(this.ventana.getControlador());
            }
        }
    }

    public void imprimir() {
        for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
            System.out.println("_____________________________________________________________________");
            System.out.println("fila : " + this.sistema.getCuadros().get(i).getFila());
            System.out.println("columna : " + this.sistema.getCuadros().get(i).getColumna());
            System.out.println("izq : " + this.sistema.getCuadros().get(i).getBotonizq().getBackground().toString());
            System.out.println("der : " + this.sistema.getCuadros().get(i).getBotonder().getBackground().toString());
        }
    }

    void conectarse() {
        try {

            this.ventana.getjBConectarse().setEnabled(false);
            this.ventana.getjTNombre().setEnabled(false);
            this.ventana.getjTDireccionIP().setEnabled(false);
            this.ventana.getjTPuerto().setEnabled(false);
            this.sistema.setNombrecliente(this.ventana.getjTNombre().getText());
            this.sistema.getComunicador().conectar(this.ventana.getjTDireccionIP().getText(), Integer.parseInt(this.ventana.getjTPuerto().getText())); //se tiene que modificar, rompe el patron

        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "El servidor no existe");
        } catch (SocketTimeoutException ex) {
            JOptionPane.showMessageDialog(null, "El servidor no esta disponible");
        } catch (IOException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
