/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gianni
 */
public class Medicos {
    private String identificacion;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String especialidad;
    private String nombreFoto;

    public Medicos() {
        this.identificacion = "";
        this.nombre = "";
        this.apellido = "";
        this.email = "";
        this.telefono = "";
        this.especialidad = "";
        this.nombreFoto = "";
    }

    public Medicos(String identificacion, String nombre, String apellido, String email, String telefono, String especialidad, String nombreFoto) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.nombreFoto = nombreFoto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    @Override
    public String toString() {
        return this.identificacion + "," + this.nombre + "," + this.apellido + "," + this.email + "," + this.telefono + "," + this.especialidad + "," + this.nombreFoto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Medicos other = (Medicos) obj;
        return identificacion.equals(other.identificacion);
    }
      
}
