package proyecto4parcial;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author fernando
 */
public class Materia {
    private String clvMateria;
    private String nombMateria;
    private byte creditos;

    /**
     *
     */
    public static final int TAR = 49;

    /**
     *
     * @param clvMateria
     * @param nombMateria
     * @param creditos
     */
    public Materia(String clvMateria, String nombMateria, byte creditos) {
        this.clvMateria = clvMateria;
        this.nombMateria = nombMateria;
        this.creditos = creditos;        
    }    
    
    /**
     *
     * @return
     */
    public String getClvMateria() {
        return clvMateria;
    }

    /**
     *
     * @return
     */
    public String getNombMateria() {
        return nombMateria;
    }

    /**
     *
     * @return
     */
    public byte getCreditos() {
        return creditos;
    }
    
    /**
     *Este metodo escribe un registro en la posicion que se encuentre el cursor del archivo que le sea 
     * proporcionado
     * @param archivo variable de referencia previamente inicializada
     * @param materia el objeto que será escrito en el registro
     * @throws IOException 
     */
    public static void escribirRegistro(RandomAccessFile archivo, Materia materia) throws IOException{
        archivo.writeUTF(materia.getClvMateria());
        archivo.writeUTF(materia.getNombMateria());
        archivo.writeByte(materia.getCreditos()); 
       
    }
    
    /**
     * Este metodo lee un registro de la posicion que se encuentre el cursor del archivo que le sea 
     * proporcionado
     * @param archivo variable de referencia previamente inicializada
     * @return objeto Materia que representa un registro
     * @throws IOException 
     */
    public static Materia leerRegistro(RandomAccessFile archivo) throws IOException{
        String clave = archivo.readUTF();
        String nombre = archivo.readUTF();
        byte creditos = archivo.readByte();
        
        return new Materia(clave, nombre, creditos);
    }

    @Override
    public String toString() {
        return clvMateria + " " + nombMateria + " " + creditos;
    }
    
    
}
