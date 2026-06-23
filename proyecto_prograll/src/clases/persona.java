package clases;

import java.util.Scanner;

public class persona {
	protected String nombre;
    protected String apellido;
    protected String nacionalidad;
    protected String genero;
    protected String estado_civil;
    protected String ocupacion;
    protected String direccion;
    protected String telefono;
    protected int ci;       
    protected int edad;
    public void leer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre:");
        nombre = sc.nextLine();
        System.out.println("Ingrese el apellido:");
        apellido = sc.nextLine();
        System.out.println("Ingrese la edad:");
        edad = sc.nextInt();
        sc.nextLine(); 
        System.out.println("Ingrese la nacionalidad:");
        nacionalidad = sc.nextLine();
        System.out.println("Ingrese el género:");
        genero = sc.nextLine();
        System.out.println("Ingrese el estado civil:");
        estado_civil = sc.nextLine();
        System.out.println("Ingrese la ocupación:");
        ocupacion = sc.nextLine();
        System.out.println("Ingrese la dirección:");
        direccion = sc.nextLine();
        System.out.println("Ingrese el teléfono:");
        telefono = sc.nextLine();
        System.out.println("Ingrese el número de cédula de identidad:");
        ci = sc.nextInt();
        sc.nextLine(); 
    }

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEstado_civil() {
		return estado_civil;
	}

	public void setEstado_civil(String estado_civil) {
		this.estado_civil = estado_civil;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCi() {
		return ci;
	}

	public void setCi(int ci) {
		this.ci = ci;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public void mostrar() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Nacionalidad: " + nacionalidad);
        System.out.println("Género: " + genero);
        System.out.println("Estado civil: " + estado_civil);
        System.out.println("Ocupación: " + ocupacion);
        System.out.println("Dirección: " + direccion);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Número de cédula de identidad: " + ci);
    }
}
