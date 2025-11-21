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

public class Reserva {
    private String codigoReserva;
    private Cliente cliente;
    private FuncionCine funcion;
    private int cantidadBoletos;
    private ArrayList<ProductoMenu> productos;

    public Reserva(String codigoReserva, Cliente cliente, FuncionCine funcion,
                   int cantidadBoletos, ArrayList<ProductoMenu> productos) {
        this.codigoReserva = codigoReserva;
        this.cliente = cliente;
        this.funcion = funcion;
        this.cantidadBoletos = cantidadBoletos;
        this.productos = productos;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public FuncionCine getFuncion() {
        return funcion;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    public ArrayList<ProductoMenu> getProductos() {
        return productos;
    }

    public double calcularTotal() {
        double total = cantidadBoletos * funcion.getPelicula().getPrecioBaseEntrada();
        for (ProductoMenu p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    @Override
    public String toString() {
        String resumen = "=== RESUMEN RESERVA ===\n";
        resumen += "Código de reserva: " + codigoReserva + "\n";
        resumen += cliente.toString() + "\n\n";
        resumen += "Función: " + funcion.toString() + "\n";
        resumen += "Boletos: " + cantidadBoletos + "\n";
        resumen += "Productos:\n";
        if (productos.isEmpty()) {
            resumen += "  (Sin productos de menú)\n";
        } else {
            for (ProductoMenu p : productos) {
                resumen += "  - " + p.toString() + "\n";
            }
        }
        resumen += "TOTAL A PAGAR: $" + String.format("%.2f", calcularTotal()) + "\n";
        return resumen;
    }
}