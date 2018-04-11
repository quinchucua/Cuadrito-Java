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
                this.hilodibujar.sleep(500);
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
            if (this.sistema.isTurno() == false) {
                GrupoBotones gb = this.sistema.getCuadros().get(i);
                gb.getBotoncentral().setEnabled(false);
                gb.getBotonarriba().setEnabled(false);
                gb.getBotonizq().setEnabled(false);
                gb.getBotonabajo().setEnabled(false);
                gb.getBotonder().setEnabled(false);
            } else {
                GrupoBotones gb = this.sistema.getCuadros().get(i);
                gb.getBotoncentral().setEnabled(true);
                gb.getBotonarriba().setEnabled(true);
                gb.getBotonizq().setEnabled(true);
                gb.getBotonabajo().setEnabled(true);
                gb.getBotonder().setEnabled(true);

            }
        }

        if (this.sistema.getComunicador().isConectado()) {
            this.ventana.getjBConectarse().setBackground(Color.GREEN);
        } else {
            this.ventana.getjBConectarse().setEnabled(true);
            this.ventana.getjTNombre().setEnabled(true);
            this.ventana.getjTDireccionIP().setEnabled(true);
            this.ventana.getjTPuerto().setEnabled(true);
        }

        if (this.sistema.isTablerocreado()) {
            agregarcontrolador();
            this.sistema.setTablerocreado(false);
        }

        if (this.sistema.getJuegosigue() == 1) {
            if (this.sistema.getPuntoscliente() == this.sistema.getPuntosservidor()) {
                JOptionPane.showMessageDialog(null, "Juego Terminado : Empate");
                this.sistema.setJuegosigue(0);
            } else if (this.sistema.getPuntoscliente() < this.sistema.getPuntosservidor()) {
                JOptionPane.showMessageDialog(null, "Juego Terminado : Gana : " + this.sistema.getNombrecliente());
                this.sistema.setJuegosigue(0);
            } else if (this.sistema.getPuntoscliente() > this.sistema.getPuntosservidor()) {
                JOptionPane.showMessageDialog(null, "Juego Terminado : Gana : " + this.sistema.getNombreservidor());
                this.sistema.setJuegosigue(0);
            }
            
            this.sistema.getComunicador().getHiloLectura().stop();
            this.sistema=null;
            this.sistema = getSistema();
            this.ventana.getjBConectarse().setBackground(null);
            this.ventana.getjBConectarse().setEnabled(true);
            this.ventana.getjTNombre().setEnabled(true);
            this.ventana.getjTDireccionIP().setEnabled(true);
            this.ventana.getjTPuerto().setEnabled(true);

        }
        this.ventana.getjTMensajes().setText(this.sistema.getComunicador().getMensajes().toString());

        this.ventana.getjPTablero().repaint();
        this.ventana.getjPTablero().updateUI();

    }

    public void agregarcontrolador() {
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

            this.sistema.getComunicador().conectar(this.ventana.getjTDireccionIP().getText(), Integer.parseInt(this.ventana.getjTPuerto().getText()));
            this.ventana.getjBConectarse().setEnabled(false);
            this.ventana.getjTNombre().setEnabled(false);
            this.ventana.getjTDireccionIP().setEnabled(false);
            this.ventana.getjTPuerto().setEnabled(false);
            this.sistema.setNombrecliente(this.ventana.getjTNombre().getText());

        } catch (ConnectException ex) {
            JOptionPane.showMessageDialog(null, "El servidor no existe");
        } catch (SocketTimeoutException ex) {
            JOptionPane.showMessageDialog(null, "El servidor no esta disponible");
        } catch (IOException ex) {
            Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void pintarboton(int fila, int columna, int orientacion) {
        System.out.println("Click en boton");

        int cierracelda = 0;
        int malajugada = 0;//indica si el boton ya habia cambiado de color

        this.sistema.setPuntoscliente(0);
        this.sistema.setPuntosservidor(0);

        //busca el grupo correspondiente a la fila y columna
        for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
            GrupoBotones gb = this.sistema.getCuadros().get(i);

            //caso en el que la fila y columna no corresponden
            if (gb.getFila() == fila && gb.getColumna() == columna) {
                if (orientacion == 0) {

                    if (gb.getBotonarriba().getBackground() == Color.red) {
                        malajugada = 1;
                    } else {
                        gb.getBotonarriba().setBackground(Color.red);
                    }

                }
                if (orientacion == 1) {
                    if (gb.getBotonder().getBackground() == Color.red) {
                        malajugada = 1;
                    } else {
                        gb.getBotonder().setBackground(Color.red);
                    }

                }
                if (orientacion == 2) {
                    if (gb.getBotonabajo().getBackground() == Color.red) {
                        malajugada = 1;
                    } else {
                        gb.getBotonabajo().setBackground(Color.red);
                    }
                }
                if (orientacion == 3) {
                    if (gb.getBotonizq().getBackground() == Color.red) {
                        malajugada = 1;
                    } else {
                        gb.getBotonizq().setBackground(Color.red);
                    }
                }
            }
            //verifica si la celda fue cerrada
            if (malajugada != 1 && gb.getBotonarriba().getBackground() == Color.red && gb.getBotonder().getBackground() == Color.red && gb.getBotonabajo().getBackground() == Color.red && gb.getBotonizq().getBackground() == Color.red) {
                if (gb.getBotoncentral().getBackground() != Color.red) {
                    gb.getBotoncentral().setBackground(Color.red);
                    gb.getBotoncentral().setBorder(null);
                    cierracelda = 2;
                    this.sistema.setTurno(true);
                    //si no cerro ninguna celda hay cambio de turno
                }
                /*
                else {
                    cierracelda = 0;
                    this.sistema.setTurno(false);
                }
                 */
            }

            //verifica cada grupo para saber si el boton central ya cambio de color e identificar si el juego sigue
            if (gb.getBotoncentral().getBackground() != Color.red) {
                this.sistema.setJuegosigue(0);
            } else {
                if (gb.getBotoncentral().getBorder() != null) {
                    this.sistema.setPuntoscliente(this.sistema.getPuntoscliente() + 1);
                } else {
                    this.sistema.setPuntosservidor(this.sistema.getPuntosservidor() + 1);
                }
            }
        }
        if (this.sistema.getPuntoscliente() + this.sistema.getPuntosservidor() == this.sistema.getCuadros().size()) {
            this.sistema.setJuegosigue(1);
        }
        if (cierracelda == 0 && malajugada != 1) {
            this.sistema.setTurno(false);
        }

        if (malajugada != 1) {
            try {
                this.sistema.getComunicador().enviarMovimiento(fila, columna, orientacion);
            } catch (IOException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se puede hacer esa jugada");
        }

    }
}
