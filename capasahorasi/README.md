# Sistema de Torre de Control - Aeropuerto

Sistema de gestion aeroportuaria desarrollado en Java con dos modos de uso: **consola** y **interfaz grafica**.

## Estructura del Proyecto

```
proyecto_prograll/
├── src/
│   ├── clases/          # Logica del negocio (modelo original)
│   │   ├── Aeropuerto.java
│   │   ├── ArchivoPasajero.java
│   │   ├── ArchivoTripu.java
│   │   ├── ArchivoVuelos.java
│   │   ├── Aviones.java
│   │   ├── Azafatas.java
│   │   ├── main.java              <-- Entrada por consola
│   │   ├── Pasajeros.java
│   │   ├── persona.java
│   │   ├── Pilotos.java
│   │   ├── Pista.java
│   │   ├── TorreDeControl.java    <-- Controlador principal
│   │   ├── Tripulacion.java
│   │   └── Vuelo.java
│   ├── gui/             # Interfaz grafica Swing (nueva)
│   │   ├── MainGUI.java           <-- Entrada por GUI
│   │   ├── VentanaPrincipal.java  <-- Ventana principal
│   │   ├── UtilidadesUI.java      <-- Estilos y utilidades
│   │   ├── PanelRegistroAeropuerto.java
│   │   ├── PanelRegistroVuelo.java
│   │   ├── PanelRegistroAvion.java
│   │   ├── PanelRegistroPiloto.java
│   │   ├── PanelRegistroAzafata.java
│   │   ├── PanelRegistroPasajero.java
│   │   ├── PanelAsignaciones.java
│   │   └── PanelConsultas.java
│   └── module-info.java
└── README.md
```

## Dos Modos de Uso

### 1. Modo Consola (original)

Ejecutar la clase `clases.main` para usar el sistema por linea de comandos.

```bash
javac src/clases/*.java
java clases.main
```

Muestra un menu numerado en la terminal para gestionar aeropuertos, vuelos, aviones, tripulacion y pasajeros.

### 2. Modo Interfaz Grafica (nueva)

Ejecutar la clase `gui.MainGUI` para lanzar la aplicacion con ventanas.

```bash
javac src/gui/*.java src/clases/*.java
java gui.MainGUI
```

O compilar todo junto:

```bash
javac src/**/*.java
java gui.MainGUI
```

## Funcionalidades

El sistema permite realizar las siguientes operaciones:

| Opcion | Descripcion |
|--------|-------------|
| Registrar aeropuerto | Crea un aeropuerto con sus pistas |
| Registrar vuelo | Registra un vuelo con origen, destino, horarios y costo |
| Registrar avion | Registra un avion con modelo, marca y capacidad |
| Registrar piloto | Registra un piloto con datos personales y licencia |
| Registrar azafata | Registra una azafata con datos personales e idioma |
| Registrar pasajero | Registra un pasajero con datos personales y pasaporte |
| Asignar avion a vuelo | Vincula un avion registrado a un vuelo |
| Asignar piloto a vuelo | Vincula un piloto registrado a un vuelo |
| Asignar azafatas a vuelo | Vincula dos azafatas a un vuelo |
| Agregar vuelo a aeropuerto | Vincula un vuelo a un aeropuerto |
| Agregar pasajero a vuelo | Agrega un pasajero a un vuelo dentro de un aeropuerto |
| Mostrar aeropuerto | Muestra todos los datos de un aeropuerto |
| Mostrar vuelo | Muestra los detalles de un vuelo especifico |
| Calcular duracion | Calcula la duracion de un vuelo |
| Calcular retraso | Determina si un vuelo tiene retraso |
| Eliminar vuelo | Elimina un vuelo de un aeropuerto |

## Interfaz Grafica

La interfaz esta desarrollada con **Java Swing** siguiendo un diseno **minimalista**:

- **Colores neutros**: tonos de gris y blanco, sin distracciones visuales
- **Menu lateral**: navegacion clara entre las diferentes secciones
- **Paneles modulares**: cada funcionalidad en su propia clase separada
- **Diseno limpio**: sin elementos decorativos innecesarios

### Navegacion
1. Seleccione una opcion del menu lateral (Aeropuerto, Vuelo, Avion, Piloto, Azafata, Pasajero)
2. Complete los campos del formulario
3. Presione el boton de registro
4. Use "Asignaciones" para vincular entidades entre si
5. Use "Consultas" para ver informacion, calcular duraciones o eliminar vuelos

## Notas

- Los datos se almacenan en memoria durante la ejecucion.
- La logica de negocio en `clases/` no fue modificada; la interfaz grafica la reutiliza tal cual.
- Los archivos `.dat` en `src/datos/` estan reservados para futura persistencia.
