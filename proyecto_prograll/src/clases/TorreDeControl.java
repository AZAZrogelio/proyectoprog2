package clases;
import java.util.Scanner;
public class TorreDeControl {
	private Aeropuerto[] aeropuertos = new Aeropuerto[10];
    private Vuelo[]      vuelos      = new Vuelo[50];
    private Aviones[]    aviones     = new Aviones[20];
    private Pilotos[]    pilotos     = new Pilotos[20];
    private Azafatas[]   azafatas    = new Azafatas[30];
    private Pasajeros[]  pasajeros   = new Pasajeros[200];

    private int numAeropuertos = 0;
    private int numVuelos      = 0;
    public Aeropuerto[] getAeropuertos() {
		return aeropuertos;
	}

	public void setAeropuertos(Aeropuerto[] aeropuertos) {
		this.aeropuertos = aeropuertos;
	}

	public Vuelo[] getVuelos() {
		return vuelos;
	}

	public void setVuelos(Vuelo[] vuelos) {
		this.vuelos = vuelos;
	}

	public Aviones[] getAviones() {
		return aviones;
	}

	public void setAviones(Aviones[] aviones) {
		this.aviones = aviones;
	}

	public Pilotos[] getPilotos() {
		return pilotos;
	}

	public void setPilotos(Pilotos[] pilotos) {
		this.pilotos = pilotos;
	}

	public Azafatas[] getAzafatas() {
		return azafatas;
	}

	public void setAzafatas(Azafatas[] azafatas) {
		this.azafatas = azafatas;
	}

	public Pasajeros[] getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(Pasajeros[] pasajeros) {
		this.pasajeros = pasajeros;
	}

	public int getNumAeropuertos() {
		return numAeropuertos;
	}

	public void setNumAeropuertos(int numAeropuertos) {
		this.numAeropuertos = numAeropuertos;
	}

	public int getNumVuelos() {
		return numVuelos;
	}

	public void setNumVuelos(int numVuelos) {
		this.numVuelos = numVuelos;
	}

	public int getNumAviones() {
		return numAviones;
	}

	public void setNumAviones(int numAviones) {
		this.numAviones = numAviones;
	}

	public int getNumPilotos() {
		return numPilotos;
	}

	public void setNumPilotos(int numPilotos) {
		this.numPilotos = numPilotos;
	}

	public int getNumAzafatas() {
		return numAzafatas;
	}

	public void setNumAzafatas(int numAzafatas) {
		this.numAzafatas = numAzafatas;
	}

	public int getNumPasajeros() {
		return numPasajeros;
	}

	public void setNumPasajeros(int numPasajeros) {
		this.numPasajeros = numPasajeros;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}


	private int numAviones     = 0;
    private int numPilotos     = 0;
    private int numAzafatas    = 0;
    private int numPasajeros   = 0;

    private Scanner sc = new Scanner(System.in);


    public void menu() {
        int opcion;
        do {
            System.out.println("\n========== TORRE DE CONTROL ==========");
            System.out.println("1. Registrar aeropuerto");
            System.out.println("2. Registrar vuelo");
            System.out.println("3. Registrar avión");
            System.out.println("4. Registrar piloto");
            System.out.println("5. Registrar azafata");
            System.out.println("6. Registrar pasajero");
            System.out.println("7. Asignar avión a vuelo");
            System.out.println("8. Asignar piloto a vuelo");
            System.out.println("9. Asignar azafatas a vuelo");
            System.out.println("10. Agregar vuelo a aeropuerto");
            System.out.println("11. Agregar pasajero a vuelo en aeropuerto");
            System.out.println("12. Mostrar aeropuerto completo");
            System.out.println("13. Mostrar vuelo específico");
            System.out.println("14. Calcular duración de vuelo");
            System.out.println("15. Calcular retraso de vuelo");
            System.out.println("16. Eliminar vuelo de aeropuerto");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:  registrarAeropuerto();          break;
                case 2:  registrarVuelo();               break;
                case 3:  registrarAvion();               break;
                case 4:  registrarPiloto();              break;
                case 5:  registrarAzafata();             break;
                case 6:  registrarPasajero();            break;
                case 7:  asignarAvionAVuelo();           break;
                case 8:  asignarPilotoAVuelo();          break;
                case 9:  asignarAzafatasAVuelo();        break;
                case 10: agregarVueloAAeropuerto();      break;
                case 11: agregarPasajeroAVuelo();        break;
                case 12: mostrarAeropuerto();            break;
                case 13: mostrarVuelo();                 break;
                case 14: calcularDuracion();             break;
                case 15: calcularRetraso();              break;
                case 16: eliminarVuelo();                break;
                case 0:  System.out.println("Saliendo de la Torre de Control."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void registrarAeropuerto() {
        if (numAeropuertos >= aeropuertos.length) {
            System.out.println("Capacidad máxima de aeropuertos alcanzada.");
            return;
        }
        Aeropuerto a = new Aeropuerto();
        a.leer();
        for (int i = 0; i < a.numero_pistas; i++) {
            System.out.println("--- Datos de la pista " + (i + 1) + " ---");
            a.pistas[i].leer();
        }
        aeropuertos[numAeropuertos++] = a;
        System.out.println("Aeropuerto registrado como #" + numAeropuertos);
    }


    private void registrarVuelo() {
        if (numVuelos >= vuelos.length) {
            System.out.println("Capacidad máxima de vuelos alcanzada.");
            return;
        }
        Vuelo v = new Vuelo();
        v.leer();
        vuelos[numVuelos++] = v;
        System.out.println("Vuelo registrado como #" + numVuelos);
    }

    private void registrarAvion() {
        if (numAviones >= aviones.length) {
            System.out.println("Capacidad máxima de aviones alcanzada.");
            return;
        }
        Aviones av = new Aviones();
        av.leer();
        aviones[numAviones++] = av;
        System.out.println("Avión registrado como #" + numAviones);
    }


    private void registrarPiloto() {
        if (numPilotos >= pilotos.length) {
            System.out.println("Capacidad máxima de pilotos alcanzada.");
            return;
        }
        Pilotos p = new Pilotos();
        p.leer();
        pilotos[numPilotos++] = p;
        System.out.println("Piloto registrado como #" + numPilotos);
    }

    private void registrarAzafata() {
        if (numAzafatas >= azafatas.length) {
            System.out.println("Capacidad máxima de azafatas alcanzada.");
            return;
        }
        Azafatas az = new Azafatas();
        az.leer();
        azafatas[numAzafatas++] = az;
        System.out.println("Azafata registrada como #" + numAzafatas);
    }

    private void registrarPasajero() {
        if (numPasajeros >= pasajeros.length) {
            System.out.println("Capacidad máxima de pasajeros alcanzada.");
            return;
        }
        Pasajeros pas = new Pasajeros();
        pas.leer();
        pasajeros[numPasajeros++] = pas;
        System.out.println("Pasajero registrado como #" + numPasajeros);
    }


    private void asignarAvionAVuelo() {
        if (numVuelos == 0 || numAviones == 0) {
            System.out.println("Registre al menos un vuelo y un avión primero.");
            return;
        }
        System.out.print("Número de vuelo (1-" + numVuelos + "): ");
        int iv = sc.nextInt(); sc.nextLine();
        System.out.print("Número de avión (1-" + numAviones + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        if (iv < 1 || iv > numVuelos || ia < 1 || ia > numAviones) {
            System.out.println("Índice inválido.");
            return;
        }
        vuelos[iv - 1].agregarAvion(aviones[ia - 1]);
        System.out.println("Avión " + ia + " asignado al vuelo " + iv + ".");
    }


    private void asignarPilotoAVuelo() {
        if (numVuelos == 0 || numPilotos == 0) {
            System.out.println("Registre al menos un vuelo y un piloto primero.");
            return;
        }
        System.out.print("Número de vuelo (1-" + numVuelos + "): ");
        int iv = sc.nextInt(); sc.nextLine();
        System.out.print("Número de piloto (1-" + numPilotos + "): ");
        int ip = sc.nextInt(); sc.nextLine();
        if (iv < 1 || iv > numVuelos || ip < 1 || ip > numPilotos) {
            System.out.println("Índice inválido.");
            return;
        }
        vuelos[iv - 1].agregarPiloto(pilotos[ip - 1]);
        System.out.println("Piloto " + ip + " asignado al vuelo " + iv + ".");
    }


    private void asignarAzafatasAVuelo() {
        if (numVuelos == 0 || numAzafatas < 2) {
            System.out.println("Registre al menos un vuelo y dos azafatas primero.");
            return;
        }
        System.out.print("Número de vuelo (1-" + numVuelos + "): ");
        int iv  = sc.nextInt(); sc.nextLine();
        System.out.print("Número de azafata 1 (1-" + numAzafatas + "): ");
        int ia1 = sc.nextInt(); sc.nextLine();
        System.out.print("Número de azafata 2 (1-" + numAzafatas + "): ");
        int ia2 = sc.nextInt(); sc.nextLine();
        if (iv < 1 || iv > numVuelos || ia1 < 1 || ia1 > numAzafatas
                || ia2 < 1 || ia2 > numAzafatas) {
            System.out.println("Índice inválido.");
            return;
        }
        vuelos[iv - 1].agregarAzafata(azafatas[ia1 - 1], azafatas[ia2 - 1]);
        System.out.println("Azafatas " + ia1 + " y " + ia2 + " asignadas al vuelo " + iv + ".");
    }

    private void agregarVueloAAeropuerto() {
        if (numAeropuertos == 0 || numVuelos == 0) {
            System.out.println("Registre al menos un aeropuerto y un vuelo primero.");
            return;
        }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        System.out.print("Número de vuelo (1-" + numVuelos + "): ");
        int iv = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos || iv < 1 || iv > numVuelos) {
            System.out.println("Índice inválido.");
            return;
        }
        aeropuertos[ia - 1].agregarVuelo(vuelos[iv - 1]);
        System.out.println("Vuelo " + iv + " agregado al aeropuerto " + ia + ".");
    }


    private void agregarPasajeroAVuelo() {
        if (numAeropuertos == 0 || numPasajeros == 0) {
            System.out.println("Registre al menos un aeropuerto y un pasajero primero.");
            return;
        }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia  = sc.nextInt(); sc.nextLine();
        System.out.print("Número de vuelo dentro del aeropuerto: ");
        int iv  = sc.nextInt(); sc.nextLine();
        System.out.print("Número de pasajero (1-" + numPasajeros + "): ");
        int ip  = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos || ip < 1 || ip > numPasajeros) {
            System.out.println("Índice inválido.");
            return;
        }
        aeropuertos[ia - 1].agregarPasajero(pasajeros[ip - 1], iv);
    }

    private void mostrarAeropuerto() {
        if (numAeropuertos == 0) {
            System.out.println("No hay aeropuertos registrados.");
            return;
        }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos) {
            System.out.println("Índice inválido.");
            return;
        }
        aeropuertos[ia - 1].mostrar();
    }

    private void mostrarVuelo() {
        if (numAeropuertos == 0) {
            System.out.println("No hay aeropuertos registrados.");
            return;
        }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        System.out.print("Número de vuelo en ese aeropuerto: ");
        int iv = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos) {
            System.out.println("Índice de aeropuerto inválido.");
            return;
        }
        aeropuertos[ia - 1].mostrar(iv);
    }


    private void calcularDuracion() {
        if (numAeropuertos == 0) { System.out.println("No hay aeropuertos."); return; }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        System.out.print("Número de vuelo: ");
        int iv = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos) { System.out.println("Inválido."); return; }
        aeropuertos[ia - 1].duracionVuelo(iv);
    }

    private void calcularRetraso() {
        if (numAeropuertos == 0) { System.out.println("No hay aeropuertos."); return; }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        System.out.print("Número de vuelo: ");
        int iv = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos) { System.out.println("Inválido."); return; }
        aeropuertos[ia - 1].retrasoVuelo(iv);
    }


    private void eliminarVuelo() {
        if (numAeropuertos == 0) { System.out.println("No hay aeropuertos."); return; }
        System.out.print("Número de aeropuerto (1-" + numAeropuertos + "): ");
        int ia = sc.nextInt(); sc.nextLine();
        System.out.print("Número de vuelo a eliminar: ");
        int iv = sc.nextInt(); sc.nextLine();
        if (ia < 1 || ia > numAeropuertos) { System.out.println("Inválido."); return; }
        aeropuertos[ia - 1].eliminarVuelo(iv);
        System.out.println("Vuelo " + iv + " eliminado del aeropuerto " + ia + ".");
    }
}
