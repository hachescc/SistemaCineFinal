/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author hugo alejandro chavarro y juan david villegas
 */

public class Cliente extends Persona {

    public Cliente(String nombre, String documento, String telefono) {
        super(nombre, documento, telefono);
    }

    @Override
    public String obtenerRol() {
        return "Cliente";
    }

    @Override
    public String toString() {
        return "--- ROL: CLIENTE ---\n" + super.toString();
    }
}

