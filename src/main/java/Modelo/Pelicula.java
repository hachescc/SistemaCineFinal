/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public class Pelicula {
    private String codigo;
    private String titulo;
    private String genero;
    private int duracionMinutos;
    private String clasificacion;
    private double precioBaseEntrada;

    public Pelicula(String codigo, String titulo, String genero,
                    int duracionMinutos, String clasificacion, double precioBaseEntrada) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.precioBaseEntrada = precioBaseEntrada;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getPrecioBaseEntrada() {
        return precioBaseEntrada;
    }

    @Override
    public String toString() {
        return titulo + " (" + clasificacion + ", " + duracionMinutos + " min)";
    }
}
