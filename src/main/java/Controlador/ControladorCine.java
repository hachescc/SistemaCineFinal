/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Cine;
import Modelo.Cliente;
import Modelo.FuncionCine;
import Modelo.ProductoMenu;
import Modelo.Reserva;
import Vista.CineGUI;
import java.util.ArrayList;

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public class ControladorCine {

    private Cine cine;
    private CineGUI vista;

    public ControladorCine(Cine cine, CineGUI vista) {
        this.cine = cine;
        this.vista = vista;
        this.vista.setControlador(this);
        cargarFuncionesEnVista();
        cargarMenuEnVista();
    }

    private void cargarFuncionesEnVista() {
        ArrayList<FuncionCine> funciones = cine.getFunciones();
        vista.mostrarFunciones(funciones);
    }

    private void cargarMenuEnVista() {
        ArrayList<ProductoMenu> productos = cine.getProductosMenu();
        vista.mostrarMenu(productos);
    }
    
    public int obtenerDisponibilidadFuncion(String codigoFuncion) {
    // Buscar la función en el modelo Cine
    FuncionCine funcion = cine.buscarFuncionPorCodigo(codigoFuncion);

    if (funcion != null) {
        return funcion.getAsientosDisponibles();
    }

    // Si no se encontró, devolvemos 0
    return 0;
}
    public int obtenerAsientosDisponibles(String codigoFuncion) {
        FuncionCine funcion = cine.buscarFuncionPorCodigo(codigoFuncion);
        if (funcion != null) {
            return funcion.getAsientosDisponibles();
        }
        return 0;
    }

    public Reserva crearReservaDesdeVista(String nombre, String documento, String telefono,
                                          String codigoFuncion, int cantidadBoletos,
                                          ArrayList<String> codigosProductosSeleccionados) {
        Cliente cliente = cine.registrarOActualizarCliente(nombre, documento, telefono);
        ArrayList<ProductoMenu> productos = cine.obtenerProductosPorCodigos(codigosProductosSeleccionados);
        String codigoReserva = cine.generarCodigoReserva();
        Reserva reserva = cine.crearReserva(
                codigoReserva,
                cliente,
                cine.buscarFuncionPorCodigo(codigoFuncion),
                cantidadBoletos,
                productos
        );
        return reserva;
    }

    public String generarResumenReserva(Reserva reserva) {
        return cine.generarResumenReserva(reserva);
    }

    public boolean eliminarReservaDesdeVista(String codigoReserva) {
        return cine.eliminarReserva(codigoReserva);
    }

    public String actualizarTelefonoCliente(String documento, String nuevoTelefono) {
        Cliente cliente = cine.buscarClientePorDocumento(documento);
        if (cliente != null) {
            cliente.setTelefono(nuevoTelefono);
            return "Teléfono actualizado correctamente para el cliente " + cliente.getNombre();
        }
        return "No se encontró un cliente con ese documento.";
    }
}

