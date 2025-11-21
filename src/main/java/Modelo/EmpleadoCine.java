/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public class EmpleadoCine extends Persona {
    private String cargo;
    private double salarioPorHora;
    private int horasTrabajadasMes;

    public EmpleadoCine(String nombre, String documento, String telefono,
                        String cargo, double salarioPorHora) {
        super(nombre, documento, telefono);
        this.cargo = cargo;
        this.salarioPorHora = salarioPorHora;
        this.horasTrabajadasMes = 0;
    }

    public void registrarHoras(int horas) {
        if (horas > 0) {
            this.horasTrabajadasMes += horas;
        }
    }

    public double calcularSalarioMensual() {
        double salarioBase = horasTrabajadasMes * salarioPorHora;
        double bonificacion = salarioBase * 0.15;
        return salarioBase + bonificacion;
    }

    @Override
    public String obtenerRol() {
        return "Empleado";
    }

    @Override
    public String toString() {
        return "--- ROL: EMPLEADO ---\n"
             + super.toString() + "\n"
             + "Cargo: " + cargo + "\n"
             + "Salario mensual: $" + String.format("%.2f", calcularSalarioMensual());
    }
}

