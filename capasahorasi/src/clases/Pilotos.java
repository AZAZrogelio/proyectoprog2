package clases;
import java.util.Scanner;
public class Pilotos extends Tripulacion{
	private String licencia;
    private String tipo_licencia;
    private int NumeroPiloto;

    public void leer() {
        super.leer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la licencia del piloto:");
        licencia = sc.next();
        sc.nextLine();
        System.out.println("Ingrese el tipo de licencia del piloto:");
        tipo_licencia = sc.next();
        sc.nextLine();
        System.out.println("Ingrese el numero de piloto:");
        NumeroPiloto = sc.nextInt();
        sc.nextLine();
    }

    public String getLicencia() {
		return licencia;
	}

	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}

	public String getTipo_licencia() {
		return tipo_licencia;
	}

	public void setTipo_licencia(String tipo_licencia) {
		this.tipo_licencia = tipo_licencia;
	}

	public int getNumeroPiloto() {
		return NumeroPiloto;
	}

	public void setNumeroPiloto(int numeroPiloto) {
		NumeroPiloto = numeroPiloto;
	}

	public void mostrar() {
        super.mostrar(); // imprime Persona + Tripulacion + Pilotos
        System.out.println("Licencia: " + licencia);
        System.out.println("Tipo de licencia: " + tipo_licencia);
        System.out.println("Numero de piloto: " + NumeroPiloto);
    }
}
