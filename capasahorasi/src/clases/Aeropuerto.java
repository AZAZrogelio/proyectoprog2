package clases;
import java.util.Scanner;
public class Aeropuerto {
	protected String nombre;
    protected String ciudad_pais;
    int numero_pistas;
    int numero_vuelos;
    protected Pista[] pistas;   
    protected Vuelo[] vuelos;   

    public void leer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del aeropuerto:");
        nombre = sc.nextLine();
        System.out.println("Ingrese la ciudad y pais del aeropuerto:");
        ciudad_pais = sc.nextLine();
        System.out.println("Ingrese el numero de pistas:");
        numero_pistas = sc.nextInt();
        sc.nextLine(); 
        pistas = new Pista[numero_pistas];
        vuelos = new Vuelo[100];
        for (int i = 0; i < numero_pistas; i++) {
            pistas[i] = new Pista(i + 1); 
        }
        numero_vuelos = 0;
    }

    public void agregarVuelo(Vuelo v) {
        if (numero_vuelos < vuelos.length) {
            vuelos[numero_vuelos] = v;
            numero_vuelos++;
        } else {
            System.out.println("No se pueden agregar mas vuelos, capacidad maxima alcanzada.");
        }
    }

    public void eliminarVuelo(int vuelo) {
        if (vuelo > 0 && vuelo <= numero_vuelos) {
            if (vuelo < numero_vuelos) {
                for (int i = vuelo - 1; i < numero_vuelos - 1; i++) {
                    vuelos[i] = vuelos[i + 1];
                }
            } else {
                vuelos[vuelo - 1] = null;
            }
            vuelos[numero_vuelos - 1] = null;
            numero_vuelos--;
        } else {
            System.out.println("Numero de vuelo invalido.");
        }
    }

    public void mostrar(int vuelo) {
        if (vuelo > 0 && vuelo <= numero_vuelos) {
            System.out.println("Vuelo " + vuelo + ":");
            vuelos[vuelo - 1].mostrar();
        } else {
            System.out.println("Numero de vuelo invalido.");
        }
    }

    public void duracionVuelo(int vuelo) {
        if (vuelo > 0 && vuelo <= numero_vuelos) {
            System.out.println("Duracion del vuelo " + vuelo + ": " + vuelos[vuelo - 1].duracion + " horas.");
        } else {
            System.out.println("Numero de vuelo invalido.");
        }
    }

    public void retrasoVuelo(int vuelo) {
        if (vuelo > 0 && vuelo <= numero_vuelos) {
            
        } else {
            System.out.println("Numero de vuelo invalido.");
        }
    }

    public void mostrar() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Ciudad y pais: " + ciudad_pais);
        System.out.println("Numero de pistas: " + numero_pistas);
        System.out.println("Numero de vuelos: " + numero_vuelos);
        for (int i = 0; i < numero_pistas; i++) {
            System.out.println("Pista " + (i + 1) + ":");
            pistas[i].mostrar();
        }
        for (int i = 0; i < numero_vuelos; i++) {
            System.out.println("Vuelo " + (i + 1) + ":");
            vuelos[i].mostrar();
        }
    }

    public void agregarPasajero(Pasajeros pasajero, int vuelo) {
        if (vuelo > 0 && vuelo <= numero_vuelos) {
            vuelos[vuelo - 1].agregarpasajero(pasajero);
        } else {
            System.out.println("Numero de vuelo invalido.");
        }
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad_pais() {
		return ciudad_pais;
	}

	public void setCiudad_pais(String ciudad_pais) {
		this.ciudad_pais = ciudad_pais;
	}

	public int getNumero_pistas() {
		return numero_pistas;
	}

	public void setNumero_pistas(int numero_pistas) {
		this.numero_pistas = numero_pistas;
	}

	public int getNumero_vuelos() {
		return numero_vuelos;
	}

	public void setNumero_vuelos(int numero_vuelos) {
		this.numero_vuelos = numero_vuelos;
	}

	public Pista[] getPistas() {
		return pistas;
	}

	public void setPistas(Pista[] pistas) {
		this.pistas = pistas;
	}

	public Vuelo[] getVuelos() {
		return vuelos;
	}

	public void setVuelos(Vuelo[] vuelos) {
		this.vuelos = vuelos;
	}
}
