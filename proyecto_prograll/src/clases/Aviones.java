package clases;

import java.util.Scanner;
public class Aviones {
	private String modelo;
    private String marca;
    public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getNumero_avion() {
		return numero_avion;
	}

	public void setNumero_avion(int numero_avion) {
		this.numero_avion = numero_avion;
	}

	public Pasajeros[] getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(Pasajeros[] pasajeros) {
		this.pasajeros = pasajeros;
	}

	public int getNumero_pasajeros() {
		return numero_pasajeros;
	}

	public void setNumero_pasajeros(int numero_pasajeros) {
		this.numero_pasajeros = numero_pasajeros;
	}

	private int capacidad;
    private int numero_avion;
    private Pasajeros[] pasajeros;
    private int numero_pasajeros;

    public void leer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el modelo del avión:");
        modelo = sc.nextLine();
        System.out.println("Ingrese la marca del avión:");
        marca = sc.nextLine();
        System.out.println("Ingrese la capacidad del avión:");
        capacidad = sc.nextInt();
        sc.nextLine(); // Limpieza: evita que el siguiente nextLine() capture el '\n' de nextInt()
        System.out.println("Ingrese el número de avión:");
        numero_avion = sc.nextInt();
        sc.nextLine();
        pasajeros = new Pasajeros[capacidad];
        numero_pasajeros = 0;
    }

    public void agregarPasajero(Pasajeros pasajero) {
        if (numero_pasajeros < capacidad) {
            pasajeros[numero_pasajeros] = pasajero;
            numero_pasajeros++;
        } else {
            System.out.println("No se pueden agregar más pasajeros, capacidad máxima alcanzada.");
        }
    }

    public void mostrar() {
        System.out.println("Modelo: " + modelo);
        System.out.println("Marca: " + marca);
        System.out.println("Capacidad: " + capacidad);
        System.out.println("Número de avión: " + numero_avion);
        System.out.println("Número de pasajeros: " + numero_pasajeros);
        for (int i = 0; i < numero_pasajeros; i++) {
            System.out.println("Pasajero " + (i + 1) + ":");
            pasajeros[i].mostrar();
        }
    }

    public void mostrar(int numero_de_pasajero) {
        if (numero_de_pasajero > 0 && numero_de_pasajero <= numero_pasajeros) {
            System.out.println("Pasajero " + numero_de_pasajero + ":");
            pasajeros[numero_de_pasajero - 1].mostrar();
        } else {
            System.out.println("Número de pasajero inválido.");
        }
    }

    public void mostrarpasajeros() {
        System.out.println("Número de pasajeros: " + numero_pasajeros);
        for (int i = 0; i < numero_pasajeros; i++) {
            System.out.println("Pasajero " + (i + 1) + ":");
            System.out.println("- " + pasajeros[i].nombre + " " + pasajeros[i].apellido);
        }
    }
}
