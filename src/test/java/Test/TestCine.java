/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Test;

import Controlador.AppCine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Controlador.ControladorCine;
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
public class TestCine {

    private Cine cine;
    private CineGUI vista;
    private ControladorCine controlador;

    @BeforeEach
    public void setUp() {
        cine = new Cine();
        vista = new CineGUI();
        controlador = new ControladorCine(cine, vista);
    }

    // unitarios

    @Test
    public void testCargaInicialCine() {
        assertEquals(4, cine.getFunciones().size(), "Debe haber 4 funciones iniciales.");
        assertEquals(4, cine.getProductosMenu().size(), "Debe haber 4 productos de menú iniciales.");
    }

    @Test
    public void testRegistrarClienteNuevo() {
        Cliente c = cine.registrarOActualizarCliente("Juan Pérez", "123", "3001234567");

        assertEquals("Juan Pérez", c.getNombre());
        assertEquals("123", c.getDocumento());
        assertEquals("3001234567", c.getTelefono());

        Cliente buscado = cine.buscarClientePorDocumento("123");
        assertNotNull(buscado, "El cliente debería estar registrado en la lista.");
        assertEquals("Juan Pérez", buscado.getNombre());
    }

    @Test
    public void testRegistrarClienteActualizaTelefono() {
        Cliente c1 = cine.registrarOActualizarCliente("Ana Gómez", "456", "3110000000");

        Cliente c2 = cine.registrarOActualizarCliente("Ana Gómez", "456", "3209999999");

        assertSame(c1, c2, "Debe ser el mismo objeto cliente (actualizado, no uno nuevo).");
        assertEquals("3209999999", c2.getTelefono(), "El teléfono debe haberse actualizado.");
    }

    @Test
    public void testReservarAsientosExitoso() {
        FuncionCine funcion = cine.buscarFuncionPorCodigo("F1");
        assertNotNull(funcion, "La función F1 debe existir.");

        int antes = funcion.getAsientosDisponibles();
        boolean reservado = funcion.reservarAsientos(5);

        assertTrue(reservado, "La reserva de asientos debería ser exitosa.");
        assertEquals(antes - 5, funcion.getAsientosDisponibles(),
                "Los asientos disponibles deben disminuir.");
    }

    @Test
    public void testReservarAsientosInsuficientes() {
        FuncionCine funcion = cine.buscarFuncionPorCodigo("F2");
        assertNotNull(funcion, "La función F2 debe existir.");

        int capacidad = funcion.getCapacidad();
        int disponiblesAntes = funcion.getAsientosDisponibles();

        boolean reservado = funcion.reservarAsientos(capacidad + 1);

        assertFalse(reservado, "No debería permitir reservar más asientos de los disponibles.");
        assertEquals(disponiblesAntes, funcion.getAsientosDisponibles(),
                "Los asientos disponibles no deben cambiar si la reserva falla.");
    }

    @Test
    public void testCalcularTotalReserva() {
        FuncionCine funcion = cine.buscarFuncionPorCodigo("F1");
        assertNotNull(funcion);

        Cliente cliente = cine.registrarOActualizarCliente("Carlos", "999", "3150000000");

        ArrayList<ProductoMenu> productos = new ArrayList<>();
        productos.add(cine.obtenerProductosPorCodigos(new ArrayList<String>() {{
            add("M1");
        }}).get(0));

        String codigoReserva = cine.generarCodigoReserva();
        Reserva reserva = cine.crearReserva(codigoReserva, cliente, funcion, 2, productos);

        double total = reserva.calcularTotal();

        assertEquals(42000.0, total, 0.001,
                "El total calculado de la reserva no es el esperado.");
    }

    @Test
    public void testCrearReservaSinAsientosLanzaExcepcion() {
        FuncionCine funcion = cine.buscarFuncionPorCodigo("F3");
        assertNotNull(funcion);

        int disponibles = funcion.getAsientosDisponibles();
        boolean lanzoExcepcion = false;

        try {
            ArrayList<String> codigosProductos = new ArrayList<>();
            controlador.crearReservaDesdeVista("María", "777", "3000000000",
                    "F3", disponibles + 1, codigosProductos);
        } catch (IllegalArgumentException ex) {
            lanzoExcepcion = true;
        }

        assertTrue(lanzoExcepcion,
                "Debe lanzar IllegalArgumentException cuando no hay asientos suficientes.");
    }

    // integración

    @Test
    public void testCrearReservaDesdeVistaIntegraModeloYControlador() {
        FuncionCine funcion = cine.buscarFuncionPorCodigo("F1");
        assertNotNull(funcion);

        int asientosAntes = funcion.getAsientosDisponibles();

        ArrayList<String> codigosProductos = new ArrayList<>();
        codigosProductos.add("M1");
        codigosProductos.add("M2");

        Reserva reserva = controlador.crearReservaDesdeVista(
                "Ana", "555", "3111111111",
                "F1", 2, codigosProductos);

        assertNotNull(reserva, "La reserva no debería ser nula.");
        assertEquals("555", reserva.getCliente().getDocumento());
        assertEquals("F1", reserva.getFuncion().getCodigo());
        assertEquals(2, reserva.getCantidadBoletos());
        assertEquals(2, reserva.getProductos().size());

        int asientosDespues = funcion.getAsientosDisponibles();
        assertEquals(asientosAntes - 2, asientosDespues,
                "Los asientos disponibles deben disminuir según la cantidad de boletos.");
    }

    @Test
    public void testEliminarReservaDesdeVistaLiberaAsientos() {
        FuncionCine funcion = cine.buscarFuncionPorCodigo("F2");
        assertNotNull(funcion);

        int asientosIniciales = funcion.getAsientosDisponibles();

        ArrayList<String> codigosProductos = new ArrayList<>();
        Reserva reserva = controlador.crearReservaDesdeVista(
                "Luis", "888", "3200000000",
                "F2", 3, codigosProductos);

        int asientosDurante = funcion.getAsientosDisponibles();
        assertEquals(asientosIniciales - 3, asientosDurante,
                "Después de crear la reserva, los asientos disponibles deben disminuir.");

        boolean eliminado = controlador.eliminarReservaDesdeVista(reserva.getCodigoReserva());
        assertTrue(eliminado, "La reserva debería eliminarse correctamente.");

        int asientosFinales = funcion.getAsientosDisponibles();
        assertEquals(asientosIniciales, asientosFinales,
                "Al eliminar la reserva, los asientos disponibles deben volver a su valor inicial.");
    }

    @Test
    public void testGenerarResumenReserva() {
        ArrayList<String> codigosProductos = new ArrayList<>();
        codigosProductos.add("M1");

        Reserva reserva = controlador.crearReservaDesdeVista(
                "Sofía", "999", "3009999999",
                "F1", 1, codigosProductos);

        String resumen = controlador.generarResumenReserva(reserva);

        assertTrue(resumen.contains("Código de reserva: " + reserva.getCodigoReserva()),
                "El resumen debe incluir el código de la reserva.");
        assertTrue(resumen.contains("--- ROL: CLIENTE ---"),
                "El resumen debe incluir los datos del cliente.");
        assertTrue(resumen.contains("TOTAL A PAGAR"),
                "El resumen debe incluir el total a pagar.");
    }
}
