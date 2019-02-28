package proyecto4parcial;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author fernando
 */
public final class Main {

    JButton btnAlumnos;
    JButton btnMaterias;
    JButton btnEspecialidad;
    JButton btnInscripcion;
    JRadioButton radioAltas;
    JRadioButton radioModificacion;
    JRadioButton radioReportes;
    JRadioButton radioBoleta;
    ButtonGroup grupoOpciones;
    JTextField txtNombre;
    JTextField txtNumControl;
    JTextField txtClaveMate;
    JTextField txtClaveEspe;
    JTextField txtCreditos;
    JTextArea txtReporte;
    ArchivosManager manejador;
    ReportesManager reportes;

    //<editor-fold desc="Constructor">

    /**
     *
     */
    public Main() {
        btnAlumnos = new JButton("1. Alumnos");
        btnMaterias = new JButton("2. Materias");
        btnEspecialidad = new JButton("3. Especialidades");
        btnInscripcion = new JButton("4. Inscripciones");
        radioAltas = new JRadioButton("Altas");
        radioModificacion = new JRadioButton("Modificaciones");
        radioReportes = new JRadioButton("Reportes");
        radioBoleta = new JRadioButton("Boleta");
        grupoOpciones = new ButtonGroup();
        grupoOpciones.add(radioAltas);
        grupoOpciones.add(radioModificacion);
        grupoOpciones.add(radioReportes);
        grupoOpciones.add(radioBoleta);
        radioAltas.setSelected(true);
        txtNombre = new JTextField(30);
        txtNumControl = new JTextField(30);
        txtClaveMate = new JTextField(30);
        txtClaveEspe = new JTextField(30);
        txtCreditos = new JTextField(30);
        txtReporte = new JTextArea(30,70);
        txtReporte.setEditable(false);
        txtNombre.setBorder(BorderFactory.createTitledBorder("Nombre"));
        txtNumControl.setBorder(BorderFactory.createTitledBorder("Numero de Control"));
        txtClaveEspe.setBorder(BorderFactory.createTitledBorder("Clave de especialidad (0-127)"));
        txtClaveMate.setBorder(BorderFactory.createTitledBorder("Clave de materia"));
        txtCreditos.setBorder(BorderFactory.createTitledBorder("Numero de Creditos"));
        manejador = new ArchivosManager();
        reportes = new ReportesManager(manejador);
        eventos();
        eventosKey();
    }
    //</editor-fold>

    void menuPrincipal() {
        JLabel lblTitutlo = new JLabel("Menú de Opciones");
        lblTitutlo.setFont(new Font("Calibri", Font.BOLD, 15));
        Object mensaje[] = {lblTitutlo, btnAlumnos, btnMaterias, btnEspecialidad, btnInscripcion};
        JOptionPane.showMessageDialog(null, mensaje, "Menú", JOptionPane.QUESTION_MESSAGE);
    }

    int menuSecundario() {
        Object mensaje[] = {radioAltas, radioModificacion, radioReportes};
        return JOptionPane.showConfirmDialog(null, mensaje, "Operación...", JOptionPane.CANCEL_OPTION);
    }

    int menuInscripciones() {
        Object mensaje[] = {radioAltas, radioBoleta};
        return JOptionPane.showConfirmDialog(null, mensaje, "Operación...", JOptionPane.CANCEL_OPTION);
    }

    void eventos() {
        //<editor-fold desc="Accion del Boton Alumnos">
        btnAlumnos.addActionListener((ActionEvent e) -> {
            int opcion = menuSecundario();
            byte resp = 1;
            if (opcion != JOptionPane.CANCEL_OPTION) {
                if (radioAltas.isSelected()) {

                    Object mensaje2[] = {
                        txtNumControl, " ", txtNombre, " ", txtClaveEspe
                    };
                    manejador.abrirArchAlumnos();
                    manejador.abrirArchEspecialidad();
                    do {
                        resp = 1;
                        opcion = JOptionPane.showConfirmDialog(null, mensaje2, "Datos del Alumno", JOptionPane.CANCEL_OPTION);
                        if (opcion != JOptionPane.CANCEL_OPTION) {
                            if (txtNumControl.getText().isEmpty() || txtNombre.getText().isEmpty() || txtClaveEspe.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Llena todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                                resp = JOptionPane.YES_OPTION;
                            } else if (manejador.buscarEspecialidad(Byte.valueOf(txtClaveEspe.getText())) != -1) {
                                String nombre = txtNombre.getText().trim();
                                String numCtrl = txtNumControl.getText().trim();
                                byte clave = Byte.valueOf(txtClaveEspe.getText().trim());
                                manejador.agregarAlumno(new Alumno(numCtrl, nombre, clave));
                                resp = (byte) JOptionPane.showConfirmDialog(null, "¿Desea Agregar Otro?", "Continuar...", JOptionPane.YES_NO_OPTION);
                                limpiar();
                            } else {
                                JOptionPane.showMessageDialog(null, "La especialidad a la que intentas inscribirte no existe", "NO EXISTE...", JOptionPane.WARNING_MESSAGE);
                                resp = JOptionPane.YES_OPTION;
                            }
                        }
                    } while (resp == JOptionPane.YES_OPTION);
                    manejador.ordAlumnos();
                    manejador.cerrarArchEspecialidad();
                    manejador.cerrarArchAlumnos();
                    limpiar();

                } else if (radioModificacion.isSelected()) {
                    manejador.abrirArchAlumnos();
                    manejador.abrirArchEspecialidad();
                    String numControl = JOptionPane.showInputDialog("Numero de control a modificar:");
                    int registro = manejador.buscarAlumno(numControl);
                    if (registro != -1) {
                        Alumno alu = manejador.leerAlumno(registro);
                        txtNombre.setText(alu.getNombre().trim());
                        txtClaveEspe.setText(alu.getClvEspecialidad() + "");
                        txtNumControl.setText(alu.getNumControl() + "");
                        txtNumControl.setEditable(false);
                        Object mensaje[] = {txtNumControl, " ", txtNombre, " ", txtClaveEspe};
                        opcion = JOptionPane.showConfirmDialog(null, mensaje, "Datos del Alumno a modificar", JOptionPane.CANCEL_OPTION);
                        if (opcion != JOptionPane.CANCEL_OPTION) {
                            if (manejador.buscarEspecialidad(Byte.valueOf(txtClaveEspe.getText())) != -1) {
                                String nombre = String.format("%-40s", txtNombre.getText().trim());
                                byte clvEspe = Byte.valueOf(txtClaveEspe.getText());
                                manejador.modAlumno(registro, new Alumno(numControl, nombre, clvEspe)); //escribimos el mismo numero de control, porque las claves primarias no deben modificarse 
                                JOptionPane.showMessageDialog(null, "Alumno modificado satisfactoriamente");
                            } else {
                                JOptionPane.showMessageDialog(null, "La especialidad a la que intentas inscribirte no existe", "NO EXISTE...", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe ese numero de control", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    manejador.cerrarArchEspecialidad();
                    manejador.cerrarArchAlumnos();
                    limpiar();

                } else if (radioReportes.isSelected()) {
                    manejador.abrirArchAlumnos();                   
                    txtReporte = new JTextArea(20, 30); //para que me deje cambiar el tamaño de la textArea                   
                    txtReporte.append(String.format("%75s", "Instituto Tecnologico de Celaya\n"));
                    txtReporte.append(String.format("%75s", "Alumnos Inscritos\n\n"));
                    txtReporte.append(String.format("%-20s\t%-40s\t%-4s\t", "Numero de Control", "Nombre", "Especialidad")+"\n");
                    
                    int i = 0;
                    Alumno alu;
                    do{
                        alu = manejador.leerAlumno(i);
                        if (alu!= null) 
                              txtReporte.append(String.format("%-20s\t%-40s\t%-4s", alu.getNumControl(), alu.getNombre(), alu.getClvEspecialidad()));
                              txtReporte.append("\n");
                        i++;
                    }while(alu != null); 
                                 
                    Object mensaje[] = {txtReporte};
                    JOptionPane.showMessageDialog(null, mensaje); 
                    reportes.geneReporteAlumnos();
                    manejador.cerrarArchAlumnos();                    
                }
            }
        });
        //</editor-fold>

        //<editor-fold desc="Accion del Boton Especialidad">
        btnEspecialidad.addActionListener((ActionEvent e) -> {
            int opcion = menuSecundario();
            byte resp = JOptionPane.NO_OPTION; //1
            if (opcion != JOptionPane.CANCEL_OPTION) {
                if (radioAltas.isSelected()) {

                    Object mensaje2[] = {
                        txtClaveEspe, " ", txtNombre
                    };
                    manejador.abrirArchEspecialidad();
                    do {
                        resp = 1;
                        opcion = JOptionPane.showConfirmDialog(null, mensaje2, "Datos de la Especialidad", JOptionPane.CANCEL_OPTION);
                        if (opcion != JOptionPane.CANCEL_OPTION) {
                            if (txtClaveEspe.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Llena todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                                resp = JOptionPane.YES_OPTION;
                            } else {
                                byte claveE = Byte.valueOf(txtClaveEspe.getText().trim());
                                String nombreE = txtNombre.getText().trim();
                                manejador.agregarEspecialidad(new Especialidad(claveE, nombreE));
                                resp = (byte) JOptionPane.showConfirmDialog(null, "¿Desea Agregar Otra?", "Continuar...", JOptionPane.YES_NO_OPTION);
                                limpiar();
                            }
                        }
                    } while (resp == JOptionPane.YES_OPTION);
                    manejador.ordEspecialidad();
                    manejador.cerrarArchEspecialidad();
                    limpiar();

                } else if (radioModificacion.isSelected()) {
                    manejador.abrirArchEspecialidad();
                    byte clvEspecialidad = Byte.parseByte(JOptionPane.showInputDialog("Clave de especialidad a modificar"));
                    int registro = manejador.buscarEspecialidad(clvEspecialidad);
                    if (registro != -1) {
                        Especialidad espe = manejador.leerEspecialidad(registro);
                        txtNombre.setText(espe.getNombEspecialidad().trim());
                        txtClaveEspe.setText(espe.getClvEspecialidad() + "");
                        txtClaveEspe.setEditable(false);
                        Object mensaje[] = {txtClaveEspe, " ", txtNombre};
                        opcion = JOptionPane.showConfirmDialog(null, mensaje, "Datos de especialidad", JOptionPane.CANCEL_OPTION);
                        if (opcion != JOptionPane.CANCEL_OPTION) {
                            String nombre = String.format("%-40s", txtNombre.getText().trim());
                            manejador.modEspecialidad(registro, new Especialidad(clvEspecialidad, nombre));
                            JOptionPane.showMessageDialog(null, "Especialidad modificada");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe esa clave de especialidad ", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    manejador.cerrarArchEspecialidad();
                    limpiar();

                } else if (radioReportes.isSelected()) {
                    manejador.abrirArchEspecialidad();
                    txtReporte = new JTextArea(15, 10); //para que me deje cambiar el tamaño de la textArea
                    
                    txtReporte.append(String.format("%60s", "Instituto Tecnologico de Celaya\n"));
                    txtReporte.append(String.format("%60s", "Especialidades ofrecidas\n\n"));
                    txtReporte.append(String.format("%-10s\t%-40s", "Clave", "Nombre")+"\n");
                    
                    Especialidad espe;
                    int i = 0;
                    do {
                        espe = manejador.leerEspecialidad(i);
                        if (espe != null) {
                            txtReporte.append(String.format("%-10s\t%-60s", espe.getClvEspecialidad(), espe.getNombEspecialidad()));
                            txtReporte.append("\n");
                        }
                        i++;
                    } while (espe != null);  
                    
                    Object mensaje[] = {txtReporte};
                    JOptionPane.showMessageDialog(null, mensaje);  
                    reportes.geneReporteEspecialidad();                    
                    manejador.cerrarArchEspecialidad();
                }
            }
        });
        //</editor-fold>

        //<editor-fold desc="Accion del Boton Materias">
        btnMaterias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = menuSecundario();
                byte resp = 1;
                if (opcion != JOptionPane.CANCEL_OPTION) {
                    if (radioAltas.isSelected()) {

                        Object mensaje2[] = {
                            txtClaveMate, " ", txtNombre, " ", txtCreditos
                        };
                        manejador.abrirArchMaterias();
                        do {
                            resp = 1;
                            opcion = JOptionPane.showConfirmDialog(null, mensaje2, "Datos de la Materia", JOptionPane.CANCEL_OPTION);
                            if (opcion != JOptionPane.CANCEL_OPTION) {
                                if (txtClaveMate.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCreditos.getText().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "LLENA TODOS LOS CAMPOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    resp = JOptionPane.YES_OPTION;
                                } else {
                                    String claveM = txtClaveMate.getText().trim();
                                    String nombreM = txtNombre.getText().trim();
                                    byte creditosM = Byte.valueOf(txtCreditos.getText().trim());
                                    manejador.agregarMateria(new Materia(claveM, nombreM, creditosM));
                                    resp = (byte) JOptionPane.showConfirmDialog(null, "¿Desea Agregar Otra?", "Continuar...", JOptionPane.YES_NO_OPTION);
                                    limpiar();
                                }
                            }
                        } while (resp == JOptionPane.YES_OPTION);
                        manejador.ordMaterias();
                        manejador.cerrarArchMaterias();
                        limpiar();

                    } else if (radioModificacion.isSelected()) {
                        manejador.abrirArchMaterias();
                        String clvM = JOptionPane.showInputDialog("Numero de materia a modificar:");
                        int registro = manejador.buscarMateria(clvM);
                        if (registro != -1) {
                            Materia mat = manejador.leerMateria(registro);
                            txtNombre.setText(mat.getNombMateria().trim());
                            txtCreditos.setText(mat.getCreditos() + "");
                            txtClaveMate.setText(mat.getClvMateria() + "");
                            txtClaveMate.setEditable(false);
                            Object mensaje[] = {txtClaveMate, " ", txtNombre, " ", txtCreditos};
                            opcion = JOptionPane.showConfirmDialog(null, mensaje, "Datos de la materia a modificar", JOptionPane.CANCEL_OPTION);
                            if (opcion != JOptionPane.CANCEL_OPTION) {
                                String nombre = String.format("%-40s", txtNombre.getText().trim());
                                byte creditos = Byte.valueOf(txtCreditos.getText());
                                manejador.modMateria(registro, new Materia(clvM, nombre, creditos)); //escribimos la misma, porque las claves primarias no deben modificarse
                                JOptionPane.showMessageDialog(null, "Materia modificado satisfactoriamente");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No existe esa materia", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                        manejador.cerrarArchMaterias();
                        limpiar();

                    } else if (radioReportes.isSelected()) {
                        manejador.abrirArchMaterias();
                        txtReporte = new JTextArea(15, 10); //para que me deje cambiar el tamaño de la textArea
                        
                        txtReporte.append(String.format("%60s", "Instituto Tecnologico de Celaya\n"));
                        txtReporte.append(String.format("%60s", "Materias disponibles\n\n"));
                        txtReporte.append(String.format("%-10s\t%-50s\t%-6s", "Clave", "Nombre", "Creditos")+"\n");                        
                        Materia mat;
                        int i = 0;
                        do {
                            mat = manejador.leerMateria(i);
                            if (mat != null) {
                                txtReporte.append(String.format("%-10s\t%-40s\t%8s", mat.getClvMateria(), mat.getNombMateria(), mat.getCreditos()));
                            }

                            i++;
                        } while (mat != null);                                       
                        Object mensaje[] = {txtReporte};
                        JOptionPane.showMessageDialog(null, mensaje);  
                        reportes.geneReporteMaterias();
                        manejador.cerrarArchMaterias();
                    }
                }
            }
        });
        //</editor-fold>

        //<editor-fold desc="Accion del Boton Inscripciones">
        btnInscripcion.addActionListener((ActionEvent e) -> {
            int opcion = menuInscripciones();
            byte resp = JOptionPane.NO_OPTION;
            String materia;
            if (opcion != JOptionPane.CANCEL_OPTION) {
                manejador.abrirArchInscripciones();
                manejador.abrirArchAlumnos();
                manejador.abrirArchMaterias();
                manejador.abrirArchEspecialidad();
                if (radioAltas.isSelected()) {
                    String numControl = JOptionPane.showInputDialog("Numero de control");
                    if (manejador.buscarAlumno(numControl) != -1) {
                        do {
                            resp = JOptionPane.NO_OPTION;
                            Object mensaje[] = {txtClaveMate};
                            opcion = JOptionPane.showConfirmDialog(null, mensaje, "Inscribir materia...", JOptionPane.CANCEL_OPTION);
                            if (opcion != JOptionPane.CANCEL_OPTION) {
                                materia = txtClaveMate.getText().isEmpty() ? " " : txtClaveMate.getText().trim();
                                if (manejador.buscarMateria(materia) != -1) { //la materia si esta registrada
                                    manejador.inscribirAlumno(numControl, materia);
                                    resp = (byte) JOptionPane.showConfirmDialog(null, "¿Deseas inscribir otra materia?", "Continuar...", JOptionPane.YES_NO_OPTION);
                                    limpiar();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Esa materia no esta registrada", "Error...", JOptionPane.WARNING_MESSAGE);
                                    resp = JOptionPane.YES_OPTION;
                                }
                            }
                        } while (resp == JOptionPane.YES_OPTION);
                        manejador.ordInscripciones();                        
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tu numero de control no esta registrado", "Error...", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (radioBoleta.isSelected()) {
                    
                    txtReporte = new JTextArea(30, 70);                    
                    txtReporte.append(String.format("%130s", "Instituto Tecnologico de Celaya\n"));
                    txtReporte.append(String.format("%130s", "Materias Inscritas\n\n"));
                    txtReporte.append(String.format("%-10s\t%-50s\t%-4s\t%-50s\t%-6s", "Alumno","Especialidad","Materia", "Nombre", "Creditos"));
                    
                    
                    int i = 0;
                    Alumno alumno;
                    Materia mat;
                    do {
                        alumno = manejador.leerAlumno(i);
                        if (alumno != null) {

                            ArrayList<String> materias = manejador.leerMateInscritas(alumno.getNumControl());
                            Especialidad espe = manejador.leerEspecialidad(manejador.buscarEspecialidad(alumno.getClvEspecialidad()));

                            if (materias != null && materias.size() > 0) {
                                txtReporte.append("\n");
                                txtReporte.append(String.format("%-10s\t%-50s\t", alumno.getNumControl(), espe.getNombEspecialidad()));

                                for (String m : materias) {
                                    mat = manejador.leerMateria(manejador.buscarMateria(m));
                                    txtReporte.append(String.format("%-4s\t%-50s\t%6s", mat.getClvMateria(), mat.getNombMateria(), mat.getCreditos()));
                                    txtReporte.append("\n");
                                    txtReporte.append(String.format("%60s\t\t", " ")); //el espacio que ocupa el numero de control y la clave de especialidad                
                                }

                                txtReporte.append("\n");
                                for (int j = 0; j < 165; j++) {
                                    txtReporte.append("-");
                                }
                            }
                        }
                        i++;
                    } while (alumno != null);                    
                                       
                    Object mensaje[] = {txtReporte};
                    JOptionPane.showMessageDialog(null, mensaje);
                    reportes.geneReporteInscripcion();
                }

                manejador.cerrarArchAlumnos();
                manejador.cerrarArchInscripciones();
                manejador.cerrarArchMaterias();
                manejador.cerrarArchEspecialidad();
            }
        });
        //</editor-fold>
    }

    //<editor-fold desc="eventos tipo KeyListener" defaultstate="collapsed">
    private void eventosKey() {
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtNombre.getText().length() == 40) {
                    e.consume();
                }
            }
        });
        txtNumControl.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtNumControl.getText().length() == 8 || e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        txtCreditos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtCreditos.getText().length() == 3 || e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        txtClaveEspe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtClaveEspe.getText().length() == 3 || e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        txtClaveMate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (txtClaveMate.getText().length() == 4) {
                    e.consume();
                }
            }
        });

    }
    //</editor-fold>

    //<editor-fold desc="Metodo limpiar">
    void limpiar() {
        txtNombre.setText("");
        txtNumControl.setText("");
        txtClaveEspe.setText("");
        txtClaveMate.setText("");
        txtCreditos.setText("");
        txtReporte.setText("");
        radioAltas.setSelected(true);
        txtClaveEspe.setEditable(true);
        txtClaveMate.setEditable(true);
        txtNumControl.setEditable(true);
    }
    //</editor-fold>

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>       

        Main main = new Main();
        main.menuPrincipal();
    }

}
