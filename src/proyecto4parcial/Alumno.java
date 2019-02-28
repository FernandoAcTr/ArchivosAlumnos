package proyecto4parcial;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author fernando
 */
public class Alumno {
    private String numControl;
    private String nombre;
    private byte  clvEspecialidad;

    /**
     *
     */
    public static final int TAR = 53;
    
    /**
     *
     * @param numControl
     * @param nombre
     * @param clvEspecialidad
     */
    public Alumno(String numControl, String nombre, byte clvEspecialidad) {
        this.numControl = numControl;
        this.nombre = nombre;
        this.clvEspecialidad = clvEspecialidad;
    }   

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public String getNumControl() {
        return numControl;
    }

    /**
     *
     * @return
     */
    public byte getClvEspecialidad() {
        return clvEspecialidad;
    }   
    
    /**
     * Este metodo escribe un registro en la posicion que se encuentre el cursor del archivo que le sea 
     * proporcionado
     * @param archivo variable de referencia previamente inicializada
     * @param alumno objeto que será escrito en un archivo
     * @throws java.io.IOException
     */
    public static void escribirRegistro(RandomAccessFile archivo, Alumno alumno) throws IOException{
        archivo.writeUTF(alumno.getNumControl());
        archivo.writeUTF(alumno.getNombre());
        archivo.writeByte(alumno.getClvEspecialidad());
    }
    
    /**
     * Este metodo lee un registro de la posicion que se encuentre el cursor del archivo que le sea 
     * proporcionado
     * @param archivo variable de referencia previamente inicializada
     * @return objeto Alumno que representa un registro
     * @throws IOException 
     */
    public static Alumno leerRegistro(RandomAccessFile archivo) throws IOException{
        String numControl = archivo.readUTF();
        String nombre = archivo.readUTF();
        byte clvEspecialidad = archivo.readByte();
        
        return new Alumno(numControl, nombre, clvEspecialidad);
    }

    @Override
    public String toString() {
        return numControl + " " + nombre + " " + clvEspecialidad;
    }
}