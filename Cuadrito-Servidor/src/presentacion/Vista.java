/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class Vista extends javax.swing.JFrame {

    /**
     * Creates new form Vista
     */
    private final Modelo modelo;
    private Controlador controlador;

    public Vista(Modelo modelo) {
        this.modelo = modelo;
        this.controlador = new Controlador(this, modelo);
        initComponents();
        agregarcontroladores();
        
    }
    
    public void agregarcontroladores()
    {
        this.jBCrearTablero.addActionListener(controlador);
        this.jBPermitirConexion.addActionListener(controlador);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPDatosTablero = new javax.swing.JPanel();
        jLFilas = new javax.swing.JLabel();
        jTFilas = new javax.swing.JTextField();
        jLColumnas = new javax.swing.JLabel();
        jTColumnas = new javax.swing.JTextField();
        jBCrearTablero = new javax.swing.JButton();
        jPTablero = new javax.swing.JPanel();
        jPConexion = new javax.swing.JPanel();
        jBPermitirConexion = new javax.swing.JButton();
        jTConexion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTMensajes = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPDatosTablero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLFilas.setText("Filas");

        jTFilas.setText("5");

        jLColumnas.setText("Columnas");

        jTColumnas.setText("5");

        jBCrearTablero.setText("Crear Tablero");

        javax.swing.GroupLayout jPDatosTableroLayout = new javax.swing.GroupLayout(jPDatosTablero);
        jPDatosTablero.setLayout(jPDatosTableroLayout);
        jPDatosTableroLayout.setHorizontalGroup(
            jPDatosTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDatosTableroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLFilas)
                .addGap(18, 18, 18)
                .addComponent(jTFilas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLColumnas)
                .addGap(18, 18, 18)
                .addComponent(jTColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBCrearTablero)
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jPDatosTableroLayout.setVerticalGroup(
            jPDatosTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDatosTableroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDatosTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLFilas)
                    .addComponent(jTFilas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLColumnas)
                    .addComponent(jTColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBCrearTablero))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPTablero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPTableroLayout = new javax.swing.GroupLayout(jPTablero);
        jPTablero.setLayout(jPTableroLayout);
        jPTableroLayout.setHorizontalGroup(
            jPTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPTableroLayout.setVerticalGroup(
            jPTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jPConexion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jBPermitirConexion.setText("Permitir Conexion");

        jTConexion.setEnabled(false);

        javax.swing.GroupLayout jPConexionLayout = new javax.swing.GroupLayout(jPConexion);
        jPConexion.setLayout(jPConexionLayout);
        jPConexionLayout.setHorizontalGroup(
            jPConexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPConexionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTConexion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBPermitirConexion)
                .addContainerGap())
        );
        jPConexionLayout.setVerticalGroup(
            jPConexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPConexionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPConexionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBPermitirConexion)
                    .addComponent(jTConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTMensajes.setColumns(20);
        jTMensajes.setRows(5);
        jScrollPane1.setViewportView(jTMensajes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPDatosTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPConexion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPConexion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPDatosTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCrearTablero;
    private javax.swing.JButton jBPermitirConexion;
    private javax.swing.JLabel jLColumnas;
    private javax.swing.JLabel jLFilas;
    private javax.swing.JPanel jPConexion;
    private javax.swing.JPanel jPDatosTablero;
    private javax.swing.JPanel jPTablero;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTColumnas;
    private javax.swing.JTextField jTConexion;
    private javax.swing.JTextField jTFilas;
    private javax.swing.JTextArea jTMensajes;
    // End of variables declaration//GEN-END:variables

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public JButton getjBCrearTablero() {
        return jBCrearTablero;
    }

    public void setjBCrearTablero(JButton jBCrearTablero) {
        this.jBCrearTablero = jBCrearTablero;
    }

    public JLabel getjLColumnas() {
        return jLColumnas;
    }

    public void setjLColumnas(JLabel jLColumnas) {
        this.jLColumnas = jLColumnas;
    }

    public JLabel getjLFilas() {
        return jLFilas;
    }

    public void setjLFilas(JLabel jLFilas) {
        this.jLFilas = jLFilas;
    }

    public JPanel getjPanel1() {
        return jPDatosTablero;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPDatosTablero = jPanel1;
    }

    public JTextField getjTColumnas() {
        return jTColumnas;
    }

    public void setjTColumnas(JTextField jTColumnas) {
        this.jTColumnas = jTColumnas;
    }

    public JTextField getjTFilas() {
        return jTFilas;
    }

    public void setjTFilas(JTextField jTFilas) {
        this.jTFilas = jTFilas;
    }

    public JPanel getjPTablero() {
        return jPTablero;
    }

    public void setjPTablero(JPanel jPTablero) {
        this.jPTablero = jPTablero;
    }

    public JButton getjBPermitirConexion() {
        return jBPermitirConexion;
    }

    public void setjBPermitirConexion(JButton jBPermitirConexion) {
        this.jBPermitirConexion = jBPermitirConexion;
    }

    public JPanel getjPConexion() {
        return jPConexion;
    }

    public void setjPConexion(JPanel jPConexion) {
        this.jPConexion = jPConexion;
    }

    public JPanel getjPDatosTablero() {
        return jPDatosTablero;
    }

    public void setjPDatosTablero(JPanel jPDatosTablero) {
        this.jPDatosTablero = jPDatosTablero;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JTextField getjTConexion() {
        return jTConexion;
    }

    public void setjTConexion(JTextField jTConexion) {
        this.jTConexion = jTConexion;
    }

    public JTextArea getjTMensajes() {
        return jTMensajes;
    }

    public void setjTMensajes(JTextArea jTMensajes) {
        this.jTMensajes = jTMensajes;
    }
    
    

}