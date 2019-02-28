package proyecto4parcial;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author fernando
 */
public class Especialidad {
    private String nombEspecialidad;
    private byte clvEspecialidad;

    /**
     *
     */
    public static final int TAR = 43;

    /**
     *
     * @param clvEspecialidad
     * @param nombEspecialidad
     */
    public Especialidad(byte clvEspecialidad, String nombEspecialidad) {
        this.nombEspecialidad = nombEspecialidad;
        this.clvEspecialidad = clvEspecialidad;
    }   

    /**
     *
     * @return
     */
    public String getNombEspecialidad() {
        return nombEspecialidad;
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
     * proporcionado.
     * @param archivo variable de referencia previamente inicializada
     * @param esp objeto que sera escrito en un registro
     * @throws IOException 
     */
    public static void escribirRegistro(RandomAccessFile archivo, Especialidad esp) throws IOException{
       archivo.writeByte(esp.getClvEspecialidad());
       archivo.writeUTF(esp.getNombEspecialidad());
    }
    /**
     * Este metodo lee un registro de la posicion que se encuentre el cursor del archivo que le sea 
     * proporcionado
     * @param archivo variable de referencia previamente inicializada
     * @return objeto Especialidad que representa un registro
     * @throws IOException 
     */
    public static Especialidad leerResgistro(RandomAccessFile archivo) throws IOException{
        byte clave = archivo.readByte();
        String nombre = archivo.readUTF();
        
        return new Especialidad(clave, nombre);
    }

    @Override
    public String toString() {
       return clvEspecialidad +" "+nombEspecialidad;
    }
    
    
    
}
