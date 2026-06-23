package clases;

import java.util.Scanner;

public class Pasajeros extends persona{
	private String numero_pasaporte;
    private String clase;
    private String asiento;

    public String getNumero_pasaporte() {
		return numero_pasaporte;
	}

	public void setNumero_pasaporte(String numero_pasaporte) {
		this.numero_pasaporte = numero_pasaporte;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getAsiento() {
		return asiento;
	}

	public void setAsiento(String asiento) {
		this.asiento = asiento;
	}

	public void leer() {
        super.leer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el número de pasaporte:");
        numero_pasaporte = sc.nextLine();
        System.out.println("Ingrese la clase del pasajero:");
        clase = sc.nextLine();
        System.out.println("Ingrese el asiento del pasajero:");
        asiento = sc.nextLine();
    }

    public void mostrar() {
        super.mostrar();
        System.out.println("Número de pasaporte: " + numero_pasaporte);
        System.out.println("Clase: " + clase);
        System.out.println("Asiento: " + asiento);
    }
}
