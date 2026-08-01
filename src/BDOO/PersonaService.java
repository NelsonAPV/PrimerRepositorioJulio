package BDOO;

import BDOO.PersonaDAO;

import java.util.ArrayList;

public class PersonaService {

    private PersonaDAO personaDAO;

    public PersonaService(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    // 1. ALTA
    public boolean darDeAltaPersona(String nombre, String apellidos, int edad) {
        if(!validarNombre(nombre) || !validarApellidos(apellidos) || !validarEdad(edad)){
            return false;
        }else{
            PersonaEntity nuevaPersona = new PersonaEntity( nombre.trim(), apellidos.trim(), edad,0);
            return personaDAO.darDeAltaPersona(nuevaPersona);
        }
    }



    // 2. ELIMINAR POR ID
    public boolean eliminarPersona(int id) {

        if(!validarId(id)){
            return false;
        }else {
            // Regla opcional: Verificar si existe antes de borrar
            PersonaEntity existe = personaDAO.consultarPersonaPorId(id);
            if (existe == null) {
                throw new IllegalArgumentException("No existe ninguna persona con el ID: " + id);
            }
            return personaDAO.eliminarPersona(id);
        }
    }


    // 3. CONSULTAR POR ID (Devuelve DTO)
    public PersonaEntity consultarPersonaPorId(int id) {

        if(!validarId(id)){
            return null;
        }else {

            PersonaEntity entity = personaDAO.consultarPersonaPorId(id);
            if (entity == null) {
                return null;
            }

            return new PersonaEntity(entity.getNombre(), entity.getApellidos(), entity.getEdad(), entity.getId());
        }
    }

    // 4. LISTADO DE TODAS LAS PERSONAS (Devuelve lista de DTOs)
    public ArrayList<PersonaEntity> listarPersonas() {
        ArrayList<PersonaEntity> entities = personaDAO.listarPersonas();


        return entities;
    }


    // 5. MODIFICAR PERSONA
    public boolean modificarPersona(int id, String nuevoNombre, String nuevosApellidos, int nuevaEdad) {

        if(!validarId(id) || !validarNombre(nuevoNombre) || !validarApellidos(nuevosApellidos) || !validarEdad(nuevaEdad)) {
            return false;
        }

        // Verificamos si la persona existe
        PersonaEntity existe = personaDAO.consultarPersonaPorId(id);
        if (existe == null) {
            System.out.println("No se puede modificar. No existe la persona con ID: " + id);
            return false;
        }else{
            PersonaEntity personaActualizada = new PersonaEntity( nuevoNombre.trim(), nuevosApellidos.trim(), nuevaEdad,id);

            return personaDAO.modificarPersona(personaActualizada);
        }


    }

    // --- Métodos auxiliares de validación ---

    private boolean validarId(int id) {
        if (id <= 0) {
            System.out.println("El ID debe ser mayor que 0.");
            return false;
        }

        return true;
    }

    private boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return false;
        }
        if (nombre.trim().length() <= 2) {
            System.out.println("El nombre debe tener más de 2 caracteres.");
            return false;
        }
        return true;

    }

    private boolean validarApellidos(String apellidos) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            System.out.println("Los apellidos no pueden estar vacíos.");
            return false;
        }
        if (apellidos.trim().length() <= 2) {
            System.out.println("Los apellidos deben tener más de 2 caracteres.");
            return false;
        }
        return true;
    }

    private boolean validarEdad(int edad) {
        // Al pedir un 'int' en Java ya garantizas que sea entero,
        // pero validamos que sea positivo y mayor de 18:
        if (edad <= 0) {
            System.out.println("La edad debe ser un número positivo.");
            return false;
        }

        if (edad < 18) {
            System.out.println("La persona debe ser mayor de edad (18 años o más).");
            return false;
        }

        return true;
    }



}
