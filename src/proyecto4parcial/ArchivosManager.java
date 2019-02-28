
package proyecto4parcial;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando
 */
public class ArchivosManager {
    private RandomAccessFile archAlumnos;
    private RandomAccessFile archEspecialidad;
    private RandomAccessFile archMaterias;
    private RandomAccessFile archInscripciones;
    private final String rutaEspecialidad = "./src/archivos/especialidad.dat";
    private final String rutaAlumnos = "./src/archivos/alumnos.dat";
    private final String rutaMateria = "./src/archivos/materia.dat";
    private final String rutaInscripciones = "./src/archivos/inscripciones.dat";

    /**
     *
     */
    public ArchivosManager() {
        archAlumnos = null;
        archEspecialidad = null;
        archMaterias = null;
    }   
    
    //<editor-fold desc="Metodos para abrir y cerrar archivos" defaultstate="collapsed">

    /**
     *
     */
    public void abrirArchAlumnos(){
        try {
            archAlumnos = new RandomAccessFile(rutaAlumnos, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void abrirArchMaterias(){
        try {
            archMaterias = new RandomAccessFile(rutaMateria, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void abrirArchEspecialidad(){
        try {
            archEspecialidad = new RandomAccessFile(rutaEspecialidad, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void abrirArchInscripciones(){
        try {
            archInscripciones = new RandomAccessFile(rutaInscripciones, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void cerrarArchAlumnos(){
        try {
            archAlumnos.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void cerrarArchMaterias(){
        try {
            archMaterias.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    
    /**
     *
     */
    public void cerrarArchEspecialidad(){
        try {
            archEspecialidad.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void cerrarArchInscripciones(){
        try {
            archInscripciones.close();
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    //</editor-fold>
    
    
    /**
     * Agrega una especialidad al final del archivo.
     * @param especialidad 
     */
    public void agregarEspecialidad(Especialidad especialidad){
        try {          
            archEspecialidad.seek(archEspecialidad.length());
            String nombre = especialidad.getNombEspecialidad();
            byte clave = especialidad.getClvEspecialidad();           
            String nombAux = String.format("%-40s", nombre);
            Especialidad.escribirRegistro(archEspecialidad, new Especialidad(clave, nombAux));
        }catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Lee el registro correspondiente al numero de registro como parametro
     * @param numRegistro numero de registro a leer, comenzando desde 0
     * @return objeto Especialidad que contiene el registro leido
     */
    public Especialidad leerEspecialidad(int numRegistro){
        Especialidad e = null;
        int pos = numRegistro * Especialidad.TAR;
        try {           
            archEspecialidad.seek(pos);
            e = Especialidad.leerResgistro(archEspecialidad);
        }catch(EOFException ex){
            return null;
        }
        catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return e;                
    }
    
    /**
     * Agrega un registro de alumno al final del archivo
     * @param alumno 
     */
    public void agregarAlumno(Alumno alumno){
        try {
            
            archAlumnos.seek(archAlumnos.length());
            String numControl = alumno.getNumControl();
            String nombre = alumno.getNombre();
            byte clvEsp = alumno.getClvEspecialidad();
            String auxNomb = String.format("%-40s", nombre);
            Alumno.escribirRegistro(archAlumnos, new Alumno(numControl, auxNomb, clvEsp));            
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Lee el registro que se le pase como parámetro del archivo alumnos
     * @param numRegistro el numero de registro a leer, comenzando desde 0
     * @return 
     */
    public Alumno leerAlumno(int numRegistro){
        Alumno alu = null;
        int pos = numRegistro * Alumno.TAR;
        try {           
            archAlumnos.seek(pos);
            alu = Alumno.leerRegistro(archAlumnos);
        }catch(EOFException ex){     
            return null;
        }
        catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return alu;
    }
    
    /**
     * Agrega una materia al final del archivo.
     * @param materia
     */
    public void agregarMateria(Materia materia){
        try {           
            archMaterias.seek(archMaterias.length());
            String clave = materia.getClvMateria();
            String nombre = materia.getNombMateria();
            byte creditos = materia.getCreditos();
            
            String auxClave = String.format("%-4s", clave);
            String auxNombre = String.format("%-40s", nombre);          

            Materia.escribirRegistro(archMaterias, new Materia(auxClave, auxNombre, creditos));
        }catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Lee el registro que se le pase como parámetro del archivo materia
     * @param numRegistro el numero de registro a leer, comenzando desde 0
     * @return 
     */
    public Materia leerMateria(int numRegistro){
        int pos = numRegistro * Materia.TAR;
        Materia mat = null;
        try {
            archMaterias.seek(pos);           
            mat = Materia.leerRegistro(archMaterias);            
        }catch(EOFException ex){  
            return null;
        }
        catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return mat;
    }
    
    /**
     * Ordena el archivo alumnos por numero de control
     */
    public void ordAlumnos(){
        Alumno aluA, aluB;
        boolean ban = true;
        try {
            int numReg = (int)archAlumnos.length()/Alumno.TAR, com, pas;
            for(pas = 1; ban; pas++){
                archAlumnos.seek(0); //regresar el cursor al registro 0
                ban = false;
                for(com = 1; com <= (numReg-pas); com++){
                    aluA = Alumno.leerRegistro(archAlumnos); //leemos el registro actual
                    aluB = Alumno.leerRegistro(archAlumnos); //leemos el registro siguiente                   
                    if(aluA.getNumControl().compareTo(aluB.getNumControl()) > 0){ //si el actual es mayor que el sigueinte 
                        ban = true;
                        archAlumnos.seek((com-1) * Alumno.TAR); //movemos el cursor al registro A
                        Alumno.escribirRegistro(archAlumnos, aluB); //primero escribimos el B
                        Alumno.escribirRegistro(archAlumnos, aluA); //y luego escribimos el A para que cambien su posicion                       
                    }
                    archAlumnos.seek(com * Alumno.TAR); //regresar el apuntador al registro donde va nuestras comparaciones
                }
            }
            archAlumnos.seek(0);
        }catch(EOFException ex){        
        }catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ordena una materia por clave de Materia
     */
    public void ordMaterias(){
        Materia matA, matB;
        boolean ban = true;
        try {
            int numReg = (int)archMaterias.length()/Materia.TAR, com, pas;
            for(pas = 1; ban; pas++){
                archMaterias.seek(0); //regresar el cursor al registro 0
                ban = false;
                for(com = 1; com <= (numReg-pas); com++){
                    matA = Materia.leerRegistro(archMaterias); //leemos el registro actual
                    matB = Materia.leerRegistro(archMaterias); //leemos el registro siguiente                   
                    if(matA.getClvMateria().compareTo(matB.getClvMateria()) > 0){ //si el actual es mayor que el sigueinte 
                        ban = true;
                        archMaterias.seek((com-1) * Materia.TAR); //movemos el cursor al registro A
                        Materia.escribirRegistro(archMaterias, matB); //primero escribimos el B
                        Materia.escribirRegistro(archMaterias, matA); //y luego escribimos el A para que cambien su posicion                       
                    }
                    archMaterias.seek(com * Materia.TAR); //regresar el apuntador al registro donde va nuestras comparaciones
                }
            }
            archMaterias.seek(0);
        }catch(EOFException ex){        
        }catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    /**
     * Ordena una especialidad por clave de especialidad
     */
    public void ordEspecialidad(){
        Especialidad espA,espB;
        boolean ban = true;
        try {
            int numR= (int) archEspecialidad.length()/Especialidad.TAR, com, pas;
            for(pas=1; ban;pas++){
              archEspecialidad.seek(0);
              ban=false;
              for(com=1;com<=(numR-pas);com++){
                  espA=Especialidad.leerResgistro(archEspecialidad);
                  espB=Especialidad.leerResgistro(archEspecialidad);
                  if(espA.getClvEspecialidad()>espB.getClvEspecialidad()){
                      ban=true;
                      archEspecialidad.seek((com-1)*Especialidad.TAR);
                      Especialidad.escribirRegistro(archEspecialidad, espB);
                      Especialidad.escribirRegistro(archEspecialidad, espA);
                  }
                  archEspecialidad.seek(com*Especialidad.TAR);
              }
            }
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * Utiliza busqueda binaria para buscar el numero de registro en donde se encuentra el numero de control como parametro
     * @param numControl El numero de control buscado
     * @return El numero de registro en donde se encuentra el numero de control. Regresa -1 de no encontrarse dicho registro
     */
    public int buscarAlumno(String numControl){
        int PM = -1;
        try {           
            int LI=0;
            int LS=(int) archAlumnos.length()/Alumno.TAR-1;
            String bus = numControl, numLeido;
            do{
                PM =(LI+LS)/2;
                archAlumnos.seek(PM*Alumno.TAR);
                numLeido = archAlumnos.readUTF();                
                if(bus.compareTo(numLeido)>0)
                  LI=PM+1;
                else
                  LS=PM-1;               
            }while(!bus.equals(numLeido) && LI<=LS);
            
            if(bus.equals(numLeido)){
                return PM;
            }else{
                return -1;
            }            
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PM;
    }
    
    /**
     * Soobreescribe el registro pasado como parametro, con la informacion del nuevo alumno. 
     * @param numRegistro El numero de registro que se desea modificar
     * @param alumno Informacion que desea reemplazar al registro 
     */
    public void modAlumno(int numRegistro, Alumno alumno){
        try {
            archAlumnos.seek(numRegistro*Alumno.TAR);
            Alumno.escribirRegistro(archAlumnos, alumno);
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Utiliza busqueda binaria para buscar el numero de registro en donde se encuentra la clave de Materia como parametro
     * @param clvMateria  La clave de la materia buscada
     * @return El numero de registro en donde se encuentra la clave de materia. Regresa -1 de no encontrarse dicho registro
     */
    public int buscarMateria(String clvMateria){
        int PM=-1;
        try {           
            int LI=0;
            int LS=(int) archMaterias.length()/Materia.TAR-1;
            String numLeido;
            String bus = String.format("%-4s", clvMateria);
            do{
                PM =(LI+LS)/2;
                archMaterias.seek(PM*Materia.TAR);
                numLeido = archMaterias.readUTF();                
                if(bus.compareTo(numLeido)>0)
                  LI=PM+1;
                else
                  LS=PM-1;               
            }while(!bus.equals(numLeido) && LI<=LS);
            
            if(bus.equals(numLeido)){
                return PM;
            }else{
                return -1;
            }            
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PM;
        
    }
    
    /**
     * Soobreescribe el registro pasado como parametro, con la informacion de la nueva materia. 
     * @param numRegistro El numero de registro que se desea modificar
     * @param materia  Informacion que desea reemplazar al registro 
     */
    public void modMateria(int numRegistro, Materia materia){
        try {
            archMaterias.seek(numRegistro*Materia.TAR);
            Materia.escribirRegistro(archMaterias, materia);
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Utiliza busqueda binaria para buscar el numero de registro en donde se encuentra la clave de especialidad como parametro
     * @param clvEspecialidad  La clave de la especialidad buscada
     * @return El numero de registro en donde se encuentra la clave de especialidad. Regresa -1 de no encontrarse dicho registro
     */
    public int buscarEspecialidad(byte clvEspecialidad){
        int PM=0;
        try {
            int LI=0;
            int LS=(int)archEspecialidad.length()/Especialidad.TAR;
            byte bus = clvEspecialidad,clvLeida;
            do{
                PM=(LI+LS)/2;
                archEspecialidad.seek(PM*Especialidad.TAR);
                clvLeida=archEspecialidad.readByte();
                if(bus>clvLeida)
                    LI=PM+1;
                else 
                    LS=PM-1;
            }while(bus != clvLeida  && LI <= LS);
            
            if(bus==clvLeida){
                return PM;
            }else{
                return -1;
            }  
        }catch(EOFException ex){  
            return -1;
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PM;
        
        
    }
    
    /**
     * Soobreescribe el registro pasado como parametro, con la informacion de la nueva especialidad. 
     * @param numRegistro El numero de registro que se desea modificar
     * @param especialidad   Informacion que desea reemplazar al registro 
     */
    public void modEspecialidad(int numRegistro, Especialidad especialidad){
        try {
            archEspecialidad.seek(numRegistro*Especialidad.TAR);
            Especialidad.escribirRegistro(archEspecialidad, especialidad);
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Inscribe un alumno en una materia
     * @param numControl El numero de control que se desea inscribir
     * @param clvMateria La clave de materia en la que se va a inscribir
     */
    public void inscribirAlumno(String numControl, String clvMateria){
        try {            
            archInscripciones.seek(archInscripciones.length());
            String clvAux = String.format("%-4s", clvMateria);
            archInscripciones.writeUTF(numControl);
            archInscripciones.writeUTF(clvAux);            
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ordena las inscripciones por numero de control
     */
    public void ordInscripciones(){
        try {
            int numR = (int)archInscripciones.length() / 16; //cada registro pesa 16 bytes
            int com, pas;
            String numA, numB, cvA, cvB;          
            boolean ban = true;
            for(pas = 1; ban; pas++){
                archInscripciones.seek(0);
                ban = false;
                for(com = 1; com <= (numR - pas); com++){
                    numA = archInscripciones.readUTF();
                    cvA = archInscripciones.readUTF();
                    numB = archInscripciones.readUTF();
                    cvB = archInscripciones.readUTF();
                    if(numA.compareTo(numB) > 0){
                        ban = true;
                        archInscripciones.seek((com-1) * 16);
                        archInscripciones.writeUTF(numB);
                        archInscripciones.writeUTF(cvB);
                        archInscripciones.writeUTF(numA);
                        archInscripciones.writeUTF(cvA);
                    }
                    archInscripciones.seek(com*16);
                }
            }
            archInscripciones.seek(0);
        }catch(EOFException ex){        
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Regresa un ArrayList tipo String con las materias inscritas por el numero de control como parametro.
     * Este metodo requiere que el archivo inscripciones este ordenado
     * @param numControl El numero de control del que se desea saber las materias inscritas
     * @return ArrayList con las claves de las materias escritas
     */
    public ArrayList<String> leerMateInscritas(String numControl){
        ArrayList<String> materias = null;
        String numLeido, clvMateria;      
        try {
           
            materias = new ArrayList<>();
            archInscripciones.seek(0);
            boolean encontrado = false;
            do{                
                numLeido = archInscripciones.readUTF();
                clvMateria = archInscripciones.readUTF();
                if(numLeido.equals(numControl)){
                    materias.add(clvMateria);
                    encontrado = true;
                }
            }while(numControl.equals(numLeido) || !encontrado);            
        }catch(EOFException ex){        
        } catch (IOException ex) {
            Logger.getLogger(ArchivosManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return materias;
    }    
    
}
