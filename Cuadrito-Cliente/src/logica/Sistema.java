package logica;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

public class Sistema {

    ArrayList<GrupoBotones> cuadros;
    Comunicador comunicador;
    private String nombrecliente = "Cliente";
    private String nombreservidor = "Servidor";
    private boolean turno = false;
    private boolean tablerocreado = false;

    public Sistema() {
        this.cuadros = new ArrayList();
        this.comunicador = new Comunicador(this);
    }

    public void crearcuadros(int filas, int columnas) {
        int tamanox = 50;//(6*80)/columnas;
        int tamanoy = 50;//(8*80)/filas;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                GrupoBotones gb = new GrupoBotones(i, j, this, tamanox, tamanoy);
                cuadros.add(gb);
            }
        }
        this.setTablerocreado(true);
    }

    public void pintarboton(int fila, int columna, int orientacion) throws IOException {
        int juegosigue = 1;
        int cierracelda = 0;
        //busca el grupo correspondiente a la fila y columna
        for (int i = 0; i < this.cuadros.size(); i++) {
            GrupoBotones gb = this.cuadros.get(i);

            //caso en el que la fila y columna no corresponden
            if (gb.getFila() == fila && gb.getColumna() == columna) {
                if (orientacion == 0) {
                    gb.getBotonarriba().setBackground(Color.red);
                }
                if (orientacion == 1) {
                    gb.getBotonder().setBackground(Color.red);
                }
                if (orientacion == 2) {
                    gb.getBotonabajo().setBackground(Color.red);
                }
                if (orientacion == 3) {
                    gb.getBotonizq().setBackground(Color.red);
                }
            }
            //verifica si la celda fue cerrada
            if (gb.getBotonarriba().getBackground() == Color.red && gb.getBotonder().getBackground() == Color.red && gb.getBotonabajo().getBackground() == Color.red && gb.getBotonizq().getBackground() == Color.red) {
                if (gb.getBotoncentral().getBackground() != Color.red) {
                    gb.getBotoncentral().setBorder(new LineBorder(Color.GREEN,5));
                    gb.getBotoncentral().setBackground(Color.red);
                    cierracelda = 2;
                    this.turno = false;
                    //si no cerro ninguna celda hay cambio de turno
                }
                /*
                else {
                    cierracelda = 0;
                    this.turno = true;
                }
                */
            } 
            //verifica cada grupo para saber si el boton central ya cambio de color e identificar si el juego sigue
            if (gb.getBotoncentral().getBackground() != Color.red) {
                juegosigue = 0;
            }
        }
        if(cierracelda==0)
        {
            this.turno = true;
        }
        this.comunicador.respondermovimiento(cierracelda, juegosigue);
    }

    public ArrayList<GrupoBotones> getCuadros() {
        return cuadros;
    }

    public void setCuadros(ArrayList<GrupoBotones> cuadros) {
        this.cuadros = cuadros;
    }

    public void borrarcuadros() {
        this.cuadros = new ArrayList();
    }

    public Comunicador getComunicador() {
        return comunicador;
    }

    public void setComunicador(Comunicador comunicador) {
        this.comunicador = comunicador;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getNombreservidor() {
        return nombreservidor;
    }

    public void setNombreservidor(String nombreservidor) {
        this.nombreservidor = nombreservidor;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    public boolean isTablerocreado() {
        return tablerocreado;
    }

    public void setTablerocreado(boolean tablerocreado) {
        this.tablerocreado = tablerocreado;
    }

}
