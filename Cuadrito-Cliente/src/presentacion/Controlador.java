package presentacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import logica.GrupoBotones;

public class Controlador implements ActionListener, WindowListener {

    private final Vista ventana;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        ventana = vista;
        this.modelo = (modelo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click algun boton controlador");
        JButton boton;
        boton = (JButton) e.getSource();
       
        if (boton == this.ventana.getjBConectarse()) {
            this.modelo.conectarse();
        }
        //se busca la ubicacion del boton que genero el evento
        //se buscan los componentes del panel del tablero
        for (int i = 0; i < this.ventana.getjPTablero().getComponentCount(); i++) {
            
            GrupoBotones gb = (GrupoBotones) this.ventana.getjPTablero().getComponent(i);
            //se busca en los botones del objeto GrupoBotones
            if (e.getSource().equals(gb.getBotonizq())) {
                System.out.println("Click boton controlador");
                System.out.println("Evento de : ");
                System.out.println("Boton : Izquierda ");
                System.out.println("Fila : " + gb.getFila());
                System.out.println("Columna : " + gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),3);
                //gb.getBotonizq().setBackground(Color.red);
                break;

            }
            if (e.getSource().equals(gb.getBotonarriba())) {
                System.out.println("Click boton controlador");
                System.out.println("Evento de : ");
                System.out.println("Boton : Arriba ");
                System.out.println("Fila : " + gb.getFila());
                System.out.println("Columna : " + gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),0);
                //gb.getBotonarriba().setBackground(Color.red);
                break;
            }
            if (e.getSource().equals(gb.getBotonder())) {
                System.out.println("Click boton controlador");
                System.out.println("Evento de : ");
                System.out.println("Boton : Derecha ");
                System.out.println("Fila : " + gb.getFila());
                System.out.println("Columna : " + gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),1);
                //gb.getBotonder().setBackground(Color.red);
                break;
            }
            if (e.getSource().equals(gb.getBotonabajo())) {
                System.out.println("Click boton controlador");
                System.out.println("Evento de : ");
                System.out.println("Boton : Abajo ");
                System.out.println("Fila : " + gb.getFila());
                System.out.println("Columna : " + gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),2);
                //gb.getBotonabajo().setBackground(Color.red);
                break;
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Cerrar la conexion el servidor");
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
