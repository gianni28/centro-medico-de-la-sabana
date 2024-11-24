/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Random;

/**
 *
 * @author Gianni
 */
public class Especialidad {

    private String nombre;
    private String codigo;

    public Especialidad() {
        this.nombre = "";
        generarCodigo();
    }

    public Especialidad(String nombre) {
        this.nombre = nombre;
        generarCodigo();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    private void generarCodigo() {
        String codigo = "ESP";
        Random random = new Random();
        int numero = random.nextInt(10000);
        codigo += String.format("%04d", numero);
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
