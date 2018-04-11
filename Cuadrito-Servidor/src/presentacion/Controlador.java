package presentacion;



import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.GrupoBotones;

public class Controlador implements ActionListener{

    private final Vista ventana;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo) {
        ventana = vista;
        this.modelo = (modelo);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton;
        boton = (JButton) e.getSource();
        /*
        if(boton == ventana.getEnviar()){
            modelo.enviarMensaje();
        }else{
            if (ventana.getEscuchar().getText().equals("Escuchar")) {
                modelo.esperarConexion();
            }else{
                modelo.terminarConexion();
            }
        }
*/      //boton para iniciar el tablero del juego
        if(boton == this.ventana.getjBPermitirConexion())
        {
            if(this.ventana.getjBPermitirConexion().getText().equals("Permitir Conexion"))
            {
                this.modelo.permitirconexion();
            }else if (this.ventana.getjBPermitirConexion().getText().equals("Cerrar Conexion"))
            {
                this.modelo.cerrarconexion();
            }
        }
        if(boton == this.ventana.getjBCrearTablero())
        {
            this.modelo.generartablero();
        }
        //se busca la ubicacion del boton que genero el evento
        //se buscan los componentes del panel del tablero
        for (int i = 0; i < this.ventana.getjPTablero().getComponentCount(); i++) {
            GrupoBotones gb = (GrupoBotones) this.ventana.getjPTablero().getComponent(i);
            //se busca en los botones del objeto GrupoBotones
            if(e.getSource().equals(gb.getBotonizq()))
            {
                System.out.println("Evento de : ");
                System.out.println("Boton : Izquierda ");
                System.out.println("Fila : "+gb.getFila());
                System.out.println("Columna : "+gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),3);
                //gb.getBotonizq().setBackground(Color.red);
                break;
                
            }
            if(e.getSource().equals(gb.getBotonarriba()))
            {
                System.out.println("Evento de : ");
                System.out.println("Boton : Arriba ");
                System.out.println("Fila : "+gb.getFila());
                System.out.println("Columna : "+gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),0);
                //gb.getBotonarriba().setBackground(Color.red);
                break;
            }
            if(e.getSource().equals(gb.getBotonder()))
            {
                System.out.println("Evento de : ");
                System.out.println("Boton : Derecha ");
                System.out.println("Fila : "+gb.getFila());
                System.out.println("Columna : "+gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),1);
                //gb.getBotonder().setBackground(Color.red);
                break;
            }
            if(e.getSource().equals(gb.getBotonabajo()))
            {
                System.out.println("Evento de : ");
                System.out.println("Boton : Abajo ");
                System.out.println("Fila : "+gb.getFila());
                System.out.println("Columna : "+gb.getColumna());
                this.modelo.pintarboton(gb.getFila(),gb.getColumna(),2);
                //gb.getBotonabajo().setBackground(Color.red);
                break;
            }
            
        }
    }
}
