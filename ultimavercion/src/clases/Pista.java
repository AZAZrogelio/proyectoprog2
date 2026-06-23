package clases;

import java.util.Scanner;

public class Pista {
	private int numero_pista;
    private float longitud;
    private String tipo_superficie;

    public Pista(int numero_pista) {
        this.numero_pista = numero_pista;
        this.longitud = 0;
        this.tipo_superficie = "";
    }

    public int getNumero_pista() {
		return numero_pista;
	}

	public void setNumero_pista(int numero_pista) {
		this.numero_pista = numero_pista;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public String getTipo_superficie() {
		return tipo_superficie;
	}

	public void setTipo_superficie(String tipo_superficie) {
		this.tipo_superficie = tipo_superficie;
	}

	public void leer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la longitud de la pista:");
        longitud = sc.nextFloat();
        sc.nextLine(); 
        System.out.println("Ingrese el tipo de superficie de la pista:");
        tipo_superficie = sc.nextLine();
    }

    public void mostrar() {
        System.out.println("Numero de pista: " + numero_pista);
        System.out.println("Longitud: " + longitud + " metros");
        System.out.println("Tipo de superficie: " + tipo_superficie);
    }
}
