/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gianni
 */
public class CitaHistoria {
    private String codigoCita;
    private String idPaciente;
    private String especialidadMedico;
    private String idMedico;
    private String fecha;
    private String dia;
    private String hora;
    private String nombreDoctor;
    private String especialidadDoctor;

    public CitaHistoria(String codigoCita, String idPaciente, String especialidadMedico, String idMedico, String fecha, String dia, String hora, String nombreDoctor) {
        this.codigoCita = codigoCita;
        this.idPaciente = idPaciente;
        this.especialidadMedico = especialidadMedico;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.dia = dia;
        this.hora = hora;
        this.nombreDoctor = nombreDoctor;
        this.especialidadDoctor = especialidadDoctor;
    }

    // Getters y setters
    public String getCodigoCita() {
        return codigoCita;
    }

    public void setCodigoCita(String codigoCita) {
        this.codigoCita = codigoCita;
    }

        public String getIdPaciente() {
            return idPaciente;
        }

        public void setIdPaciente(String idPaciente) {
            this.idPaciente = idPaciente;
        }

        public String getEspecialidadMedico() {
            return especialidadMedico;
        }

        public void setEspecialidadMedico(String especialidadMedico) {
            this.especialidadMedico = especialidadMedico;
        }

        public String getIdMedico() {
            return idMedico;
        }

        public void setIdMedico(String idMedico) {
            this.idMedico = idMedico;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getNombreDoctor() {
            return nombreDoctor;
        }

        public void setNombreDoctor(String nombreDoctor) {
            this.nombreDoctor = nombreDoctor;
        }

        public String getEspecialidadDoctor() {
            return especialidadDoctor;
        }

        public void setEspecialidadDoctor(String especialidadDoctor) {
            this.especialidadDoctor = especialidadDoctor;
        }
    

}
