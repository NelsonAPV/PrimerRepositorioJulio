package BDOO;

import BDOO.ConexionBD;
import BDOO.PersonaDAO;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Principal {


	/*
	* Este tipo de BD es util cuando se trabaja con objetos,
	* NO es util cuando se realizan consultas muy complejas
	* */

	static Scanner teclado;
	static PersonaService personaService;

	public static void main(String[] args) {

		ConexionBD conexionBD = new ConexionBD();
		PersonaDAO personaDAO = new PersonaDAO(conexionBD);
		personaService = new PersonaService(personaDAO);
		teclado = new Scanner(System.in);

		int op = 0;

		do {
			do{
				try {
					System.out.println("Elige una opcion");
					System.out.println("1-Alta persona");
					System.out.println("2-Baja persona");
					System.out.println("3-Consulta persona");
					System.out.println("4-Modificar persona");
					System.out.println("5-Listado persona");
					System.out.println("6-Fin");
					op = teclado.nextInt();

					if(op < 1 || op > 6) {
						System.out.println("\nOPcion no valida\n");
					}

				} catch (InputMismatchException e) {
					System.out.println("No has introducido un numero");
					teclado.next();
				}
			}while(op < 1 || op > 6);


			switch (op) {
			case 1:
				altaPersona();
				break;

			case 2: 
				eliminarPersona();
				break;

			case 3:
				consultarPersona();
				break;

			case 4:
				modificarPersona();
				break;

			case 5:
				listadoPersona();
				break;

			default:
				System.out.println("Fin de la aplicacion");
			}



		}while(op != 6);
	}

	/**
	 * 
	 */
	private static void listadoPersona() {

		
		ArrayList<PersonaEntity> listaPersona = personaService.listarPersonas();
		
		if(!listaPersona.isEmpty()) {
			
			for(PersonaEntity persona: listaPersona) {
				System.out.println(persona.getId() + "-> " + persona.getNombre() + " " + persona.getApellidos() + " " + persona.getEdad());
			}
			System.out.println();
		}else {
			System.out.println("No existen personas");
		}
		
		
		
		
	}

	/**
	 * 
	 */
	private static void consultarPersona() {

		int id = 0;

		System.out.println("Introduce id");
		id = teclado.nextInt();

		PersonaEntity persona = personaService.consultarPersonaPorId(id);

		if(persona != null) {
			System.out.println(persona.getId() + "-> " + persona.getNombre() + " " + persona.getApellidos() + " " + persona.getEdad());
		}else {
			System.out.println("No existe la persona");
		}
	}


	private static void modificarPersona() {

		int id = 0;
		String nombre = "";
		String apellidos = "";
		int edad = 0;
		
		System.out.println("Introduce id");
		id = teclado.nextInt();
		
		System.out.println("Introduce un nombre");
		nombre = teclado.next();

		System.out.println("Introduce apellidos");
		apellidos = teclado.next() + teclado.nextLine();

		System.out.println("Introduce edad");
		edad = teclado.nextInt();

		personaService.modificarPersona(id,nombre, apellidos, edad);
		
		System.out.println("\nDatos modificados\n");
	}

	private static void eliminarPersona() {

		int id = 0;

		System.out.println("Introduce id");
		id = teclado.nextInt();

		boolean res = personaService.eliminarPersona(id);

		if(res) {
			System.out.println("\nLa persona ha ido eliminada\n");
		}else {
			System.out.println("\nLa persona no ha sido eliminada\n");
		}

	}

	private static void altaPersona() {

		String nombre = "";
		String apellidos = "";
		int edad = 0;

		System.out.println("Introduce un nombre");
		nombre = teclado.next();

		System.out.println("Introduce apellidos");
		apellidos = teclado.next() + teclado.nextLine();

		System.out.println("Introduce edad");
		edad = teclado.nextInt();

		if(personaService.darDeAltaPersona(nombre,apellidos,edad)){
			System.out.println("\nPersona guardada\n");
		}
	}
}
