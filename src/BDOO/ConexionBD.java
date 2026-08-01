package BDOO;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class ConexionBD {

    static final String DB_FILE = "personas.db4o";

    public ObjectContainer abrirConexion() {
        // Abre (o crea si no existe) el archivo de la base de datos de objetos
        return Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB_FILE);
    }

}
