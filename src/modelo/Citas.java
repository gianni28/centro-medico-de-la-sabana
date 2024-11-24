/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author Gianni
 */
public class Citas {
    private String codigoCita;
    private String identificacionPac;
    private String especialidadDoc;
    private String identificacionDoc;
    private String fechaCita;
    private String diaCita;
    private String horaCita;

    public Citas() {
        this.codigoCita = "";
        this.identificacionPac = "";
        this.especialidadDoc = "";
        this.identificacionDoc = "";
        this.fechaCita = "";
        this.diaCita = "";
        this.horaCita = "";
    }

    public Citas(String codigoCita, String identificacionPac, String especialidadDoc, String identificacionDoc, String fechaCita, String diaCita, String horaCita) {
        this.codigoCita = codigoCita;
        this.identificacionPac = identificacionPac;
        this.especialidadDoc = especialidadDoc;
        this.identificacionDoc = identificacionDoc;
        this.fechaCita = fechaCita;
        this.diaCita = diaCita;
        this.horaCita = horaCita;
    }

    public String getCodigoCita() {
        return codigoCita;
    }

    public void setCodigoCita(String codigoCita) {
        this.codigoCita = codigoCita;
    }

    public String getIdentificacionPac() {
        return identificacionPac;
    }

    public void setIdentificacionPac(String identificacionPac) {
        this.identificacionPac = identificacionPac;
    }

    public String getEspecialidadDoc() {
        return especialidadDoc;
    }

    public void setEspecialidadDoc(String especialidadDoc) {
        this.especialidadDoc = especialidadDoc;
    }

    public String getIdentificacionDoc() {
        return identificacionDoc;
    }

    public void setIdentificacionDoc(String identificacionDoc) {
        this.identificacionDoc = identificacionDoc;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getDiaCita() {
        return diaCita;
    }

    public void setDiaCita(String diaCita) {
        this.diaCita = diaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    @Override
    public String toString() {
        return codigoCita + "," + identificacionPac + "," + especialidadDoc + "," + identificacionDoc + "," + fechaCita + "," + diaCita + "," + horaCita;
    }

    
}
