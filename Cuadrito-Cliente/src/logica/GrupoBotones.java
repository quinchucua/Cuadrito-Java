package logica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ASUS
 */
public class GrupoBotones extends JPanel {

    Sistema sistema;

    JButton botoncentral;
    JButton botonizq;
    JButton botonder;
    JButton botonabajo;
    JButton botonarriba;

    int ancho;
    int alto;
    int fila;
    int columna;

    public GrupoBotones(int y, int x, Sistema sistema,int ancho,int alto) {
        this.sistema = sistema;
        this.ancho = ancho;
        this.alto = alto;
        this.fila = y;
        this.columna = x;
        construirgrupo();
    }

    public void construirgrupo() {
        this.setOpaque(false);
        
        int posicionx = 5 + (ancho - ancho/10*2) * columna;
        int posiciony = 5 + (alto -alto/10*2) * fila;
        
        this.setBounds(posicionx, posiciony, ancho, alto);

        this.botoncentral = new JButton();
        this.botoncentral.setName("Centro");
        this.botonder = new JButton();
        this.botonder.setName("Lado");
        this.botonabajo = new JButton();
        this.botonabajo.setName("Lado");

        if (this.columna == 0) {
            this.botonizq = new JButton();
            this.botonizq.setName("Lado");
            this.botonizq.setBounds(0, alto / 10 * 2, ancho / 10 * 2, alto / 10 * 6);
        } else {
            for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
                if (this.sistema.getCuadros().get(i).getColumna() == this.getColumna() - 1 && this.sistema.getCuadros().get(i).getFila() == this.getFila()) {
                    this.botonizq = this.sistema.getCuadros().get(i).getBotonder();
                    this.botonizq.setBounds(0, alto / 10 * 2, ancho / 10 * 2, alto / 10 * 6);
                }
            }
        }

        if (this.fila == 0) {
            this.botonarriba = new JButton();
            this.botonarriba.setName("Lado");
            this.botonarriba.setBounds(ancho / 10 * 2, 0, ancho / 10 * 6, alto / 10 * 2);
        } else {
            for (int i = 0; i < this.sistema.getCuadros().size(); i++) {
                if (this.sistema.getCuadros().get(i).getColumna() == this.getColumna() && this.sistema.getCuadros().get(i).getFila() == this.getFila()-1) {
                    this.botonarriba = this.sistema.getCuadros().get(i).getBotonabajo();
                    this.botonarriba.setBounds(ancho / 10 * 2, 0, ancho / 10 * 6, alto / 10 * 2);
                }
            }
        }

        this.botoncentral.setBounds(ancho / 10 * 3, alto / 10 * 3, ancho / 10 * 4, alto / 10 * 4);
        this.botonder.setBounds(ancho / 10 * 8, alto / 10 * 2, ancho / 10 * 2, alto / 10 * 6);
        this.botonabajo.setBounds(ancho / 10 * 2, alto / 10 * 8, ancho / 10 * 6, alto / 10 * 2);

        this.setLayout(null);

        this.add(this.botonizq);
        this.add(this.botonarriba);
        this.add(this.botonder);
        this.add(this.botonabajo);
        this.add(this.botoncentral);
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public JButton getBotoncentral() {
        return botoncentral;
    }

    public void setBotoncentral(JButton botoncentral) {
        this.botoncentral = botoncentral;
    }

    public JButton getBotonizq() {
        return botonizq;
    }

    public void setBotonizq(JButton botonizq) {
        this.botonizq = botonizq;
    }

    public JButton getBotonder() {
        return botonder;
    }

    public void setBotonder(JButton botonder) {
        this.botonder = botonder;
    }

    public JButton getBotonabajo() {
        return botonabajo;
    }

    public void setBotonabajo(JButton botonabajo) {
        this.botonabajo = botonabajo;
    }

    public JButton getBotonarriba() {
        return botonarriba;
    }

    public void setBotonarriba(JButton botonarriba) {
        this.botonarriba = botonarriba;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

}
