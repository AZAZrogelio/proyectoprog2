package clases;

import java.util.Scanner;

public class Vuelo {
	private String numero_vuelo;
    private String origen;
    private String destino;
    private float hora_salida;
    private float hora_llegada;
    private float costo;
    private String fecha_salida;
    private String fecha_llegada;
    private Pilotos piloto;
    private Azafatas azafata1, azafata2;
    private Aeropuerto aeropuerto_origen;
    private Aeropuerto aeropuerto_destino;
    private Aviones avion;
    float duracion;

    public String getNumero_vuelo() {
		return numero_vuelo;
	}

	public void setNumero_vuelo(String numero_vuelo) {
		this.numero_vuelo = numero_vuelo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public float getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(float hora_salida) {
		this.hora_salida = hora_salida;
	}

	public float getHora_llegada() {
		return hora_llegada;
	}

	public void setHora_llegada(float hora_llegada) {
		this.hora_llegada = hora_llegada;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(String fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public String getFecha_llegada() {
		return fecha_llegada;
	}

	public void setFecha_llegada(String fecha_llegada) {
		this.fecha_llegada = fecha_llegada;
	}

	public Pilotos getPiloto() {
		return piloto;
	}

	public void setPiloto(Pilotos piloto) {
		this.piloto = piloto;
	}

	public Azafatas getAzafata1() {
		return azafata1;
	}

	public void setAzafata1(Azafatas azafata1) {
		this.azafata1 = azafata1;
	}

	public Azafatas getAzafata2() {
		return azafata2;
	}

	public void setAzafata2(Azafatas azafata2) {
		this.azafata2 = azafata2;
	}

	public Aeropuerto getAeropuerto_origen() {
		return aeropuerto_origen;
	}

	public void setAeropuerto_origen(Aeropuerto aeropuerto_origen) {
		this.aeropuerto_origen = aeropuerto_origen;
	}

	public Aeropuerto getAeropuerto_destino() {
		return aeropuerto_destino;
	}

	public void setAeropuerto_destino(Aeropuerto aeropuerto_destino) {
		this.aeropuerto_destino = aeropuerto_destino;
	}

	public Aviones getAvion() {
		return avion;
	}

	public void setAvion(Aviones avion) {
		this.avion = avion;
	}

	public float getDuracion() {
		return duracion;
	}

	public void setDuracion(float duracion) {
		this.duracion = duracion;
	}

	public void leer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el número de vuelo:");
        numero_vuelo = sc.nextLine();
        System.out.println("Ingrese el origen del vuelo:");
        origen = sc.nextLine();
        System.out.println("Ingrese el destino del vuelo:");
        destino = sc.nextLine();
        System.out.println("Hora de salida del vuelo:");
        hora_salida = sc.nextFloat();
        System.out.println("Hora de llegada del vuelo:");
        hora_llegada = sc.nextFloat();
        System.out.println("Ingrese el costo del vuelo:");
        costo = sc.nextFloat();
        sc.nextLine(); // Limpieza tras nextFloat()
        System.out.println("Ingrese la fecha de salida del vuelo:");
        fecha_salida = sc.nextLine();
        System.out.println("Ingrese la fecha de llegada del vuelo:");
        fecha_llegada = sc.nextLine();
        duracion = 0;
    }

    public void agregarPiloto(Pilotos piloto) {
        this.piloto = piloto;
    }

    public void agregarAzafata(Azafatas azafata1, Azafatas azafata2) {
        this.azafata1 = azafata1;
        this.azafata2 = azafata2;
    }

    public void agregarAeropuertoOrigen(Aeropuerto aeropuerto_origen) {
        this.aeropuerto_origen = aeropuerto_origen;
    }

    public void agregarAeropuertoDestino(Aeropuerto aeropuerto_destino) {
        this.aeropuerto_destino = aeropuerto_destino;
    }

    public void agregarAvion(Aviones avion) {
        this.avion = avion;
    }

    public void duracionVuelo() {
        try {
            this.duracion = Math.abs(hora_llegada - hora_salida);
            System.out.println("Duración del vuelo: " + duracion + " horas.");
        } catch (Exception e) {
            System.out.println("Error al calcular la duración del vuelo: " + e.getMessage());
        }
    }

    public void calcularatraso() {
        try {
            float retraso = hora_llegada - hora_salida;
            if (retraso > 0) {
                System.out.println("El vuelo tiene un retraso de " + retraso + " horas.");
            } else {
                System.out.println("El vuelo no tiene retraso.");
            }
        } catch (Exception e) {
            System.out.println("Error al calcular el retraso del vuelo: " + e.getMessage());
        }
    }

    public void agregarpasajero(Pasajeros pasajero) {
        if (avion != null) {
            avion.agregarPasajero(pasajero);
        } else {
            System.out.println("No se puede agregar pasajero, no hay avión asignado al vuelo.");
        }
    }

    public void mostrar() {
        System.out.println("Número de vuelo: " + numero_vuelo);
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Hora de salida: " + hora_salida);
        System.out.println("Hora de llegada: " + hora_llegada);
        System.out.println("Costo: " + costo);
        System.out.println("Fecha de salida: " + fecha_salida);
        System.out.println("Fecha de llegada: " + fecha_llegada);
        System.out.println("Duración: " + duracion + " horas.");
        if (piloto != null) {
            System.out.println("Piloto:");
            piloto.mostrar();
        }
        if (azafata1 != null) {
            System.out.println("Azafata 1:");
            azafata1.mostrar();
        }
        if (azafata2 != null) {
            System.out.println("Azafata 2:");
            azafata2.mostrar();
        }
        if (aeropuerto_origen != null) {
            System.out.println("Aeropuerto de origen:");
            aeropuerto_origen.mostrar();
        }
        if (aeropuerto_destino != null) {
            System.out.println("Aeropuerto de destino:");
            aeropuerto_destino.mostrar();
        }
        if (avion != null) {
            System.out.println("Avión:");
            avion.mostrar();
        }
    }
}