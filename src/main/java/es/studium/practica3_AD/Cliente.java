package es.studium.practica3_AD;

public class Cliente { 
	private int idCliente; 
	private String nombre; 
	private String apellidos; 
	private String email; 
	private String dni; 
	private String clave;
	
	public Cliente() {}
	
	public Cliente(int idCliente, String nombre, String apellidos, String email, String dni, String clave) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.dni = dni;
		this.clave = clave;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public String toString() {
		return idCliente + ", " + nombre + ", " + apellidos + ", " + email + ", " + dni + ", " + clave;
	}
}
