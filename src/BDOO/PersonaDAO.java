package BDOO;


import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.ArrayList;

public class PersonaDAO {


    /*
    * En las bases de datos orientadas a objetos puras, el concepto tradicional de
    * "Clave Primaria" ($PK$) tal como lo conocemos en MySQL no existe.En su lugar,
    *  cada objeto guardado tiene una OID (Object Identifier o Identificador de Objeto)
    *  asignada automáticamente por el motor en memoria. La identidad del objeto no la define
    * una columna, sino el propio objeto en sí.
    *
    * ¿Cómo hacer que el ID no se repita en Db4o?
Al no existir la cláusula AUTO_INCREMENT de MySQL, la mejor práctica en Java dentro
*  del patrón N-Capas consiste en calcular el siguiente ID disponible desde la capa DAO
*  o Service antes de guardar.
    *
    * */

    private ConexionBD conexionBD;

    public PersonaDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    // Método dentro de PersonaDAO
    private int obtenerSiguienteId(ObjectContainer db) {
        ObjectSet<PersonaEntity> todas = db.queryByExample(new PersonaEntity());

        int maxId = 0;
        for (PersonaEntity p : todas) {
            if (p.getId() > maxId) {
                maxId = p.getId();
            }
        }

        return maxId + 1; // El nuevo ID será el mayor + 1
    }


    // 1. ALTA (Guardar objeto nativo)
    public boolean darDeAltaPersona(PersonaEntity persona) {
        ObjectContainer db = conexionBD.abrirConexion();
        try {

            int nuevoId = obtenerSiguienteId(db);
            persona.setId(nuevoId);

            db.store(persona); // Guarda el objeto Java directo en el disco
            db.commit();
            return true;
        } finally {
            db.close();
        }
    }

    public PersonaEntity consultarPersonaPorId(int id) {
        ObjectContainer db = conexionBD.abrirConexion();
        try {
            // Creamos una plantilla con el ID a buscar
            PersonaEntity plantilla = new PersonaEntity();
            plantilla.setId(id);

            ObjectSet<PersonaEntity> resultado = db.queryByExample(plantilla);
            if (resultado.hasNext()) {

                plantilla = resultado.next();

                return plantilla;
            }
            return null;
        } finally {
            db.close();
        }
    }

    // 3. LISTAR TODAS LAS PERSONAS
    public ArrayList<PersonaEntity> listarPersonas() {
        ObjectContainer db = conexionBD.abrirConexion();
        try {
            // Pasar la clase vacía devuelve TODOS los objetos de ese tipo
            ObjectSet<PersonaEntity> resultado = db.queryByExample(new PersonaEntity());

            ArrayList<PersonaEntity> listaPersona = new ArrayList<PersonaEntity>();

            for(PersonaEntity p : resultado){
                listaPersona.add(p);
            }

            return listaPersona;
        } finally {
            db.close();
        }
    }

    // 4. ELIMINAR PERSONA
    public boolean eliminarPersona(int id) {
        ObjectContainer db = conexionBD.abrirConexion();
        try {
            PersonaEntity encontrada = buscarPorIdInterno(db, id);
            if (encontrada != null) {
                db.delete(encontrada); // Elimina el objeto
                db.commit();
                return true;
            }
            return false;
        } finally {
            db.close();
        }
    }

    // 5. MODIFICAR PERSONA
    public boolean modificarPersona(PersonaEntity personaActualizada) {
        ObjectContainer db = conexionBD.abrirConexion();
        try {
            PersonaEntity encontrada = buscarPorIdInterno(db, personaActualizada.getId());
            if (encontrada != null) {
                // Actualizamos los campos en el objeto de la BD
                encontrada.setNombre(personaActualizada.getNombre());
                encontrada.setApellidos(personaActualizada.getApellidos());
                encontrada.setEdad(personaActualizada.getEdad());

                db.store(encontrada); // Vuelve a guardar el objeto actualizado
                db.commit();
                return true;
            }
            return false;
        } finally {
            db.close();
        }
    }

    private PersonaEntity buscarPorIdInterno(ObjectContainer db, int id) {
        PersonaEntity plantilla = new PersonaEntity();
        plantilla.setId(id);
        ObjectSet<PersonaEntity> resultado = db.queryByExample(plantilla);

        plantilla = resultado.next();

        return resultado.hasNext() ? plantilla : null;
    }
}



