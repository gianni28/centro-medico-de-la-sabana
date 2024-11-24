/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gianni
 */
public class Disponibilidad {
    
    private String codigoDispo;
    private String identificacionDoc;
    private String dia;
    private String desde;
    private String hasta;

    public Disponibilidad() {
        this.codigoDispo = "";
        this.identificacionDoc = "";
        this.dia = "";
        this.desde = "";
        this.hasta = "";
    }
 
    public Disponibilidad(String codigoDispo, String identificacionDoc, String dia, String desde, String hasta) {
        this.codigoDispo = codigoDispo;
        this.identificacionDoc = identificacionDoc;
        this.dia = dia;
        this.desde = desde;
        this.hasta = hasta;
    }

    public String getCodigoDispo() {
        return codigoDispo;
    }

    public void setCodigoDispo(String codigoDispo) {
        this.codigoDispo = codigoDispo;
    }

    public String getIdentificacionDoc() {
        return identificacionDoc;
    }

    public void setIdentificacionDoc(String identificacionDoc) {
        this.identificacionDoc = identificacionDoc;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    @Override
    public String toString() {
        return this.codigoDispo + "," + this.identificacionDoc + "," + this.dia + "," + this.desde + "," + this.hasta;
    }
   
    
}
