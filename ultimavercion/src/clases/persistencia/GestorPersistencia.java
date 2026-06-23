package clases.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import clases.Aeropuerto;
import clases.Aviones;
import clases.Azafatas;
import clases.Pasajeros;
import clases.persona;
import clases.Pilotos;
import clases.Pista;
import clases.Vuelo;

/**
 * Gestor de persistencia que coordina el guardado y cargado de todos los datos
 * del sistema usando RandomAccessFile con registros de tamano fijo.
 * 
 * No modifica las clases del dominio. Solo se comunica con TorreDeControl
 * a traves de sus metodos getters/setters publicos.
 */
public class GestorPersistencia {

    // ============================================================
    // TAMANOS DE REGISTRO (bytes) - registros de tamano fijo
    // ============================================================
    private static final int TAM_PASAJERO   = 1024;
    private static final int TAM_PILOTO     = 1024;
    private static final int TAM_AZAFATA    = 1024;
    private static final int TAM_AVION      = 8192;
    private static final int TAM_VUELO      = 512;
    private static final int TAM_AEROPUERTO = 8192;

    // ============================================================
    // TAMANOS MAXIMOS DE STRING (en caracteres)
    // ============================================================
    private static final int MAX_NOMBRE        = 40;
    private static final int MAX_APELLIDO      = 40;
    private static final int MAX_NACIONALIDAD  = 25;
    private static final int MAX_GENERO        = 15;
    private static final int MAX_ESTADO_CIVIL  = 15;
    private static final int MAX_OCUPACION     = 30;
    private static final int MAX_DIRECCION     = 60;
    private static final int MAX_TELEFONO      = 15;
    private static final int MAX_PASAPORTE     = 25;
    private static final int MAX_CLASE         = 15;
    private static final int MAX_ASIENTO       = 10;
    private static final int MAX_EXPERIENCIA   = 30;
    private static final int MAX_UNIVERSIDAD   = 30;
    private static final int MAX_LICENCIA      = 20;
    private static final int MAX_TIPO_LICENCIA = 20;
    private static final int MAX_IDIOMA        = 20;
    private static final int MAX_UNIFORME      = 20;
    private static final int MAX_MODELO        = 30;
    private static final int MAX_MARCA         = 30;
    private static final int MAX_NUM_VUELO     = 20;
    private static final int MAX_ORIGEN        = 40;
    private static final int MAX_DESTINO       = 40;
    private static final int MAX_FECHA         = 20;
    private static final int MAX_AERO_NOMBRE   = 50;
    private static final int MAX_CIUDAD_PAIS   = 50;
    private static final int MAX_SUPERFICIE    = 50;

    // ============================================================
    // CAPACIDADES MAXIMAS (deben coincidir con TorreDeControl)
    // ============================================================
    private static final int MAX_PASAJEROS   = 200;
    private static final int MAX_PILOTOS     = 20;
    private static final int MAX_AZAFATAS    = 30;
    private static final int MAX_AVIONES     = 20;
    private static final int MAX_VUELOS      = 50;
    private static final int MAX_AEROPUERTOS = 10;
    private static final int MAX_PISTAS      = 10;
    private static final int MAX_PASAJEROS_POR_AVION = 200;

    // ============================================================
    // RUTAS DE ARCHIVOS
    // ============================================================
    private final String dirDatos;
    private final String archivoPasajeros;
    private final String archivoPilotos;
    private final String archivoAzafatas;
    private final String archivoAviones;
    private final String archivoVuelos;
    private final String archivoAeropuertos;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================
    public GestorPersistencia(String directorioDatos) {
        this.dirDatos = directorioDatos;
        new File(dirDatos).mkdirs();
        this.archivoPasajeros   = dirDatos + "/pasajeros_pers.dat";
        this.archivoPilotos     = dirDatos + "/pilotos_pers.dat";
        this.archivoAzafatas    = dirDatos + "/azafatas_pers.dat";
        this.archivoAviones     = dirDatos + "/aviones_pers.dat";
        this.archivoVuelos      = dirDatos + "/vuelos_pers.dat";
        this.archivoAeropuertos = dirDatos + "/aeropuertos_pers.dat";
    }

    // ============================================================
    // METODO PRINCIPAL: CARGAR
    // ============================================================

    /**
     * Carga todos los datos desde los archivos .dat.
     * Devuelve true si tuvo exito. Los arrays y contadores se actualizan.
     */
    public boolean cargar(Pasajeros[] pasajerosArr, Pilotos[] pilotosArr, Azafatas[] azafatasArr,
                          Aviones[] avionesArr, Vuelo[] vuelosArr, Aeropuerto[] aeropuertosArr,
                          int[] contadores) {
        try {
            System.out.println("[Persistencia] Cargando datos...");

            // 1. Cargar entidades base (sin relaciones)
            int nPasajeros   = cargarPasajeros(pasajerosArr);
            int nPilotos     = cargarPilotos(pilotosArr);
            int nAzafatas    = cargarAzafatas(azafatasArr);
            int nAviones     = cargarAviones(avionesArr, pasajerosArr);
            int nVuelos      = cargarVuelosBase(vuelosArr);
            int nAeropuertos = cargarAeropuertosBase(aeropuertosArr, vuelosArr);

            // 2. Reconstruir relaciones entre entidades (vuelos)
            reconstruirRelacionesVuelos(vuelosArr, pilotosArr, azafatasArr, avionesArr, aeropuertosArr);

            // 3. Devolver contadores
            contadores[0] = nPasajeros;
            contadores[1] = nPilotos;
            contadores[2] = nAzafatas;
            contadores[3] = nAviones;
            contadores[4] = nVuelos;
            contadores[5] = nAeropuertos;

            System.out.println("[Persistencia] Datos cargados exitosamente.");
            System.out.println("  Pasajeros:   " + nPasajeros);
            System.out.println("  Pilotos:     " + nPilotos);
            System.out.println("  Azafatas:    " + nAzafatas);
            System.out.println("  Aviones:     " + nAviones);
            System.out.println("  Vuelos:      " + nVuelos);
            System.out.println("  Aeropuertos: " + nAeropuertos);
            return true;

        } catch (IOException e) {
            System.out.println("[Persistencia] Error al cargar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // METODO PRINCIPAL: GUARDAR
    // ============================================================

    /**
     * Guarda todos los datos a los archivos .dat.
     */
    public boolean guardar(Pasajeros[] pasajerosArr, Pilotos[] pilotosArr, Azafatas[] azafatasArr,
                           Aviones[] avionesArr, Vuelo[] vuelosArr, Aeropuerto[] aeropuertosArr,
                           int nPasajeros, int nPilotos, int nAzafatas, int nAviones,
                           int nVuelos, int nAeropuertos) {
        try {
            System.out.println("[Persistencia] Guardando datos...");

            guardarPasajeros(pasajerosArr, nPasajeros);
            guardarPilotos(pilotosArr, nPilotos);
            guardarAzafatas(azafatasArr, nAzafatas);
            guardarAviones(avionesArr, nAviones, pasajerosArr);
            guardarVuelos(vuelosArr, nVuelos, pilotosArr, azafatasArr, avionesArr, aeropuertosArr);
            guardarAeropuertos(aeropuertosArr, nAeropuertos, vuelosArr);

            System.out.println("[Persistencia] Datos guardados exitosamente.");
            return true;

        } catch (IOException e) {
            System.out.println("[Persistencia] Error al guardar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // PASAJEROS
    // ============================================================

    private void guardarPasajeros(Pasajeros[] arr, int n) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivoPasajeros, "rw")) {
            raf.setLength(0);
            raf.writeInt(n);
            for (int i = 0; i < n; i++) {
                Pasajeros p = arr[i];
                long pos = 4L + (long) i * TAM_PASAJERO;
                raf.seek(pos);
                raf.writeBoolean(true);
                raf.writeInt(p.getCi());
                raf.writeInt(p.getEdad());
                escribirString(raf, p.getNombre(), MAX_NOMBRE);
                escribirString(raf, p.getApellido(), MAX_APELLIDO);
                escribirString(raf, p.getNacionalidad(), MAX_NACIONALIDAD);
                escribirString(raf, p.getGenero(), MAX_GENERO);
                escribirString(raf, p.getEstado_civil(), MAX_ESTADO_CIVIL);
                escribirString(raf, p.getOcupacion(), MAX_OCUPACION);
                escribirString(raf, p.getDireccion(), MAX_DIRECCION);
                escribirString(raf, p.getTelefono(), MAX_TELEFONO);
                escribirString(raf, p.getNumero_pasaporte(), MAX_PASAPORTE);
                escribirString(raf, p.getClase(), MAX_CLASE);
                escribirString(raf, p.getAsiento(), MAX_ASIENTO);
            }
        }
    }

    private int cargarPasajeros(Pasajeros[] arr) throws IOException {
        File f = new File(archivoPasajeros);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(archivoPasajeros, "r")) {
            int n = raf.readInt();
            for (int i = 0; i < n; i++) {
                long pos = 4L + (long) i * TAM_PASAJERO;
                raf.seek(pos);
                if (!raf.readBoolean()) continue;
                Pasajeros p = new Pasajeros();
                p.setCi(raf.readInt());
                p.setEdad(raf.readInt());
                p.setNombre(leerString(raf, MAX_NOMBRE));
                p.setApellido(leerString(raf, MAX_APELLIDO));
                p.setNacionalidad(leerString(raf, MAX_NACIONALIDAD));
                p.setGenero(leerString(raf, MAX_GENERO));
                p.setEstado_civil(leerString(raf, MAX_ESTADO_CIVIL));
                p.setOcupacion(leerString(raf, MAX_OCUPACION));
                p.setDireccion(leerString(raf, MAX_DIRECCION));
                p.setTelefono(leerString(raf, MAX_TELEFONO));
                p.setNumero_pasaporte(leerString(raf, MAX_PASAPORTE));
                p.setClase(leerString(raf, MAX_CLASE));
                p.setAsiento(leerString(raf, MAX_ASIENTO));
                arr[i] = p;
            }
            return n;
        }
    }

    // ============================================================
    // PILOTOS
    // ============================================================

    private void guardarPilotos(Pilotos[] arr, int n) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivoPilotos, "rw")) {
            raf.setLength(0);
            raf.writeInt(n);
            for (int i = 0; i < n; i++) {
                Pilotos p = arr[i];
                long pos = 4L + (long) i * TAM_PILOTO;
                raf.seek(pos);
                raf.writeBoolean(true);
                escribirPersona(raf, p);
                escribirString(raf, p.getExperiencia(), MAX_EXPERIENCIA);
                escribirString(raf, p.getUniversidad(), MAX_UNIVERSIDAD);
                escribirString(raf, p.getLicencia(), MAX_LICENCIA);
                escribirString(raf, p.getTipo_licencia(), MAX_TIPO_LICENCIA);
                raf.writeInt(p.getNumeroPiloto());
            }
        }
    }

    private int cargarPilotos(Pilotos[] arr) throws IOException {
        File f = new File(archivoPilotos);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(archivoPilotos, "r")) {
            int n = raf.readInt();
            for (int i = 0; i < n; i++) {
                long pos = 4L + (long) i * TAM_PILOTO;
                raf.seek(pos);
                if (!raf.readBoolean()) continue;
                Pilotos p = new Pilotos();
                leerPersona(raf, p);
                p.setExperiencia(leerString(raf, MAX_EXPERIENCIA));
                p.setUniversidad(leerString(raf, MAX_UNIVERSIDAD));
                p.setLicencia(leerString(raf, MAX_LICENCIA));
                p.setTipo_licencia(leerString(raf, MAX_TIPO_LICENCIA));
                p.setNumeroPiloto(raf.readInt());
                arr[i] = p;
            }
            return n;
        }
    }

    // ============================================================
    // AZAFATAS
    // ============================================================

    private void guardarAzafatas(Azafatas[] arr, int n) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivoAzafatas, "rw")) {
            raf.setLength(0);
            raf.writeInt(n);
            for (int i = 0; i < n; i++) {
                Azafatas a = arr[i];
                long pos = 4L + (long) i * TAM_AZAFATA;
                raf.seek(pos);
                raf.writeBoolean(true);
                escribirPersona(raf, a);
                escribirString(raf, a.getExperiencia(), MAX_EXPERIENCIA);
                escribirString(raf, a.getUniversidad(), MAX_UNIVERSIDAD);
                escribirString(raf, a.getIdioma(), MAX_IDIOMA);
                escribirString(raf, a.getUniforme(), MAX_UNIFORME);
                raf.writeInt(a.getNumero_azafata());
            }
        }
    }

    private int cargarAzafatas(Azafatas[] arr) throws IOException {
        File f = new File(archivoAzafatas);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(archivoAzafatas, "r")) {
            int n = raf.readInt();
            for (int i = 0; i < n; i++) {
                long pos = 4L + (long) i * TAM_AZAFATA;
                raf.seek(pos);
                if (!raf.readBoolean()) continue;
                Azafatas a = new Azafatas();
                leerPersona(raf, a);
                a.setExperiencia(leerString(raf, MAX_EXPERIENCIA));
                a.setUniversidad(leerString(raf, MAX_UNIVERSIDAD));
                a.setIdioma(leerString(raf, MAX_IDIOMA));
                a.setUniforme(leerString(raf, MAX_UNIFORME));
                a.setNumero_azafata(raf.readInt());
                arr[i] = a;
            }
            return n;
        }
    }

    // ============================================================
    // AVIONES (incluye indices de pasajeros asignados)
    // ============================================================

    private void guardarAviones(Aviones[] arr, int n, Pasajeros[] pasajerosArr) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivoAviones, "rw")) {
            raf.setLength(0);
            raf.writeInt(n);
            for (int i = 0; i < n; i++) {
                Aviones av = arr[i];
                long pos = 4L + (long) i * TAM_AVION;
                raf.seek(pos);
                raf.writeBoolean(true);
                escribirString(raf, av.getModelo(), MAX_MODELO);
                escribirString(raf, "", MAX_MARCA); // placeholder para futuro campo marca
                raf.writeInt(av.getCapacidad());
                raf.writeInt(av.getNumero_avion());
                raf.writeInt(av.getNumero_pasajeros());
                // Guardar indices de los pasajeros asignados a este avion
                Pasajeros[] pax = av.getPasajeros();
                int np = av.getNumero_pasajeros();
                for (int j = 0; j < MAX_PASAJEROS_POR_AVION; j++) {
                    if (j < np && pax != null && pax[j] != null) {
                        int idx = buscarIndicePasajero(pasajerosArr, pax[j]);
                        raf.writeInt(idx);
                    } else {
                        raf.writeInt(-1);
                    }
                }
            }
        }
    }

    private int cargarAviones(Aviones[] arr, Pasajeros[] pasajerosArr) throws IOException {
        File f = new File(archivoAviones);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(archivoAviones, "r")) {
            int n = raf.readInt();
            int[][] pasajeroIds = new int[n][MAX_PASAJEROS_POR_AVION];
            for (int i = 0; i < n; i++) {
                long pos = 4L + (long) i * TAM_AVION;
                raf.seek(pos);
                if (!raf.readBoolean()) continue;
                Aviones av = new Aviones();
                av.setModelo(leerString(raf, MAX_MODELO));
                leerString(raf, MAX_MARCA); // placeholder
                av.setCapacidad(raf.readInt());
                av.setNumero_avion(raf.readInt());
                int npGuardado = raf.readInt();
                // Leer indices de pasajeros
                for (int j = 0; j < MAX_PASAJEROS_POR_AVION; j++) {
                    pasajeroIds[i][j] = raf.readInt();
                }
                arr[i] = av;
            }
            // Segunda pasada: reconstruir referencias a pasajeros
            for (int i = 0; i < n; i++) {
                Aviones av = arr[i];
                if (av == null) continue;
                Pasajeros[] paxArr = new Pasajeros[av.getCapacidad()];
                int actual = 0;
                for (int j = 0; j < MAX_PASAJEROS_POR_AVION && actual < av.getCapacidad(); j++) {
                    int idx = pasajeroIds[i][j];
                    if (idx >= 0 && idx < MAX_PASAJEROS && pasajerosArr[idx] != null) {
                        paxArr[actual++] = pasajerosArr[idx];
                    }
                }
                av.setNumero_pasajeros(actual);
                av.setPasajeros(paxArr);
            }
            return n;
        }
    }

    // ============================================================
    // VUELOS (base + relaciones)
    // ============================================================

    private void guardarVuelos(Vuelo[] arr, int n, Pilotos[] pilotosArr, Azafatas[] azafatasArr,
                                Aviones[] avionesArr, Aeropuerto[] aeropuertosArr) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivoVuelos, "rw")) {
            raf.setLength(0);
            raf.writeInt(n);
            for (int i = 0; i < n; i++) {
                Vuelo v = arr[i];
                long pos = 4L + (long) i * TAM_VUELO;
                raf.seek(pos);
                raf.writeBoolean(true);
                escribirString(raf, v.getNumero_vuelo(), MAX_NUM_VUELO);
                escribirString(raf, v.getOrigen(), MAX_ORIGEN);
                escribirString(raf, v.getDestino(), MAX_DESTINO);
                raf.writeFloat(v.getHora_salida());
                raf.writeFloat(v.getHora_llegada());
                raf.writeFloat(v.getCosto());
                escribirString(raf, v.getFecha_salida(), MAX_FECHA);
                escribirString(raf, v.getFecha_llegada(), MAX_FECHA);
                raf.writeFloat(v.getDuracion());
                // Indices de relaciones (-1 si null)
                raf.writeInt(buscarIndicePiloto(pilotosArr, v.getPiloto()));
                raf.writeInt(buscarIndiceAzafata(azafatasArr, v.getAzafata1()));
                raf.writeInt(buscarIndiceAzafata(azafatasArr, v.getAzafata2()));
                raf.writeInt(buscarIndiceAvion(avionesArr, v.getAvion()));
                raf.writeInt(buscarIndiceAeropuerto(aeropuertosArr, v.getAeropuerto_origen()));
                raf.writeInt(buscarIndiceAeropuerto(aeropuertosArr, v.getAeropuerto_destino()));
            }
        }
    }

    private int cargarVuelosBase(Vuelo[] arr) throws IOException {
        File f = new File(archivoVuelos);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(archivoVuelos, "r")) {
            int n = raf.readInt();
            for (int i = 0; i < n; i++) {
                long pos = 4L + (long) i * TAM_VUELO;
                raf.seek(pos);
                if (!raf.readBoolean()) continue;
                Vuelo v = new Vuelo();
                v.setNumero_vuelo(leerString(raf, MAX_NUM_VUELO));
                v.setOrigen(leerString(raf, MAX_ORIGEN));
                v.setDestino(leerString(raf, MAX_DESTINO));
                v.setHora_salida(raf.readFloat());
                v.setHora_llegada(raf.readFloat());
                v.setCosto(raf.readFloat());
                v.setFecha_salida(leerString(raf, MAX_FECHA));
                v.setFecha_llegada(leerString(raf, MAX_FECHA));
                v.setDuracion(raf.readFloat());
                // Saltar indices de relaciones (se usan en reconstruirRelacionesVuelos)
                raf.readInt(); raf.readInt(); raf.readInt(); raf.readInt(); raf.readInt(); raf.readInt();
                arr[i] = v;
            }
            return n;
        }
    }

    // ============================================================
    // AEROPUERTOS (base con pistas y vuelos)
    // ============================================================

    private void guardarAeropuertos(Aeropuerto[] arr, int n, Vuelo[] vuelosArr) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivoAeropuertos, "rw")) {
            raf.setLength(0);
            raf.writeInt(n);
            for (int i = 0; i < n; i++) {
                Aeropuerto a = arr[i];
                long pos = 4L + (long) i * TAM_AEROPUERTO;
                raf.seek(pos);
                raf.writeBoolean(true);
                escribirString(raf, a.getNombre(), MAX_AERO_NOMBRE);
                escribirString(raf, a.getCiudad_pais(), MAX_CIUDAD_PAIS);
                raf.writeInt(a.getNumero_pistas());
                raf.writeInt(a.getNumero_vuelos());
                // Guardar pistas
                Pista[] pistas = a.getPistas();
                for (int j = 0; j < MAX_PISTAS; j++) {
                    if (j < a.getNumero_pistas() && pistas != null && pistas[j] != null) {
                        raf.writeBoolean(true);
                        raf.writeInt(pistas[j].getNumero_pista());
                        raf.writeFloat(pistas[j].getLongitud());
                        escribirString(raf, pistas[j].getTipo_superficie(), MAX_SUPERFICIE);
                    } else {
                        raf.writeBoolean(false);
                        raf.writeInt(0);
                        raf.writeFloat(0f);
                        escribirString(raf, "", MAX_SUPERFICIE);
                    }
                }
                // Guardar indices de vuelos
                Vuelo[] vuelosAero = a.getVuelos();
                int nv = a.getNumero_vuelos();
                for (int j = 0; j < MAX_VUELOS; j++) {
                    if (j < nv && vuelosAero != null && vuelosAero[j] != null) {
                        int idx = buscarIndiceVuelo(vuelosArr, vuelosAero[j]);
                        raf.writeInt(idx);
                    } else {
                        raf.writeInt(-1);
                    }
                }
            }
        }
    }

    private int cargarAeropuertosBase(Aeropuerto[] arr, Vuelo[] vuelosArr) throws IOException {
        File f = new File(archivoAeropuertos);
        if (!f.exists()) return 0;
        try (RandomAccessFile raf = new RandomAccessFile(archivoAeropuertos, "r")) {
            int n = raf.readInt();
            int[][] vueloIds = new int[n][MAX_VUELOS];
            for (int i = 0; i < n; i++) {
                long pos = 4L + (long) i * TAM_AEROPUERTO;
                raf.seek(pos);
                if (!raf.readBoolean()) continue;
                Aeropuerto a = new Aeropuerto();
                a.setNombre(leerString(raf, MAX_AERO_NOMBRE));
                a.setCiudad_pais(leerString(raf, MAX_CIUDAD_PAIS));
                int numPistas = raf.readInt();
                int numVuelosAero = raf.readInt();
                a.setNumero_pistas(numPistas);
                a.setNumero_vuelos(numVuelosAero);
                // Cargar pistas
                Pista[] pistas = new Pista[MAX_PISTAS];
                for (int j = 0; j < MAX_PISTAS; j++) {
                    boolean activa = raf.readBoolean();
                    int numPista = raf.readInt();
                    float longitud = raf.readFloat();
                    String superficie = leerString(raf, MAX_SUPERFICIE);
                    if (activa && j < numPistas) {
                        pistas[j] = new Pista(numPista);
                        pistas[j].setLongitud(longitud);
                        pistas[j].setTipo_superficie(superficie);
                    }
                }
                a.setPistas(pistas);
                a.setVuelos(new Vuelo[100]);
                // Leer indices de vuelos
                for (int j = 0; j < MAX_VUELOS; j++) {
                    vueloIds[i][j] = raf.readInt();
                }
                arr[i] = a;
            }
            // Segunda pasada: asignar vuelos a aeropuertos
            for (int i = 0; i < n; i++) {
                Aeropuerto a = arr[i];
                if (a == null) continue;
                int nv = a.getNumero_vuelos();
                int actual = 0;
                for (int j = 0; j < MAX_VUELOS && actual < nv; j++) {
                    int idx = vueloIds[i][j];
                    if (idx >= 0 && idx < MAX_VUELOS && vuelosArr[idx] != null) {
                        a.agregarVuelo(vuelosArr[idx]);
                    }
                }
            }
            return n;
        }
    }

    // ============================================================
    // RECONSTRUCCION DE RELACIONES DE VUELOS
    // ============================================================

    private void reconstruirRelacionesVuelos(Vuelo[] vuelosArr, Pilotos[] pilotosArr,
                                              Azafatas[] azafatasArr, Aviones[] avionesArr,
                                              Aeropuerto[] aeropuertosArr) throws IOException {
        File f = new File(archivoVuelos);
        if (!f.exists()) return;
        try (RandomAccessFile raf = new RandomAccessFile(archivoVuelos, "r")) {
            int n = raf.readInt();
            for (int i = 0; i < n; i++) {
                Vuelo v = vuelosArr[i];
                if (v == null) continue;
                // Saltar a la posicion de los indices de relacion
                long pos = 4L + (long) i * TAM_VUELO;
                raf.seek(pos);
                raf.readBoolean(); // activo
                leerString(raf, MAX_NUM_VUELO);
                leerString(raf, MAX_ORIGEN);
                leerString(raf, MAX_DESTINO);
                raf.readFloat(); raf.readFloat(); raf.readFloat();
                leerString(raf, MAX_FECHA); leerString(raf, MAX_FECHA);
                raf.readFloat();
                // Leer los indices de relacion
                int pilotoIdx     = raf.readInt();
                int azafata1Idx   = raf.readInt();
                int azafata2Idx   = raf.readInt();
                int avionIdx      = raf.readInt();
                int aeroOrigenIdx = raf.readInt();
                int aeroDestinoIdx = raf.readInt();
                // Reconstruir objetos relacionados
                if (pilotoIdx >= 0 && pilotoIdx < MAX_PILOTOS && pilotosArr[pilotoIdx] != null) {
                    v.agregarPiloto(pilotosArr[pilotoIdx]);
                }
                Azafatas az1 = (azafata1Idx >= 0 && azafata1Idx < MAX_AZAFATAS) ? azafatasArr[azafata1Idx] : null;
                Azafatas az2 = (azafata2Idx >= 0 && azafata2Idx < MAX_AZAFATAS) ? azafatasArr[azafata2Idx] : null;
                if (az1 != null) {
                    v.agregarAzafata(az1, az2);
                }
                if (avionIdx >= 0 && avionIdx < MAX_AVIONES && avionesArr[avionIdx] != null) {
                    v.agregarAvion(avionesArr[avionIdx]);
                }
                if (aeroOrigenIdx >= 0 && aeroOrigenIdx < MAX_AEROPUERTOS && aeropuertosArr[aeroOrigenIdx] != null) {
                    v.agregarAeropuertoOrigen(aeropuertosArr[aeroOrigenIdx]);
                }
                if (aeroDestinoIdx >= 0 && aeroDestinoIdx < MAX_AEROPUERTOS && aeropuertosArr[aeroDestinoIdx] != null) {
                    v.agregarAeropuertoDestino(aeropuertosArr[aeroDestinoIdx]);
                }
            }
        }
    }

    // ============================================================
    // METODOS AUXILIARES: buscar indices por referencia de objeto
    // ============================================================

    private int buscarIndicePasajero(Pasajeros[] arr, Pasajeros obj) {
        if (obj == null) return -1;
        for (int i = 0; i < MAX_PASAJEROS; i++) if (arr[i] == obj) return i;
        return -1;
    }

    private int buscarIndicePiloto(Pilotos[] arr, Pilotos obj) {
        if (obj == null) return -1;
        for (int i = 0; i < MAX_PILOTOS; i++) if (arr[i] == obj) return i;
        return -1;
    }

    private int buscarIndiceAzafata(Azafatas[] arr, Azafatas obj) {
        if (obj == null) return -1;
        for (int i = 0; i < MAX_AZAFATAS; i++) if (arr[i] == obj) return i;
        return -1;
    }

    private int buscarIndiceAvion(Aviones[] arr, Aviones obj) {
        if (obj == null) return -1;
        for (int i = 0; i < MAX_AVIONES; i++) if (arr[i] == obj) return i;
        return -1;
    }

    private int buscarIndiceVuelo(Vuelo[] arr, Vuelo obj) {
        if (obj == null) return -1;
        for (int i = 0; i < MAX_VUELOS; i++) if (arr[i] == obj) return i;
        return -1;
    }

    private int buscarIndiceAeropuerto(Aeropuerto[] arr, Aeropuerto obj) {
        if (obj == null) return -1;
        for (int i = 0; i < MAX_AEROPUERTOS; i++) if (arr[i] == obj) return i;
        return -1;
    }

    // ============================================================
    // METODOS AUXILIARES: escribir/leer persona
    // ============================================================

    private void escribirPersona(RandomAccessFile raf, persona p) throws IOException {
        raf.writeInt(p.getCi());
        raf.writeInt(p.getEdad());
        escribirString(raf, p.getNombre(), MAX_NOMBRE);
        escribirString(raf, p.getApellido(), MAX_APELLIDO);
        escribirString(raf, p.getNacionalidad(), MAX_NACIONALIDAD);
        escribirString(raf, p.getGenero(), MAX_GENERO);
        escribirString(raf, p.getEstado_civil(), MAX_ESTADO_CIVIL);
        escribirString(raf, p.getOcupacion(), MAX_OCUPACION);
        escribirString(raf, p.getDireccion(), MAX_DIRECCION);
        escribirString(raf, p.getTelefono(), MAX_TELEFONO);
    }

    private void leerPersona(RandomAccessFile raf, persona p) throws IOException {
        p.setCi(raf.readInt());
        p.setEdad(raf.readInt());
        p.setNombre(leerString(raf, MAX_NOMBRE));
        p.setApellido(leerString(raf, MAX_APELLIDO));
        p.setNacionalidad(leerString(raf, MAX_NACIONALIDAD));
        p.setGenero(leerString(raf, MAX_GENERO));
        p.setEstado_civil(leerString(raf, MAX_ESTADO_CIVIL));
        p.setOcupacion(leerString(raf, MAX_OCUPACION));
        p.setDireccion(leerString(raf, MAX_DIRECCION));
        p.setTelefono(leerString(raf, MAX_TELEFONO));
    }

    // ============================================================
    // METODOS AUXILIARES: strings de tamano fijo
    // ============================================================

    private void escribirString(RandomAccessFile raf, String texto, int maxChars) throws IOException {
        if (texto == null) texto = "";
        StringBuilder sb = new StringBuilder(texto);
        sb.setLength(maxChars);
        for (int i = 0; i < maxChars; i++) {
            raf.writeChar(sb.charAt(i));
        }
    }

    private String leerString(RandomAccessFile raf, int maxChars) throws IOException {
        char[] chars = new char[maxChars];
        for (int i = 0; i < maxChars; i++) {
            chars[i] = raf.readChar();
        }
        String resultado = new String(chars);
        int idx = resultado.indexOf('\0');
        return (idx >= 0) ? resultado.substring(0, idx).trim() : resultado.trim();
    }
}
