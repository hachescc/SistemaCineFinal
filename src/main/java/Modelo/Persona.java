/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo; 

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public abstract class Persona {
    protected String nombre;
    protected String documento;
    protected String telefono;

    public Persona(String nombre, String documento, String telefono) {
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public abstract String obtenerRol();

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n"
             + "Documento: " + documento + "\n"
             + "Teléfono: " + telefono;
    }
}