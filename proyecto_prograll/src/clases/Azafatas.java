package clases;

import java.util.Scanner;

public class Azafatas extends Tripulacion {
	private String idioma;
    private String uniforme;
    private int numero_azafata;

    public void leer() {
        super.leer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el idioma que habla la azafata:");
        idioma = sc.next();
        sc.nextLine();
        System.out.println("Ingrese el uniforme de la azafata:");
        uniforme = sc.next();
        sc.nextLine();
        System.out.println("Ingrese el número de azafata:");
        numero_azafata = sc.nextInt();
        sc.nextLine();
    }

    public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getUniforme() {
		return uniforme;
	}

	public void setUniforme(String uniforme) {
		this.uniforme = uniforme;
	}

	public int getNumero_azafata() {
		return numero_azafata;
	}

	public void setNumero_azafata(int numero_azafata) {
		this.numero_azafata = numero_azafata;
	}

	public void mostrar() {
        super.mostrar(); // imprime Persona + Tripulacion + Azafatas
        System.out.println("Idioma: " + idioma);
        System.out.println("Uniforme: " + uniforme);
        System.out.println("Número de azafata: " + numero_azafata);
    }
}
