/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public class Cine {
    private ArrayList<Pelicula> peliculas;
    private ArrayList<FuncionCine> funciones;
    private ArrayList<ProductoMenu> productosMenu;
    private ArrayList<Cliente> clientes;
    private ArrayList<Reserva> reservas;
    private int contadorReservas;

    public Cine() {
        peliculas = new ArrayList<Pelicula>();
        funciones = new ArrayList<FuncionCine>();
        productosMenu = new ArrayList<ProductoMenu>();
        clientes = new ArrayList<Cliente>();
        reservas = new ArrayList<Reserva>();
        contadorReservas = 1;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        Pelicula peli1 = new Pelicula("P1", "La Aventura Espacial",
                "Ciencia ficción", 120, "PG-13", 15000);
        Pelicula peli2 = new Pelicula("P2", "Risas en Familia",
                "Comedia", 95, "TP", 12000);
        Pelicula peli3 = new Pelicula("P3", "Terror en la Noche",
                "Terror", 110, "+15", 16000);

        peliculas.add(peli1);
        peliculas.add(peli2);
        peliculas.add(peli3);

        funciones.add(new FuncionCine("F1", peli1, "1", "3:00 PM", 50));
        funciones.add(new FuncionCine("F2", peli1, "2", "8:00 PM", 40));
        funciones.add(new FuncionCine("F3", peli2, "3", "5:00 PM", 35));
        funciones.add(new FuncionCine("F4", peli3, "4", "10:00 PM", 30));

        productosMenu.add(new ProductoMenu("M1", "Palomitas grandes", 12000, "Comida"));
        productosMenu.add(new ProductoMenu("M2", "Gaseosa 22 oz", 8000, "Bebida"));
        productosMenu.add(new ProductoMenu("M3", "Perro caliente", 15000, "Comida"));
        productosMenu.add(new ProductoMenu("M4", "Combo Nachos", 18000, "Comida"));
    }

    public ArrayList<FuncionCine> getFunciones() {
        return funciones;
    }

    public ArrayList<ProductoMenu> getProductosMenu() {
        return productosMenu;
    }

    public Cliente registrarOActualizarCliente(String nombre, String documento, String telefono) {
        Cliente existente = buscarClientePorDocumento(documento);
        if (existente != null) {
            existente.setTelefono(telefono);
            return existente;
        }
        Cliente nuevo = new Cliente(nombre, documento, telefono);
        clientes.add(nuevo);
        return nuevo;
    }

    public Cliente buscarClientePorDocumento(String documento) {
        for (Cliente c : clientes) {
            if (c.getDocumento().equals(documento)) {
                return c;
            }
        }
        return null;
    }

    public FuncionCine buscarFuncionPorCodigo(String codigo) {
        for (FuncionCine f : funciones) {
            if (f.getCodigo().equals(codigo)) {
                return f;
            }
        }
        return null;
    }

    public ArrayList<ProductoMenu> obtenerProductosPorCodigos(ArrayList<String> codigosSeleccionados) {
        ArrayList<ProductoMenu> resultado = new ArrayList<ProductoMenu>();
        for (String cod : codigosSeleccionados) {
            for (ProductoMenu p : productosMenu) {
                if (p.getCodigo().equals(cod)) {
                    resultado.add(p);
                }
            }
        }
        return resultado;
    }

    public String generarCodigoReserva() {
        String codigo = "R" + contadorReservas;
        contadorReservas++;
        return codigo;
    }

    public Reserva crearReserva(String codigoReserva, Cliente cliente, FuncionCine funcion,
                                int cantidadBoletos, ArrayList<ProductoMenu> productosSeleccionados) {
        if (funcion == null) {
            throw new IllegalArgumentException("La función seleccionada no existe.");
        }
        boolean reservado = funcion.reservarAsientos(cantidadBoletos);
        if (!reservado) {
            throw new IllegalArgumentException("No hay suficientes asientos disponibles.");
        }
        ArrayList<ProductoMenu> copiaProductos = new ArrayList<ProductoMenu>(productosSeleccionados);
        Reserva reserva = new Reserva(codigoReserva, cliente, funcion, cantidadBoletos, copiaProductos);
        reservas.add(reserva);
        return reserva;
    }

    public Reserva buscarReservaPorCodigo(String codigoReserva) {
        for (Reserva r : reservas) {
            if (r.getCodigoReserva().equals(codigoReserva)) {
                return r;
            }
        }
        return null;
    }

    public boolean eliminarReserva(String codigoReserva) {
        Reserva r = buscarReservaPorCodigo(codigoReserva);
        if (r != null) {
            FuncionCine funcion = r.getFuncion();
            funcion.liberarAsientos(r.getCantidadBoletos());
            reservas.remove(r);
            return true;
        }
        return false;
    }

    public String generarResumenReserva(Reserva reserva) {
        return reserva.toString();
    }
}