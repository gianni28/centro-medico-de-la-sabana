/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gianni
 */
public class Pacientes {
    private String identificacion;
    private String nombre;
    private String apellido;
    private String genero;
    private String fechanacimiento;
    private String telefono;

    public Pacientes(String trim, String trim1, String trim2, String trim3, String trim4, String trim5, String trim6) {
        this.identificacion = "";
        this.nombre = "";
        this.apellido = "";
        this.genero = "";
        this.fechanacimiento = "";
        this.telefono = "";
    }

    public Pacientes(String identificacion, String nombre, String apellido, String genero, String fechanacimiento, String telefono) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.fechanacimiento = fechanacimiento;
        this.telefono = telefono;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return identificacion + "," + nombre + "," + apellido + "," + genero + "," + fechanacimiento + "," + telefono;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pacientes other = (Pacientes) obj;
        return identificacion.equals(other.identificacion);
    }

}
