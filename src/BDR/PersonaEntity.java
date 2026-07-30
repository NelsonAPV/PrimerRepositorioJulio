package BDR;

public class PersonaEntity {

	private String nombre;
	private String apellidos;
	private int edad;
	private int id;
	
	
	public PersonaEntity(String nombre, String apellidos, int edad, int id) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
		
	
	
}
