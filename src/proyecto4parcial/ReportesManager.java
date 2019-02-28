package proyecto4parcial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando
 */
public class ReportesManager {

    ArchivosManager manejador;
    private final String rutaEspecialidad = "./src/archivos/r_especialidad.txt";
    private final String rutaAlumnos = "./src/archivos/r_alumnos.txt";
    private final String rutaMateria = "./src/archivos/r_materia.txt";
    private final String rutaInscripciones = "./src/archivos/r_inscripciones.txt";

    /**
     *
     * @param manejador
     */
    public ReportesManager(ArchivosManager manejador) {
        this.manejador = manejador;
    }

    /**
     *
     */
    public void geneReporteAlumnos() {
        PrintWriter escritor = null;

        try {
            escritor = new PrintWriter(new BufferedWriter(new FileWriter(rutaAlumnos)));
            escritor.println(String.format("%65s", "Instituto Tecnologico de Celaya"));
            escritor.println(String.format("%60s", "Alumnos Inscritos"));
            escritor.println(String.format("%-20s\t%-40s\t%-4s\t", "Numero de Control", "Nombre", "Especialidad"));
            
            int i = 0;
            Alumno alu;
            do{
                alu = manejador.leerAlumno(i);
                if (alu!= null) 
                      escritor.println(String.format("%-20s\t%-40s\t%4s", alu.getNumControl(), alu.getNombre(), alu.getClvEspecialidad()));
                i++;
            }while(alu != null);
            
        } catch (IOException ex) {
            Logger.getLogger(ReportesManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            escritor.close();
        }
    }

    /**
     * Algoritmo que fue usado para ejemplo, no sirve de nada en el proyecto,
     * como tu en su vida
     */
    public void leerAlumno() {
        try {
            BufferedReader lector = new BufferedReader(new FileReader(rutaAlumnos));
            String linea = "";
            String leido;
            do {
                leido = lector.readLine();
                if (leido != null) {
                    linea += leido + "\n";
                }
            } while (leido != null);
            System.out.println(linea);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportesManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void geneReporteMaterias() {
        PrintWriter escritor = null;
        try {
            escritor = new PrintWriter(new BufferedWriter(new FileWriter(rutaMateria)));
            escritor.println(String.format("%55s", "Instituto Tecnologico de Celaya"));
            escritor.println(String.format("%50s", "Materias Disponibles"));
            escritor.println(String.format("%-10s\t%-40s\t%-6s", "Clave", "Nombre", "Creditos"));

            Materia mat;
            int i = 0;
            do {
                mat = manejador.leerMateria(i);
                if (mat != null) {
                    escritor.println(String.format("%-10s\t%-40s\t%8s", mat.getClvMateria(), mat.getNombMateria(), mat.getCreditos()));
                }

                i++;
            } while (mat != null);

        } catch (IOException ex) {
            Logger.getLogger(ReportesManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            escritor.close();
        }
    }

    /**
     *
     */
    public void geneReporteEspecialidad() {
        PrintWriter escritor = null;
        try {
            escritor = new PrintWriter(new BufferedWriter(new FileWriter(rutaEspecialidad)));
            escritor.println(String.format("%55s", "Instituto Tecnologico de Celaya"));
            escritor.println(String.format("%55s", "Especialidades Disponibles"));
            escritor.println(String.format("%-10s\t%-40s", "Clave", "Nombre"));

            Especialidad espe;
            int i = 0;
            do {
                espe = manejador.leerEspecialidad(i);
                if (espe != null) {
                    escritor.println(String.format("%-10s\t%-60s", espe.getClvEspecialidad(), espe.getNombEspecialidad()));
                }
                i++;
            } while (espe != null);

        } catch (IOException ex) {
            Logger.getLogger(ReportesManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            escritor.close();
        }
    }

    /**
     *
     */
    public void geneReporteInscripcion() {
        PrintWriter escritor = null;
        try {
            escritor = new PrintWriter(new BufferedWriter(new FileWriter(rutaInscripciones)));
            escritor.println(String.format("%80s", "Instituto Tecnologico de Celaya"));
            escritor.println(String.format("%75s", "Materias Inscritas"));
            escritor.println(String.format("%-10s\t%-50s\t%-4s\t%-6s", "Alumno", "Especialidad", "Materia", "Nombre", "Creditos"));

            int i = 0;
            Alumno alumno;
            Materia mat;
            do {
                alumno = manejador.leerAlumno(i);
                if (alumno != null) {
                    
                    ArrayList<String> materias = manejador.leerMateInscritas(alumno.getNumControl());
                    Especialidad espe = manejador.leerEspecialidad(manejador.buscarEspecialidad(alumno.getClvEspecialidad()));

                    if (materias != null && materias.size() > 0) {
                        escritor.println();
                        escritor.print(String.format("%-10s\t%-50s\t", alumno.getNumControl(), espe.getNombEspecialidad()));

                        for (String e : materias) {
                            mat = manejador.leerMateria(manejador.buscarMateria(e));
                            escritor.println(String.format("%-4s\t%-50s\t%6s", mat.getClvMateria(), mat.getNombMateria(), mat.getCreditos()));
                            escritor.print(String.format("%60s\t", " ")); //el espacio que ocupa el numero de control y la clave de especialidad                
                        }

                        escritor.println();
                        for (int j = 0; j < 165; j++) {
                            escritor.print("-");
                        }
                    }
                }
                i++;
            } while (alumno != null);

            
        } catch (IOException ex) {
            Logger.getLogger(ReportesManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            escritor.close();
        }
    }

}
