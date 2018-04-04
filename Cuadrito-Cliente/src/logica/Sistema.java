package logica;

import java.util.ArrayList;

public class Sistema 
{
    ArrayList<GrupoBotones> cuadros;
    Comunicador comunicador;

    public Sistema() {
        this.cuadros = new ArrayList();
        this.comunicador = new Comunicador(this);
    }
    
    public void crearcuadros(int filas, int columnas)
    {
        int tamanox = 50;//(6*80)/columnas;
        int tamanoy = 50;//(8*80)/filas;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                GrupoBotones gb = new GrupoBotones(i,j,this,tamanox,tamanoy);
                cuadros.add(gb);
            }
        }
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
    
    
    
}
