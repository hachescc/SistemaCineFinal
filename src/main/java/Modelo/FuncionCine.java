/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public class FuncionCine {
    private String codigo;
    private Pelicula pelicula;
    private String sala;
    private String horario;
    private int capacidad;
    private int asientosOcupados;

    public FuncionCine(String codigo, Pelicula pelicula, String sala,
                       String horario, int capacidad) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.horario = horario;
        this.capacidad = capacidad;
        this.asientosOcupados = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public String getSala() {
        return sala;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getAsientosOcupados() {
        return asientosOcupados;
    }

    public int getAsientosDisponibles() {
        return capacidad - asientosOcupados;
    }

    public boolean reservarAsientos(int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        if (cantidad <= getAsientosDisponibles()) {
            asientosOcupados += cantidad;
            return true;
        }
        return false;
    }

    public void liberarAsientos(int cantidad) {
        if (cantidad > 0 && cantidad <= asientosOcupados) {
            asientosOcupados -= cantidad;
        }
    }

    @Override
    public String toString() {
    String titulo = pelicula.getTitulo();
    if (codigo.equals("F2")) {
        titulo = titulo + " (3D)";
    }

    
    return codigo + " - " + titulo
           + " | Sala " + sala
           + " | " + horario
           + " | Disp: " + getAsientosDisponibles() + "/" + capacidad;
    }
}

