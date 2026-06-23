package clases;

import java.util.Scanner;

public class Tripulacion extends persona {
	protected String experiencia;
    protected String Universidad;

    public void leer() {
        super.leer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la experiencia del tripulante:");
        experiencia = sc.next();
        sc.nextLine();
        System.out.println("Ingrese la universidad del tripulante:");
        Universidad = sc.next();
        sc.nextLine();
    }

    public void mostrar() {
        super.mostrar();
        System.out.println("Experiencia: " + experiencia);
        System.out.println("Universidad: " + Universidad);
    }

	public String getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}

	public String getUniversidad() {
		return Universidad;
	}

	public void setUniversidad(String universidad) {
		Universidad = universidad;
	}
}
