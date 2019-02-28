package proyecto4parcial;

import java.util.ArrayList;

/**
 *
 * @author fernando
 */
public class PruebaLista {

    /**
     *
     * @param args
     */
    public static void main(String[] args){
//        System.out.println("------------------Lista de Alumnos------------------");
        ArchivosManager manejador = new ArchivosManager();
//        manejador.abrirArchAlumnos();
//        //manejador.ordAlumnos();
//        int i = 0;
//        Alumno alumno;
//        do{
//            alumno = manejador.leerAlumno(i);
//            if (alumno!= null) System.out.println(alumno);
//            i++;
//        }while(alumno != null);
//        manejador.cerrarArchAlumnos();
////        
//        System.out.println("------------------Lista de Materias------------------");
//        manejador.abrirArchMaterias();
//        i=0;
//        Materia materia;
//        do{
//            materia = manejador.leerMateria(i);
//            if(materia != null) System.out.println(materia);
//            i++;
//        }while(materia != null);
//        manejador.cerrarArchMaterias();
////        
//        System.out.println("------------------Lista de Especialidades------------------");
//        manejador.abrirArchEspecialidad();
//        i = 0;
//        Especialidad espe;
//        do{
//            espe = manejador.leerEspecialidad(i);
//            if(espe != null) System.out.println(espe);
//            i++;            
//        }while(espe != null);
//        manejador.cerrarArchEspecialidad();
////        
//        System.out.println("------------------Inscripciones------------------");
//       
//        manejador.abrirArchInscripciones();
//        manejador.abrirArchMaterias();
//        manejador.abrirArchAlumnos();
//        ArrayList<Alumno> alumnos = manejador.leerAlumnos();
//        System.out.print(String.format("%-10s", "Alumno"));
//        System.out.print(String.format("%-11s", "Materia"));
//        System.out.print(String.format("%-40s", "Nombre"));
//        System.out.println(String.format("%-4s", "Creditos"));
//        
//        for(Alumno alu : alumnos){
//            ArrayList<String> materias = manejador.leerMateInscritas(alu.getNumControl());
//            int registro;
//            Materia mat;
//            if(materias != null && materias.size() > 0){
//                System.out.print(String.format("%-10s", alu.getNumControl()));
//                for(String e: materias){
//                    registro = manejador.buscarMateria(e);
//                    mat = manejador.leerMateria(registro);
//                    System.out.print(String.format("%-11s", mat.getClvMateria()));
//                    System.out.print(String.format("%-40s", mat.getNombMateria()));
//                    System.out.println(mat.getCreditos());
//
//                    System.out.print("          ");
//                }
//                System.out.println("");
//            }
//        }
//        manejador.cerrarArchAlumnos();
//        manejador.cerrarArchMaterias();
//        manejador.cerrarArchInscripciones();
    
        ReportesManager m = new ReportesManager(manejador);
        m.leerAlumno();
    }
    
}
